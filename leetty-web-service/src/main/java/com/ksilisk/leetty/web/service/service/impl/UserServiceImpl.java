package com.ksilisk.leetty.web.service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.leetty.common.codegen.client.MatchedUserGraphQLQuery;
import com.ksilisk.leetty.common.codegen.client.MatchedUserProjectionRoot;
import com.ksilisk.leetty.common.codegen.client.UserProfileUserQuestionProgressV2GraphQLQuery;
import com.ksilisk.leetty.common.codegen.client.UserProfileUserQuestionProgressV2ProjectionRoot;
import com.ksilisk.leetty.common.codegen.types.QuestionCount;
import com.ksilisk.leetty.common.codegen.types.UserQuestionProgress;
import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.common.user.UserProfile;
import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.ksilisk.leetty.web.service.entity.User;
import com.ksilisk.leetty.web.service.exception.type.EntityNotFoundException.UserNotFountException;
import com.ksilisk.leetty.web.service.exception.type.LeetCodeUserNotFoundException;
import com.ksilisk.leetty.web.service.exception.type.LeettyRequestValidationException;
import com.ksilisk.leetty.web.service.repository.UserRepository;
import com.ksilisk.leetty.web.service.service.UserService;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import com.netflix.graphql.dgs.client.codegen.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final ObjectMapper OM = new ObjectMapper().setSerializationInclusion(NON_NULL);

    private final UserRepository userRepository;
    private final GraphQLLeetCodeClient leetCodeClient;

    @Override
    @Transactional
    public void putUser(UserDto userDto) {
        User newUser = OM.convertValue(userDto, User.class);
        userRepository.save(newUser);
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFountException::new);
        return OM.convertValue(user, UserDto.class);
    }

    @Override
    @Transactional
    public void patchUser(UserDto userDto) {
        if (userDto.userId() == null) {
            throw new LeettyRequestValidationException("User Id should be provided");
        }
        User existsUser = userRepository.findById(userDto.userId())
                .orElseThrow(UserNotFountException::new);
        existsUser.patch(userDto);
        userRepository.save(existsUser);
    }

    @Override
    public UserProfile getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFountException::new);
        GraphQLQuery matchedUserQuery =
                MatchedUserGraphQLQuery.newRequest().username(user.getLeetcodeUsername()).build();
        BaseSubProjectionNode<?, ?> matchedUserProjectionNode = new MatchedUserProjectionRoot<>()
                .username().githubUrl().twitterUrl().email().linkedinUrl()
                .profile().realName().aboutMe().countryName().websites().school().company().jobTitle().ranking();
        com.ksilisk.leetty.common.codegen.types.User userProfile = leetCodeClient.execute(
                        matchedUserQuery, matchedUserProjectionNode, com.ksilisk.leetty.common.codegen.types.User.class)
                .orElseThrow(LeetCodeUserNotFoundException::new);

        GraphQLQuery questionsProgressQuery =
                UserProfileUserQuestionProgressV2GraphQLQuery.newRequest().userSlug(user.getLeetcodeUsername()).build();
        BaseSubProjectionNode<?, ?> questionsProgressProjectionNode =
                new UserProfileUserQuestionProgressV2ProjectionRoot<>().numAcceptedQuestions().count();
        UserQuestionProgress userQuestionProgress =
                leetCodeClient.execute(questionsProgressQuery, questionsProgressProjectionNode, UserQuestionProgress.class)
                        .orElseThrow(LeetCodeUserNotFoundException::new);
        return prepareUserProfileResponse(userProfile, userQuestionProgress);
    }

    private UserProfile prepareUserProfileResponse(com.ksilisk.leetty.common.codegen.types.User userProfileInfo,
                                                   UserQuestionProgress userQuestionProgress) {
        String workInfo = null;
        if (userProfileInfo.getProfile().getCompany() != null && userProfileInfo.getProfile().getJobTitle() != null) {
            workInfo = userProfileInfo.getProfile().getCompany() + " | " + userProfileInfo.getProfile().getJobTitle();
        }
        int countSolvedQuestions = userQuestionProgress.getNumAcceptedQuestions()
                .stream()
                .mapToInt(QuestionCount::getCount)
                .sum();
        return new UserProfile(
                userProfileInfo.getProfile().getRealName(),
                userProfileInfo.getUsername(),
                userProfileInfo.getProfile().getRanking(),
                countSolvedQuestions,
                userProfileInfo.getProfile().getCountryName(),
                userProfileInfo.getLinkedinUrl(),
                userProfileInfo.getTwitterUrl(),
                userProfileInfo.getGithubUrl(),
                workInfo,
                userProfileInfo.getProfile().getSchool()
        );
    }
}

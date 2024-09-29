package com.ksilisk.leetty.common.user;

public record UserProfile(String name, String username, Integer rank, Integer solvedQuestions, String country,
                          String linkedInProfile, String twitterProfile, String githubProfile, String work,
                          String education) {
}

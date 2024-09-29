package com.ksilisk.leetty.telegram.bot.util.impl;

import com.ksilisk.leetty.telegram.bot.exception.type.LeetCodeValidationUrlException;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeUrlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URL;

@Slf4j
@Component
public class LeetCodeUrlParserImpl implements LeetCodeUrlParser {
    private static final String HTTPS_PROTOCOL_PREFIX = "https://";
    private static final String HTTP_PROTOCOL_PREFIX = "http://";
    private static final String LEETCODE_HOST = "leetcode.com";


    @Override
    public String parseTitleSlug(String url) {
        String[] urlPath = validateAndGetUrlPath(url);
        if (urlPath.length <= 2 || !StringUtils.hasText(urlPath[2])) {
            throw new LeetCodeValidationUrlException("Incorrect url path to title slug");
        }
        return urlPath[2];
    }

    @Override
    public String parseUsername(String url) {
        String[] urlPath = validateAndGetUrlPath(url);
        if (urlPath.length <= 2 || !"u".equals(urlPath[1])) {
            throw new LeetCodeValidationUrlException("Incorrect url path to username");
        }
        return urlPath[2].toLowerCase();
    }

    private String[] validateAndGetUrlPath(String url) {
        try {
            if (!StringUtils.hasText(url)) {
                throw new LeetCodeValidationUrlException("Url should has text");
            }
            if (!url.startsWith(HTTPS_PROTOCOL_PREFIX) && !url.startsWith(HTTP_PROTOCOL_PREFIX)) {
                url = HTTPS_PROTOCOL_PREFIX + url;
            }
            URL leetCodeUrl = new URL(url);
            if (!leetCodeUrl.getHost().equals(LEETCODE_HOST)) {
                throw new LeetCodeValidationUrlException("Incorrect leetcode host");
            }
            return leetCodeUrl.getFile().split("/");

        } catch (LeetCodeValidationUrlException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LeetCodeValidationUrlException("Incorrect LeetCode url provided", ex);
        }
    }
}

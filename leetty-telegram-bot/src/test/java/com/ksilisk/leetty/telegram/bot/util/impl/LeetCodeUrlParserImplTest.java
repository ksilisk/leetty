package com.ksilisk.leetty.telegram.bot.util.impl;

import com.ksilisk.leetty.telegram.bot.exception.type.LeetCodeValidationUrlException;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeUrlParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class LeetCodeUrlParserImplTest {
    final LeetCodeUrlParser leetCodeUrlParser = new LeetCodeUrlParserImpl();

    @Test
    void validUrlWithoutProtocolPrefix_shouldReturnTitleSlug() {
        // given
        String testSlug = "test";
        String url = "leetcode.com/problem/" + testSlug;
        // when
        String titleSlug = leetCodeUrlParser.getTitleSlug(url);
        // then
        assertEquals(testSlug, titleSlug);
    }

    @Test
    void validUrlWithProtocolHTTPS_shouldReturnTitleSlug() {
        // given
        String testSlug = "test";
        String url = "https://leetcode.com/problem/" + testSlug;
        // when
        String titleSlug = leetCodeUrlParser.getTitleSlug(url);
        // then
        assertEquals(testSlug, titleSlug);
    }

    @Test
    void validUrlWithProtocolHTTP_shouldReturnTitleSlug() {
        // given
        String testSlug = "test";
        String url = "http://leetcode.com/problem/" + testSlug;
        // when
        String titleSlug = leetCodeUrlParser.getTitleSlug(url);
        // then
        assertEquals(testSlug, titleSlug);
    }

    @Test
    void validUrlWithProtocolAndAnotherDataOnPrefix_shouldReturnTitleSlug() {
        // given
        String testSlug = "test";
        String url = "http://leetcode.com/problem/" + testSlug + "/fjsjj/sdfjksdajfk/sdjfksajdfksja/sdjfkdasjkf";
        // when
        String titleSlug = leetCodeUrlParser.getTitleSlug(url);
        // then
        assertEquals(testSlug, titleSlug);
    }

    @Test
    void invalidEmptyUrl_shouldThrowException() {
        // given
        String url = "";
        // then
        assertThrowsExactly(LeetCodeValidationUrlException.class, () -> leetCodeUrlParser.getTitleSlug(url));
    }

    @Test
    void invalidUrlWithAnotherHost_shouldThrowException() {
        // given
        String testSlug = "test";
        String url = "http://leetcodef.com/problem/" + testSlug;
        // then
        assertThrowsExactly(LeetCodeValidationUrlException.class, () -> leetCodeUrlParser.getTitleSlug(url));
    }

    @Test
    void invalidUrlWithInvalidProtocol_shouldThrowException() {
        // given
        String testSlug = "test";
        String url = "httssp://leetcode.com/problem/" + testSlug;
        // then
        assertThrowsExactly(LeetCodeValidationUrlException.class, () -> leetCodeUrlParser.getTitleSlug(url));
    }

    @Test
    void invalidUrlWithRandomSymbols_shouldThrowException() {
        // given
        String url = "/f .f/ /sfj/35 53 9asfas /. ...fajsklfjhsjgjkfshgjkshjgkhsakjhdkjfjk";
        // then
        assertThrowsExactly(LeetCodeValidationUrlException.class, () -> leetCodeUrlParser.getTitleSlug(url));
    }

    @Test
    void invalidUrlWithTruncatedPrefix_shouldThrowException() {
        // given
        String url = "http://leetcode.com/problem";
        // then
        assertThrowsExactly(LeetCodeValidationUrlException.class, () -> leetCodeUrlParser.getTitleSlug(url));
    }

    @Test
    void invalidUrlWithEmptyTitleSlug_shouldThrowException() {
        // given
        String url = "http://leetcode.com/problem//";
        // then
        assertThrowsExactly(LeetCodeValidationUrlException.class, () -> leetCodeUrlParser.getTitleSlug(url));
    }
}
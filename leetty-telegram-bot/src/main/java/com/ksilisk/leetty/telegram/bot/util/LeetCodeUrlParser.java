package com.ksilisk.leetty.telegram.bot.util;

public interface LeetCodeUrlParser {
    String parseTitleSlug(String url);

    String parseUsername(String url);
}

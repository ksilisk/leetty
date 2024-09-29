package com.ksilisk.leetty.web.service.exception.type;

public class LeetCodeUserNotFoundException extends EntityNotFoundException {
    public LeetCodeUserNotFoundException() {
        super("LeetCode user not found");
    }
}

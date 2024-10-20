package com.ksilisk.leetty.telegram.bot.guard;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.guard.Guard;

public abstract class LeettyGuard extends Guard<LeettyBotStates, LeettyBotEvent, LeettyBotGuard> {
}

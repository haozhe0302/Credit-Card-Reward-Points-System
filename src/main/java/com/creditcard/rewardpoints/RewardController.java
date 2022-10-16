package com.creditcard.rewardpoints;

import com.creditcard.entity.*;
import com.creditcard.rewardpoints.RewardService;

import java.time.YearMonth;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RewardController {
    @Autowired
    private RewardService rewardService;

    @GetMapping("/details")
    public String viewHomePage() {

        YearMonth month = YearMonth.parse("2022-10");

        Reward reward = rewardService.findMaxReward(month, 2500, 1000, 510);

        return "reward_points_details";
    }
}


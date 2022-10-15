package com.creditcard.rewardpoints;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RewardController {
    @GetMapping("/details")
    public String viewHomePage() {
        return "reward_points_details";
    }
}


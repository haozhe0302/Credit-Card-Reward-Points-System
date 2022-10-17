package com.creditcard.rewardpoints;

import com.creditcard.entity.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RewardController {
    @Autowired
    private RewardService rewardService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/details")
    public String viewRewardDetails(@RequestParam("file") MultipartFile file, Model model) {
        // Validate file
        if (!file.isEmpty()) {
            try {
                Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                List<Transaction> transactionHistory = new CsvToBeanBuilder<Transaction>(reader).withType(Transaction.class).build().parse();

                // Distract the month yyyy/MM from transactionHistory date yyyy/MM/dd
                HashSet<String> dateByMonth = transactionService.dateToMonth(transactionHistory);

                List<Integer> amountInCategory = transactionService.amountCountByCategory(transactionHistory);
                int sportsAmount = amountInCategory.get(0);
                int timAmount = amountInCategory.get(1);
                int subwayAmount = amountInCategory.get(2);
                int otherAmount = amountInCategory.get(3);

                System.out.println("-------------------");
                System.out.println("Sports Total Amount:" + sportsAmount);
                System.out.println("Tim Total Amount:" + timAmount);
                System.out.println("Subway Total Amount:" + subwayAmount);

                // Temporarily only one single month of transaction input data is allowed. If not, raise warning to the user
                if (dateByMonth.size() != 1) {
                    System.out.println("Warnig: dateByMonth.size() != 1");
                    // TODO: Return to home page and raise warning message to user
                } else {
                    Reward reward = rewardService.findMaxReward(dateByMonth.iterator().next(), sportsAmount, timAmount, subwayAmount, otherAmount);

                    // Calculate points contribution for each transaction
                    transactionHistory = transactionService.setPointsForEachTransaction(transactionHistory, reward);

                    model.addAttribute("reward", reward);
                    model.addAttribute("totalPoints", reward.getTotalPoints());
                    model.addAttribute("sportsPoints", reward.getSportsPoints());
                    model.addAttribute("timPoints", reward.getTimPoints());
                    model.addAttribute("subwayPoints", reward.getSubwayPoints());
                    model.addAttribute("otherPoints", reward.getOtherPoints());
                    model.addAttribute("transactionHistory", transactionHistory);
                    return "reward_points_details";
                }
            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }

        } else {
            model.addAttribute("message", "Please select a validate CSV file to upload.");
            model.addAttribute("status", false);
        }

        return "reward_points_details";
    }
}


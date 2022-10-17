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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RewardController {
    @Autowired
    private RewardService rewardService;

    @PostMapping("/details")
    public String uploadCsvParse(@RequestParam("file") MultipartFile file, Model model) {
        // Validate file
        if (!file.isEmpty()) {
            try {
                Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                List<Transactions> transactionHistory = new CsvToBeanBuilder<Transactions>(reader).withType(Transactions.class).build().parse();

                int sportsAmount = 0;
                int timAmount = 0;
                int subwayAmount = 0;
                HashSet<String> dateByMonth = new HashSet<>();

                for (Transactions transaction : transactionHistory) {
                    // Distract date to yyyy/MM format
                    String[] dateSplit = transaction.getDate().split("/");
                    dateByMonth.add(dateSplit[0] + "/" + dateSplit[1]);

                    // Sum the monthly purchase amount for each merchant
                    switch (transaction.getMerchantCode()) {
                        case "sportcheck":
                            sportsAmount += transaction.getAmountCents();
                            break;
                        case "tim_hortons":
                            timAmount += transaction.getAmountCents();
                            break;
                        case "subway":
                            subwayAmount += transaction.getAmountCents();
                            break;
                    }
                }

                System.out.println("-------------------");
                System.out.println("Sports Total Amount:" + sportsAmount);
                System.out.println("Tim Total Amount:" + timAmount);
                System.out.println("Subway Total Amount:" + subwayAmount);

                // Temporarily only one single month of transaction input data is allowed. If not, raise warning to the user
                if (dateByMonth.size() != 1) {
                    System.out.println("Warnig: dateByMonth.size() != 1");
                    // TODO: Return to home page and raise warning message to user
                } else {
                    Reward reward = rewardService.findMaxReward(dateByMonth.iterator().next(), sportsAmount, timAmount, subwayAmount);

                    // Calculate points contribution for each transaction
                    for (Transactions transaction : transactionHistory) {
                        switch (transaction.getMerchantCode()) {
                            case "sportcheck":
                                transaction.setRewardPoints((float) Math.round(100 * transaction.getAmountCents() * reward.getSportsAvgPointsRate())/100);
                                break;
                            case "tim_hortons":
                                transaction.setRewardPoints((float) Math.round(100 * transaction.getAmountCents() * reward.getTimAvgPointsRate())/100);
                                break;
                            case "subway":
                                transaction.setRewardPoints((float) Math.round(100 * transaction.getAmountCents() * reward.getSubwayAvgPointsRate())/100);
                                break;
                        }
                    }

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


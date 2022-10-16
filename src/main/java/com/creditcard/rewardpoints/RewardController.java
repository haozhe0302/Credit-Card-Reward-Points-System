package com.creditcard.rewardpoints;

import com.creditcard.entity.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.YearMonth;
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
        if (! file.isEmpty()) {
            try {
                Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                List<Transactions> transactionHistory = new CsvToBeanBuilder<Transactions>(reader).withType(Transactions.class).build().parse();

                int sportsAmount = 0;
                int timAmount = 0;
                int subwayAmount = 0;
                // List<String> dateByMonth = new ArrayList<>();

                // Sum the monthly purchase amount for each merchant
                for (Transactions transaction : transactionHistory) {
                    String merchantCode = transaction.getMerchantCode();
                    // dateByMonth.(transaction.getDate().substring(0, 10));

                    switch (merchantCode) {
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

                System.out.println(sportsAmount);
                System.out.println(timAmount);
                System.out.println(subwayAmount);

                YearMonth month = YearMonth.parse("2022-10");
                Reward reward = rewardService.findMaxReward(month, sportsAmount, timAmount, subwayAmount);

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


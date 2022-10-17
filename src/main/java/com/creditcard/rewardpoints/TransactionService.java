package com.creditcard.rewardpoints;

import com.creditcard.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    public HashSet<String> dateToMonth (List<Transaction> transactionHistory) {
        HashSet<String> dateByMonth = new HashSet<>();

        for (Transaction transaction : transactionHistory) {
            // Distract date to yyyy/MM format
            String[] dateSplit = transaction.getDate().split("/");
            dateByMonth.add(dateSplit[0] + "/" + dateSplit[1]);
        }

        return dateByMonth;
    }

    public List<Integer> amountCountByCategory (List<Transaction> transactionHistory) {
        int sportsAmount = 0;
        int timAmount = 0;
        int subwayAmount = 0;
        int otherAmount = 0;

        for (Transaction transaction : transactionHistory) {
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
                default:
                    otherAmount += transaction.getAmountCents();
            }
        }

        List<Integer> amountByCategory = new ArrayList<>(4);
        amountByCategory.add(sportsAmount);
        amountByCategory.add(timAmount);
        amountByCategory.add(subwayAmount);
        amountByCategory.add(otherAmount);

        return amountByCategory;
    }
}

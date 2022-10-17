package com.creditcard.rewardpoints;

import com.creditcard.entity.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    public HashSet<String> dateToMonth (List<Transaction> transactionHistory) {
        /*
        Distract the month yyyy/MM from transactionHistory date yyyy/MM/dd

        Args:
            transactionHistory: Customer Transaction History, which is a list of all individual transaction info

        Returns:
            dateByMonth: HahSet which contains all the months appear in the transaction history
         */
        HashSet<String> dateByMonth = new HashSet<>();

        for (Transaction transaction : transactionHistory) {
            // Distract date to yyyy/MM format
            String[] dateSplit = transaction.getDate().split("/");
            dateByMonth.add(dateSplit[0] + "/" + dateSplit[1]);
        }

        return dateByMonth;
    }

    public List<Integer> amountCountByCategory (List<Transaction> transactionHistory) {
        /*
        Sum the total amount of each merchant of the 3 and others

        Args:
            transactionHistory: Customer Transaction History, which is a list of all individual transaction info

        Returns:
            amountByCategory: List contains the total amount of each merchant of the 3 and others
         */
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

    public List<Transaction> setPointsForEachTransaction (List<Transaction> transactionHistory, Reward reward) {
        /*
        Calculate points contribution for each transaction and pass them to transactionHistory

        Args:
            transactionHistory: Customer Transaction History, which is a list of all individual transaction info
            reward: Monthly reward points details
        Returns:
            transactionHistory: Customer Transaction History, which is a list of all individual transaction info
            Note: Points contribution for each transaction are added.
         */
        for (Transaction transaction : transactionHistory) {
            switch (transaction.getMerchantCode()) {
                case "sportcheck":
                    transaction.setRewardPoints((float) Math.round(100 * (float) transaction.getAmountCents() * reward.getSportsAvgPointsRate())/100);
                    break;
                case "tim_hortons":
                    transaction.setRewardPoints((float) Math.round(100 * (float) transaction.getAmountCents() * reward.getTimAvgPointsRate())/100);
                    break;
                case "subway":
                    transaction.setRewardPoints((float) Math.round(100 * (float) transaction.getAmountCents() * reward.getSubwayAvgPointsRate())/100);
                    break;
                default:
                    transaction.setRewardPoints((float) Math.round(100 * (float) transaction.getAmountCents() * reward.getOtherAvgPointsRate())/100);
            }
        }

        return transactionHistory;
    }
}

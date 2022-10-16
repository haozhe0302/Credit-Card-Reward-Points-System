package com.creditcard.rewardpoints;

import com.creditcard.entity.Reward;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class RewardService {

    public Reward findMaxReward (YearMonth month, Integer sportsAmount, Integer timAmount, Integer subwayAmount) {
        /*
        Calculate the maximum reward points customer could get monthly by dynamic programming

        Args:
            Customer total monthly purchase amount from 3 companies (unit: cent)

        Returns:
            Reward Class contains points owned by Rule 1, 2, 4, 6, and the leftover points owned by Rule 7
         */
        Reward reward = new Reward();

        int sportsPoints = 0;
        int timsPoints = 0;
        int subwayPoints = 0;
        int sportsLeftoverPoints = 0;
        int timsLeftoverPoints = 0;
        int subwayLeftoverPoints = 0;

        /*
        Applied rule in dynamic programming:
        rule = 0: No rule is applied
        rule = 1: Rule 1: 500 points for every $75 spend at Sport Check, $25 spend at Tim Hortons and $25 spend at Subway
        rule = 2: Rule 2: 300 points for every $75 spend at Sport Check and $25 spend at Tim Hortons
        rule = 3: Rule 4: 150 points for every $25 spend at Sport Check, $10 spend at Tim Hortons and $10 spend at Subway
        rule = 4: Rule 6: 75 point for every $20 spend at Sport Check
        (Similiar to the item in knapsack problem)
         */
        int ruleNum = 4;

        // To optimise space complexity, every capacity == 1 actually means 500 cents == 5 dollors
        // This would save 99.2% of the array space
        // NOTE: Remember in the dp loops every capacity ++ means 5 dollars rise now
        int sportsCapacity = sportsAmount / 500;
        int timCapacity = timAmount / 500;
        int subwayCapacity = subwayAmount / 500;

        // The capacity consumption for each rule (Similiar to the weight in knapsack problem)
        // e.g. consumption[1] = {15, 5, 5} refers to Rule 1: Sport Check 15 ($75),
        int[][] consumption = {{0, 0, 0}, {15, 5, 5}, {15, 5, 0}, {5, 2, 2}, {4, 0, 0}};

        // The reward points owned if certain rule promotion is applied
        // e.g. points[0] = 500 refers to Rule 1: 500 points
        int[] points = {0, 500, 300, 150, 75};

        // Record the maximum capacity used to achieve the maximum reward points
        // It would be our index to find the dp with the largest value faster
        int sportsUsedCapacity = 0;
        int timUsedCapacity = 0;
        int subwayUsedCapacity = 0;

        // dp[rule][sc][th][sw]: For number of fist 'rule' rules applied, sc = sports capacity left, th = tim horton capacity left, sw = subway capacity left
        // dp records the maximum reward points that possible under these constraints
        int[][][][] dp = new int[ruleNum + 1][sportsCapacity + 1][timCapacity + 1][subwayCapacity + 1];

        // Initialize dp
        // If there is no rule applied, obviously the reward points would all be 0 no matter what capacities are
        for (int sc = 0; sc <= sportsCapacity; sc ++) {
            for (int th = 0; th <= timCapacity; th ++) {
                for (int sw = 0; sw <= subwayCapacity; sw ++) {
                    dp[0][sc][th][sw] = 0;
                }
            }
        }

        // Dynamic programming
        // Check project document for details
        for (int rule = 1; rule <= ruleNum; rule ++) {
            for (int sc = 0; sc <= sportsCapacity; sc ++) {
                for (int th = 0; th <= timCapacity; th ++) {
                    for (int sw = 0; sw <= subwayCapacity; sw ++) {
                        dp[rule][sc][th][sw] = dp[rule - 1][sc][th][sw];

                        if (sc >= consumption[rule][0] && th >= consumption[rule][1] && sw >= consumption[rule][2]) {
                            dp[rule][sc][th][sw] = Math.max(dp[rule - 1][sc][th][sw], dp[rule][sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]] + points[rule]);
                        }

                        subwayUsedCapacity = sw;
                    }
                    timUsedCapacity = th;
                }
                sportsUsedCapacity = sc;
            }
        }

        System.out.println(month.toString());
        System.out.println(dp[ruleNum][sportsUsedCapacity][timUsedCapacity][subwayUsedCapacity]);


        return reward;
    }
}

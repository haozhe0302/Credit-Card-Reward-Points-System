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

        // Applied rule in dynamic programming:
        // rule = 0: No rule is applied
        // rule = 1: Rule 1: 500 points for every $75 spend at Sport Check, $25 spend at Tim Hortons and $25 spend at Subway
        // rule = 2: Rule 2: 300 points for every $75 spend at Sport Check and $25 spend at Tim Hortons
        // rule = 3: Rule 4: 150 points for every $25 spend at Sport Check, $10 spend at Tim Hortons and $10 spend at Subway
        // rule = 4: Rule 6: 75 point for every $20 spend at Sport Check
        // (Similiar to the items in knapsack problem)
        int ruleNum = 4;

        // To optimise space complexity, every capacity == 1 actually means 500 cents == 5 dollors
        // This would save 99.2% of the array space
        // NOTE: Remember in the dp loops every capacity++ means 5 dollars rise now
        int sportsCapacity = sportsAmount / 500;
        int timCapacity = timAmount / 500;
        int subwayCapacity = subwayAmount / 500;

        // The capacity consumption for each rule (Similiar to the weights in knapsack problem)
        // e.g. consumption[1] = {15, 5, 5} refers to Rule 1: Sport Check 15 ($75),
        int[][] consumption = {{0, 0, 0}, {15, 5, 5}, {15, 5, 0}, {5, 2, 2}, {4, 0, 0}};

        // The reward points owned if certain rule promotion is applied
        // e.g. points[0] = 500 refers to Rule 1: 500 points
        int[] points = {0, 500, 300, 150, 75};

        // The reward points contribution of all 3 companies
        // e.g. companyPoints[1][0] == 300 means sports check contributes 300 out of 500 points for Rule 1 promotion
        float[][] merchantPoints = {{0, 0, 0}, {300, 100, 100}, {225, 75, 0}, {(float) 250/3, (float) 100/3, (float) 100/3}, {75, 0, 0}};

        // Record the maximum capacity used to achieve the maximum reward points
        // It would be our index to find the dp with the largest value faster
        int sportsUsedCapacity = 0;
        int timUsedCapacity = 0;
        int subwayUsedCapacity = 0;
        int maxPoints = 0;

        // dp[rule][sc][th][sw][]: For number of fist 'rule' rules applied, sc = sports capacity left, th = tim horton capacity left, sw = subway capacity left
        // There is an array [points, r1, r2, r3, r4], which points represent the maximum reward points
        // r1, r2, r3, r4 refer to the number of each rule (1, 2, 4, 6) that applied
        int[][][][][] dp = new int[ruleNum + 1][sportsCapacity + 1][timCapacity + 1][subwayCapacity + 1][ruleNum + 1];

        // Initialize dp
        // If there is no rule applied, obviously the reward points would all be 0 no matter what capacities are
        for (int sc = 0; sc <= sportsCapacity; sc ++) {
            for (int th = 0; th <= timCapacity; th ++) {
                for (int sw = 0; sw <= subwayCapacity; sw ++) {
                    Arrays.fill(dp[0][sc][th][sw], 0);
                }
            }
        }

        // Dynamic programming
        // Time Complexity: O(NXYZ), where N = number of rules, X = total transaction amount in Sport Check, Y = total transaction amount in Tim Hortons, Z = total transaction amount in Subway
        // Space Complexity: O(NXYZ), but we could use some optimisation techniques to significantly reduce the actual space required
        // Check project document for details
        for (int rule = 1; rule <= ruleNum; rule ++) {
            for (int sc = 0; sc <= sportsCapacity; sc ++) {
                for (int th = 0; th <= timCapacity; th ++) {
                    for (int sw = 0; sw <= subwayCapacity; sw ++) {
                        dp[rule][sc][th][sw] = dp[rule - 1][sc][th][sw];

                        if (sc >= consumption[rule][0] && th >= consumption[rule][1] && sw >= consumption[rule][2]) {
                            // dp[rule][sc][th][sw] = Math.max(dp[rule - 1][sc][th][sw], dp[rule][sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]] + points[rule]);
                            if (dp[rule - 1][sc][th][sw][0] < dp[rule][sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]][0] + points[rule]) {
                                dp[rule][sc][th][sw][0] = dp[rule][sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]][0] + points[rule];
                                dp[rule][sc][th][sw][rule] = dp[rule][sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]][rule] + 1;

                                // When maximum reward points is found, record the dp index
                                if (dp[rule][sc][th][sw][0] > maxPoints) {
                                    maxPoints = dp[rule][sc][th][sw][0];
                                    sportsUsedCapacity = sc;
                                    timUsedCapacity = th;
                                    subwayUsedCapacity = sw;
                                }
                            }
                        }
                    }
                }
            }
        }

        int rule1Num = dp[ruleNum][sportsUsedCapacity][timUsedCapacity][subwayUsedCapacity][1];
        int rule2Num = dp[ruleNum][sportsUsedCapacity][timUsedCapacity][subwayUsedCapacity][2];
        int rule4Num = dp[ruleNum][sportsUsedCapacity][timUsedCapacity][subwayUsedCapacity][3];
        int rule6Num = dp[ruleNum][sportsUsedCapacity][timUsedCapacity][subwayUsedCapacity][4];

        float sportsPoints = rule1Num * merchantPoints[1][0] + rule2Num * merchantPoints[2][0] + rule4Num * merchantPoints[3][0] + rule6Num * merchantPoints[4][0];
        float timPoints = rule1Num * merchantPoints[1][1] + rule2Num * merchantPoints[2][1] + rule4Num * merchantPoints[3][1] + rule6Num * merchantPoints[4][1];
        float subwayPoints = rule1Num * merchantPoints[1][2] + rule2Num * merchantPoints[2][2] + rule4Num * merchantPoints[3][2] + rule6Num * merchantPoints[4][2];
        float sportsLeftoverPoints = (float) (sportsAmount/100 - 5 * sportsUsedCapacity);
        float timLeftoverPoints = (float) (timAmount/100 - 5 * timUsedCapacity);
        float subwayLeftoverPoints = (float) (subwayAmount/100 - 5 * subwayUsedCapacity);
        int finalLeftoverPoint = ((sportsAmount + timAmount + subwayAmount - 500 * (sportsUsedCapacity + timUsedCapacity + subwayUsedCapacity)) - (int) (sportsLeftoverPoints + timLeftoverPoints + subwayLeftoverPoints) * 100)/100;

        // Decide where we should place the finalLeftoverPoint
        if (sportsPoints > 0 || sportsLeftoverPoints > 0) {
            sportsPoints = sportsPoints + sportsLeftoverPoints + finalLeftoverPoint;
            timPoints += timLeftoverPoints;
            subwayPoints += subwayLeftoverPoints;
        } else if (timPoints > 0 || timLeftoverPoints > 0) {
            sportsPoints += sportsLeftoverPoints;
            timPoints = timPoints + timLeftoverPoints + finalLeftoverPoint;
            subwayPoints += subwayLeftoverPoints;
        } else if (subwayPoints > 0 || subwayLeftoverPoints > 0) {
            sportsPoints += sportsLeftoverPoints;
            timPoints += timLeftoverPoints;
            subwayPoints = subwayPoints + subwayLeftoverPoints  + finalLeftoverPoint;
        } else {
            sportsPoints = sportsPoints + sportsLeftoverPoints + finalLeftoverPoint;
            timPoints += timLeftoverPoints;
            subwayPoints += subwayLeftoverPoints;
        }

        // Pass calculation results to reward
        Reward reward = new Reward(month, sportsAmount, timAmount, subwayAmount, sportsPoints, timPoints, subwayPoints, rule1Num, rule2Num, rule4Num, rule6Num);

        // Output console test
        System.out.println("-------------------");
        System.out.println("Month: " + month.toString());

        System.out.println("Rule 1: " + rule1Num);
        System.out.println("Rule 2: " + rule2Num);
        System.out.println("Rule 4: " + rule4Num);
        System.out.println("Rule 6: " + rule6Num);
        System.out.println("-------------------");

        System.out.println("Sports Used Amount: " + 500 * sportsUsedCapacity);
        System.out.println("Tim Used Amount: " + 500 * timUsedCapacity);
        System.out.println("Subway Used Amount: " + 500 * subwayUsedCapacity);

        System.out.println("Sports Leftover Amount: " + (sportsAmount - 500 * sportsUsedCapacity));
        System.out.println("Tim Leftover Amount: " + (timAmount - 500 * timUsedCapacity));
        System.out.println("Subway Leftover Amount: " + (subwayAmount - 500 * subwayUsedCapacity));
        System.out.println("-------------------");

        System.out.println("Sports Leftover Points: " + sportsLeftoverPoints);
        System.out.println("Tim Leftover Points: " + timLeftoverPoints);
        System.out.println("Subway Leftover Points: " + subwayLeftoverPoints);
        System.out.println("Final Leftover Point: " + finalLeftoverPoint);
        System.out.println("-------------------");

        System.out.println("Sports Points: " + sportsPoints);
        System.out.println("Tim Points: " + timPoints);
        System.out.println("Subway Points: " + subwayPoints);
        System.out.println("Total Points: " + reward.getTotalPoints());
        System.out.println("-------------------");

        return reward;
    }
}

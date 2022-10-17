package com.creditcard.entity;

import java.util.*;

public class Reward {
    private String month;

    private Integer sportsAmount;
    private Integer timAmount;
    private Integer subwayAmount;
    private Integer otherAmount;

    private Float sportsPoints;
    private Float timPoints;
    private Float subwayPoints;
    private Float otherPoints;

    private Integer rule1Num;
    private Integer rule2Num;
    private Integer rule4Num;
    private Integer rule6Num;
    private Integer rule7Num;

    // Constructor
    public Reward(){}

    public Reward(String month, Integer sportsAmount, Integer timAmount, Integer subwayAmount, Integer otherAmount, Float sportsPoints, Float timPoints, Float subwayPoints, Integer rule1Num, Integer rule2Num, Integer rule4Num, Integer rule6Num, Integer rule7Num) {
        this.month = month;

        this.sportsAmount = sportsAmount;
        this.timAmount = timAmount;
        this.subwayAmount = subwayAmount;
        this.otherAmount = otherAmount;

        this.sportsPoints = sportsPoints;
        this.timPoints = timPoints;
        this.subwayPoints = subwayPoints;
        this.otherPoints = (float) (otherAmount/100);

        this.rule1Num = rule1Num;
        this.rule2Num = rule2Num;
        this.rule4Num = rule4Num;
        this.rule6Num = rule6Num;
        this.rule7Num = rule7Num;
    }

    // Getter
    public String getMonth() {
        return month;
    }

    public Integer getSportsAmount() {
        return sportsAmount;
    }

    public Integer getTimAmount() { return timAmount; }

    public Integer getSubwayAmount() {
        return subwayAmount;
    }

    public Integer getOtherAmount() {
        return otherAmount;
    }

    public Float getSportsPoints() {
        return (float) Math.round(100 * sportsPoints)/100;
    }

    public Float getTimPoints() {
        return (float) Math.round(100 * timPoints)/100;
    }

    public Float getSubwayPoints() {
        return (float) Math.round(100 * subwayPoints)/100;
    }

    public Float getOtherPoints() {
        return (float) Math.round(100 * otherPoints)/100;
    }

    public Integer getTotalPoints() {
        return Math.round(100 * (sportsPoints + timPoints + subwayPoints + otherPoints))/100;
    }

    public Float getSportsAvgPointsRate() {
        return sportsPoints/(float) sportsAmount;
    }

    public Float getTimAvgPointsRate() {
        return timPoints/(float) timAmount;
    }

    public Float getSubwayAvgPointsRate() {
        return subwayPoints/(float) subwayAmount;
    }

    public Float getOtherAvgPointsRate() {
        return otherPoints/(float) otherAmount;
    }

    public Float getTotalAvgPointsRate() {
        return (sportsPoints + timPoints + subwayPoints + otherPoints)/(float) (sportsAmount + timAmount + subwayAmount + otherAmount);
    }

    public Integer getRule1Num() {
        return rule1Num;
    }

    public Integer getRule2Num() {
        return rule2Num;
    }

    public Integer getRule4Num() {
        return rule4Num;
    }

    public Integer getRule6Num() {
        return rule6Num;
    }

    public Integer getRule7Num() {
        return rule7Num;
    }

    // Setter

    public void setMonth(String month) {
        this.month = month;
    }

    public void setSportsAmount(Integer sportsAmount) {
        this.sportsAmount = sportsAmount;
    }

    public void setTimAmount(Integer timAmount) {
        this.timAmount = timAmount;
    }

    public void setSubwayAmount(Integer subwayAmount) {
        this.subwayAmount = subwayAmount;
    }

    public void setOtherAmount(Integer otherAmount) {
        this.otherAmount = otherAmount;
    }

    public void setSportsPoints(Float sportsPoints) {
        this.sportsPoints = sportsPoints;
    }

    public void setTimPoints(Float timPoints) {
        this.timPoints = timPoints;
    }

    public void setSubwayPoints(Float subwayPoints) {
        this.subwayPoints = subwayPoints;
    }

    public void setOtherPoints(Float otherPoints) {
        this.otherPoints = otherPoints;
    }

    public void setRule1Num(Integer rule1Num) {
        this.rule1Num = rule1Num;
    }

    public void setRule2Num(Integer rule2Num) {
        this.rule2Num = rule2Num;
    }

    public void setRule4Num(Integer rule4Num) {
        this.rule4Num = rule4Num;
    }

    public void setRule6Num(Integer rule6Num) {
        this.rule6Num = rule6Num;
    }

    public void setRule7Num(Integer rule7Num) {
        this.rule7Num = rule7Num;
    }
}

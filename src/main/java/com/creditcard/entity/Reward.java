package com.creditcard.entity;

import java.time.YearMonth;
import java.util.*;

public class Reward {
    private YearMonth month;
    private Float sportsPoints;
    private Float timPoints;
    private Float subwayPoints;

    private Integer rule1Num;
    private Integer rule2Num;
    private Integer rule4Num;
    private Integer rule6Num;

    // Constructor
    public Reward(){}

    public Reward(YearMonth month, Float sportsPoints, Float timPoints, Float subwayPoints, Integer rule1Num, Integer rule2Num, Integer rule4Num, Integer rule6Num) {
        this.month = month;

        this.sportsPoints = sportsPoints;
        this.timPoints = timPoints;
        this.subwayPoints = subwayPoints;

        this.rule1Num = rule1Num;
        this.rule2Num = rule2Num;
        this.rule4Num = rule4Num;
        this.rule6Num = rule6Num;
    }

    // Getter
    public YearMonth getMonth() {
        return month;
    }

    public Float getSportsPoints() {
        return sportsPoints;
    }

    public Float getTimPoints() {
        return timPoints;
    }

    public Float getSubwayPoints() {
        return subwayPoints;
    }

    public Float getTotalPoints() {
        return sportsPoints + timPoints + subwayPoints;
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

    // Setter
    public void setMonth(YearMonth month) {
        this.month = month;
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
}

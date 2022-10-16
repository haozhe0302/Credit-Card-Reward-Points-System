package com.creditcard.entity;

import java.util.*;

public class Reward {
    private Date month;
    private Integer sportsPoints;
    private Integer timPoints;
    private Integer subwayPoints;
    private Integer sportsLeftoverPoints;
    private Integer timLeftoverPoints;
    private Integer subwayLeftoverPoints;
    private Integer totalPoints;

    // Constructor
    public Reward(){}

    public Reward(Date month, Integer sportsPoints, Integer timPoints, Integer subwayPoints, Integer sportsLeftoverPoints, Integer timLeftoverPoints, Integer subwayLeftoverPoints) {
        this.month = month;
        this.sportsPoints = sportsPoints;
        this.timPoints = timPoints;
        this.subwayPoints = subwayPoints;
        this.sportsLeftoverPoints = sportsLeftoverPoints;
        this.timLeftoverPoints = timLeftoverPoints;
        this.subwayLeftoverPoints = subwayLeftoverPoints;
        this.totalPoints = sportsPoints + timPoints + subwayPoints + sportsLeftoverPoints + timLeftoverPoints + subwayLeftoverPoints;
    }

    // Getter
    public Date getMonth() {
        return month;
    }

    public Integer getSportsPoints() {
        return sportsPoints;
    }

    public Integer getTimPoints() {
        return timPoints;
    }

    public Integer getSubwayPoints() {
        return subwayPoints;
    }

    public Integer getSportsLeftoverPoints() {
        return sportsLeftoverPoints;
    }

    public Integer getTimLeftoverPoints() {
        return timLeftoverPoints;
    }

    public Integer getSubwayLeftoverPoints() {
        return subwayLeftoverPoints;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    // Setter
    public void setMonth(Date month) {
        this.month = month;
    }

    public void setSportsPoints(Integer sportsPoints) {
        this.sportsPoints = sportsPoints;
    }

    public void setTimPoints(Integer timPoints) {
        this.timPoints = timPoints;
    }

    public void setSubwayPoints(Integer subwayPoints) {
        this.subwayPoints = subwayPoints;
    }

    public void setSportsLeftoverPoints(Integer sportsLeftoverPoints) {
        this.sportsLeftoverPoints = sportsLeftoverPoints;
    }

    public void setTimLeftoverPoints(Integer timLeftoverPoints) {
        this.timLeftoverPoints = timLeftoverPoints;
    }

    public void setSubwayLeftoverPoints(Integer subwayLeftoverPoints) {
        this.subwayLeftoverPoints = subwayLeftoverPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
}

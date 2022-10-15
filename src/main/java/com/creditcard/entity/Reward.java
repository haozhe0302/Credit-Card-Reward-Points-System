package com.creditcard.entity;

import java.util.*;

public class Reward {
    private Date month;
    private Integer sportsPoints;
    private Integer timsPoints;
    private Integer subwayPoints;
    private Integer sportsLeftoverPoints;
    private Integer timsLeftoverPoints;
    private Integer subwayLeftoverPoints;
    private Integer totalPoints;

    // Constructor
    public Reward(){}

    public Reward(Date month, Integer sportsPoints, Integer timsPoints, Integer subwayPoints, Integer sportsLeftoverPoints, Integer timsLeftoverPoints, Integer subwayLeftoverPoints) {
        this.month = month;
        this.sportsPoints = sportsPoints;
        this.timsPoints = timsPoints;
        this.subwayPoints = subwayPoints;
        this.sportsLeftoverPoints = sportsLeftoverPoints;
        this.timsLeftoverPoints = timsLeftoverPoints;
        this.subwayLeftoverPoints = subwayLeftoverPoints;
        this.totalPoints = sportsPoints + timsPoints + subwayPoints + sportsLeftoverPoints + timsLeftoverPoints + subwayLeftoverPoints;
    }

    // Getter
    public Date getMonth() {
        return month;
    }

    public Integer getSportsPoints() {
        return sportsPoints;
    }

    public Integer getTimsPoints() {
        return timsPoints;
    }

    public Integer getSubwayPoints() {
        return subwayPoints;
    }

    public Integer getSportsLeftoverPoints() {
        return sportsLeftoverPoints;
    }

    public Integer getTimsLeftoverPoints() {
        return timsLeftoverPoints;
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

    public void setTimsPoints(Integer timsPoints) {
        this.timsPoints = timsPoints;
    }

    public void setSubwayPoints(Integer subwayPoints) {
        this.subwayPoints = subwayPoints;
    }

    public void setSportsLeftoverPoints(Integer sportsLeftoverPoints) {
        this.sportsLeftoverPoints = sportsLeftoverPoints;
    }

    public void setTimsLeftoverPoints(Integer timsLeftoverPoints) {
        this.timsLeftoverPoints = timsLeftoverPoints;
    }

    public void setSubwayLeftoverPoints(Integer subwayLeftoverPoints) {
        this.subwayLeftoverPoints = subwayLeftoverPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
}

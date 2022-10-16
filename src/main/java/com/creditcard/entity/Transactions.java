package com.creditcard.entity;

import com.opencsv.bean.CsvBindByName;

import java.util.*;

public class Transactions {
    @CsvBindByName
    private Integer transactionId;

    @CsvBindByName
    private String date;

    @CsvBindByName
    private String merchantCode;

    @CsvBindByName
    private Integer amountCents;

    @CsvBindByName
    private Float rewardPoints;

    // Constructor
    public Transactions(){}

    public Transactions(Integer transactionId, String date, String merchantCode, Integer amountCents) {
        this.transactionId = transactionId;
        this.date = date;
        this.merchantCode = merchantCode;
        this.amountCents = amountCents;
    }

    // Getter
    public Integer getTransactionId() {
        return transactionId;
    }

    public String getDate() {
        return date;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public Integer getAmountCents() {
        return amountCents;
    }

    public Float getRewardPoints() {
        return rewardPoints;
    }

    // Setter
    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public void setAmountCents(Integer amountCents) {
        this.amountCents = amountCents;
    }

    public void setRewardPoints(Float rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}

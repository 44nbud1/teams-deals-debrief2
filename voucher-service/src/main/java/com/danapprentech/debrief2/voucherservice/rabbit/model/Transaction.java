package com.danapprentech.debrief2.voucherservice.rabbit.model;

import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private Integer idTransaction;
    private String idUser;
    private Double amount;
    private Date transactionDate;
    private Boolean isCredit;
    private Integer idTransactionStatus;
    private Integer idPaymentMethod;
    private Integer idService;
    private Integer idGoods;
    private Date createdAt;
    private Date updatedAt;

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Boolean isCredit) {
        this.isCredit = isCredit;
    }

    public Integer getIdTransactionStatus() {
        return idTransactionStatus;
    }

    public void setIdTransactionStatus(Integer idTransactionStatus) {
        this.idTransactionStatus = idTransactionStatus;
    }

    public Integer getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(Integer idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public Integer getIdGoods() {
        return idGoods;
    }

    public void setIdGoods(Integer idGoods) {
        this.idGoods = idGoods;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}

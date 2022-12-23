package com.idev4.admin.dto;

public class MwDsbltyRptDTO {
    public Long clntSeq;
    public Integer gender;
    public String dtOfDsblty;
    public String cmnt;
    public String prntLoanAppSeq;

    public Long getClntSeq() {
        return this.clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getDtOfDsblty() {
        return this.dtOfDsblty;
    }

    public void setDtOfDsblty(String dtOfDsblty) {
        this.dtOfDsblty = dtOfDsblty;
    }

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }

    public String getPrntLoanAppSeq() {
        return prntLoanAppSeq;
    }

    public void setPrntLoanAppSeq(String prntLoanAppSeq) {
        this.prntLoanAppSeq = prntLoanAppSeq;
    }
}

package com.idev4.admin.dto;

public class ReportDeathDTO {
    public Long clntSeq;
    public Integer gender;
    public String deathDt;
    public String dethCase;
    public String deathcertf;
    public String prntLoanAppSeq;

    public Long getClntSeq() {
        return clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getDeathDt() {
        return deathDt;
    }

    public void setDeathDt(String deathDt) {
        this.deathDt = deathDt;
    }

    public String getDethCase() {
        return dethCase;
    }

    public void setDethCase(String dethCase) {
        this.dethCase = dethCase;
    }

    public String getDeathcertf() {
        return deathcertf;
    }

    public void setDeathcertf(String deathcertf) {
        this.deathcertf = deathcertf;
    }

    public String getPrntLoanAppSeq() {
        return prntLoanAppSeq;
    }

    public void setPrntLoanAppSeq(String prntLoanAppSeq) {
        this.prntLoanAppSeq = prntLoanAppSeq;
    }

    @Override
    public String toString() {
        return "ReportDeathDTO{" +
                "clntSeq=" + clntSeq +
                ", gender=" + gender +
                ", deathDt='" + deathDt + '\'' +
                ", dethCase='" + dethCase + '\'' +
                ", deathcertf='" + deathcertf + '\'' +
                ", prntLoanAppSeq=" + prntLoanAppSeq +
                '}';
    }
}

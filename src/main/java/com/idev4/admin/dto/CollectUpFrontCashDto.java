package com.idev4.admin.dto;

public class CollectUpFrontCashDto {
    Long clntId;

    String clntNm;

    Long dthRptSeq;

    String instr;

    Long amt;

    Long rcvblAmt;

    String rcvryTypsSeq;


    public Long getClntId() {
        return clntId;
    }

    public void setClntId(Long clntId) {
        this.clntId = clntId;
    }

    public String getClntNm() {
        return clntNm;
    }

    public void setClntNm(String clntNm) {
        this.clntNm = clntNm;
    }

    public Long getDthRptSeq() {
        return dthRptSeq;
    }

    public void setDthRptSeq(Long dthRptSeq) {
        this.dthRptSeq = dthRptSeq;
    }

    public String getInstr() {
        return instr;
    }

    public void setInstr(String instr) {
        this.instr = instr;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public Long getRcvblAmt() {
        return rcvblAmt;
    }

    public void setRcvblAmt(Long rcvblAmt) {
        this.rcvblAmt = rcvblAmt;
    }

    public String getRcvryTypsSeq() {
        return rcvryTypsSeq;
    }

    public void setRcvryTypsSeq(String rcvryTypsSeq) {
        this.rcvryTypsSeq = rcvryTypsSeq;
    }

    @Override
    public String toString() {
        return "CollectUpFrontCashDto{" +
                "clntId=" + clntId +
                ", clntNm='" + clntNm + '\'' +
                ", dthRptSeq=" + dthRptSeq +
                ", instr='" + instr + '\'' +
                ", amt=" + amt +
                ", rcvblAmt=" + rcvblAmt +
                ", rcvryTypsSeq='" + rcvryTypsSeq + '\'' +
                '}';
    }
}

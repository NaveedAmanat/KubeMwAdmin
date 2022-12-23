package com.idev4.admin.dto;

public class LoanServingDTO {

    public String clntId;

    public String clntSeq;

    public String frstNm;

    public String lastNm;

    public long loanAmt;

    public long rcvdAmt;

    public long sercvChrgs;

    public long dthRptSeq;

    public long amt;

    //Added by Areeba
    public long dsbltyRptSeq;

    public long dsbltyAmt;
    //Ended by Areeba

    //Added by Yousaf Dated: 26-09-2022
    public long anmlDthSeq;

    public long anmlDthAmt;
    //Ended by Yousaf

    public long brnchSeq;

    public boolean paid;

    public String prd;

    public String disDate;

    public long relTypFlg;

    public boolean post;

    public String expSeq;

    // public List< Long > expList;

    public String prdId;

    // added by yousaf dated: 04/07/2022 due to HIL double client shown in credit servicing
    public String prntLoanAppSeq;

    public long rcvryCrntRecFlg;
}




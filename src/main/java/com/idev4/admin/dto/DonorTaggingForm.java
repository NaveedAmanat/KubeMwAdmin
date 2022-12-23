package com.idev4.admin.dto;

import java.util.List;

public class DonorTaggingForm {

    public Long frmAmt;

    public Long toAmt;

    public Long cycle;

    public String frmDt;

    public String toDt;

    // CR-Donor Tagging
    // Added LoanAppSeq Search Filter
    // Added By Naveed - 20-12-2021
    public Long loanAppSeq;

    public Long clntSeq;

    public int funder;

    public boolean uploader;
    // End By Naveed - 20-12-2021

    public List<Long> branchs;

    public List<Long> districts;

    public List<Long> sectors;

    public List<Long> activities;

    public List<Long> prds;

    public List<Long> prd_grps;

    public Long fundAmt;

}

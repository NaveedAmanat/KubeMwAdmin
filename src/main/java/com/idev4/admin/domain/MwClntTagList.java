package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/* AUTHOR: Areeba
 *   Dated 1-2-2022
 *   Client Tag List
 */
@Entity
@Table(name = "MW_CLNT_TAG_LIST")
public class MwClntTagList {
    @Id
    @Column(name = "CLNT_TAG_LIST_SEQ")
    private Long clntTagListSeq;

    @Column(name = "EFF_START_DT")
    private Instant effStartDt;

    @Column(name = "CNIC_NUM")
    private Long cnicNum;

    @Column(name = "TAGS_SEQ")
    private Long tagsSeq;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpdDt;

    @Column(name = "EFF_END_DT")
    private Instant effEndDt;

    @Column(name = "DEL_FLG")
    private Long delFlg;

    @Column(name = "TAG_FROM_DT")
    private Instant tagFromDt;

    @Column(name = "TAG_TO_DT")
    private Instant tagToDt;

    @Column(name = "RMKS")
    private String rmks;

    @Column(name = "SYNC_FLG")
    private Long syncFlg;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "CRNT_REC_FLG")
    private Long crntRecFlg;

    MwClntTagList() {
    }

    public Long getClntTagListSeq() {
        return this.clntTagListSeq;
    }

    public void setClntTagListSeq(Long clntTagListSeq) {
        this.clntTagListSeq = clntTagListSeq;
    }

    public Instant getEffStartDt() {
        return this.effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getCnicNum() {
        return this.cnicNum;
    }

    public void setCnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
    }

    public Long getTagsSeq() {
        return this.tagsSeq;
    }

    public void setTagsSeq(Long tagsSeq) {
        this.tagsSeq = tagsSeq;
    }

    public String getCrtdBy() {
        return this.crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getCrtdDt() {
        return this.crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return this.lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Instant getLastUpdDt() {
        return this.lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Instant getEffEndDt() {
        return this.effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Long getDelFlg() {
        return this.delFlg;
    }

    public void setDelFlg(Long delFlg) {
        this.delFlg = delFlg;
    }

    public Instant getTagFromDt() {
        return this.tagFromDt;
    }

    public void setTagFromDt(Instant tagFromDt) {
        this.tagFromDt = tagFromDt;
    }

    public Instant getTagToDt() {
        return this.tagToDt;
    }

    public void setTagToDt(Instant tagToDt) {
        this.tagToDt = tagToDt;
    }

    public String getRmks() {
        return this.rmks;
    }

    public void setRmks(String rmks) {
        this.rmks = rmks;
    }

    public Long getSyncFlg() {
        return this.syncFlg;
    }

    public void setSyncFlg(Long syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getLoanAppSeq() {
        return this.loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Long getCrntRecFlg() {
        return this.crntRecFlg;
    }

    public void setCrntRecFlg(Long crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }
}

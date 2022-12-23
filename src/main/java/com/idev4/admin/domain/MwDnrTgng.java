package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_DNR_TGNG")
public class MwDnrTgng {

    @Id
    @Column(name = "DNR_TGNG_SEQ")
    private Long dnrTgngSeq;

    @Column(name = "DNR_SEQ")
    private Long dnrSeq;

    @Column(name = "TOT_TAG_AMT")
    private Long totTagAmt;

    @Column(name = "TAG_AMT")
    private Long tagAmt;


    @Column(name = "crtd_by")
    private String crtdBy;

    @Column(name = "crtd_dt")
    private Instant crtdDt;

    @Column(name = "last_upd_by")
    private String lastUpdBy;

    @Column(name = "last_upd_dt")
    private Instant lastUpdDt;

    @Column(name = "del_flg")
    private Boolean delFlg;

    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "TAG_CLNTS")
    private Long tagClnts;

    public Long getTagClnts() {
        return tagClnts;
    }

    public void setTagClnts(Long tagClnts) {
        this.tagClnts = tagClnts;
    }

    public Long getDnrTgngSeq() {
        return dnrTgngSeq;
    }

    public void setDnrTgngSeq(Long dnrTgngSeq) {
        this.dnrTgngSeq = dnrTgngSeq;
    }

    public Long getDnrSeq() {
        return dnrSeq;
    }

    public void setDnrSeq(Long dnrSeq) {
        this.dnrSeq = dnrSeq;
    }

    public Long getTotTagAmt() {
        return totTagAmt;
    }

    public void setTotTagAmt(Long totTagAmt) {
        this.totTagAmt = totTagAmt;
    }

    public Long getTagAmt() {
        return tagAmt;
    }

    public void setTagAmt(Long tagAmt) {
        this.tagAmt = tagAmt;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }


}

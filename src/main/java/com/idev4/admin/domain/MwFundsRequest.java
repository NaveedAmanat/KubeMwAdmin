package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * @Added, Naveed
 * @Date, 14-06-2022
 * @Description, SCR - systemization Funds Request
 */

@Entity
@Table(name = "MW_FUND_REQUEST")
public class MwFundsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FUND_REQ_SEQ")
    private Long fundReqSeq;

    @Column(name = "ENTY_SEQ")
    private Long entySeq;

    @Column(name = "ENTY_TYP")
    private String entyTyp;

    @Column(name = "AMT")
    private Long amt;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "CRTD_BY")
    private String crdtBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    public MwFundsRequest() {
    }

    public MwFundsRequest(Long fundReqSeq, Long entySeq, String entyTyp, Long amt, String remarks, Boolean crntRecFlg, Instant crtdDt, String crdtBy, Instant lastUpDt, String lastUpdBy) {
        this.fundReqSeq = fundReqSeq;
        this.entySeq = entySeq;
        this.entyTyp = entyTyp;
        this.amt = amt;
        this.remarks = remarks;
        this.crntRecFlg = crntRecFlg;
        this.crtdDt = crtdDt;
        this.crdtBy = crdtBy;
        this.lastUpDt = lastUpDt;
        this.lastUpdBy = lastUpdBy;
    }

    public Long getFundReqSeq() {
        return fundReqSeq;
    }

    public void setFundReqSeq(Long fundReqSeq) {
        this.fundReqSeq = fundReqSeq;
    }

    public Long getEntySeq() {
        return entySeq;
    }

    public void setEntySeq(Long entySeq) {
        this.entySeq = entySeq;
    }

    public String getEntyTyp() {
        return entyTyp;
    }

    public void setEntyTyp(String entyTyp) {
        this.entyTyp = entyTyp;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getCrdtBy() {
        return crdtBy;
    }

    public void setCrdtBy(String crdtBy) {
        this.crdtBy = crdtBy;
    }

    public Instant getLastUpDt() {
        return lastUpDt;
    }

    public void setLastUpDt(Instant lastUpDt) {
        this.lastUpDt = lastUpDt;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }
}

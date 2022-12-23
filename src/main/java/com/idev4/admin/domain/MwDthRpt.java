package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "mw_dth_rpt")
// @IdClass ( MwDthRptId.class )
public class MwDthRpt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dth_rpt_seq")
    private Long dthRptSeq;

    @Column(name = "clnt_seq")
    private Long clntSeq;

    // @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "clnt_nom_flg")
    private Integer clntNomFlg;

    @Column(name = "dt_of_dth")
    private Instant dtOfDth;

    @Column(name = "cause_of_dth")
    private String causeOfDth;

    @Column(name = "dth_cert_num")
    private String dthCertNum;

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

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "amt")
    private Long amt;

    @Column(name = "CMNT")
    private String cmnt;

    @Column(name = "INSR_CLM_STS")
    private Long insrClmSts;

    @Column(name = "INSR_CLM_RMRKS")
    private String insrClmRmrks;

    @Column(name = "ADJ_FLG")
    private Long adjFlg;

    public Long getAdjFlg() {
        return adjFlg;
    }

    public void setAdjFlg(Long adjFlg) {
        this.adjFlg = adjFlg;
    }

    public Long getInsrClmSts() {
        return insrClmSts;
    }

    public void setInsrClmSts(Long insrClmSts) {
        this.insrClmSts = insrClmSts;
    }

    public String getInsrClmRmrks() {
        return insrClmRmrks;
    }

    public void setInsrClmRmrks(String insrClmRmrks) {
        this.insrClmRmrks = insrClmRmrks;
    }

    public Long getDthRptSeq() {
        return dthRptSeq;
    }

    public void setDthRptSeq(Long dthRptSeq) {
        this.dthRptSeq = dthRptSeq;
    }

    public Long getClntSeq() {
        return clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Integer getClntNomFlg() {
        return clntNomFlg;
    }

    public void setClntNomFlg(Integer clntNomFlg) {
        this.clntNomFlg = clntNomFlg;
    }

    public Instant getDtOfDth() {
        return dtOfDth;
    }

    public void setDtOfDth(Instant dtOfDth) {
        this.dtOfDth = dtOfDth;
    }

    public String getCauseOfDth() {
        return causeOfDth;
    }

    public void setCauseOfDth(String causeOfDth) {
        this.causeOfDth = causeOfDth;
    }

    public String getDthCertNum() {
        return dthCertNum;
    }

    public void setDthCertNum(String dthCertNum) {
        this.dthCertNum = dthCertNum;
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

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

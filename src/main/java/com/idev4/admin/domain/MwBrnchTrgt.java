package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_BRNCH_TRGT")
public class MwBrnchTrgt {
    @Id
    @Column(name = "BRNCH_TARGETS_SEQ")
    private Long brnchTargetsSeq;

    @Column(name = "TRGT_YR")
    private Long trgtYr;

    @Column(name = "TRGT_PERD")
    private Long trgtPerd;

    @Column(name = "TRGT_CLIENTS")
    private Long trgtClients;

    @Column(name = "TRGT_AMT")
    private Long trgtAmt;

    @Column(name = "BRNCH_SEQ")
    private Long brnchSeq;

    @Column(name = "EFF_START_DT")
    private Instant effStartDt;

    @Column(name = "PRD_SEQ")
    private Long prdSeq;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "DEL_FLG")
    private Boolean delFlg;

    public Long getBrnchTargetsSeq() {
        return this.brnchTargetsSeq;
    }

    public void setBrnchTargetsSeq(Long brnchTargetsSeq) {
        this.brnchTargetsSeq = brnchTargetsSeq;
    }

    public Long getTrgtYr() {
        return this.trgtYr;
    }

    public void setTrgtYr(Long trgtYr) {
        this.trgtYr = trgtYr;
    }

    public Long getTrgtPerd() {
        return this.trgtPerd;
    }

    public void setTrgtPerd(Long trgtPerd) {
        this.trgtPerd = trgtPerd;
    }

    public Long getTrgtClients() {
        return this.trgtClients;
    }

    public void setTrgtClients(Long trgtClients) {
        this.trgtClients = trgtClients;
    }

    public Long getTrgtAmt() {
        return this.trgtAmt;
    }

    public void setTrgtAmt(Long trgtAmt) {
        this.trgtAmt = trgtAmt;
    }

    public Long getBrnchSeq() {
        return this.brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Instant getEffStartDt() {
        return this.effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getPrdSeq() {
        return this.prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
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

    public Boolean getDelFlg() {
        return this.delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }
}

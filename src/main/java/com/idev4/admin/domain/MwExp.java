package com.idev4.admin.domain;

import com.idev4.admin.ids.MwExpId;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "Mw_Exp")
@IdClass(MwExpId.class)
public class MwExp {

    @Id
    @Column(name = "EXP_SEQ")
    private Long expSeq;

    @Column(name = "PYMT_TYP_SEQ")
    private Long pymtTypSeq;

    @Column(name = "BRNCH_SEQ")
    private Long brnchSeq;

    @Column(name = "EXPNS_STS_KEY")
    private Long expnsStsKey;

    @Column(name = "EXPNS_ID")
    private String expnsId;

    @Column(name = "EXPNS_DSCR")
    private String expnsDscr;

    @Column(name = "INSTR_NUM")
    private String instrNum;

    @Column(name = "EXPNS_AMT")
    private Double expnsAmt;

    @Column(name = "EXPNS_TYP_SEQ")
    private Long expnsTypSeq;

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

    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "post_flg")
    private Boolean postFlg;

    @Column(name = "exp_ref")
    private String expRef;

    @Column(name = "PYMT_RCT_FLG")
    private Long pymtRctFlg;

    public Long getPymtTypSeq() {
        return pymtTypSeq;
    }

    public void setPymtTypSeq(Long pymtTypSeq) {
        this.pymtTypSeq = pymtTypSeq;
    }

    public String getExpRef() {
        return expRef;
    }

    public void setExpRef(String expRef) {
        this.expRef = expRef;
    }

    public Long getExpSeq() {
        return expSeq;
    }

    public void setExpSeq(Long expSeq) {
        this.expSeq = expSeq;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getExpnsStsKey() {
        return expnsStsKey;
    }

    public void setExpnsStsKey(Long expnsStsKey) {
        this.expnsStsKey = expnsStsKey;
    }

    public String getExpnsId() {
        return expnsId;
    }

    public void setExpnsId(String expnsId) {
        this.expnsId = expnsId;
    }

    public String getExpnsDscr() {
        return expnsDscr;
    }

    public void setExpnsDscr(String expnsDscr) {
        this.expnsDscr = expnsDscr;
    }

    public String getInstrNum() {
        return instrNum;
    }

    public void setInstrNum(String instrNum) {
        this.instrNum = instrNum;
    }

    public Double getExpnsAmt() {
        return expnsAmt;
    }

    public void setExpnsAmt(Double expnsAmt) {
        this.expnsAmt = expnsAmt;
    }

    public Long getExpnsTypSeq() {
        return expnsTypSeq;
    }

    public void setExpnsTypSeq(Long expnsTypSeq) {
        this.expnsTypSeq = expnsTypSeq;
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

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Boolean getPostFlg() {
        return postFlg;
    }

    public void setPostFlg(Boolean postFlg) {
        this.postFlg = postFlg;
    }

    public Long getPymtRctFlg() {
        return pymtRctFlg;
    }

    public void setPymtRctFlg(Long pymtRctFlg) {
        this.pymtRctFlg = pymtRctFlg;
    }

}

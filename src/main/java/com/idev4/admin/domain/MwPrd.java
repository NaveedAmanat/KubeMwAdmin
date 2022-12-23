package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MW_PRD")
public class MwPrd {
    @Id
    @Column(name = "PRD_SEQ")
    private Long prdSeq;

    @Column(name = "EFF_START_DT")
    private java.sql.Date effStartDt;

    @Column(name = "PRD_GRP_SEQ")
    private Long prdGrpSeq;

    @Column(name = "PRD_ID")
    private String prdId;

    @Column(name = "PRD_NM")
    private String prdNm;

    @Column(name = "PRD_CMNT")
    private String prdCmnt;

    @Column(name = "PRD_STS_KEY")
    private Long prdStsKey;

    @Column(name = "PRD_TYP_KEY")
    private Long prdTypKey;

    @Column(name = "IRR_FLG")
    private Long irrFlg;

    @Column(name = "RNDNG_SCL")
    private Long rndngScl;

    @Column(name = "RNDNG_ADJ")
    private Long rndngAdj;

    @Column(name = "DAILY_ACCURAL_FLG")
    private Long dailyAccuralFlg;

    @Column(name = "FND_BY_KEY")
    private Long fndByKey;

    @Column(name = "CRNCY_CD_KEY")
    private Long crncyCdKey;

    @Column(name = "MLT_LOAN_FLG")
    private Long mltLoanFlg;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private java.sql.Date crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private java.sql.Date lastUpdDt;

    @Column(name = "DEL_FLG")
    private Long delFlg;

    @Column(name = "EFF_END_DT")
    private java.sql.Date effEndDt;

    @Column(name = "CRNT_REC_FLG")
    private Long crntRecFlg;

    @Column(name = "IRR_VAL")
    private Long irrVal;

    @Column(name = "PDC_NUM")
    private Long pdcNum;

    @Column(name = "CS_FLG")
    private Long csFlg;

    @Column(name = "CLNT_TSDQ_FLG")
    private Long clntTsdqFlg;

    @Column(name = "COB_TSDQ_FLG")
    private Long cobTsdqFlg;

    @Column(name = "NOM_TSDQ_FLG")
    private Long nomTsdqFlg;

    @Column(name = "INSR_FIXED_VLU")
    private String insrFixedVlu;

    public Long getPrdSeq() {
        return this.prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public java.sql.Date getEffStartDt() {
        return this.effStartDt;
    }

    public void setEffStartDt(java.sql.Date effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getPrdGrpSeq() {
        return this.prdGrpSeq;
    }

    public void setPrdGrpSeq(Long prdGrpSeq) {
        this.prdGrpSeq = prdGrpSeq;
    }

    public String getPrdId() {
        return this.prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public String getPrdNm() {
        return this.prdNm;
    }

    public void setPrdNm(String prdNm) {
        this.prdNm = prdNm;
    }

    public String getPrdCmnt() {
        return this.prdCmnt;
    }

    public void setPrdCmnt(String prdCmnt) {
        this.prdCmnt = prdCmnt;
    }

    public Long getPrdStsKey() {
        return this.prdStsKey;
    }

    public void setPrdStsKey(Long prdStsKey) {
        this.prdStsKey = prdStsKey;
    }

    public Long getPrdTypKey() {
        return this.prdTypKey;
    }

    public void setPrdTypKey(Long prdTypKey) {
        this.prdTypKey = prdTypKey;
    }

    public Long getIrrFlg() {
        return this.irrFlg;
    }

    public void setIrrFlg(Long irrFlg) {
        this.irrFlg = irrFlg;
    }

    public Long getRndngScl() {
        return this.rndngScl;
    }

    public void setRndngScl(Long rndngScl) {
        this.rndngScl = rndngScl;
    }

    public Long getRndngAdj() {
        return this.rndngAdj;
    }

    public void setRndngAdj(Long rndngAdj) {
        this.rndngAdj = rndngAdj;
    }

    public Long getDailyAccuralFlg() {
        return this.dailyAccuralFlg;
    }

    public void setDailyAccuralFlg(Long dailyAccuralFlg) {
        this.dailyAccuralFlg = dailyAccuralFlg;
    }

    public Long getFndByKey() {
        return this.fndByKey;
    }

    public void setFndByKey(Long fndByKey) {
        this.fndByKey = fndByKey;
    }

    public Long getCrncyCdKey() {
        return this.crncyCdKey;
    }

    public void setCrncyCdKey(Long crncyCdKey) {
        this.crncyCdKey = crncyCdKey;
    }

    public Long getMltLoanFlg() {
        return this.mltLoanFlg;
    }

    public void setMltLoanFlg(Long mltLoanFlg) {
        this.mltLoanFlg = mltLoanFlg;
    }

    public String getCrtdBy() {
        return this.crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public java.sql.Date getCrtdDt() {
        return this.crtdDt;
    }

    public void setCrtdDt(java.sql.Date crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return this.lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public java.sql.Date getLastUpdDt() {
        return this.lastUpdDt;
    }

    public void setLastUpdDt(java.sql.Date lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Long getDelFlg() {
        return this.delFlg;
    }

    public void setDelFlg(Long delFlg) {
        this.delFlg = delFlg;
    }

    public java.sql.Date getEffEndDt() {
        return this.effEndDt;
    }

    public void setEffEndDt(java.sql.Date effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Long getCrntRecFlg() {
        return this.crntRecFlg;
    }

    public void setCrntRecFlg(Long crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getIrrVal() {
        return this.irrVal;
    }

    public void setIrrVal(Long irrVal) {
        this.irrVal = irrVal;
    }

    public Long getPdcNum() {
        return this.pdcNum;
    }

    public void setPdcNum(Long pdcNum) {
        this.pdcNum = pdcNum;
    }

    public Long getCsFlg() {
        return this.csFlg;
    }

    public void setCsFlg(Long csFlg) {
        this.csFlg = csFlg;
    }

    public Long getClntTsdqFlg() {
        return this.clntTsdqFlg;
    }

    public void setClntTsdqFlg(Long clntTsdqFlg) {
        this.clntTsdqFlg = clntTsdqFlg;
    }

    public Long getCobTsdqFlg() {
        return this.cobTsdqFlg;
    }

    public void setCobTsdqFlg(Long cobTsdqFlg) {
        this.cobTsdqFlg = cobTsdqFlg;
    }

    public Long getNomTsdqFlg() {
        return this.nomTsdqFlg;
    }

    public void setNomTsdqFlg(Long nomTsdqFlg) {
        this.nomTsdqFlg = nomTsdqFlg;
    }

    public String getInsrFixedVlu() {
        return this.insrFixedVlu;
    }

    public void setInsrFixedVlu(String insrFixedVlu) {
        this.insrFixedVlu = insrFixedVlu;
    }
}

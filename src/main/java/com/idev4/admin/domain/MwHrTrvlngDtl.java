package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/*Authored by Areeba
HR Travelling SCR
Dated - 23-06-2022
*/

@Entity
@Table(name = "MW_HR_TRVLNG_DTL")
public class MwHrTrvlngDtl {

    @Id
    @Column(name = "MW_HR_TRVLNG_DTL_SEQ")
    private Long mwHrTrvlngDtlSeq;

    @Column(name = "TRVLNG_MNTH")
    private Instant trvlngMnth;

    @Column(name = "TRV_MNTH_DAYS")
    private Long trvMnthDays;

    @Column(name = "WRKNG_DYS")
    private Long wrkngDys;

    @Column(name = "CRTFCTN_DT")
    private Instant crtfctnDt;

    @Column(name = "WRKNG_STRT_DT")
    private Instant wrkngStrtDt;

    @Column(name = "WRKNG_END_DT")
    private Instant wrkngEndDt;

    @Column(name = "PER_DAY_VALUE")
    private Long perDayValue;

    @Column(name = "REG_SEQ")
    private Long regSeq;

    @Column(name = "AREA_SEQ")
    private Long areaSeq;

    @Column(name = "BRNCH_SEQ")
    private Long brnchSeq;

    @Column(name = "PORT_SEQ")
    private Long portSeq;

    @Column(name = "HRID")
    private String hrid;

    @Column(name = "DISB_CLNTS")
    private Long disbClnts;

    @Column(name = "DISB_AMT")
    private Long disbAmt;

    @Column(name = "REF_CD_TRVLNG_ROL")
    private Long refCdTrvlngRol;

    @Column(name = "REF_CD_TRVLNG_ROL_DSCR")
    private String refCdTrvlngRolDscr;

    @Column(name = "REF_CD_CALC_TYP")
    private Long refCdCalcTyp;

    @Column(name = "REF_CD_CALC_TYP_DSCR")
    private String refCdCalcTypDscr;

    @Column(name = "FIELD_TYPE_SEQ")
    private Long fieldTypeSeq;

    @Column(name = "FIELD_TYPE_DSCR")
    private String fieldTypeDscr;

    @Column(name = "INCTVE")
    private Long inctve;

    @Column(name = "TRNS_DATE")
    private Instant trnsDate;

    public Long getMwHrTrvlngDtlSeq() {
        return this.mwHrTrvlngDtlSeq;
    }

    public void setMwHrTrvlngDtlSeq(Long mwHrTrvlngDtlSeq) {
        this.mwHrTrvlngDtlSeq = mwHrTrvlngDtlSeq;
    }

    public Instant getTrvlngMnth() {
        return this.trvlngMnth;
    }

    public void setTrvlngMnth(Instant trvlngMnth) {
        this.trvlngMnth = trvlngMnth;
    }

    public Long getTrvMnthDays() {
        return this.trvMnthDays;
    }

    public void setTrvMnthDays(Long trvMnthDays) {
        this.trvMnthDays = trvMnthDays;
    }

    public Long getWrkngDys() {
        return this.wrkngDys;
    }

    public void setWrkngDys(Long wrkngDys) {
        this.wrkngDys = wrkngDys;
    }

    public Instant getCrtfctnDt() {
        return this.crtfctnDt;
    }

    public void setCrtfctnDt(Instant crtfctnDt) {
        this.crtfctnDt = crtfctnDt;
    }

    public Instant getWrkngStrtDt() {
        return this.wrkngStrtDt;
    }

    public void setWrkngStrtDt(Instant wrkngStrtDt) {
        this.wrkngStrtDt = wrkngStrtDt;
    }

    public Instant getWrkngEndDt() {
        return this.wrkngEndDt;
    }

    public void setWrkngEndDt(Instant wrkngEndDt) {
        this.wrkngEndDt = wrkngEndDt;
    }

    public Long getPerDayValue() {
        return perDayValue;
    }

    public void setPerDayValue(Long perDayValue) {
        this.perDayValue = perDayValue;
    }

    public Long getRegSeq() {
        return this.regSeq;
    }

    public void setRegSeq(Long regSeq) {
        this.regSeq = regSeq;
    }

    public Long getAreaSeq() {
        return this.areaSeq;
    }

    public void setAreaSeq(Long areaSeq) {
        this.areaSeq = areaSeq;
    }

    public Long getBrnchSeq() {
        return this.brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getPortSeq() {
        return this.portSeq;
    }

    public void setPortSeq(Long portSeq) {
        this.portSeq = portSeq;
    }

    public String getHrid() {
        return this.hrid;
    }

    public void setHrid(String hrid) {
        this.hrid = hrid;
    }

    public Long getDisbClnts() {
        return this.disbClnts;
    }

    public void setDisbClnts(Long disbClnts) {
        this.disbClnts = disbClnts;
    }

    public Long getDisbAmt() {
        return this.disbAmt;
    }

    public void setDisbAmt(Long disbAmt) {
        this.disbAmt = disbAmt;
    }

    public Long getRefCdTrvlngRol() {
        return this.refCdTrvlngRol;
    }

    public void setRefCdTrvlngRol(Long refCdTrvlngRol) {
        this.refCdTrvlngRol = refCdTrvlngRol;
    }

    public String getRefCdTrvlngRolDscr() {
        return this.refCdTrvlngRolDscr;
    }

    public void setRefCdTrvlngRolDscr(String refCdTrvlngRolDscr) {
        this.refCdTrvlngRolDscr = refCdTrvlngRolDscr;
    }

    public Long getRefCdCalcTyp() {
        return this.refCdCalcTyp;
    }

    public void setRefCdCalcTyp(Long refCdCalcTyp) {
        this.refCdCalcTyp = refCdCalcTyp;
    }

    public String getRefCdCalcTypDscr() {
        return this.refCdCalcTypDscr;
    }

    public void setRefCdCalcTypDscr(String refCdCalcTypDscr) {
        this.refCdCalcTypDscr = refCdCalcTypDscr;
    }

    public Long getFieldTypeSeq() {
        return this.fieldTypeSeq;
    }

    public void setFieldTypeSeq(Long fieldTypeSeq) {
        this.fieldTypeSeq = fieldTypeSeq;
    }

    public String getFieldTypeDscr() {
        return this.fieldTypeDscr;
    }

    public void setFieldTypeDscr(String fieldTypeDscr) {
        this.fieldTypeDscr = fieldTypeDscr;
    }

    public Long getInctve() {
        return this.inctve;
    }

    public void setInctve(Long inctve) {
        this.inctve = inctve;
    }

    public Instant getTrnsDate() {
        return this.trnsDate;
    }

    public void setTrnsDate(Instant trnsDate) {
        this.trnsDate = trnsDate;
    }
}

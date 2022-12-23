package com.idev4.admin.domain;

import com.idev4.admin.ids.MwAnmlRgstrId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "mw_anml_rgstr")
@IdClass(MwAnmlRgstrId.class)
public class MwAnmlRgstr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "anml_rgstr_seq")
    private Long anmlRgstrSeq;

    @Column(name = "loan_app_seq")
    private Long loanAppSeq;

    @Column(name = "rgstr_cd")
    private String rgstrCd;

    @Column(name = "tag_num")
    private String tagNum;

    @Column(name = "anml_knd")
    private Long anmlKnd;

    @Column(name = "anml_typ")
    private Long anmlTyp;

    @Column(name = "anml_clr")
    private Long anmlClr;

    @Column(name = "anml_brd")
    private String anmlBrd;

    @Column(name = "prch_dt")
    private Instant prchDt;

    @Column(name = "age_yr")
    private Long ageYr;

    @Column(name = "age_mnth")
    private Long ageMnth;

    @Column(name = "prch_amt")
    private Long prchAmt;

    @Column(name = "pic_dt")
    private Instant picDt;

    @Column(name = "anml_pic")
    @Lob
    private String anmlPic;

    @Column(name = "tag_pic")
    @Lob
    private String tagPic;

    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

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

    @Column(name = "ANML_STS")
    private Long anmlSts;

    public Long getAnmlSts() {
        return anmlSts;
    }

    public void setAnmlSts(Long anmlSts) {
        this.anmlSts = anmlSts;
    }

    public Long getAnmlRgstrSeq() {
        return anmlRgstrSeq;
    }

    public void setAnmlRgstrSeq(Long anmlRgstrSeq) {
        this.anmlRgstrSeq = anmlRgstrSeq;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public String getRgstrCd() {
        return rgstrCd;
    }

    public void setRgstrCd(String rgstrCd) {
        this.rgstrCd = rgstrCd;
    }

    public String getTagNum() {
        return tagNum;
    }

    public void setTagNum(String tagNum) {
        this.tagNum = tagNum;
    }

    public Long getAnmlKnd() {
        return anmlKnd;
    }

    public void setAnmlKnd(Long anmlKnd) {
        this.anmlKnd = anmlKnd;
    }

    public Long getAnmlTyp() {
        return anmlTyp;
    }

    public void setAnmlTyp(Long anmlTyp) {
        this.anmlTyp = anmlTyp;
    }

    public Long getAnmlClr() {
        return anmlClr;
    }

    public void setAnmlClr(Long anmlClr) {
        this.anmlClr = anmlClr;
    }

    public String getAnmlBrd() {
        return anmlBrd;
    }

    public void setAnmlBrd(String anmlBrd) {
        this.anmlBrd = anmlBrd;
    }

    public Instant getPrchDt() {
        return prchDt;
    }

    public void setPrchDt(Instant prchDt) {
        this.prchDt = prchDt;
    }

    public Long getAgeYr() {
        return ageYr;
    }

    public void setAgeYr(Long ageYr) {
        this.ageYr = ageYr;
    }

    public Long getAgeMnth() {
        return ageMnth;
    }

    public void setAgeMnth(Long ageMnth) {
        this.ageMnth = ageMnth;
    }

    public Long getPrchAmt() {
        return prchAmt;
    }

    public void setPrchAmt(Long prchAmt) {
        this.prchAmt = prchAmt;
    }

    public Instant getPicDt() {
        return picDt;
    }

    public void setPicDt(Instant picDt) {
        this.picDt = picDt;
    }

    public String getAnmlPic() {
        return anmlPic;
    }

    public void setAnmlPic(String anmlPic) {
        this.anmlPic = anmlPic;
    }

    public String getTagPic() {
        return tagPic;
    }

    public void setTagPic(String tagPic) {
        this.tagPic = tagPic;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
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

}

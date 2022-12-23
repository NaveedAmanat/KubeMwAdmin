package com.idev4.admin.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "MW_SANC_LIST")
public class MwSancList implements Serializable {
    @Id
    @GeneratedValue(generator = "SANCTION_LIST_SEQ")
    @SequenceGenerator(name = "SANCTION_LIST_SEQ", sequenceName = "SANCTION_LIST_SEQ", allocationSize = 1)
    @Column(name = "SANC_SEQ")
    private Long sancSeq;

    @Column(name = "NATIONAL_ID")
    private String nationalId;

    @Column(name = "CNIC_NUM")
    private String cnicNum;

    @Column(name = "FIRST_NM")
    private String frstNm;

    @Column(name = "LAST_NM")
    private String lastNm;

    @Column(name = "FATHER_NM")
    private String fatherNm;

    @Column(name = "CNTRY")
    private String cntry;

    @Column(name = "PRVNCE")
    private String prvnce;

    @Column(name = "DSTRCT")
    private String dstrct;

    @Column(name = "DOB")
    private Date dob;

    @Column(name = "DOB_FRMT")
    private String dobFrmt;

    @Column(name = "REF_NO")
    private String refNo;

    @Column(name = "SANC_TYPE")
    private String sancType;

    @Column(name = "IS_VALID_CNIC")
    private Long isValidCnic;

    @Column(name = "IS_MTCH_FOUND")
    private Long isMtchFound;

    @Column(name = "REMRKS")
    @Lob
    private String remarks;

    @Column(name = "CRNT_REC_FLG")
    private Long crntRecFlg;

    @Column(name = "DEL_FLG")
    private Long delFlg;

    @Column(name = "PROCESD_REC_FLG")
    private Long procesdRecFlg;

    @Column(name = "TAG_CLNT_FLG")
    private Long tagClntFlg;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Date crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private Date lastUpdDt;

    public Long getSancSeq() {
        return sancSeq;
    }

    public void setSancSeq(Long sancSeq) {
        this.sancSeq = sancSeq;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getCnicNum() {
        return cnicNum;
    }

    public void setCnicNum(String cnicNum) {
        this.cnicNum = cnicNum;
    }

    public String getFrstNm() {
        return frstNm;
    }

    public void setFrstNm(String frstNm) {
        this.frstNm = frstNm;
    }

    public String getLastNm() {
        return lastNm;
    }

    public void setLastNm(String lastNm) {
        this.lastNm = lastNm;
    }

    public String getFatherNm() {
        return fatherNm;
    }

    public void setFatherNm(String fatherNm) {
        this.fatherNm = fatherNm;
    }

    public String getCntry() {
        return cntry;
    }

    public void setCntry(String cntry) {
        this.cntry = cntry;
    }

    public String getPrvnce() {
        return prvnce;
    }

    public void setPrvnce(String prvnce) {
        this.prvnce = prvnce;
    }

    public String getDstrct() {
        return dstrct;
    }

    public void setDstrct(String dstrct) {
        this.dstrct = dstrct;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getDobFrmt() {
        return dobFrmt;
    }

    public void setDobFrmt(String dobFrmt) {
        this.dobFrmt = dobFrmt;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getSancType() {
        return sancType;
    }

    public void setSancType(String sancType) {
        this.sancType = sancType;
    }

    public Long getIsValidCnic() {
        return isValidCnic;
    }

    public void setIsValidCnic(Long isValidCnic) {
        this.isValidCnic = isValidCnic;
    }

    public Long getIsMtchFound() {
        return isMtchFound;
    }

    public void setIsMtchFound(Long isMtchFound) {
        this.isMtchFound = isMtchFound;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Long crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Long delFlg) {
        this.delFlg = delFlg;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Date getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Date crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Date getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Date lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Long getProcesdRecFlg() {
        return procesdRecFlg;
    }

    public void setProcesdRecFlg(Long procesdRecFlg) {
        this.procesdRecFlg = procesdRecFlg;
    }

    public Long getTagClntFlg() {
        return tagClntFlg;
    }

    public void setTagClntFlg(Long tagClntFlg) {
        this.tagClntFlg = tagClntFlg;
    }

    @Override
    public String toString() {
        return "MwSancList{" +
                "sancSeq=" + sancSeq +
                ", nationalId='" + nationalId + '\'' +
                ", cnicNum='" + cnicNum + '\'' +
                ", frstNm='" + frstNm + '\'' +
                ", lastNm='" + lastNm + '\'' +
                ", fatherNm='" + fatherNm + '\'' +
                ", cntry='" + cntry + '\'' +
                ", prvnce='" + prvnce + '\'' +
                ", dstrct='" + dstrct + '\'' +
                ", dob=" + dob +
                ", dobFrmt='" + dobFrmt + '\'' +
                ", refNo='" + refNo + '\'' +
                ", sancType='" + sancType + '\'' +
                ", isValidCnic=" + isValidCnic +
                ", isMtchFound=" + isMtchFound +
                ", remarks='" + remarks + '\'' +
                ", crntRecFlg=" + crntRecFlg +
                ", delFlg=" + delFlg +
                ", procesdRecFlg=" + procesdRecFlg +
                ", tagClntFlg=" + tagClntFlg +
                ", crtdBy='" + crtdBy + '\'' +
                ", crtdDt=" + crtdDt +
                ", lastUpdBy='" + lastUpdBy + '\'' +
                ", lastUpdDt=" + lastUpdDt +
                '}';
    }
}

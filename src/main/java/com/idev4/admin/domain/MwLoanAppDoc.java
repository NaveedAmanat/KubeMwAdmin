package com.idev4.admin.domain;

import com.idev4.admin.ids.MwLoanAppDocId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwLoanAppDoc.
 */
@Entity
@Table(name = "mw_loan_app_doc")
@IdClass(MwLoanAppDocId.class)
public class MwLoanAppDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "loan_app_doc_seq")
    private Long loanAppDocSeq;

    @Id
    @Column(name = "loan_app_seq")
    private Long loanAppSeq;

    @Id
    @Column(name = "doc_seq")
    private Long docSeq;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "doc_img")
    @Lob
    private String docImg;

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

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    @Column(name = "CNIC_NUM")
    private Long cnicNum;

    @Column(name = "COMPANY_NM")
    private String companyNm;

    @Column(name = "NOM_CNIC_NUM")
    private Long nomCnicNum;

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getLoanAppDocSeq() {
        return loanAppDocSeq;
    }

    public void setLoanAppDocSeq(Long loanAppDocSeq) {
        this.loanAppDocSeq = loanAppDocSeq;
    }

    public MwLoanAppDoc loanAppDocSeq(Long loanAppDocSeq) {
        this.loanAppDocSeq = loanAppDocSeq;
        return this;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public MwLoanAppDoc loanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
        return this;
    }

    public Long getDocSeq() {
        return docSeq;
    }

    public void setDocSeq(Long docSeq) {
        this.docSeq = docSeq;
    }

    public MwLoanAppDoc docSeq(Long docSeq) {
        this.docSeq = docSeq;
        return this;
    }

    public String getDocImg() {
        return docImg;
    }

    public void setDocImg(String docImg) {
        this.docImg = docImg;
    }

    public MwLoanAppDoc docImg(String docImg) {
        this.docImg = docImg;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwLoanAppDoc crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwLoanAppDoc crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwLoanAppDoc lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwLoanAppDoc lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwLoanAppDoc delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwLoanAppDoc effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwLoanAppDoc effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwLoanAppDoc crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Long getCnicNum() {
        return cnicNum;
    }

    public void setCnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
    }

    public String getCompanyNm() {
        return companyNm;
    }

    public void setCompanyNm(String companyNm) {
        this.companyNm = companyNm;
    }

    public Long getNomCnicNum() {
        return nomCnicNum;
    }

    public void setNomCnicNum(Long nomCnicNum) {
        this.nomCnicNum = nomCnicNum;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwLoanAppDoc mwLoanAppDoc = (MwLoanAppDoc) o;
        if (mwLoanAppDoc.getLoanAppDocSeq() == null || getLoanAppDocSeq() == null) {
            return false;
        }
        return Objects.equals(getLoanAppDocSeq(), mwLoanAppDoc.getLoanAppDocSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLoanAppDocSeq());
    }

    @Override
    public String toString() {
        return "MwLoanAppDoc{" +
                "loanAppDocSeq=" + loanAppDocSeq +
                ", loanAppSeq=" + loanAppSeq +
                ", docSeq=" + docSeq +
                ", crntRecFlg=" + crntRecFlg +
                ", docImg='" + docImg + '\'' +
                ", crtdBy='" + crtdBy + '\'' +
                ", crtdDt=" + crtdDt +
                ", lastUpdBy='" + lastUpdBy + '\'' +
                ", lastUpdDt=" + lastUpdDt +
                ", delFlg=" + delFlg +
                ", effStartDt=" + effStartDt +
                ", effEndDt=" + effEndDt +
                ", syncFlg=" + syncFlg +
                ", cnicNum=" + cnicNum +
                ", companyNm='" + companyNm + '\'' +
                ", nomCnicNum=" + nomCnicNum +
                '}';
    }
}

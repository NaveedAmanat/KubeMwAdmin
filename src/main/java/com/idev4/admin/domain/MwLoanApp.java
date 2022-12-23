package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwLoanApp.
 */
@Entity
@Table(name = "mw_loan_App")
public class MwLoanApp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "clnt_seq")
    public Long clntSeq;
    @Column(name = "prd_seq")
    public Long prdSeq;
    @Column(name = "port_SEQ")
    public Long portSeq;
    @Column(name = "loan_App_id")
    public Long loanAppId;
    @Column(name = "LOAN_APP_PVRTY_SCR")
    public Long pscScore;
    @Column(name = "LOAN_APP_STS_DT")
    public Instant loanAppStsDt;
    @Column(name = "PRNT_LOAN_APP_SEQ")
    public Long prntLoanAppSeq;
    @Id
    @Column(name = "loan_app_seq")
    private Long loanAppSeq;
    @Column(name = "loan_id")
    private String loanId;
    @Column(name = "loan_cycl_num")
    private Long loanCyclNum;
    @Column(name = "rqstd_loan_amt")
    private Double rqstdLoanAmt;
    @Column(name = "aprvd_loan_amt")
    private Double aprvdLoanAmt;
    @Column(name = "rcmnd_loan_amt")
    private Double rcmndLoanAmt;
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
    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;
    @Column(name = "loan_app_sts")
    private Long loanAppSts;
    @Column(name = "cmnt")
    private String cmnt;
    @Column(name = "REJECTION_REASON_CD")
    private Long rejectionReasonCd;
    @Column(name = "TBL_SCRN_FLG")
    private Boolean tblScrn;
    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    @Column(name = "rel_addr_as_clnt_flg")
    private Boolean relAddrAsClntFLg;

    @Column(name = "co_bwr_addr_as_clnt_flg")
    private Boolean coBwrAddrAsClntFlg;

    @Column(name = "LOAN_UTL_STS_SEQ")
    private Long loanUtlStsSeq;

    @Column(name = "LOAN_UTL_CMNT")
    private String loanUtlCmnt;

    @Column(name = "CRDT_BND")
    private String crdtBnd;

    @Column(name = "APP_VRSN")
    private String appVrsn;


    @Column(name = "IS_KRK_VALID")
    private Integer isKrkValid;

    @Column(name = "DNR_SEQ")
    private Integer dnrSeq;

    @Column(name = "OD_CHK_FLG")
    private Boolean odChkFlg;

    public Integer getDnrSeq() {
        return dnrSeq;
    }

    public void setDnrSeq(Integer dnrSeq) {
        this.dnrSeq = dnrSeq;
    }

    public Integer getIsKrkValid() {
        return isKrkValid;
    }

    public void setIsKrkValid(Integer isKrkValid) {
        this.isKrkValid = isKrkValid;
    }

    public String getAppVrsn() {
        return appVrsn;
    }

    public void setAppVrsn(String appVrsn) {
        this.appVrsn = appVrsn;
    }

    public String getCrdtBnd() {
        return crdtBnd;
    }

    public void setCrdtBnd(String crdtBnd) {
        this.crdtBnd = crdtBnd;
    }

    public Long getLoanUtlStsSeq() {
        return loanUtlStsSeq;
    }

    public void setLoanUtlStsSeq(Long loanUtlStsSeq) {
        this.loanUtlStsSeq = loanUtlStsSeq;
    }

    public String getLoanUtlCmnt() {
        return loanUtlCmnt;
    }

    public void setLoanUtlCmnt(String loanUtlCmnt) {
        this.loanUtlCmnt = loanUtlCmnt;
    }

    public Boolean getRelAddrAsClntFLg() {
        return relAddrAsClntFLg;
    }

    public void setRelAddrAsClntFLg(Boolean relAddrAsClntFLg) {
        this.relAddrAsClntFLg = relAddrAsClntFLg;
    }

    public Boolean getCoBwrAddrAsClntFlg() {
        return coBwrAddrAsClntFlg;
    }

    public void setCoBwrAddrAsClntFlg(Boolean coBwrAddrAsClntFlg) {
        this.coBwrAddrAsClntFlg = coBwrAddrAsClntFlg;
    }

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getPrntLoanAppSeq() {
        return prntLoanAppSeq;
    }

    public void setPrntLoanAppSeq(Long prntLoanAppSeq) {
        this.prntLoanAppSeq = prntLoanAppSeq;
    }

    public Boolean getTblScrn() {
        return tblScrn;
    }

    public void setTblScrn(Boolean tblScrn) {
        this.tblScrn = tblScrn;
    }

    public Instant getLoanAppStsDt() {
        return loanAppStsDt;
    }

    public void setLoanAppStsDt(Instant loanAppStsDt) {
        this.loanAppStsDt = loanAppStsDt;
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

    public Long getPscScore() {
        return pscScore;
    }

    public void setPscScore(Long pscScore) {
        this.pscScore = pscScore;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public MwLoanApp loanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
        return this;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public MwLoanApp loanId(String loanId) {
        this.loanId = loanId;
        return this;
    }

    public Long getLoanCyclNum() {
        return loanCyclNum;
    }

    public void setLoanCyclNum(Long loanCyclNum) {
        this.loanCyclNum = loanCyclNum;
    }

    public MwLoanApp loanCyclNum(Long loanCyclNum) {
        this.loanCyclNum = loanCyclNum;
        return this;
    }

    public Double getRqstdLoanAmt() {
        return rqstdLoanAmt;
    }

    public void setRqstdLoanAmt(Double rqstdLoanAmt) {
        this.rqstdLoanAmt = rqstdLoanAmt;
    }

    public MwLoanApp rqstdLoanAmt(Double rqstdLoanAmt) {
        this.rqstdLoanAmt = rqstdLoanAmt;
        return this;
    }

    public Double getAprvdLoanAmt() {
        return aprvdLoanAmt;
    }

    public void setAprvdLoanAmt(Double aprvdLoanAmt) {
        this.aprvdLoanAmt = aprvdLoanAmt;
    }

    public MwLoanApp aprvdLoanAmt(Double aprvdLoanAmt) {
        this.aprvdLoanAmt = aprvdLoanAmt;
        return this;
    }

    public Double getRcmndLoanAmt() {
        return rcmndLoanAmt;
    }

    public void setRcmndLoanAmt(Double rcmndLoanAmt) {
        this.rcmndLoanAmt = rcmndLoanAmt;
    }

    public MwLoanApp rcmndLoanAmt(Double rcmndLoanAmt) {
        this.rcmndLoanAmt = rcmndLoanAmt;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwLoanApp crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwLoanApp crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwLoanApp lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwLoanApp lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwLoanApp delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwLoanApp effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwLoanApp effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwLoanApp crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public Long getLoanAppSts() {
        return loanAppSts;
    }

    public void setLoanAppSts(Long loanAppSts) {
        this.loanAppSts = loanAppSts;
    }

    public MwLoanApp loanAppSts(Long loanAppSts) {
        this.loanAppSts = loanAppSts;
        return this;
    }

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }

    public MwLoanApp cmnt(String cmnt) {
        this.cmnt = cmnt;
        return this;
    }

    public Long getRejectionReasonCd() {
        return rejectionReasonCd;
    }

    public void setRejectionReasonCd(Long rejectionReasonCd) {
        this.rejectionReasonCd = rejectionReasonCd;
    }

    public MwLoanApp rejectionReasonCd(Long rejectionReasonCd) {
        this.rejectionReasonCd = rejectionReasonCd;
        return this;
    }

    public Long getClntSeq() {
        return clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public Long getPortSeq() {
        return portSeq;
    }

    public void setPortSeq(Long portSeq) {
        this.portSeq = portSeq;
    }

    public Long getLoanAppId() {
        return loanAppId;
    }

    public void setLoanAppId(Long loanAppId) {
        this.loanAppId = loanAppId;
    }

    public Boolean getOdChkFlg() {
        return odChkFlg;
    }

    public void setOdChkFlg(Boolean odChkFlg) {
        this.odChkFlg = odChkFlg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwLoanApp mwLoanApp = (MwLoanApp) o;
        if (mwLoanApp.getLoanAppSeq() == null || getLoanAppSeq() == null) {
            return false;
        }
        return Objects.equals(getLoanAppSeq(), mwLoanApp.getLoanAppSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLoanAppSeq());
    }


    @Override
    public String toString() {
        return "MwLoanApp{" +
                "loanAppSeq=" + loanAppSeq +
                ", loanId='" + loanId + '\'' +
                ", loanCyclNum=" + loanCyclNum +
                ", rqstdLoanAmt=" + rqstdLoanAmt +
                ", aprvdLoanAmt=" + aprvdLoanAmt +
                ", rcmndLoanAmt=" + rcmndLoanAmt +
                ", crtdBy='" + crtdBy + '\'' +
                ", crtdDt=" + crtdDt +
                ", lastUpdBy='" + lastUpdBy + '\'' +
                ", lastUpdDt=" + lastUpdDt +
                ", delFlg=" + delFlg +
                ", effStartDt=" + effStartDt +
                ", effEndDt=" + effEndDt +
                ", crntRecFlg=" + crntRecFlg +
                ", loanAppSts=" + loanAppSts +
                ", cmnt='" + cmnt + '\'' +
                ", rejectionReasonCd=" + rejectionReasonCd +
                ", clntSeq=" + clntSeq +
                ", prdSeq=" + prdSeq +
                ", portSeq=" + portSeq +
                ", loanAppId=" + loanAppId +
                ", pscScore=" + pscScore +
                ", loanAppStsDt=" + loanAppStsDt +
                ", tblScrn=" + tblScrn +
                ", prntLoanAppSeq=" + prntLoanAppSeq +
                ", syncFlg=" + syncFlg +
                ", relAddrAsClntFLg=" + relAddrAsClntFLg +
                ", coBwrAddrAsClntFlg=" + coBwrAddrAsClntFlg +
                ", loanUtlStsSeq=" + loanUtlStsSeq +
                ", loanUtlCmnt='" + loanUtlCmnt + '\'' +
                ", crdtBnd='" + crdtBnd + '\'' +
                ", appVrsn='" + appVrsn + '\'' +
                ", isKrkValid=" + isKrkValid +
                ", dnrSeq=" + dnrSeq +
                ", odChkFlg=" + odChkFlg +
                '}';
    }
}

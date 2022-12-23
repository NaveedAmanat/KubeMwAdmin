package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * @Added, Naveed
 * @Date, 15-09-2022
 * @Description, SCR - Vehicle loan
 */

@Entity
@Table(name = "MW_VEHICLE_INFO")
public class MwVehicleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VHCLE_SEQ")
    private Long vhcleSeq;

    @Column(name = "EFF_START_DT")
    private Instant effStartDt;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "OWNER_NM")
    private String ownerNm;

    @Column(name = "VHCLE_REGTRN_NO")
    private String vhcleRegtrnNo;

    @Column(name = "REF_CD_VHCL_MKR_SEQ")
    private Long refCdVhclMakerSeq;

    @Column(name = "REF_CD_ENGN_PWR_CC_SEQ")
    private Long engnePwrCc;

    @Column(name = "REF_CD_VHCL_MDL_YR")
    private String vhcleModelYr;

    @Column(name = "ENGNE_NO")
    private String engneNo;

    @Column(name = "CHASSIS_NO")
    private String chassisNO;

    @Column(name = "INSURD_AMT")
    private Long insurdAmt;

    @Column(name = "PRCHSE_DT")
    private Instant prchseDt;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "REF_CD_VHCL_CLR_SEQ")
    private Long vhcleColor;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    @Column(name = "DEL_FLG")
    private Boolean delFlg;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "CRTD_BY")
    private String crdtBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "EFF_END_DT")
    private Instant effEndDt;

    public MwVehicleInfo() {
    }

    public Long getVhcleSeq() {
        return vhcleSeq;
    }

    public void setVhcleSeq(Long vhcleSeq) {
        this.vhcleSeq = vhcleSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public String getOwnerNm() {
        return ownerNm;
    }

    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    public String getVhcleRegtrnNo() {
        return vhcleRegtrnNo;
    }

    public void setVhcleRegtrnNo(String vhcleRegtrnNo) {
        this.vhcleRegtrnNo = vhcleRegtrnNo;
    }

    public Long getRefCdVhclMakerSeq() {
        return refCdVhclMakerSeq;
    }

    public void setRefCdVhclMakerSeq(Long refCdVhclMakerSeq) {
        this.refCdVhclMakerSeq = refCdVhclMakerSeq;
    }

    public String getVhcleModelYr() {
        return vhcleModelYr;
    }

    public void setVhcleModelYr(String vhcleModelYr) {
        this.vhcleModelYr = vhcleModelYr;
    }

    public Long getEngnePwrCc() {
        return engnePwrCc;
    }

    public void setEngnePwrCc(Long engnePwrCc) {
        this.engnePwrCc = engnePwrCc;
    }

    public String getEngneNo() {
        return engneNo;
    }

    public void setEngneNo(String engneNo) {
        this.engneNo = engneNo;
    }

    public String getChassisNO() {
        return chassisNO;
    }

    public void setChassisNO(String chassisNO) {
        this.chassisNO = chassisNO;
    }

    public Long getInsurdAmt() {
        return insurdAmt;
    }

    public void setInsurdAmt(Long insurdAmt) {
        this.insurdAmt = insurdAmt;
    }

    public Instant getPrchseDt() {
        return prchseDt;
    }

    public void setPrchseDt(Instant prchseDt) {
        this.prchseDt = prchseDt;
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

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Long getVhcleColor() {
        return vhcleColor;
    }

    public void setVhcleColor(Long vhcleColor) {
        this.vhcleColor = vhcleColor;
    }
}

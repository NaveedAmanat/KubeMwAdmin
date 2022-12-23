package com.idev4.admin.domain;

import javax.persistence.*;

@Entity
@Table(name = "MW_PNL_HSPTLS_REL")
public class MwPnlHsptlsRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BRNCH_SEQ")
    private Long brnchSeq;

    @Column(name = "HSPTLS_ID")
    private Long hsptlsId;

    @Column(name = "DISTANCE")
    private Double distance;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "CRNT_REC_FLG")
    private Long crntRecFlg;

    @Column(name = "DEL_FLG")
    private Long delFlg;

    @Column(name = "CRTD_DT")
    private java.util.Date crtdDt;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "LAST_UPD_DT")
    private java.util.Date lastUpdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrnchSeq() {
        return this.brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getHsptlsId() {
        return this.hsptlsId;
    }

    public void setHsptlsId(Long hsptlsId) {
        this.hsptlsId = hsptlsId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getRemarks() {
        return this.remarks;
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

    public java.util.Date getCrtdDt() {
        return this.crtdDt;
    }

    public void setCrtdDt(java.util.Date crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getCrtdBy() {
        return this.crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public java.util.Date getLastUpdDt() {
        return this.lastUpdDt;
    }

    public void setLastUpdDt(java.util.Date lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public String getLastUpdBy() {
        return this.lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }
}

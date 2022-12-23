package com.idev4.admin.domain;

import javax.persistence.*;

@Entity
@Table(name = "MW_PNL_HSPTLS")
public class MwPnlHsptls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "HSPTLS_NM")
    private String hsptlsNm;

    @Column(name = "HSPTLS_ADDR")
    private String hsptlsAddr;

    @Column(name = "HSPTLS_PH")
    private String hsptlsPh;

    @Column(name = "HSPTLS_TYP_SEQ")
    private Long hsptlsTypSeq;

    @Column(name = "HSPTLS_STS_SEQ")
    private Long hsptlsStsSeq;

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

    public String getHsptlsNm() {
        return this.hsptlsNm;
    }

    public void setHsptlsNm(String hsptlsNm) {
        this.hsptlsNm = hsptlsNm;
    }

    public String getHsptlsAddr() {
        return this.hsptlsAddr;
    }

    public void setHsptlsAddr(String hsptlsAddr) {
        this.hsptlsAddr = hsptlsAddr;
    }

    public String getHsptlsPh() {
        return this.hsptlsPh;
    }

    public void setHsptlsPh(String hsptlsPh) {
        this.hsptlsPh = hsptlsPh;
    }

    public Long getHsptlsTypSeq() {
        return hsptlsTypSeq;
    }

    public void setHsptlsTypSeq(Long hsptlsTypSeq) {
        this.hsptlsTypSeq = hsptlsTypSeq;
    }

    public Long getHsptlsStsSeq() {
        return hsptlsStsSeq;
    }

    public void setHsptlsStsSeq(Long hsptlsStsSeq) {
        this.hsptlsStsSeq = hsptlsStsSeq;
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

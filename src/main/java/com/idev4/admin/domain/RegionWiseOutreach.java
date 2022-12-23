package com.idev4.admin.domain;

import com.idev4.admin.ids.RegionWiseOutreachId;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "REGION_WISE_OUTREACH")
@IdClass(RegionWiseOutreachId.class)
public class RegionWiseOutreach {

    //private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "OUTREACH_MONTH")
    private Instant outreachMonth;

    @Column(name = "OPENING")
    private Long opening;

    @Column(name = "TARGETS")
    private Long targets;

    @Column(name = "MATURING_LOANS")
    private Long maturingLoans;

    @Column(name = "CLOSING")
    private Long closing;

    @Column(name = "TRANS_DATE")
    private Instant transDate;

    @Column(name = "REGION_CD")
    private Long regionCd;

    public Instant getOutreachMonth() {
        return this.outreachMonth;
    }

    public void setOutreachMonth(Instant outreachMonth) {
        this.outreachMonth = outreachMonth;
    }

    public Long getOpening() {
        return this.opening;
    }

    public void setOpening(Long opening) {
        this.opening = opening;
    }

    public Long getTargets() {
        return this.targets;
    }

    public void setTargets(Long targets) {
        this.targets = targets;
    }

    public Long getMaturingLoans() {
        return this.maturingLoans;
    }

    public void setMaturingLoans(Long maturingLoans) {
        this.maturingLoans = maturingLoans;
    }

    public Long getClosing() {
        return this.closing;
    }

    public void setClosing(Long closing) {
        this.closing = closing;
    }

    public Instant getTransDate() {
        return this.transDate;
    }

    public void setTransDate(Instant transDate) {
        this.transDate = transDate;
    }

    public Long getRegionCd() {
        return this.regionCd;
    }

    public void setRegionCd(Long regionCd) {
        this.regionCd = regionCd;
    }
}

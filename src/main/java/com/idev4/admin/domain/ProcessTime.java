package com.idev4.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROCESS_TIME")
public class ProcessTime {
    @Column(name = "PROCESS")
    private String process;

    @Column(name = "DT_TIME")
    private java.util.Date dtTime;

    @Column(name = "REMARKS")
    private String remarks;

    @Id
    @Column(name = "PK_SEQ")
    private Long pkSeq;

    public String getProcess() {
        return this.process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public java.util.Date getDtTime() {
        return this.dtTime;
    }

    public void setDtTime(java.util.Date dtTime) {
        this.dtTime = dtTime;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getPkSeq() {
        return this.pkSeq;
    }

    public void setPkSeq(Long pkSeq) {
        this.pkSeq = pkSeq;
    }
}

package com.idev4.admin.service;

/*
Authored by Areeba
Dated 17-3-2022
Monthly Accounts Reporting
*/

import com.idev4.admin.domain.ProcessTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MonAccProcessesService {

    private final Logger log = LoggerFactory.getLogger(MonAccProcessesService.class);

    private final EntityManager entityManager;

    public MonAccProcessesService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Map<String, Object> callPrcMonProcesses() {
        Map<String, Object> mapResp = new HashMap<>();

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("KASHF_REPORTING.MON_PROCESSES");
        storedProcedure.registerStoredProcedureParameter("p_msg_out", String.class, ParameterMode.OUT);

        storedProcedure.execute();

        String parmOutputProcedure = storedProcedure.getOutputParameterValue("p_msg_out").toString();

        if (parmOutputProcedure.contains("running") || parmOutputProcedure.contains("completed")) {
            log.info("MON_PROCESSES: executed.");
            mapResp.put("Response", parmOutputProcedure);
        } else if (parmOutputProcedure.contains("success")) {
            log.info("MON_PROCESSES: success.");
            mapResp.put("Response", "SUCCESS");
        } else {
            log.info("MON_PROCESSES: Execution Unsuccessful.");
            mapResp.put("Response", "ERROR");
        }
        return mapResp;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getProcesses(Integer pageIndex, Integer pageSize, Boolean isCount) {

        String queryScript = " SELECT * FROM KASHF_REPORTING.PROCESS_TIME PT ";

        String queryCount = " SELECT COUNT(*) FROM KASHF_REPORTING.PROCESS_TIME ";

        List<ProcessTime> processList = entityManager.createNativeQuery(queryScript + "\r\n order by 4 asc", ProcessTime.class)
                .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("Processes", processList);

        Long totalCount = 0L;
        if (isCount.booleanValue()) {
            totalCount = new BigDecimal(
                    entityManager.createNativeQuery(queryCount)
                            .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCount);

        return response;
    }


}

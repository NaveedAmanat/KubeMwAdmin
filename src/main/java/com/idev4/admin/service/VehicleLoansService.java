package com.idev4.admin.service;

import com.idev4.admin.domain.MwLoanAppDoc;
import com.idev4.admin.domain.MwVehicleInfo;
import com.idev4.admin.dto.VehicleLoanDto;
import com.idev4.admin.repository.MwLoanAppDocRepository;
import com.idev4.admin.repository.MwVehicleInfoRepository;
import com.idev4.admin.web.rest.util.SequenceFinder;
import com.idev4.admin.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
public class VehicleLoansService {
    private final Logger log = LoggerFactory.getLogger(VehicleLoansService.class);
    @Autowired
    Utils utils;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MwVehicleInfoRepository mwVehicleInfoRepository;
    @Autowired
    private MwLoanAppDocRepository mwLoanAppDocRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> getAllActiveVehicleLoans(Long branchSeq, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        log.info("Active Vehicle Loans --> pageIndex = " + pageIndex + ", pageSize = " + pageSize + ", filter = " + filter);

        String script = "  SELECT CLNT.CLNT_ID,\n" +
                "         CLNT.CLNT_SEQ,\n" +
                "         APP.LOAN_APP_SEQ,\n" +
                "         APP.APRVD_LOAN_AMT,\n" +
                "         APP.LOAN_CYCL_NUM,\n" +
                "         CLNT.FRST_NM || ' ' || CLNT.LAST_NM       AS CLNT_NM,\n" +
                "         TO_CHAR (VH.DSBMT_DT, 'yyyy-MM-dd')       AS DSBMT_DT,\n" +
                "         APP.PRD_SEQ,\n" +
                "         MP.PRD_NM,\n" +
                "         MP.PRD_CMNT,\n" +
                "         MVI.INSURD_AMT,\n" +
                "         MVI.OWNER_NM,\n" +
                "         MVI.VHCLE_REGTRN_NO,\n" +
                "         MVI.REF_CD_VHCL_MKR_SEQ,\n" +
                "         MVI.REF_CD_VHCL_MDL_YR,\n" +
                "         MVI.REF_CD_ENGN_PWR_CC_SEQ,\n" +
                "         MVI.ENGNE_NO,\n" +
                "         MVI.CHASSIS_NO,\n" +
                "         TO_CHAR (MVI.PRCHSE_DT, 'yyyy-MM-dd')     AS PRCHSE_DT,\n" +
                "         BR.BRNCH_NM,\n" +
                "         BR.BRNCH_DSCR,\n" +
                "         AR.AREA_NM,\n" +
                "         AR.AREA_DSCR,\n" +
                "         REG.REG_NM,\n" +
                "         REG.REG_DSCR,\n" +
                "         MVI.REF_CD_VHCL_CLR_SEQ,\n" +
                "         MVI.VHCLE_SEQ\n" +
                "    FROM MW_LOAN_APP APP\n" +
                "         JOIN MW_CLNT CLNT\n" +
                "             ON CLNT.CLNT_SEQ = APP.CLNT_SEQ AND CLNT.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_DSBMT_VCHR_HDR VH\n" +
                "             ON VH.LOAN_APP_SEQ = APP.LOAN_APP_SEQ AND VH.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_DSBMT_VCHR_DTL DTL\n" +
                "             ON DTL.DSBMT_HDR_SEQ = VH.DSBMT_HDR_SEQ AND DTL.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_BRNCH BR\n" +
                "             ON BR.BRNCH_SEQ = APP.BRNCH_SEQ AND BR.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_AREA AR ON AR.AREA_SEQ = BR.AREA_SEQ AND AR.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_REG REG ON REG.REG_SEQ = AR.REG_SEQ AND REG.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_PRD MP\n" +
                "             ON     MP.PRD_SEQ = APP.PRD_SEQ\n" +
                "                AND MP.CRNT_REC_FLG = 1\n" +
                "                AND MP.PRD_TYP_KEY != 1165 AND MP.PRD_GRP_SEQ = 22\n" +
                "         LEFT OUTER JOIN MW_VEHICLE_INFO MVI\n" +
                "             ON MVI.LOAN_APP_SEQ = APP.LOAN_APP_SEQ AND MVI.CRNT_REC_FLG = 1\n" +
                "   WHERE     APP.BRNCH_SEQ = :P_BRNCH_SEQ\n" +
                "         AND APP.LOAN_APP_STS = 703\n" +
                "         AND APP.CRNT_REC_FLG = 1 ";

        String scriptCount = "  SELECT COUNT (*)\n" +
                " FROM MW_LOAN_APP APP\n" +
                "         JOIN MW_CLNT CLNT\n" +
                "             ON CLNT.CLNT_SEQ = APP.CLNT_SEQ AND CLNT.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_DSBMT_VCHR_HDR VH\n" +
                "             ON VH.LOAN_APP_SEQ = APP.LOAN_APP_SEQ AND VH.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_DSBMT_VCHR_DTL DTL\n" +
                "             ON DTL.DSBMT_HDR_SEQ = VH.DSBMT_HDR_SEQ AND DTL.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_BRNCH BR\n" +
                "             ON BR.BRNCH_SEQ = APP.BRNCH_SEQ AND BR.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_AREA AR ON AR.AREA_SEQ = BR.AREA_SEQ AND AR.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_REG REG ON REG.REG_SEQ = AR.REG_SEQ AND REG.CRNT_REC_FLG = 1\n" +
                "         JOIN MW_PRD MP\n" +
                "             ON     MP.PRD_SEQ = APP.PRD_SEQ\n" +
                "                AND MP.CRNT_REC_FLG = 1\n" +
                "                AND MP.PRD_TYP_KEY != 1165 AND MP.PRD_GRP_SEQ = 22\n" +
                "         LEFT OUTER JOIN MW_VEHICLE_INFO MVI\n" +
                "             ON MVI.LOAN_APP_SEQ = APP.LOAN_APP_SEQ AND MVI.CRNT_REC_FLG = 1\n" +
                "   WHERE     APP.BRNCH_SEQ = :P_BRNCH_SEQ\n" +
                "         AND APP.LOAN_APP_STS = 703\n" +
                "         AND APP.CRNT_REC_FLG = 1";

        if (filter != null && filter.length() > 0) {
            String search = " AND (CLNT.CLNT_SEQ LIKE '%?%') OR (APP.LOAN_APP_SEQ LIKE '%?%') OR (LOWER(CLNT.FRST_NM) LIKE '%?%') OR (LOWER(CLNT.LAST_NM) LIKE '%?%') "
                    .replace("?", filter.toLowerCase());
            script += search;
            scriptCount += search;
        }

        List<Object[]> records = entityManager.createNativeQuery(script + "\n ORDER BY VH.DSBMT_DT DESC")
                .setParameter("P_BRNCH_SEQ", branchSeq).setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<VehicleLoanDto> lists = new ArrayList();

        records.forEach(r -> {
            VehicleLoanDto dto = new VehicleLoanDto();

            dto.clntId = r[0] == null ? "0" : r[0].toString();
            dto.clntSeq = r[1] == null ? "0" : r[1].toString();
            dto.loanAppSeq = r[2] == null ? "0" : r[2].toString();
            dto.aprvdAmt = r[3] == null ? "0" : r[3].toString();
            dto.cyclNum = r[4] == null ? "0" : r[4].toString();
            dto.clntName = r[5] == null ? "" : r[5].toString();
            dto.dsbmtDt = r[6] == null ? "" : r[6].toString();
            dto.prdSeq = r[7] == null ? "" : r[7].toString();
            dto.prdNm = r[8] == null ? "" : r[8].toString();
            dto.prdDscr = r[9] == null ? "" : r[9].toString();
            dto.insurdAmt = r[10] == null ? 0 : new BigDecimal(r[10].toString()).longValue();
            dto.ownerNm = r[11] == null ? "" : r[11].toString();
            dto.vhcleRegtrnNo = r[12] == null ? "" : r[12].toString();
            dto.refCdVhclMakerSeq = r[13] == null ? 0 : new BigDecimal(r[13].toString()).longValue();
            dto.vhcleModelYr = r[14] == null ? "" : r[14].toString();
            dto.engnePwrCc = r[15] == null ? 0 : new BigDecimal(r[15].toString()).longValue();
            dto.engneNo = r[16] == null ? "" : r[16].toString();
            dto.chassisNO = r[17] == null ? "" : r[17].toString();
            dto.prchseDt = r[18] == null ? "" : r[18].toString();
            dto.brnchNm = r[19] == null ? "" : r[19].toString();
            dto.brnchDscr = r[20] == null ? "" : r[20].toString();
            dto.areaNm = r[21] == null ? "" : r[21].toString();
            dto.areaDscr = r[22] == null ? "" : r[22].toString();
            dto.regNm = r[23] == null ? "" : r[23].toString();
            dto.regDscr = r[24] == null ? "" : r[24].toString();
            dto.vhcleColor = r[25] == null ? 0 : new BigDecimal(r[25].toString()).longValue();
            dto.vhcleSeq = r[26] == null ? "" : r[26].toString();

            lists.add(dto);
        });

        Map<String, Object> resp = new HashMap<>();
        resp.put("lists", lists);

        Long totalCountResult = 0L;
        if (isCount.booleanValue()) {
            totalCountResult = new BigDecimal(entityManager.createNativeQuery(scriptCount).setParameter("P_BRNCH_SEQ", branchSeq)
                    .getSingleResult().toString()).longValue();
        }
        resp.put("count", totalCountResult);
        return resp;
    }

    public Map<String, Object> addVehicleInfo(VehicleLoanDto vehicleLoanDto, String user) throws ParseException {

        Map<String, Object> response = new HashMap<>();
        MwVehicleInfo mwVehicleInfo = new MwVehicleInfo();
        Instant instant = Instant.now();


        MwVehicleInfo exVehicleInfo = mwVehicleInfoRepository.findOneByLoanAppSeqAndCrntRecFlg(Long.parseLong(vehicleLoanDto.loanAppSeq), true);

        if (exVehicleInfo != null) {
            response.put("error", "Vehicle Found Against this Credit ID");
        } else {
            long seq = SequenceFinder.findNextVal(Sequences.FUND_REQ_SEQ);

            mwVehicleInfo.setVhcleSeq(seq);
            mwVehicleInfo.setInsurdAmt(vehicleLoanDto.insurdAmt);
            mwVehicleInfo.setOwnerNm(vehicleLoanDto.ownerNm.trim());
            mwVehicleInfo.setRefCdVhclMakerSeq(vehicleLoanDto.refCdVhclMakerSeq);
            mwVehicleInfo.setVhcleModelYr(vehicleLoanDto.vhcleModelYr);
            mwVehicleInfo.setVhcleRegtrnNo(vehicleLoanDto.vhcleRegtrnNo);
            mwVehicleInfo.setEngnePwrCc(vehicleLoanDto.engnePwrCc);
            mwVehicleInfo.setEngneNo(vehicleLoanDto.engneNo);
            mwVehicleInfo.setChassisNO(vehicleLoanDto.chassisNO);
            mwVehicleInfo.setLoanAppSeq(Long.parseLong(vehicleLoanDto.loanAppSeq.trim()));
            mwVehicleInfo.setPrchseDt((new SimpleDateFormat("dd-MM-yyyy").parse(vehicleLoanDto.prchseDt)).toInstant());
            mwVehicleInfo.setVhcleColor(vehicleLoanDto.vhcleColor);
            mwVehicleInfo.setEffStartDt(instant);
            mwVehicleInfo.setCrtdDt(instant);
            mwVehicleInfo.setCrdtBy(user);
            mwVehicleInfo.setCrntRecFlg(true);
            mwVehicleInfo.setDelFlg(false);
            mwVehicleInfoRepository.save(mwVehicleInfo);

            response.put("success", "Vehicle Insurance Enrollment Successfully");
        }

        return response;
    }

    public Map<String, Object> updateVehicleInfo(VehicleLoanDto vehicleLoanDto, long loanAppSeq, String user) throws ParseException {

        Map<String, Object> response = new HashMap<>();

        Instant instant = Instant.now();
        MwVehicleInfo mwVehicleInfo = mwVehicleInfoRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);

        if (mwVehicleInfo != null) {
            mwVehicleInfo.setInsurdAmt(vehicleLoanDto.insurdAmt);
            mwVehicleInfo.setOwnerNm(vehicleLoanDto.ownerNm.trim());
            mwVehicleInfo.setRefCdVhclMakerSeq(vehicleLoanDto.refCdVhclMakerSeq);
            mwVehicleInfo.setVhcleModelYr(vehicleLoanDto.vhcleModelYr);
            mwVehicleInfo.setVhcleRegtrnNo(vehicleLoanDto.vhcleRegtrnNo);
            mwVehicleInfo.setEngnePwrCc(vehicleLoanDto.engnePwrCc);
            mwVehicleInfo.setEngneNo(vehicleLoanDto.engneNo);
            mwVehicleInfo.setChassisNO(vehicleLoanDto.chassisNO);
            mwVehicleInfo.setLoanAppSeq(Long.parseLong(vehicleLoanDto.loanAppSeq));
            mwVehicleInfo.setPrchseDt((new SimpleDateFormat("dd-MM-yyyy").parse(vehicleLoanDto.prchseDt)).toInstant());
            mwVehicleInfo.setVhcleColor(vehicleLoanDto.vhcleColor);
            mwVehicleInfo.setLastUpdBy(user);
            mwVehicleInfo.setLastUpDt(instant);

            mwVehicleInfoRepository.save(mwVehicleInfo);

            response.put("success", "Vehicle Insurance Enrollment Updated Successfully");
        } else {
            response.put("error", "Vehicle Not Found Against this Credit ID ");
        }
        return response;
    }


    public Map<String, Object> uploadImage(MultipartFile file, String loanAppSeq, String user) {

        Map<String, Object> response = new HashMap<>();
        Instant instant = Instant.now();

        try {
            System.out.println("Original Image Byte Size - " + file.getBytes().length);

            MwLoanAppDoc existingLoanAppDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(Long.parseLong(loanAppSeq.split("_")[0]), Long.parseLong(loanAppSeq.split("_")[1]), true);

            if (existingLoanAppDoc != null) {
                existingLoanAppDoc.setDocImg(Base64.getEncoder().encodeToString(file.getBytes()));
                existingLoanAppDoc.setLastUpdDt(Instant.now());
                existingLoanAppDoc.setLastUpdBy(user);

                mwLoanAppDocRepository.save(existingLoanAppDoc);

                response.put("success", "Image Updated successfully");
                return response;
            }

            MwLoanAppDoc mwLoanAppDoc = new MwLoanAppDoc();

            mwLoanAppDoc.setLoanAppDocSeq(Long.parseLong(loanAppSeq.split("_")[0]));
            mwLoanAppDoc.setEffStartDt(instant);
            mwLoanAppDoc.setDocSeq(Long.parseLong(loanAppSeq.split("_")[1]));
            mwLoanAppDoc.setDocImg(Base64.getEncoder().encodeToString(file.getBytes()));
            mwLoanAppDoc.setLoanAppSeq(Long.parseLong(loanAppSeq.split("_")[0]));
            mwLoanAppDoc.setCrntRecFlg(true);
            mwLoanAppDoc.setDelFlg(false);
            mwLoanAppDoc.setCrtdDt(Instant.now());
            mwLoanAppDoc.setCrtdBy(user);
            mwLoanAppDocRepository.save(mwLoanAppDoc);

            response.put("success", "Image uploaded successfully");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "Image not uploaded successfully");
        }
        return response;
    }

    public Map<String, Object> downloadImage(long loanAppSeq, long docSeq) {

        Map<String, Object> response = new HashMap<>();

        MwLoanAppDoc mwLoanAppDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(loanAppSeq, docSeq, true);
        if (mwLoanAppDoc != null) {
            response.put("success", mwLoanAppDoc);
        } else {
            response.put("error", "Image not Found");
        }
        return response;
    }

    public Map<String, Object> downloadImage(long loanAppSeq) {

        Map<String, Object> response = new HashMap<>();

        List<MwLoanAppDoc> mwLoanAppDoc = mwLoanAppDocRepository.findByLoanAppDocSeqAndDocSeqIn(loanAppSeq);
        if (mwLoanAppDoc != null) {
            response.put("success", mwLoanAppDoc);
        } else {
            response.put("error", "Image not Found");
        }
        return response;
    }

    public Map<String, Object> getVehicleDtlByLoanApp(Long loanAppSeq) {
        Map<String, Object> response = new HashMap<>();

        //
        MwVehicleInfo mwVehicleInfo = mwVehicleInfoRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);

        if (mwVehicleInfo != null) {
            response.put("success", mwVehicleInfo);
        } else {
            response.put("failed", "No Data Found");
        }
        return response;
    }
}

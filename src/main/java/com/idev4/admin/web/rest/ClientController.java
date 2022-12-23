package com.idev4.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.admin.domain.MwBrnchClsng;
import com.idev4.admin.repository.MwBrnchClsngRepository;
import com.idev4.admin.service.MwClntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    MwClntService mwClntService;

    @Autowired
    MwBrnchClsngRepository mwBrnchClsngRepository;

//    @CrossOrigin
//    @GetMapping ( "/all-active-clnts/{id:.+}/{brnchSeq}" )
//    @Timed
//    public ResponseEntity< List< LoanServingDTO > > allActiveClnts( @PathVariable String id, @PathVariable Long brnchSeq ) {
//        return new ResponseEntity<>( mwClntService.getAllActiveClint( id, brnchSeq ), HttpStatus.OK );
//    }

    @GetMapping("/all-active-clnts")
    @Timed
    public ResponseEntity<Map<String, Object>> allActiveClnts(
            @RequestParam String userId, @RequestParam Long brnchSeq,
            @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
            @RequestParam(required = false) String filter, @RequestParam Boolean isCount) {
        return new ResponseEntity<>(mwClntService.getAllActiveClint(userId, brnchSeq, pageIndex, pageSize, filter, isCount), HttpStatus.OK);
    }

    //Added by Areeba - 22-04-2022 - OD CHECK
    @GetMapping("/all-clnts-loan-app")
    @Timed
    public ResponseEntity<Map<String, Object>> allClntsLoanApp(
            @RequestParam String userId, @RequestParam Long brnchSeq,
            @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
            @RequestParam(required = false) String filter, @RequestParam Boolean isCount) {
        return new ResponseEntity<>(mwClntService.getAllClntsLoanApp(userId, brnchSeq, pageIndex, pageSize, filter, isCount), HttpStatus.OK);
    }

    @GetMapping("/od-clnts-loan-app")
    @Timed
    public ResponseEntity<Map<String, Object>> odClntsLoanApp(
            @RequestParam Long brnchSeq) {
        return new ResponseEntity<>(mwClntService.getBypassODClntsLoanApp(brnchSeq), HttpStatus.OK);
    }

    @PutMapping("/bypass-overdue/{loanAppSeq}/{checked}")
    @Timed
    public ResponseEntity<Map> updateBypassOD(@PathVariable Long loanAppSeq,
                                              @PathVariable Boolean checked) throws URISyntaxException {
        Map<String, String> resp = new HashMap<String, String>();

        int flag = mwClntService.updateOD(loanAppSeq, checked);

        if (flag == -1) {
            resp.put("error", "Loan Application Not Found !!");
            return ResponseEntity.ok().body(resp);
        }

        resp.put("update", "OD Check updated Successfully");
        return ResponseEntity.ok().body(resp);
    }
    //Ended by Areeba

    @GetMapping("/close-branch/{id}")
    @Timed
    public ResponseEntity<List<Screen>> closeBranch(@PathVariable Long id) {
        MwBrnchClsng clsng = mwBrnchClsngRepository.findOneByBrnchSeqAndBrnchOpnFlg(id, true);
        List<Screen> screens = new ArrayList<Screen>();
        if (clsng == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        clsng.setBrnchClsdDt(LocalDateTime.from(LocalDateTime.now(ZoneId.of("Asia/Karachi"))));
        clsng.setBrnchOpnFlg(false);
        clsng.setBrnchClsdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        mwBrnchClsngRepository.save(clsng);
        screens.add(new Screen("Home", "dashboard/bm", true, true, true));
        screens.add(new Screen("Process Applications", "loan-origination", true, true, true));
        screens.add(new Screen("Reports", "reports", true, true, true));
        return new ResponseEntity<>(screens, HttpStatus.OK);
    }

    public class Screen {

        public String name;

        public String url;

        public boolean readFlag;

        public boolean writeFlag;

        public boolean deleteFlag;

        public Screen() {
            super();
        }

        public Screen(String name, String url, boolean readFlag, boolean writeFlag, boolean deleteFlag) {
            super();
            this.name = name;
            this.url = url;
            this.readFlag = readFlag;
            this.writeFlag = writeFlag;
            this.deleteFlag = deleteFlag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isReadFlag() {
            return readFlag;
        }

        public void setReadFlag(boolean readFlag) {
            this.readFlag = readFlag;
        }

        public boolean isWriteFlag() {
            return writeFlag;
        }

        public void setWriteFlag(boolean writeFlag) {
            this.writeFlag = writeFlag;
        }

        public boolean isDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(boolean deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

    }

}

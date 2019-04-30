package pensa.on.duty.api.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pensa.on.duty.api.framework.StaticContent;
import pensa.on.duty.api.framework.V1;
import pensa.on.duty.api.model.FullMonth;
import pensa.on.duty.api.service.Adapter;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(V1.URI_SET_MONTH_ABSOLUTE)
public class SetMonth {

    @Autowired
    Adapter adapter;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Operation to add a update product")
    public DeferredResult<ResponseEntity<FullMonth>> getDuties(@RequestBody FullMonth fullMonth,
                                                               HttpServletRequest request) {
        DeferredResult<ResponseEntity<FullMonth>> deferredResult = new DeferredResult<>();

        FullMonth fullMonthC = StaticContent.getEmptySelectedFullMonth(adapter.getFullMonthList(),fullMonth.getDutyPeriod());
        fullMonthC.getFullDayList().forEach(fullDayC -> {
            fullMonth.getFullDayList().forEach(fullDay -> {
                if (fullDayC.getDayOfMonth().equals(fullDay.getDayOfMonth())) {
                    fullDayC.setDayWeight(fullDay.getDayWeight());
                    fullDayC.setSpecializerNumber(fullDay.getSpecializerNumber());
                }
            });
        });

        adapter.addFullMonth(fullMonthC);

        deferredResult.setResult(new ResponseEntity<>(fullMonth, HttpStatus.OK));
        return deferredResult;
    }
}

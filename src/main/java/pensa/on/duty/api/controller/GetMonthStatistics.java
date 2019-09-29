package pensa.on.duty.api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pensa.on.duty.api.framework.V1;
import pensa.on.duty.api.model.MonthStatistics;
import pensa.on.duty.api.service.Adapter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(V1.URI_GET_MONTH_STATISTICS_ABSOLUTE)
public class GetMonthStatistics {

    @Autowired
    Adapter adapter;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Operation returns selected months statistics")
    public DeferredResult<ResponseEntity<MonthStatistics>> getMonthStatistics(@RequestParam Map<String, String> parameters,
                                                                              HttpServletRequest request) {

        DeferredResult<ResponseEntity<MonthStatistics>> deferredResult = new DeferredResult<>();
        // receive parameter values
        Map<String, String> requestParameters = new HashMap<>(parameters);
        String month = "01/" + requestParameters.get("month");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(month, formatter);

        MonthStatistics monthStatistics = adapter.calculateMonthStatistics(localDate);

        adapter.addMonthStatistics(monthStatistics);

        deferredResult.setResult(new ResponseEntity<>(monthStatistics, HttpStatus.OK));

        return deferredResult;
    }

}

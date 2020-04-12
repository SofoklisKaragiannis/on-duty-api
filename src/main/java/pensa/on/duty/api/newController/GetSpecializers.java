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
import pensa.on.duty.api.model.ResponseMonth;
import pensa.on.duty.api.model.Specializer;
import pensa.on.duty.api.service.Adapter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pensa.on.duty.api.framework.Converter.fullMonthToResponseMonth;

@CrossOrigin
@RestController
@RequestMapping(V1.URI_GET_DUTIES_ABSOLUTE)
public class GetDuties {

    @Autowired
    Adapter adapter;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Operation to get duties of a full month")
    public DeferredResult<ResponseEntity<ResponseMonth>> getDuties(@RequestParam Map<String, String> parameters,
                                                                HttpServletRequest request) {

        DeferredResult<ResponseEntity<ResponseMonth>> deferredResult = new DeferredResult<>();
        // receive parameter values
        Map<String, String> requestParameters = new HashMap<>(parameters);
        String month = "01/" + requestParameters.get("month") + "/" + requestParameters.get("year");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(month, formatter);
        Integer daysInMonth = localDate.getMonth().length(true);

        List<Specializer> specializerList = adapter.getSpecializerList(localDate, daysInMonth);

        FullMonth fullMonth = StaticContent.getEmptySelectedFullMonth(adapter.getFullMonthList(),localDate);
        StaticContent.populateDuties(fullMonth, localDate, specializerList, adapter);

        adapter.addFullMonth(fullMonth);

        ResponseMonth responseMonth = fullMonthToResponseMonth(fullMonth);
        // if invalid parameters --> bad request
        if (requestParameters.isEmpty()) {
            return StaticContent.getAddErrorResult(deferredResult, responseMonth, HttpStatus.BAD_REQUEST, "Wrong parameters!");
        }

        deferredResult.setResult(new ResponseEntity<>(responseMonth, HttpStatus.OK));

        return deferredResult;
    }
}

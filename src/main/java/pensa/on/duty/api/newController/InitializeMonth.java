package pensa.on.duty.api.newController;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pensa.on.duty.api.framework.V2;
import pensa.on.duty.api.model.ResponseStatusMessage;
import pensa.on.duty.api.newModel.RequestMonth;
import pensa.on.duty.api.service.AdapterRefactor;
import pensa.on.duty.api.validators.DateValidatorUsingDateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pensa.on.duty.api.defaultData.InitializeMonthDefaultData.*;

@CrossOrigin
@RestController
@RequestMapping(V2.URI_SET_INITIALIZE_ABSOLUTE)
public class InitializeMonth {

    @Autowired
    AdapterRefactor adapter;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Operation to add a update product")
    public DeferredResult<ResponseEntity<ResponseStatusMessage>> getDuties(@RequestBody RequestMonth requestMonth,
                                                                           HttpServletRequest request) {
        DeferredResult<ResponseEntity<ResponseStatusMessage>> deferredResult = new DeferredResult<>();

        ResponseStatusMessage responseStatusMessage = new ResponseStatusMessage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateValidatorUsingDateTimeFormatter validator = new DateValidatorUsingDateTimeFormatter(formatter);
        if (validator.isValid("01/"+requestMonth.getMonth())) {
            LocalDate localDate = LocalDate.parse("01/"+requestMonth.getMonth(), formatter);

            List<LocalDate> dates = Stream.iterate(localDate.withDayOfMonth(1), date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(localDate.withDayOfMonth(1), localDate.plusMonths(1).withDayOfMonth(1)))
                    .collect(Collectors.toList());

            adapter.addFullMonth(initializeMonthValues(localDate));
            adapter.addAvailability(initializeAvailabilityValues(localDate));
            initializeDayValues(dates).forEach(fd -> adapter.addFullDay(fd));

            responseStatusMessage.setMessage("Month successfully initialized!");
            deferredResult.setResult(new ResponseEntity<>(responseStatusMessage, HttpStatus.OK));
        } else {
            responseStatusMessage.setMessage("Wrong month (json should have the format {month: MM/YY})");
            deferredResult.setResult(new ResponseEntity<>(responseStatusMessage, HttpStatus.BAD_REQUEST));
        }
        return deferredResult;
    }
}

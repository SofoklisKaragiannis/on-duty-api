package pensa.on.duty.api.newController;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pensa.on.duty.api.framework.V1;
import pensa.on.duty.api.model.ResponseStatusMessage;
import pensa.on.duty.api.newModel.Availability;
import pensa.on.duty.api.newModel.FullDay;
import pensa.on.duty.api.newModel.FullMonth;
import pensa.on.duty.api.newModel.RequestMonth;
import pensa.on.duty.api.service.AdapterRefactor;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(V1.URI_SET_INITIALIZE_ABSOLUTE)
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

            FullMonth month = new FullMonth();
            month.setDutyPeriod(localDate);
            month.setMonthName(localDate.getMonth().name());
            month.setYear(localDate.getYear());
            LocalDate start = localDate.withDayOfMonth(1);
            LocalDate end = localDate.plusMonths(1).withDayOfMonth(1);
            List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(start, end))
                    .collect(Collectors.toList());
            month.setFullDayList(dates);

            Availability availability = new Availability();
            availability.setDutyPeriod(localDate);
            availability.setYear(localDate.getYear());
            availability.setMonthName(localDate.getMonth().name());

            dates.forEach(ld -> {
                FullDay fullDay = new FullDay();
                fullDay.setDay(ld);
                fullDay.setFullday(ld.getDayOfWeek().name());
                fullDay.setTotalexperience(1);
                fullDay.setDayOfMonth(ld.getDayOfMonth());
                switch (ld.getDayOfWeek()) {
                    case WEDNESDAY:
                        fullDay.setSpecializerNumber(5);
                        fullDay.setDayWeight(4);
                        fullDay.setTotalexperience(12);
                        break;
                    case THURSDAY:
                        fullDay.setSpecializerNumber(2);
                        fullDay.setDayWeight(2);
                        fullDay.setDayWeight(6);
                        break;
                    case SUNDAY:
                        fullDay.setSpecializerNumber(1);
                        fullDay.setDayWeight(4);
                        break;
                    case SATURDAY:
                        fullDay.setSpecializerNumber(1);
                        fullDay.setDayWeight(3);
                        break;
                    default:
                        fullDay.setSpecializerNumber(1);
                        fullDay.setDayWeight(1);
                        break;
                }

                adapter.addFullDay(fullDay);
                availability.addNonAvailable(ld.getDayOfMonth(), null);
            });
            adapter.addFullMonth(month);
            adapter.addAvailability(availability);
            responseStatusMessage.setMessage("Month successfully initialized!");
            responseStatusMessage.setStatus(HttpStatus.OK.toString());
        } else {
            responseStatusMessage.setMessage("Wrong month (json should have the format {month: MM/YY})");
            responseStatusMessage.setStatus(HttpStatus.BAD_REQUEST.toString());
        }
        deferredResult.setResult(new ResponseEntity<>(responseStatusMessage, HttpStatus.OK));
        return deferredResult;
    }
    private class DateValidatorUsingDateTimeFormatter {
        private DateTimeFormatter dateFormatter;

        public DateValidatorUsingDateTimeFormatter(DateTimeFormatter dateFormatter) {
            this.dateFormatter = dateFormatter;
        }

        public boolean isValid(String dateStr) {
            try {
                this.dateFormatter.parse(dateStr);
            } catch (DateTimeParseException e) {
                return false;
            }
            return true;
        }
    }
}

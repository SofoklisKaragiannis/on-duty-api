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
import pensa.on.duty.api.newModel.AvailabilityRequest;
import pensa.on.duty.api.newModel.Specializer;
import pensa.on.duty.api.service.AdapterRefactor;
import pensa.on.duty.api.validators.DateValidatorUsingDateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(V2.URI_SET_ADD_AVAILABILITY)
public class AddAvailability {

    @Autowired
    AdapterRefactor adapter;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Operation to add or update specializer")
    public DeferredResult<ResponseEntity<ResponseStatusMessage>> addAvailability(@RequestBody AvailabilityRequest availabilityRequest,
                                                                                   HttpServletRequest request) {
        DeferredResult<ResponseEntity<ResponseStatusMessage>> deferredResult = new DeferredResult<>();

        ResponseStatusMessage responseStatusMessage = new ResponseStatusMessage();
        responseStatusMessage.setMessage("Invalid request");
        responseStatusMessage.setStatus(HttpStatus.BAD_REQUEST.name());



        if (isValidAvailabilityRequest(availabilityRequest)) {
            String dateInString = "Mon, 05 May 1980";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
            List<Integer> nonAvailableSpecializers = adapter.getAvailabilityList().getNonAvailableDay(LocalDate.parse(availabilityRequest.getExcludeDay(), formatter));
            if (!nonAvailableSpecializers.contains(availabilityRequest.getSpecializerId())) {
                nonAvailableSpecializers.add(availabilityRequest.getSpecializerId());
                responseStatusMessage.setMessage("Specializer added");
                responseStatusMessage.setStatus(HttpStatus.OK.name());
            } else {
                responseStatusMessage.setMessage("Specializer already added");
                responseStatusMessage.setStatus(HttpStatus.TOO_MANY_REQUESTS.name());
            }
        }

        deferredResult.setResult(new ResponseEntity<>(responseStatusMessage, HttpStatus.valueOf(responseStatusMessage.getStatus())));
        return deferredResult;
    }

    private boolean isValidAvailabilityRequest(AvailabilityRequest availabilityRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateValidatorUsingDateTimeFormatter validator = new DateValidatorUsingDateTimeFormatter(formatter);
        if (Optional.ofNullable(availabilityRequest.getSpecializerId()).orElse(0) > 0 &&
            adapter.getSpecializerList().containsId(availabilityRequest.getSpecializerId()) &&
                    validator.isValid(availabilityRequest.getExcludeDay())) {
            return true;
        }
        return false;
    }
}

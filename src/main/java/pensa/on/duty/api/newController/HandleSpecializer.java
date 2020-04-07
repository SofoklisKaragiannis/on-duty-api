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
import pensa.on.duty.api.newModel.Specializer;
import pensa.on.duty.api.service.AdapterRefactor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(V2.URI_SET_HANDLE_SPEC)
public class HandleSpecializer {

    @Autowired
    AdapterRefactor adapter;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Operation to add or update specializer")
    public DeferredResult<ResponseEntity<ResponseStatusMessage>> handleSpecializer(@RequestBody Specializer requestSpecializer,
                                                                           HttpServletRequest request) {
        DeferredResult<ResponseEntity<ResponseStatusMessage>> deferredResult = new DeferredResult<>();

        ResponseStatusMessage responseStatusMessage = new ResponseStatusMessage();
        responseStatusMessage.setMessage("Invalid request");
        responseStatusMessage.setStatus(HttpStatus.BAD_REQUEST.name());

        if (isNewSpecializer(requestSpecializer, adapter)) {
            if (isRequestSpecializerValidForCreate(requestSpecializer)) {
                requestSpecializer.setId(adapter.getSpecializerList().getNewSpecializerId());
                adapter.getSpecializerList().add(requestSpecializer);
                responseStatusMessage.setMessage("Specializer added!");
                responseStatusMessage.setStatus(HttpStatus.OK.name());
            }
        } else if (isRequestSpecializerValidForUpdate(requestSpecializer)) {
            Specializer specializer = adapter.getSpecializerList().getSpecializerById(requestSpecializer.getId());
            specializer.setName(StringUtils.isNotEmpty(requestSpecializer.getName())?requestSpecializer.getName(): specializer.getName());
            specializer.setExperience(Optional.ofNullable(requestSpecializer.getExperience()).orElse(0) > 0 ? requestSpecializer.getExperience():specializer.getExperience());
            specializer.setGrade(Optional.ofNullable(requestSpecializer.getGrade()).orElse(0) > 0? requestSpecializer.getGrade():specializer.getGrade());
            responseStatusMessage.setMessage("Specializer Updated!");
            responseStatusMessage.setStatus(HttpStatus.OK.name());
        }
        deferredResult.setResult(new ResponseEntity<>(responseStatusMessage, HttpStatus.valueOf(responseStatusMessage.getStatus())));
        return deferredResult;
    }

    private boolean isRequestSpecializerValidForCreate(Specializer requestSpecializer) {
        if (StringUtils.isNotEmpty(requestSpecializer.getName()) &&
                Integer.signum(requestSpecializer.getExperience().intValue()) > 0 &&
                Integer.signum(requestSpecializer.getGrade().intValue()) > 0) {
            return true;
        }
        return false;
    }

    private boolean isRequestSpecializerValidForUpdate(Specializer requestSpecializer) {
        if (Optional.ofNullable(requestSpecializer.getId()).orElse(0) > 0 &&
                (StringUtils.isNotEmpty(requestSpecializer.getName()) ||
                        Optional.ofNullable(requestSpecializer.getExperience()).orElse(0) > 0 ||
                        Optional.ofNullable(requestSpecializer.getGrade()).orElse(0) > 0)
        ) {
            return true;
        }
        return false;
    }

    private Boolean isNewSpecializer(Specializer requestSpecializer, AdapterRefactor adapter) {
        if (Optional.ofNullable(requestSpecializer.getId()).orElse(0) > 0 &&
                adapter.getSpecializerList().containsId(requestSpecializer.getId())) {
            return false;
        }
        return true;
    }
}

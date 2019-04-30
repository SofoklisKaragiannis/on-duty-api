package pensa.on.duty.api.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pensa.on.duty.api.framework.V1;
import pensa.on.duty.api.model.Specializer;
import pensa.on.duty.api.service.Adapter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@CrossOrigin
@RestController
@RequestMapping(V1.URI_ADD_SPECIALIZER_ABSOLUTE)
public class AddSpecializer {

    @Autowired
    Adapter adapter;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Operation to add new specializer")
    public DeferredResult<ResponseEntity<Specializer>> addSpecializer(@RequestBody Specializer specializer,
                                                               HttpServletRequest request) {
        DeferredResult<ResponseEntity<Specializer>> deferredResult = new DeferredResult<>();

        LocalDate localDate = LocalDate.now();
        Integer daysInMonth = localDate.getMonth().length(true);

        boolean isNewSpecializer = true;
        for (Specializer specializerC : adapter.getSpecializerList(localDate, daysInMonth)) {
            if (specializerC.getName().equals(specializer.getName())) {
                deferredResult.setResult(new ResponseEntity<>(specializer, HttpStatus.ALREADY_REPORTED));
                isNewSpecializer = false;
            }
        }
        if (isNewSpecializer) {
            adapter.getSpecializerList(localDate, daysInMonth).add(specializer);
            deferredResult.setResult(new ResponseEntity<>(specializer, HttpStatus.OK));
        }
        return deferredResult;
    }
}

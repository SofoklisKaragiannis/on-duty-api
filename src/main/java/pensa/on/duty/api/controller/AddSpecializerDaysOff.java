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
@RequestMapping(V1.URI_ADD_SPECIALIZER_DAYS_OFF_ABSOLUTE)
public class AddSpecializerDaysOff {

    @Autowired
    Adapter adapter;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Operation to append specialisers days off")
    public DeferredResult<ResponseEntity<Specializer>> addSpecializerDaysOff(@RequestBody Specializer specializer,
                                                               HttpServletRequest request) {
        DeferredResult<ResponseEntity<Specializer>> deferredResult = new DeferredResult<>();
        LocalDate localDate;
        if (specializer.getAvailability().keySet().size() == 1) {
            localDate = specializer.getAvailability().keySet().iterator().next();

            adapter.getSpecializerList(localDate, localDate.getMonth().length(true)).forEach(specializerC -> {
                if (specializerC.getName().equals(specializer.getName())) {
                    if (specializerC.getAvailability().get(localDate) == null) {
                        adapter.getSpecializerList(localDate, localDate.getMonth().length(true));
                    }

                    specializerC.getAvailability().get(localDate).getAvailability().forEach((key, value) -> {
                        specializer.getAvailability().get(localDate).getAvailability().forEach((keyNew, valueNew) -> {
                            if (key.equals(keyNew)) {
                                specializerC.getAvailability().get(localDate).getAvailability().replace(key, valueNew);
                            }
                        });
                    } );
                    deferredResult.setResult(new ResponseEntity<>(specializerC, HttpStatus.OK));
                }
            });
        }
        return deferredResult;
    }
}

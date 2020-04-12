package pensa.on.duty.api.newController;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pensa.on.duty.api.defaultData.InitializeSpecializersDefaultData;
import pensa.on.duty.api.framework.V2;
import pensa.on.duty.api.newModel.Specializer;
import pensa.on.duty.api.service.AdapterRefactor;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(V2.URI_SET_GET_SPECIALIZERS)
public class GetSpecializers {

    @Autowired
    AdapterRefactor adapter;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Operation to get total specializers")
    public DeferredResult<ResponseEntity<List<Specializer>>> getSpecializers(@RequestParam Map<String, String> parameters,
                                                                             HttpServletRequest request) {

        DeferredResult<ResponseEntity<List<Specializer>>> deferredResult = new DeferredResult<>();
        Map<String, String> requestParameters = new HashMap<>(parameters);
        if (Boolean.parseBoolean(requestParameters.get("default")) ) {
            InitializeSpecializersDefaultData.getDefaultSpecializers().forEach(sp -> {
                    if (!adapter.getSpecializerList().containsId(sp.getId())) adapter.getSpecializerList().add(sp);
            });
        }

        deferredResult.setResult(new ResponseEntity<>(adapter.getSpecializerList().getSpecializerList(), HttpStatus.OK));

        return deferredResult;
    }
}

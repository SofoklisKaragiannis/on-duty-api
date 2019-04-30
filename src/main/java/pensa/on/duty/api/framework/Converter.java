package pensa.on.duty.api.framework;

import pensa.on.duty.api.model.FullMonth;
import pensa.on.duty.api.model.ResponseDay;
import pensa.on.duty.api.model.ResponseMonth;

public class Converter {

    public static ResponseMonth fullMonthToResponseMonth(FullMonth fullMonth) {
        ResponseMonth responseMonth = new ResponseMonth();
        responseMonth.setDutyPeriod(fullMonth.getDutyPeriod());
        responseMonth.setMonthName(fullMonth.getMonthName());
        responseMonth.setYear(fullMonth.getYear());
        fullMonth.getFullDayList().forEach(fullDay -> {
            ResponseDay responseDay = new ResponseDay();
            responseDay.setDay(fullDay.getDay());
            responseDay.setDayOfMonth(fullDay.getDayOfMonth());
            responseDay.setFullday(fullDay.getFullday());
            fullDay.getSpecializerList().forEach(specializer -> {
                responseDay.getSpecializerNameList().add(specializer.getName());
            });
            responseMonth.getResponseDayList().add(responseDay);
        });
        return responseMonth;
    }
}

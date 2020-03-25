package pensa.on.duty.api.service;

import org.springframework.stereotype.Service;
import pensa.on.duty.api.newModel.Availability;
import pensa.on.duty.api.newModel.FullDay;
import pensa.on.duty.api.newModel.FullMonth;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdapterRefactor {

    private List<FullMonth> fullMonthList;

    private List<FullDay> fullDayList;

    private List<Availability> availabilityList;

    public List<FullMonth> getFullMonthList() {
        if (this.fullMonthList == null) this.fullMonthList = new ArrayList<>();
        return fullMonthList;
    }

    public void setFullMonthList(List<FullMonth> fullMonthList) {
        this.fullMonthList = fullMonthList;
    }

    public void addFullMonth(FullMonth fullMonth) {
        getFullMonthList().add(fullMonth);
    }

    public List<FullDay> getFullDayList() {
        if (this.fullDayList == null) this.fullDayList = new ArrayList<>();
        return fullDayList;
    }

    public void setFullDayList(List<FullDay> fullDayList) {
        this.fullDayList = fullDayList;
    }

    public void addFullDay(FullDay fullDay) {
        getFullDayList().add(fullDay);
    }

    public List<Availability> getAvailabilityList() {
        if (this.availabilityList == null) this.availabilityList = new ArrayList<>();
        return availabilityList;
    }

    public void setAvailabilityList(List<Availability> availabilityList) {
        this.availabilityList = availabilityList;
    }

    public void addAvailability(Availability availability) {
        getAvailabilityList().add(availability);
    }
}

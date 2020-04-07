package pensa.on.duty.api.framework;

import pensa.on.duty.api.newModel.Availability;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static pensa.on.duty.api.defaultData.InitializeMonthDefaultData.initializeAvailabilityValues;

public class AvailabilityList {
    List<Availability> availabilityList;

    public AvailabilityList () {
        availabilityList = new ArrayList<>();
    }

    public AvailabilityList (List<Availability> availabilityList) {

        this.availabilityList = availabilityList;
    }

    public List<Availability> getAvailabilityList() {
        return availabilityList;
    }

    public boolean containsMonth(LocalDate date) {
        if (availabilityList.stream().anyMatch(av->
         av.getDutyPeriod().getMonthValue() == date.getMonthValue() &&
                 av.getDutyPeriod().getYear() == date.getYear()
        )) return true;
        return false;
    }

    public Availability getAvailability(LocalDate date) {
        if (containsMonth(date)) {
            return availabilityList.stream().filter(av->
                    av.getDutyPeriod().getMonthValue() == date.getMonthValue() &&
                            av.getDutyPeriod().getYear() == date.getYear()).findFirst().get();
        }
        Availability availability = initializeAvailabilityValues(date);
        return availability;
    }

    public boolean containsNonAvailableDay(LocalDate date) {
        Availability availability = getAvailability(date);
        if (availability.getNonAvailable().containsKey(date)) return true;
        return false;
    }

    public List<Integer> getNonAvailableDay(LocalDate date) {
        if (containsNonAvailableDay(date)) {
            return getAvailability(date).getNonAvailable().get(date);
        } else {
            return new ArrayList<Integer>();
        }
    }

}

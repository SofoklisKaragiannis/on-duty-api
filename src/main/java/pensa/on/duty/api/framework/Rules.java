package pensa.on.duty.api.framework;

import pensa.on.duty.api.model.FullDay;
import pensa.on.duty.api.model.FullMonth;
import pensa.on.duty.api.model.Specializer;

import java.time.LocalDate;
import java.util.List;

public class Rules {

    public static boolean hasCurrentDay(FullMonth fullMonth, Specializer specializer, FullDay fullDay) {
        if (fullMonth.getFullDayList().get(fullDay.getDay().getDayOfMonth()-1).getSpecializerList().contains(specializer)) {
            return true;
        }
        return false;
    }

    public static Integer checkCurrentDay(FullMonth fullMonth, List<Specializer> specializerList, Integer dayIndex, Integer specializerIndex) {
        if (fullMonth.getFullDayList().get(dayIndex).getSpecializerList().contains(specializerList.get(specializerIndex))) {
            if (specializerIndex + 1 <  specializerList.size()) {
                specializerIndex ++;
            } else {
                specializerIndex = 0;
            }
        }
        return specializerIndex;
    }

    public static boolean hasConsecutiveDays(FullMonth fullMonth, Specializer specializer, FullDay fullDay) {
        if ((fullDay.getDay().getDayOfMonth() > 1 && fullMonth.getFullDayList().get(fullDay.getDay().getDayOfMonth()-2).getSpecializerList().contains(specializer)) ||
            (fullDay.getDay().getDayOfMonth() < fullMonth.getFullDayList().size() && fullMonth.getFullDayList().get(fullDay.getDay().getDayOfMonth()).getSpecializerList().contains(specializer))) {
            return true;
        }
        return false;
    }

    public static Integer checkConsecutiveDays(FullMonth fullMonth, List<Specializer> specializerList, Integer dayIndex, Integer specializerIndex) {
        while (fullMonth.getFullDayList().get(dayIndex-1).getSpecializerList().contains(specializerList.get(specializerIndex))) {
            if (specializerIndex + 1 <  specializerList.size()) {
                specializerIndex ++;
            } else {
                specializerIndex = 0;
            }
        }
        return specializerIndex;
    }

    public static boolean isAvailable(FullMonth fullMonth, Specializer specializer, FullDay fullDay) {
        if (specializer.getAvailability().get(fullMonth.getDutyPeriod()).getAvailability().get(fullDay.getDay().getDayOfMonth()).equals("A")) {
            return true;
        }
        return false;
    }

    public static Integer getAvailabilityIndex(List<Specializer> specializerList, Integer dayIndex, Integer specializerIndex, LocalDate localDate) {
        while (!specializerList.get(specializerIndex).getAvailability().get(localDate).getAvailability().get(dayIndex).equals("A")){
            if (specializerIndex + 1 <  specializerList.size()) {
                specializerIndex ++;
            } else {
                specializerIndex = 0;
            }
        }
        return specializerIndex;
    }
}

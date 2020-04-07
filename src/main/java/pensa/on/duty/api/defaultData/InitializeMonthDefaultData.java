package pensa.on.duty.api.defaultData;

import pensa.on.duty.api.newController.InitializeMonth;
import pensa.on.duty.api.newModel.Availability;
import pensa.on.duty.api.newModel.FullDay;
import pensa.on.duty.api.newModel.FullMonth;
import pensa.on.duty.api.service.AdapterRefactor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InitializeMonthDefaultData {
    public static List<FullDay> initializeDayValues(List<LocalDate> dates) {
        return dates.stream().map(ld -> {
            FullDay fullDay = new FullDay();
            fullDay.setDay(ld);
            fullDay.setFullday(ld.getDayOfWeek().name());
            fullDay.setTotalexperience(1);
            fullDay.setDayOfMonth(ld.getDayOfMonth());
            setDefaultDayValues(ld, fullDay);


            return fullDay;
        }).collect(Collectors.toList());
    }

    public static FullMonth initializeMonthValues(LocalDate ld) {
        List<LocalDate> dates = Stream.iterate(ld.withDayOfMonth(1), date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(ld.withDayOfMonth(1), ld.plusMonths(1).withDayOfMonth(1)))
                .collect(Collectors.toList());
        FullMonth month = new FullMonth();
        month.setDutyPeriod(ld);
        month.setMonthName(ld.getMonth().name());
        month.setYear(ld.getYear());
        month.setFullDayList(dates);
        return month;
    }

    public static Availability initializeAvailabilityValues(LocalDate localDate) {
        List<LocalDate> dates = Stream.iterate(localDate.withDayOfMonth(1), date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(localDate.withDayOfMonth(1), localDate.plusMonths(1).withDayOfMonth(1)))
                .collect(Collectors.toList());
        Availability availability = new Availability();
        availability.setDutyPeriod(localDate);
        availability.setYear(localDate.getYear());
        availability.setMonthName(localDate.getMonth().name());
        dates.forEach(ld ->availability.addNonAvailable(ld, null));
        return availability;
    }

    private static void setDefaultDayValues(LocalDate ld, FullDay fullDay) {
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
    }
}

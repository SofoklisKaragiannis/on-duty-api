package pensa.on.duty.api.framework;

import pensa.on.duty.api.model.Availability;
import pensa.on.duty.api.model.FullDay;
import pensa.on.duty.api.model.Specializer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DefaultValues {

    public static FullDay setDefaultFullDayData(LocalDate ld) {
        FullDay fullDay = new FullDay();
        fullDay.setDayOfMonth(ld.getDayOfMonth());
        fullDay.setFullday(ld.getDayOfWeek().name());
        fullDay.setDay(ld);
        switch (ld.getDayOfWeek()) {
            case WEDNESDAY:
                fullDay.setSpecializerNumber(5);
                fullDay.setDayWeight(4);
                break;
            case THURSDAY:
                fullDay.setSpecializerNumber(2);
                fullDay.setDayWeight(2);
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

        return fullDay;
    }

    public static void setDefaultSpecializerAvailabilityIfNotExist(LocalDate localDate, Integer daysInMonth, Specializer specializer) {
        List<LocalDate> daysRange = Stream.iterate(localDate, date -> date.plusDays(1)).limit(daysInMonth).collect(toList());
        Availability availability = new Availability();
        if (specializer.getAvailability().get(localDate) != null ) {
            availability = specializer.getAvailability().get(localDate);
        }
        final Availability availabilityF = availability;
        daysRange.forEach(ld -> {
            if (availabilityF.getAvailability().get(ld.getDayOfMonth()) == null)
                availabilityF.getAvailability().put(ld.getDayOfMonth(), "A");
        });
        specializer.getAvailability().put(localDate, availabilityF);
    }

    public static List<Specializer> getDefaultSpecializers() {
        List<Specializer> specializerList = new ArrayList<>();
        Specializer specializer1 = new Specializer();
        specializer1.setId(1);
        specializer1.setName("ΧΡΙΣΤΑΝΤΩΝΗ");
        specializerList.add(specializer1);

        Specializer specializer2 = new Specializer();
        specializer2.setId(2);
        specializer2.setName("ΠΑΠΑΔΑΚΗ");
        specializerList.add(specializer2);

        Specializer specializer3 = new Specializer();
        specializer3.setId(3);
        specializer3.setName("ΚΑΡΑΓΙΑΝΟΠΟΥΛΟΣ");
        specializerList.add(specializer3);

        Specializer specializer4 = new Specializer();
        specializer4.setId(4);
        specializer4.setName("ΠΑΝΑΓΙΩΤΙΔΗ");
        specializerList.add(specializer4);

        Specializer specializer5 = new Specializer();
        specializer5.setId(5);
        specializer5.setName("ΘΑΝΟΠΟΥΛΟΥ");
        specializerList.add(specializer5);

        Specializer specializer6 = new Specializer();
        specializer6.setId(6);
        specializer6.setName("ΜΠΡΑΒΟΥ");
        specializerList.add(specializer6);

        Specializer specializer7 = new Specializer();
        specializer7.setId(7);
        specializer7.setName("ΠΕΓΚΟΥ");
        specializerList.add(specializer7);

        Specializer specializer8 = new Specializer();
        specializer8.setId(8);
        specializer8.setName("ΜΑΥΡΙΔΟΥ");
        specializerList.add(specializer8);

        Specializer specializer9 = new Specializer();
        specializer9.setId(9);
        specializer9.setName("ΜΑΡΓΑΡΙΤΗ");
        specializerList.add(specializer9);

        Specializer specializer10 = new Specializer();
        specializer10.setId(10);
        specializer10.setName("ΤΣΑΝΑΚΑΛΗΣ");
        specializerList.add(specializer10);

        return specializerList;
    }

    public static void addDefaultAvailabilityForSpecializersIfNotExist(LocalDate localDate, Integer daysInMonth, List<Specializer> specializerList) {
        specializerList.forEach(specializer -> {
                setDefaultSpecializerAvailabilityIfNotExist(localDate, daysInMonth, specializer);
        });
    }

    public static Map<DayOfWeek, Integer> getStatisticsEmptyWeek() {
        Map<DayOfWeek, Integer> week = new HashMap<>();
        week.put(DayOfWeek.MONDAY, 0);
        week.put(DayOfWeek.TUESDAY, 0);
        week.put(DayOfWeek.WEDNESDAY, 0);
        week.put(DayOfWeek.THURSDAY, 0);
        week.put(DayOfWeek.FRIDAY, 0);
        week.put(DayOfWeek.SATURDAY, 0);
        week.put(DayOfWeek.SUNDAY, 0);
        return week;
    }
}

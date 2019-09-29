package pensa.on.duty.api.framework;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import pensa.on.duty.api.model.*;
import pensa.on.duty.api.service.Adapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Collection of static methods
 */
public class StaticContent {

    public static DeferredResult<ResponseEntity<ResponseMonth>> getAddErrorResult(DeferredResult<ResponseEntity<ResponseMonth>> deferredResult,
                                                                              ResponseMonth fullMonth,
                                                                              HttpStatus httpStatus,
                                                                              String message) {
        ResponseStatusMessage statusMessage = new ResponseStatusMessage();
        statusMessage.setStatus(httpStatus.name());
        statusMessage.setMessage(message);
        fullMonth.setStatusMessage(statusMessage);
        deferredResult.setResult(new ResponseEntity<>(fullMonth, httpStatus));
        return deferredResult;
    }

    public static void polulateDuties(FullMonth fullMonth, LocalDate period, List<Specializer> specializerList, Adapter adapter) {
        fullMonth.getFullDayList().forEach(fullDay -> {
            StaticContent.setSpecializer(fullMonth, fullDay,  specializerList, period);
        });

        boolean areDutiesEquilisedByDays = true;
        while (areDutiesEquilisedByDays) {
            areDutiesEquilisedByDays = DaysEqualization.equalizeDutiesByDays(fullMonth, adapter);
        }

        boolean areDutiesEquilisedByWeight = true;
        while (areDutiesEquilisedByWeight) {
            areDutiesEquilisedByWeight = WeightEqualization.equalizeDutiesByWeight(fullMonth, adapter);
        }

        boolean areDaysEquilisedByExperience = true;
        Integer equalisationLoops = 200;
        Integer index = 0;
        while (areDaysEquilisedByExperience && index <= equalisationLoops) {
            areDaysEquilisedByExperience = DaysExperienceEqualisation.equalizeDaysByExperince(fullMonth, adapter);
            index++;
        }
        System.out.println("index :" + index);
    }

    public static boolean canExhangeDaysEperience(FullMonth fullMonth, Specializer higherS, Specializer lowerS, FullDay fullDayHigher, FullDay fullDayLower) {
        return Rules.hasCurrentDay(fullMonth, higherS, fullDayHigher) &&
                !Rules.hasCurrentDay(fullMonth, higherS, fullDayLower) &&
                Rules.hasCurrentDay(fullMonth, lowerS, fullDayLower) &&
                !Rules.hasCurrentDay(fullMonth, lowerS, fullDayHigher) &&
                !Rules.hasConsecutiveDays(fullMonth, higherS, fullDayLower) &&
                !Rules.hasConsecutiveDays(fullMonth, lowerS, fullDayHigher) &&
                Rules.isAvailable(fullMonth, higherS, fullDayLower) &&
                Rules.isAvailable(fullMonth, lowerS, fullDayHigher) ;
    }

    public static boolean canExhangeDays(FullMonth fullMonth, Specializer higherS, Specializer lowerS, FullDay fullDayHigher, FullDay fullDayLower) {
        return Rules.hasCurrentDay(fullMonth, higherS, fullDayHigher) &&
                !Rules.hasCurrentDay(fullMonth, higherS, fullDayLower) &&
                Rules.hasCurrentDay(fullMonth, lowerS, fullDayLower) &&
                !Rules.hasCurrentDay(fullMonth, lowerS, fullDayHigher) &&
                !Rules.hasConsecutiveDays(fullMonth, higherS, fullDayLower) &&
                !Rules.hasConsecutiveDays(fullMonth, lowerS, fullDayHigher) &&
                Rules.isAvailable(fullMonth, higherS, fullDayLower) &&
                Rules.isAvailable(fullMonth, lowerS, fullDayHigher) &&
                fullDayHigher.getDayWeight() > fullDayLower.getDayWeight();
    }

    public static boolean canMoveDays(FullMonth fullMonth, Specializer specializer, FullDay fullDayHigher) {
        return  !Rules.hasCurrentDay(fullMonth, specializer, fullDayHigher) &&
                !Rules.hasConsecutiveDays(fullMonth, specializer, fullDayHigher) &&
                Rules.isAvailable(fullMonth, specializer, fullDayHigher);
    }

    public static void addRemoveSpecializerToFullDay(FullMonth fullMonth, Specializer removeS, Specializer addS, FullDay fullDay) {
        FullDay fullDayH = fullMonth.getFullDayList().get(fullDay.getDay().getDayOfMonth() - 1);
        fullDayH.getSpecializerList().remove(removeS);
        fullDayH.getSpecializerList().add(addS);
    }

    public static List<FullDay> getDutyDays(Specializer specializer, FullMonth fullMonth) {

        return fullMonth.getFullDayList().stream()
                .filter(fullDay -> (fullDay.getSpecializerList().contains(specializer)))
                .sorted(Comparator.comparing(fullDay -> -fullDay.getDayWeight()))
                .collect(Collectors.toList());
    }

    public static void setSpecializer(FullMonth fullMonth, FullDay fullDay, List<Specializer> specializerList, LocalDate localDate) {
        Integer dayIndex = fullMonth.getFullDayList().indexOf(fullDay);
        if (dayIndex == 0) {
            IntStream.range(0, fullMonth.getFullDayList().get(dayIndex).getSpecializerNumber()).forEach(i -> {
                fullMonth.getFullDayList().get(dayIndex).getSpecializerList().add(specializerList.get(i));
            });
        } else {
            Specializer lastSpecializer  = fullMonth.getFullDayList().get(dayIndex-1).getSpecializerList().get(fullMonth.getFullDayList().get(dayIndex-1).getSpecializerList().size()-1);
            Integer specializerIndex = specializerList.indexOf(lastSpecializer);

            for (int i = 1; i<= fullMonth.getFullDayList().get(dayIndex).getSpecializerNumber(); i ++) {
                specializerIndex = Rules.checkCurrentDay(fullMonth, specializerList, dayIndex, specializerIndex);
                specializerIndex = Rules.checkConsecutiveDays(fullMonth, specializerList, dayIndex, specializerIndex);
                specializerIndex = Rules.getAvailabilityIndex(specializerList, dayIndex, specializerIndex, localDate);
                fullMonth.getFullDayList().get(dayIndex).getSpecializerList().add(specializerList.get(specializerIndex));
            }
        }
    }


    public static boolean hasMonthAdded(List<FullMonth> fullMonthList, LocalDate period) {
        for (FullMonth fullMonthC : fullMonthList) {
            if (fullMonthC.getDutyPeriod().equals(period)) {
                return true;
            }
        }
        return false;
    }


    public static FullMonth getEmptySelectedFullMonth(List<FullMonth> fullMonthList, LocalDate period) {
        if (!StaticContent.hasMonthAdded(fullMonthList, period)) {
            FullMonth fullMonth = new FullMonth();
            fullMonth.setYear(period.getYear());
            fullMonth.setMonthName(period.getMonth().name());
            fullMonth.setDutyPeriod(period);
            List<LocalDate> daysRange = Stream.iterate(period, date -> date.plusDays(1)).limit(period.getMonth().length(true)).collect(toList());
            daysRange.forEach(ld -> {
                FullDay fullDay = DefaultValues.setDefaultFullDayData(ld);
                fullDay.setSpecializerList(new ArrayList<Specializer>());
                fullMonth.getFullDayList().add(fullDay);
            });
            return fullMonth;
        } else {
            for (FullMonth fullMonthC : fullMonthList) {
                if (fullMonthC.getDutyPeriod().equals(period)) {
                    fullMonthC.getFullDayList().forEach(fullDay -> fullDay.setSpecializerList(new ArrayList<Specializer>()));
                    return fullMonthC;
                }
            }
        }
        return null;
    }

    public static void setStatisticsMonthSpecializerDaysList(MonthStatistics monthStatistics, FullDay fullDay, Specializer specializer) {
        if (!monthStatistics.getSpecializerDaysList().containsKey(specializer.getName())) {
            monthStatistics.getSpecializerDaysList().put(specializer.getName(), DefaultValues.getStatisticsEmptyWeek());
            monthStatistics.getSpecializerDaysList().get(specializer.getName()).replace(fullDay.getDay().getDayOfWeek(), 1);
        } else {
            monthStatistics.getSpecializerDaysList().get(specializer.getName()).replace(fullDay.getDay().getDayOfWeek(),
                    monthStatistics.getSpecializerDaysList().get(specializer.getName()).get(fullDay.getDay().getDayOfWeek())+1);

        }
    }

    public static void setStatisticsMonthSpecializerDays(MonthStatistics monthStatistics, Specializer specializer) {
        if (!monthStatistics.getSpecializerDays().containsKey(specializer.getName())) {
            monthStatistics.getSpecializerDays().put(specializer.getName(), 1);
        } else {
            monthStatistics.getSpecializerDays().replace(specializer.getName(),
                    monthStatistics.getSpecializerDays().get(specializer.getName()) + 1);
        }
    }

    public static void setStatisticsMonthSpecializerWeight(MonthStatistics monthStatistics, FullDay fullDay, Specializer specializer) {
        if (!monthStatistics.getSpecializerWeight().containsKey(specializer.getName())) {
            monthStatistics.getSpecializerWeight().put(specializer.getName(), fullDay.getDayWeight());
        } else {
            monthStatistics.getSpecializerWeight().replace(specializer.getName(),
                    monthStatistics.getSpecializerWeight().get(specializer.getName()) + fullDay.getDayWeight());
        }
    }
}

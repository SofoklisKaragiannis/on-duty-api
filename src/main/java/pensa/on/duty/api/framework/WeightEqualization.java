package pensa.on.duty.api.framework;

import javafx.util.Pair;
import pensa.on.duty.api.model.FullDay;
import pensa.on.duty.api.model.FullMonth;
import pensa.on.duty.api.model.MonthStatistics;
import pensa.on.duty.api.model.Specializer;
import pensa.on.duty.api.service.Adapter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WeightEqualization {
    public static boolean equalizeDutiesByWeight(FullMonth fullMonth, Adapter adapter) {
        boolean areEqualized = false;
        MonthStatistics monthStatistics = adapter.calculateMonthStatistics(fullMonth.getDutyPeriod());

        List<Pair<String, Integer>> sortedWeightList =
                monthStatistics.getSpecializerWeight().entrySet().stream()
                        .sorted(Comparator.comparing(e -> -e.getValue()))
                        .map(e -> new Pair<>(e.getKey(), e.getValue()))
                        .collect(Collectors.toList());


        for (int i = 0; i < sortedWeightList.size(); i++) {
            for (int j = sortedWeightList.size()-1; j >= 0; j--) {
                Pair<String, Integer> higher = sortedWeightList.get(i);
                Pair<String, Integer> lower = sortedWeightList.get(j);
                areEqualized = equalizationProcessByWeight(fullMonth, adapter, higher, lower);
                if (areEqualized) {
                    break;
                }
            }
            if (areEqualized) {
                break;
            }
        }
        return areEqualized;
    }

    public static boolean equalizationProcessByWeight(FullMonth fullMonth, Adapter adapter, Pair<String, Integer> higher, Pair<String, Integer> lower) {
        boolean areEqualized = false;
        Specializer higherS = adapter.getSpecializerByName(higher.getKey());
        Specializer lowerS = adapter.getSpecializerByName(lower.getKey());

        List<FullDay> matchingDatesHigher = getDateMatchingHalfWeightForHigher(higher.getValue(), lower.getValue(), higherS, lowerS, fullMonth);
        List<FullDay> matchingDatesLower = getDateMatchingHalfWeightForLower(higher.getValue(), lower.getValue(), higherS, lowerS, fullMonth);

        for (FullDay fullDayHigher : matchingDatesHigher) {
            for (FullDay fullDayLower : matchingDatesLower) {
                if (StaticContent.canExhangeDays(fullMonth, higherS, lowerS, fullDayHigher, fullDayLower)) {
//                    System.out.println("Higher " + higherS.getName() + " : " + fullDayHigher.getDay().getDayOfMonth());
//                    System.out.println("Lower " + lowerS.getName() + " : " + fullDayLower.getDay().getDayOfMonth());

                    StaticContent.addRemoveSpecializerToFullDay(fullMonth, higherS, lowerS, fullDayHigher);

                    StaticContent.addRemoveSpecializerToFullDay(fullMonth, lowerS, higherS, fullDayLower);

                    areEqualized = true;
                    break;
                }
            }
        }
        return areEqualized;
    }

    public static List<FullDay> getDateMatchingHalfWeightForHigher(Integer higher, Integer lower, Specializer highterS, Specializer lowerS, FullMonth fullMonth) {
        Integer halfWeightDist = (higher-lower)/2;

        return fullMonth.getFullDayList().stream()
                    .filter(fullDay -> (fullDay.getDayWeight() <= halfWeightDist+1 &&
                                        fullDay.getSpecializerList().contains(highterS) &&
                                       !fullDay.getSpecializerList().contains(lowerS))
                    )
                    .sorted(Comparator.comparing(fullDay -> -fullDay.getDayWeight()))
                    .collect(Collectors.toList());
    }

    public static List<FullDay> getDateMatchingHalfWeightForLower(Integer higher, Integer lower, Specializer highterS, Specializer lowerS, FullMonth fullMonth) {
        Integer halfWeightDist = (higher-lower)/2;

        return fullMonth.getFullDayList().stream()
                .filter(fullDay -> (fullDay.getDayWeight() <= halfWeightDist +1 &&
                                   !fullDay.getSpecializerList().contains(highterS) &&
                                    fullDay.getSpecializerList().contains(lowerS))
                )
                .sorted(Comparator.comparing(fullDay -> fullDay.getDayWeight()))
                .collect(Collectors.toList());
    }
}

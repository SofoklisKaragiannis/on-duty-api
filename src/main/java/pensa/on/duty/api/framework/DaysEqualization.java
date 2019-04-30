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

public class DaysEqualization {
    public static boolean equalizeDutiesByDays(FullMonth fullMonth, Adapter adapter) {
        boolean areEqualized = false;
        MonthStatistics monthStatistics = adapter.calculateMonthStatistics(fullMonth.getDutyPeriod());

        List<Pair<String, Integer>> sortedDayList =
                monthStatistics.getSpecializerDays().entrySet().stream()
                        .sorted(Comparator.comparing(e -> -e.getValue()))
                        .map(e -> new Pair<>(e.getKey(), e.getValue()))
                        .collect(Collectors.toList());

        for (int i = 0; i < sortedDayList.size(); i++) {
            for (int j = sortedDayList.size()-1; j >= 0; j--) {
                Pair<String, Integer> higher = sortedDayList.get(i);
                Pair<String, Integer> lower = sortedDayList.get(j);
                areEqualized = equalizationProcessByDays(fullMonth, adapter, higher, lower);
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

    public static boolean equalizationProcessByDays(FullMonth fullMonth, Adapter adapter, Pair<String, Integer> higher, Pair<String, Integer> lower) {
        boolean areEqualized = false;
        if (higher.getValue() > lower.getValue()) {
            Specializer higherS = adapter.getSpecializerByName(higher.getKey());
            Specializer lowerS = adapter.getSpecializerByName(lower.getKey());

            List<FullDay> dutyDatysHigher = StaticContent.getDutyDays(higherS, fullMonth);

            for (FullDay fullDayHigher : dutyDatysHigher) {
                if (StaticContent.canMoveDays(fullMonth, lowerS, fullDayHigher)) {
//                    System.out.println("Higher : " + higherS.getName());
//                    System.out.println("Lower : " + lowerS.getName() + " : " + fullDayHigher.getDay().getDayOfMonth());

                    StaticContent.addRemoveSpecializerToFullDay(fullMonth, higherS, lowerS, fullDayHigher);

                    areEqualized = true;
                    break;
                }

            }
        }
        return areEqualized;
    }
}

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

public class DaysExperienceEqualisation {

    public static boolean equalizeDaysByExperince(FullMonth fullMonth, Adapter adapter) {
        boolean areEqualized = false;
        MonthStatistics monthStatistics = adapter.calculateMonthStatistics(fullMonth.getDutyPeriod());

        List<Pair<Integer, Integer>> sortedExperienceDayList =
                monthStatistics.getTotalDayExperience().entrySet().stream()
                        .filter(p -> fullMonth.getFullDayList().get(p.getKey() - 1).getSpecializerNumber() > 1)
                        .sorted(Comparator.comparing(e -> -e.getValue()))
                        .map(e -> new Pair<>(e.getKey(), e.getValue()))
                        .collect(Collectors.toList());

        for (int i = 0; i < sortedExperienceDayList.size(); i++) {
            for (int j = sortedExperienceDayList.size() - 1; j >= 0; j--) {
                Pair<Integer, Integer> higher = sortedExperienceDayList.get(i);
                Pair<Integer, Integer> lower = sortedExperienceDayList.get(j);
                FullDay fullDayHigher = fullMonth.getFullDayList().get(higher.getKey());
                FullDay fullDayLower = fullMonth.getFullDayList().get(lower.getKey());
                areEqualized = equalizationProcessByExperience(fullMonth, higher.getValue(), lower.getValue(), fullDayHigher, fullDayLower);
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

    private static boolean equalizationProcessByExperience(FullMonth fullMonth, Integer higherExp, Integer lowerExp, FullDay fullDayHigher, FullDay fullDayLower) {
        boolean areEqualized = false;
        if (fullDayHigher.getSpecializerNumber().equals(fullDayLower.getSpecializerNumber()) && higherExp > lowerExp) {

            List<Specializer> specializersHigher = fullDayHigher.getSpecializerList();
            List<Specializer> specializersLower = fullDayLower.getSpecializerList();
            Integer specializerMaxExperience = specializersHigher.stream().mapToInt(s -> s.getExperience()).max().getAsInt();
//                    Integer specializerMinExperience = specializersLower.stream().mapToInt(s -> s.getExperience()).min().getAsInt();
            for (Specializer higherS : specializersHigher) {
                for (Specializer lowerS : specializersLower) {
                    if (higherExp - lowerExp > specializerMaxExperience &&
                            higherS.getExperience() > lowerS.getExperience()) {

//                        Specializer higherS = specializersHigher.stream().filter(s -> s.getExperience() == specializerMaxExperience).collect(Collectors.toList()).get(0);
//                        Specializer lowerS = specializersLower.stream().filter(s -> s.getExperience() == specializerMinExperience).collect(Collectors.toList()).get(0);
//                        if (StaticContent.canExhangeDaysEperience(fullMonth, higherS, lowerS, fullDayHigher, fullDayLower)) {
//                            System.out.println("Higher " + higherS.getName() + " : " + fullDayHigher.getDay().getDayOfMonth());
//                            System.out.println("Lower " + lowerS.getName() + " : " + fullDayLower.getDay().getDayOfMonth());
//
//                            StaticContent.addRemoveSpecializerToFullDay(fullMonth, higherS, lowerS, fullDayHigher);
//
//                            StaticContent.addRemoveSpecializerToFullDay(fullMonth, lowerS, higherS, fullDayLower);
//
//                            areEqualized = true;
//                            break;
//                        }
                    }
                }
                if (areEqualized) {
                    break;
                }
            }
        }
        return areEqualized;
    }
}

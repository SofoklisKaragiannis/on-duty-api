package pensa.on.duty.api.service;

import org.springframework.stereotype.Service;
import pensa.on.duty.api.framework.StaticContent;
import pensa.on.duty.api.model.FullMonth;
import pensa.on.duty.api.model.MonthStatistics;
import pensa.on.duty.api.model.Specializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static pensa.on.duty.api.framework.DefaultValues.addDefaultAvailabilityForSpecializersIfNotExist;
import static pensa.on.duty.api.framework.DefaultValues.getDefaultSpecializers;

@Service
public class Adapter {

    private List<Specializer> specializerList;

    private List<FullMonth> fullMonthList;

    private List<MonthStatistics> monthStatisticsList;

    public List<Specializer> getSpecializerList(LocalDate localDate, Integer daysInMonth) {
        if (specializerList == null) {
            specializerList = getDefaultSpecializers();
        }
        addDefaultAvailabilityForSpecializersIfNotExist(localDate, daysInMonth, specializerList);
        return specializerList;
    }

    public List<Specializer> getSpecializerList() {
        if (specializerList != null) {
            return specializerList;
        }
        return null;
    }

    public Specializer getSpecializerByName(String name) {
        if (specializerList != null) {
            for (Specializer specializerC : specializerList) {
                if (name.equals(specializerC.getName())) {
                    return specializerC;
                }
            }
        }
        return null;
    }

    public List<FullMonth> getFullMonthList() {
        if (fullMonthList == null) fullMonthList = new ArrayList<>();
        return fullMonthList;
    }

    public void addFullMonth(FullMonth fullMonth) {
        Boolean containsFullMonth = false;
        for (FullMonth fullMonthC : getFullMonthList()) {
            if (fullMonthC.getDutyPeriod().equals(fullMonth.getDutyPeriod())) {
                fullMonthC.setFullDayList(fullMonth.getFullDayList());
                containsFullMonth = true;
            }
        }
        if (!containsFullMonth) {
            getFullMonthList().add(fullMonth);
        }
    }

    public List<MonthStatistics> getMonthStatisticsList() {
        if (monthStatisticsList== null) monthStatisticsList = new ArrayList<>();
        return monthStatisticsList;
    }

    public void addMonthStatistics(MonthStatistics monthStatistics) {
        AtomicBoolean hasMonthAdded = new AtomicBoolean(false);
        getMonthStatisticsList().forEach(monthStatisticsC -> {
            if (monthStatisticsC.getDutyPeriod().equals(monthStatistics.getDutyPeriod())) {
                monthStatisticsC.setTotalDays(monthStatistics.getTotalDays());
                monthStatisticsC.setTotalHumanDays(monthStatistics.getTotalHumanDays());
                monthStatisticsC.setTotalWeight(monthStatistics.getTotalWeight());
                monthStatisticsC.setSpecializerWeight(monthStatistics.getSpecializerWeight());
                monthStatisticsC.setSpecializerDays(monthStatistics.getSpecializerDays());
                monthStatisticsC.setSpecializerDaysList(monthStatistics.getSpecializerDaysList());
                hasMonthAdded.set(true);
            }
        });
        if (!hasMonthAdded.get()) {
            getMonthStatisticsList().add(monthStatistics);
        }
    }

    public MonthStatistics calculateMonthStatistics(LocalDate period) {
        MonthStatistics monthStatistics = new MonthStatistics();
        monthStatistics.setYear(period.getYear());
        monthStatistics.setMonthName(period.getMonth().name());
        monthStatistics.setDutyPeriod(period);
        getFullMonthList().forEach(fullMonth -> {
            if (fullMonth.getDutyPeriod().equals(period)) {
                fullMonth.getFullDayList().forEach(fullDay -> fullDay.getSpecializerNumber());
                monthStatistics.setTotalHumanDays(fullMonth.getFullDayList().stream().mapToInt(fullDay -> fullDay.getSpecializerNumber()).sum());
                monthStatistics.setTotalWeight(fullMonth.getFullDayList().stream().mapToInt(fullDay -> fullDay.getDayWeight()).sum());
                monthStatistics.setTotalDays(fullMonth.getFullDayList().size());
                fullMonth.getFullDayList().forEach(fullDay -> {
                    fullDay.getSpecializerList().forEach(specializer ->
                    {
                        StaticContent.setStatisticsMonthSpecializerWeight(monthStatistics, fullDay, specializer);

                        StaticContent.setStatisticsMonthSpecializerDays(monthStatistics, specializer);

                        StaticContent.setStatisticsMonthSpecializerDaysList(monthStatistics, fullDay, specializer);

                        monthStatistics.getTotalDayExperience().put(fullDay.getDayOfMonth(), fullDay.getSpecializerList().stream().mapToInt(s -> s.getExperience()).sum());

                    });
                });
            }
        });
        return monthStatistics;
    }
}

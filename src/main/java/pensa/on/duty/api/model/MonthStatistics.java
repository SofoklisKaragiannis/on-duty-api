package pensa.on.duty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonthStatistics implements Cloneable{

    @JsonProperty("monthName")
    private String monthName;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("dutyPeriod")
    private LocalDate dutyPeriod;

    @JsonProperty("totalHumanDays")
    private Integer totalHumanDays;

    @JsonProperty("totalDays")
    private Integer totalDays;

    @JsonProperty("totalWeight")
    private Integer totalWeight;

    @JsonProperty("specializerWeight")
    private Map<String, Integer> specializerWeight;

    @JsonProperty("specializerDays")
    private Map<String, Integer> specializerDays;

    @JsonProperty("totalDayExperience")
    private Map<Integer, Integer> totalDayExperience;

    @JsonProperty("specializerDaysList")
    private Map<String, Map<DayOfWeek, Integer>> specializerDaysList;

    public Integer getTotalHumanDays() {
        return totalHumanDays;
    }

    public void setTotalHumanDays(Integer totalHumanDays) {
        this.totalHumanDays = totalHumanDays;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totaldays) {
        this.totalDays = totaldays;
    }

    public Integer getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Integer totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Map<String, Integer> getSpecializerWeight() {
        if (specializerWeight == null) specializerWeight = new HashMap<>();
        return specializerWeight;
    }

    public void setSpecializerWeight(Map<String, Integer> specializerWeight) {
        this.specializerWeight = specializerWeight;
    }

    public Map<String, Integer> getSpecializerDays() {
        if (specializerDays == null) specializerDays = new HashMap<>();
        return specializerDays;
    }

    public void setSpecializerDays(Map<String, Integer> specializerDays) {
        this.specializerDays = specializerDays;
    }

    public Map<Integer, Integer> getTotalDayExperience() {
        if (totalDayExperience == null) totalDayExperience = new HashMap<>();
        return totalDayExperience;
    }

    public void setTotalDayExperience(Map<Integer, Integer> totalDayExperience) {
        this.totalDayExperience = totalDayExperience;
    }

    public Map<String, Map<DayOfWeek, Integer>> getSpecializerDaysList() {
        if (specializerDaysList == null) specializerDaysList = new HashMap<>();
        return specializerDaysList;
    }

    public void setSpecializerDaysList(Map<String, Map<DayOfWeek, Integer>> specializerDaysList) {
        this.specializerDaysList = specializerDaysList;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDate getDutyPeriod() {
        return dutyPeriod;
    }

    public void setDutyPeriod(LocalDate dutyPeriod) {
        this.dutyPeriod = dutyPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MonthStatistics that = (MonthStatistics) o;
        return Objects.equals(totalHumanDays, that.totalHumanDays) &&
                Objects.equals(totalDays, that.totalDays) &&
                Objects.equals(totalWeight, that.totalWeight) &&
                Objects.equals(specializerWeight, that.specializerWeight) &&
                Objects.equals(monthName, that.monthName) &&
                Objects.equals(year, that.year) &&
                Objects.equals(dutyPeriod, that.dutyPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalHumanDays, totalDays, totalWeight, specializerWeight, monthName, year, dutyPeriod);
    }

    public MonthStatistics clone() throws CloneNotSupportedException {
        return (MonthStatistics) super.clone();
    }

}

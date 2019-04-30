package pensa.on.duty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullDay implements Cloneable {

    @JsonProperty("day")
    private LocalDate day;

    @JsonProperty("fullday")
    private String fullday;

    @JsonProperty("dayOfMonth")
    private Integer dayOfMonth;

    @JsonProperty("specializerList")
    private List<Specializer> specializerList;

    @JsonProperty("specializerNumber")
    private Integer specializerNumber;

    @JsonProperty("dayWeight")
    private Integer dayWeight;

    public String getFullday() {
        return fullday;
    }

    public void setFullday(String fullday) {
        this.fullday = fullday;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public List<Specializer> getSpecializerList() {
        if (specializerList == null) specializerList = new ArrayList<>();
        return specializerList;
    }

    public void setSpecializerList(List<Specializer> specializerList) {
        this.specializerList = specializerList;
    }

    public Integer getSpecializerNumber() {
        return specializerNumber;
    }

    public void setSpecializerNumber(Integer specializerNumber) {
        this.specializerNumber = specializerNumber;
    }

    public Integer getDayWeight() {
        return dayWeight;
    }

    public void setDayWeight(Integer dayWeight) {
        this.dayWeight = dayWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FullDay that = (FullDay) o;
        return Objects.equals(day, that.day) &&
                Objects.equals(specializerList, that.specializerList) &&
                Objects.equals(specializerNumber, that.specializerNumber) &&
                Objects.equals(fullday, that.fullday) &&
                Objects.equals(dayOfMonth, that.dayOfMonth) &&
                Objects.equals(dayWeight, that.dayWeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, specializerList, specializerNumber, fullday, dayOfMonth, dayWeight);
    }

    public FullDay clone() throws CloneNotSupportedException {
        return (FullDay) super.clone();
    }
}

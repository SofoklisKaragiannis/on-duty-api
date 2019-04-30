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
public class ResponseDay implements Cloneable{

    @JsonProperty("day")
    private LocalDate day;

    @JsonProperty("fullday")
    private String fullday;

    @JsonProperty("dayOfMonth")
    private Integer dayOfMonth;

    @JsonProperty("specializerNameList")
    private List<String> specializerNameList;

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

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

    public List<String> getSpecializerNameList() {
        if (specializerNameList == null) specializerNameList = new ArrayList<>();
        return specializerNameList;
    }

    public void setSpecializerNameList(List<String> specializerNameList) {
        this.specializerNameList = specializerNameList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseDay that = (ResponseDay) o;
        return Objects.equals(dayOfMonth, that.dayOfMonth) &&
                Objects.equals(specializerNameList, that.specializerNameList) &&
                Objects.equals(day, that.day) &&
                Objects.equals(fullday, that.fullday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specializerNameList, day, dayOfMonth, fullday);
    }

    public ResponseDay clone() throws CloneNotSupportedException {
        return (ResponseDay) super.clone();
    }
}

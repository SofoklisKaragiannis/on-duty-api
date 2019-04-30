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
public class FullMonth implements Cloneable {

    @JsonProperty("monthName")
    private String monthName;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("dutyPeriod")
    private LocalDate dutyPeriod;

    @JsonProperty("fullDayList")
    private List<FullDay> fullDayList;

    @JsonProperty("statusMessage")
    private ResponseStatusMessage statusMessage;

    public List<FullDay> getFullDayList() {
        if (fullDayList == null) fullDayList = new ArrayList<>();
        return fullDayList;
    }

    public void setFullDayList(List<FullDay> fullDayList) {
        this.fullDayList = fullDayList;
    }

    public void setStatusMessage(ResponseStatusMessage statusMessage) {
        this.statusMessage = statusMessage;
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

    public ResponseStatusMessage getStatusMessage() {
        return statusMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FullMonth that = (FullMonth) o;
        return Objects.equals(fullDayList, that.fullDayList) &&
                Objects.equals(statusMessage, that.statusMessage) &&
                Objects.equals(monthName, that.monthName) &&
                Objects.equals(year, that.year) &&
                Objects.equals(dutyPeriod, that.dutyPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullDayList, statusMessage, monthName, year, dutyPeriod);
    }

    public FullMonth clone() throws CloneNotSupportedException {
        return (FullMonth) super.clone();
    }
}

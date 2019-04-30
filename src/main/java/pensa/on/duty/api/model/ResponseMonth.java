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
public class ResponseMonth implements Cloneable{

    @JsonProperty("monthName")
    private String monthName;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("dutyPeriod")
    private LocalDate dutyPeriod;

    @JsonProperty("responseDayList")
    private List<ResponseDay> responseDayList;

    @JsonProperty("statusMessage")
    private ResponseStatusMessage statusMessage;

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

    public List<ResponseDay> getResponseDayList() {
        if (responseDayList == null) responseDayList = new ArrayList<>();
        return responseDayList;
    }

    public void setResponseDayList(List<ResponseDay> responseDayList) {
        this.responseDayList = responseDayList;
    }

    public ResponseStatusMessage getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(ResponseStatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseMonth that = (ResponseMonth) o;
        return Objects.equals(responseDayList, that.responseDayList) &&
                Objects.equals(dutyPeriod, that.dutyPeriod) &&
                Objects.equals(year, that.year) &&
                Objects.equals(monthName, that.monthName) &&
                Objects.equals(statusMessage, that.statusMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseDayList, monthName, year, responseDayList, statusMessage);
    }

    public ResponseMonth clone() throws CloneNotSupportedException {
        return (ResponseMonth) super.clone();
    }
}

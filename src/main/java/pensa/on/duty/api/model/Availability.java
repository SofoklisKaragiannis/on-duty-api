package pensa.on.duty.api.model;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "dutyPeriod")
public class Availability implements Cloneable {

    @JsonProperty("monthName")
    private String monthName;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("dutyPeriod")
    private LocalDate dutyPeriod;

    @JsonProperty("availability")
    private Map<Integer, String> availability;

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

    public Map<Integer, String> getAvailability() {
        if (availability == null) availability = new HashMap<>();
        return availability;
    }

    public void setAvailability(Map<Integer, String> availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Availability that = (Availability) o;
        return  Objects.equals(availability, that.availability) &&
                Objects.equals(monthName, that.monthName) &&
                Objects.equals(year, that.year) &&
                Objects.equals(dutyPeriod, that.dutyPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(availability, monthName, year, dutyPeriod);
    }

    public Availability clone() throws CloneNotSupportedException {
        return (Availability) super.clone();
    }
}

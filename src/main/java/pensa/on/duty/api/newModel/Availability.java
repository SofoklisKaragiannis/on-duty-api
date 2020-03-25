package pensa.on.duty.api.newModel;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.*;

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

    @JsonProperty("nonAvailable")
    private Map<Integer, List<Integer>> nonAvailable;

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

    public Map<Integer, List<Integer>> getNonAvailable() {
        if (nonAvailable == null) {
            nonAvailable = new HashMap<Integer, List<Integer>>();
        }
        return nonAvailable;
    }

    public void setNonAvailable(Map<Integer, List<Integer>> nonAvailable) {
        this.nonAvailable = nonAvailable;
    }

    public void addNonAvailable(Integer dayNo, Integer id) {
        List<Integer> ids = getNonAvailable().get(dayNo);
        if (ids == null) {
            ids = new ArrayList<>();
        }
        if (id != null) ids.add(id);
        if (!getNonAvailable().containsKey(dayNo)) {
            getNonAvailable().put(dayNo, ids);
        }
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
        return  Objects.equals(nonAvailable, that.nonAvailable) &&
                Objects.equals(monthName, that.monthName) &&
                Objects.equals(year, that.year) &&
                Objects.equals(dutyPeriod, that.dutyPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nonAvailable, monthName, year, dutyPeriod);
    }

    public Availability clone() throws CloneNotSupportedException {
        return (Availability) super.clone();
    }
}

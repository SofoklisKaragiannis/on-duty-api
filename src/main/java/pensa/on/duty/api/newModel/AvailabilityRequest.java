package pensa.on.duty.api.newModel;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "dutyPeriod")
public class AvailabilityRequest implements Cloneable {

    @JsonProperty("excludeDay")
    private String excludeDay;

    @JsonProperty("specializerId")
    private Integer specializerId;

    public String getExcludeDay() {
        return excludeDay;
    }

    public void setExcludeDay(String excludeDay) {
        this.excludeDay = excludeDay;
    }

    public Integer getSpecializerId() {
        return specializerId;
    }

    public void setSpecializerId(Integer specializerId) {
        this.specializerId = specializerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvailabilityRequest that = (AvailabilityRequest) o;
        return Objects.equals(excludeDay, that.excludeDay) &&
                Objects.equals(specializerId, that.specializerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(excludeDay, specializerId);
    }

    public AvailabilityRequest clone() throws CloneNotSupportedException {
        return (AvailabilityRequest) super.clone();
    }
}

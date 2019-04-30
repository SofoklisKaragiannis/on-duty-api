package pensa.on.duty.api.model;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * supports Product json contents
 * id - title - description - price - weightFactor
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Specializer implements Cloneable{

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("availability")
    private Map<LocalDate, Availability> availability;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<LocalDate, Availability> getAvailability() {
        if (availability == null) availability = new HashMap<>();
        return availability;
    }

    public void setAvailability(Map<LocalDate, Availability> availability) {
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
        Specializer that = (Specializer) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(availability, that.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, availability );
    }

    public Specializer clone() throws CloneNotSupportedException {
        return (Specializer) super.clone();
    }
}

package pensa.on.duty.api.newModel;

import com.fasterxml.jackson.annotation.*;
import pensa.on.duty.api.model.Availability;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Specializer implements Cloneable{

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("experience")
    private Integer experience;

    @JsonProperty("grade")
    private Integer grade;

    @JsonProperty("active")
    private Boolean active;

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

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
                Objects.equals(experience, that.experience) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, experience, grade, active);
    }

    public Specializer clone() throws CloneNotSupportedException {
        return (Specializer) super.clone();
    }
}

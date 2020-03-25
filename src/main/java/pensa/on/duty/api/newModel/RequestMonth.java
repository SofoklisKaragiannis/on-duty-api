package pensa.on.duty.api.newModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pensa.on.duty.api.model.ResponseStatusMessage;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestMonth {
    @JsonProperty("month")
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestMonth that = (RequestMonth) o;
        return Objects.equals(month, that.month) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month);
    }

    public ResponseStatusMessage clone() throws CloneNotSupportedException {
        return (ResponseStatusMessage) super.clone();
    }
}

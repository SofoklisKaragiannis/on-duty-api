package pensa.on.duty.api.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


/**
 * supports ResponseStatusMessage JSON contents
 * status - message
 * it is used only in error handling
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStatusMessage implements Cloneable{

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseStatusMessage that = (ResponseStatusMessage) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status , message);
    }

    public ResponseStatusMessage clone() throws CloneNotSupportedException {
        return (ResponseStatusMessage) super.clone();
    }
}

package com.mtm.service.rest.config;

        import com.fasterxml.jackson.annotation.JsonProperty;
        import io.dropwizard.Configuration;
        import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Admin on 2/23/2019.
 */
public class RestConfiguration extends Configuration{

    @NotEmpty
    private String message;

    @NotEmpty
    private String firstParameter;

    @NotEmpty
    private String secondParameter;

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirstParameter() {
        return firstParameter;
    }

    public void setFirstParameter(String firstParameter) {
        this.firstParameter = firstParameter;
    }

    public String getSecondParameter() {
        return secondParameter;
    }

    public void setSecondParameter(String secondParameter) {
        this.secondParameter = secondParameter;
    }
}

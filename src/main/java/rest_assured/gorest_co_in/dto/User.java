package rest_assured.gorest_co_in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    @JsonProperty("email")
    private String mEmail;
    @JsonProperty("gender")
    private String mGender;
    @JsonProperty("id")
    private Integer mId;
    @JsonProperty("name")
    private String mName;
    @JsonProperty("status")
    private String mStatus;
}
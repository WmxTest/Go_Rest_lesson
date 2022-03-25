package rest_assured.gorest_co_in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "field",
        "message"
})
@Data
public class PostNegative {

    @JsonProperty("field")
    public String field;
    @JsonProperty("message")
    public String message;

}

package rest_assured.gorest_co_in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "post_id",
        "name",
        "email",
        "body"
})

@Data
public class Comment {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("post_id")
    private Integer postId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("body")
    private String body;
}
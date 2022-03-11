package rest_assured.gorest_co_in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "user_id",
        "title",
        "due_on",
        "status"
})
@Data
public class Todo {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("user_id")
    public Integer userId;
    @JsonProperty("title")
    public String title;
    @JsonProperty("due_on")
    public String dueOn;
    @JsonProperty("status")
    public String status;

}
package rest_assured.gorest_co_in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByPosition;
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
    @CsvBindByPosition(position = 0)
    private Integer id;
    @JsonProperty("post_id")
    @CsvBindByPosition(position = 1)
    private Integer postId;
    @JsonProperty("name")
    @CsvBindByPosition(position = 2)
    private String name;
    @JsonProperty("email")
    @CsvBindByPosition(position = 3)
    private String email;
    @JsonProperty("body")
    @CsvBindByPosition(position = 4)
    private String body;
}
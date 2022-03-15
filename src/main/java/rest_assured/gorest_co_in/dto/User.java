package rest_assured.gorest_co_in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class User {

    @CsvBindByPosition(position = 0)
    @JsonProperty("email")
    private String mEmail;
    @CsvBindByPosition(position = 1)
    @JsonProperty("gender")
    private String mGender;
    @CsvBindByPosition(position = 2)
    @JsonProperty("id")
    private Integer mId;
    @CsvBindByPosition(position = 3)
    @JsonProperty("name")
    private String mName;
    @CsvBindByPosition(position = 4)
    @JsonProperty("status")
    private String mStatus;

    @Override
    public String toString() {
        return "User{" +
                "mEmail='" + mEmail + '\'' +
                ", mGender='" + mGender + '\'' +
                ", mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mStatus='" + mStatus + '\'' +
                '}';
    }
}
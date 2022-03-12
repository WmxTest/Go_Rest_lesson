package rest_assured.gorest_co_in.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TodoStatuses {
    PENDING("pending"),
    COMPLETED("completed");

    private String status;
}
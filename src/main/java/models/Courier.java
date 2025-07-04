package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Courier {

    private Long id;
    private String nameOfCourier;
    private String numberOfPhone;
    private Long userID;
    private String status;

}

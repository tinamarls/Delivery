package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
public class Client {

    private Long id;
    private String nameOfClient;
    private String numberOfPhone;
    private Date dateOfBirth;
    private Long userID;
    private Integer bonuses;

}

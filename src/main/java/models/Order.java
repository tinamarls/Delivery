package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
@AllArgsConstructor

public class Order {

    Long id;
    String nameOfClient;
    String phoneNumber;
    Integer fullPrice;
    String status;
    String address;
    String comment;
    Long idClient;
    Long idCourier;

}

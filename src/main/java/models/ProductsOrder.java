package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
@AllArgsConstructor
public class ProductsOrder {

    Long id;
    Long idProduct;
    Integer count;
    Long idOrder;

}

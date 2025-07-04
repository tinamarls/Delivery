package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
@AllArgsConstructor
public class Product {

    private Long id;
    private String nameOfProduct;
    private Integer price;
    private String category;
    private String description;
    private Integer count;
    private String photoNameFile;

}

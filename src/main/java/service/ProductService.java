package service;

import dto.FileDto;

public interface ProductService {

    void saveProduct(String name, String category, Integer price, String desc, FileDto fileDto);

}

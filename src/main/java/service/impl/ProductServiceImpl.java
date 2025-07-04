package service.impl;

import dto.FileDto;
import models.Product;
import repository.ProductRepository;
import repository.impl.ProductRepositoryImpl;
import service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public void saveProduct(String name, String category, Integer price, String desc, FileDto fileDto) {

        String originalNameForFile = UUID.randomUUID() + fileDto.getName();

        try {
            Files.copy(fileDto.getInputStream(), Paths.get(fileDto.getStoragePath() + "\\" + originalNameForFile));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        Product product = Product.builder()
                .nameOfProduct(name)
                .category(category)
                .price(price)
                .description(desc)
                .photoNameFile(originalNameForFile)
                .build();

        productRepository.save(product);

    }
}

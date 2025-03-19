package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.dto.ProductDto;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.request.product.AddProductRequest;
import com.dailycodework.dream_shops.request.product.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);

    boolean productExists(String name, String brand);

    Product createProduct(AddProductRequest request, Category category);

    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest request, Long productId);

    Product updateExistingProduct(Product existingProduct, UpdateProductRequest request);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}

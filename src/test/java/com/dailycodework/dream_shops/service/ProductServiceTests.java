package com.dailycodework.dream_shops.service;

import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.CategoryRepository;
import com.dailycodework.dream_shops.repository.ProductRepository;
import com.dailycodework.dream_shops.request.product.AddProductRequest;
import com.dailycodework.dream_shops.request.product.UpdateProductRequest;
import com.dailycodework.dream_shops.service.product.ProductService;
import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void ProductService_addProduct() {
        Category swCat = new Category("Smartwatch");
        AddProductRequest request = new AddProductRequest("Samsung 2 Pro Watch", "Samsung",
                new BigDecimal(450), 8, "Samsung smart watch", swCat);

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(productService.createProduct(request, swCat));

        Product savedProduct = productService.addProduct(request);

        Assertions.assertThat(savedProduct).isNotNull();
    }

    @Test
    public void ProductService_findProductById() {
        Category swCat = new Category("Smartwatch");
        AddProductRequest request = new AddProductRequest("Samsung 2 Pro Watch", "Samsung",
                new BigDecimal(450), 8, "Samsung smart watch", swCat);

        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(productService.createProduct(request, swCat)));

        Product savedProduct = productService.getProductById(1L);

        Assertions.assertThat(savedProduct).isNotNull();
    }

    @Test
    public void ProductService_updateProduct() {
        Category swCat = new Category("Smartwatch");
        AddProductRequest request = new AddProductRequest("Samsung 2 Pro Watch", "Samsung",
                new BigDecimal(450), 8, "Samsung smart watch", swCat);

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(productService.createProduct(request, swCat));
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(productService.createProduct(request, swCat)));

        UpdateProductRequest updateRequest = new UpdateProductRequest(request.getId(), request.getName(),
                request.getBrand(), request.getPrice(), request.getInventory(), request.getDescription(), request.getCategory());

        Product savedProduct = productService.updateProduct(updateRequest, 1L);

        Assertions.assertThat(savedProduct).isNotNull();
    }

    @Test
    public void ProductService_deleteProductById() {
        Category swCat = new Category("Smartwatch");
        Product product = new Product("Samsung 2 Pro Watch", "Samsung",
                new BigDecimal(450), 8, "Samsung smart watch", swCat);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertAll(() -> productService.deleteProductById(1L));
    }
}

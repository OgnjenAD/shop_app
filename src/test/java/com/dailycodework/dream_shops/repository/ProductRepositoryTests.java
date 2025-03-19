package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void ProductRepository_findByCategoryName() {
        //Arrange
        List<Product> products;
        Category stvCat = new Category("Smart TV");
        Product p1 = new Product("Samsung Smart TV 1", "Samsung", new BigDecimal(50), 1, "Samsung smart TV", stvCat);
        Product p2 = new Product("Samsung Smart TV 2", "Samsung", new BigDecimal(550), 10, "Samsung smart TV", stvCat);

        productRepository.save(p1);
        productRepository.save(p2);

        //Act
        products = productRepository.findByCategoryName("Smart TV");

        //Assert
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(products.get(0).getId()).isNotEqualTo(0L);
    }

    @Test
    public void ProductRepository_findByBrandAndName() {
        //Arrange
        String pBrand = "Samsung";
        String pName = "Samsung Smart TV 1";
        Category stvCat = new Category("Smart TV");

        List<Product> foundProducts;
        Product p1 = new Product("Samsung Smart TV 1", "Samsung", new BigDecimal(50), 1, "Samsung smart TV", stvCat);
        Product p2 = new Product("Samsung Smart TV 2", "Samsung", new BigDecimal(550), 10, "Samsung smart TV", stvCat);

        productRepository.save(p1);
        productRepository.save(p2);

        //Act
        foundProducts = productRepository.findByBrandAndName(pBrand, pName);

        //Assert
        Assertions.assertThat(foundProducts).isNotEmpty();
        Assertions.assertThat(foundProducts.stream().filter(product -> product.getName().contains(pName))).isNotEmpty();
        Assertions.assertThat(foundProducts.stream().filter(product -> product.getName().contains(pBrand))).isNotEmpty();

    }

    @Test
    public void ProductRepository_countByBrandAndName() {
        //Arrange
        String pBrand = "Samsung";
        String pName = "Samsung 1 Pro Watch";
        Long foundProducts;

        Category stvCat = new Category("Smart TV");

        Product p1 = new Product("Samsung 1 Pro Watch", "Samsung", new BigDecimal(50), 1, "Samsung smart TV", stvCat);
        Product p2 = new Product("Samsung Smart TV 2", "Samsung", new BigDecimal(550), 10, "Samsung smart TV", stvCat);

        productRepository.save(p1);
        productRepository.save(p2);

        //Act
        foundProducts = productRepository.countByBrandAndName(pBrand, pName);

        //Assert
        Assertions.assertThat(foundProducts).isNotNull();
        Assertions.assertThat(foundProducts.intValue()).isEqualTo(1);
    }

    @Test
    public void ProductRepository_existsByNameAndBrand() {
        //Arrange
        String pBrand = "Samsung";
        String pName = "Samsung 1 Pro Watch";
        Boolean foundProducts;

        Category stvCat = new Category("Smart TV");

        Product p1 = new Product("Samsung 1 Pro Watch", "Samsung", new BigDecimal(50), 1, "Samsung smart TV", stvCat);
        Product p2 = new Product("Samsung Smart TV 2", "Samsung", new BigDecimal(550), 10, "Samsung smart TV", stvCat);

        productRepository.save(p1);
        productRepository.save(p2);

        //Act
        foundProducts = productRepository.existsByNameAndBrand(pName, pBrand);

        //Assert
        Assertions.assertThat(foundProducts).isNotNull();
        Assertions.assertThat(foundProducts).isTrue();
    }

    @Test
    public void ProductRepository_updateProduct() {
        //Arrange
        List<Product> foundProducts;

        Category accCat = new Category("Accessories");

        Product p1 = new Product("Samsung 1 Pro Watch", "Samsung", new BigDecimal(50), 1, "Samsung smart TV", accCat);

        productRepository.save(p1);

        //Act
        foundProducts = productRepository.findAll();

        Product savedProduct = foundProducts.get(0);
        savedProduct.setName("TEST NAME");
        savedProduct.setInventory(100);

        productRepository.save(savedProduct);

        //Assert
        Assertions.assertThat(foundProducts.get(0)).isNotNull();
        Assertions.assertThat(foundProducts.get(0).getInventory()).isEqualTo(100);
    }

    @Test
    public void ProductRepository_deleteProduct() {
        //Arrange
        List<Product> products;
        Category stvCat = new Category("Smart TV");
        Product p1 = new Product("Samsung Smart TV 1", "Samsung", new BigDecimal(50), 1, "Samsung smart TV", stvCat);
        Product p2 = new Product("Samsung Smart TV 2", "Samsung", new BigDecimal(550), 10, "Samsung smart TV", stvCat);

        productRepository.save(p1);
        productRepository.save(p2);

        //Act
        productRepository.deleteById(1L);
        products = productRepository.findAll();

        //Assert
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(products.get(0).getId()).isNotEqualTo(1L);
    }

}

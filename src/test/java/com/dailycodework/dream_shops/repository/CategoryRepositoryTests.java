package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.Category;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void CategoryRepository_addCategory() {
        Category automobile = new Category("Automobile");
        Category foundCategory;

        categoryRepository.save(automobile);
        foundCategory = categoryRepository.findByName(automobile.getName());

        Assertions.assertThat(foundCategory).isNotNull();
        Assertions.assertThat(foundCategory.getName()).isEqualTo(automobile.getName());
    }

    @Test
    public void CategoryRepository_updateCategory() {
        Category automobile = new Category("Automobile");
        Category foundCategory;

        categoryRepository.save(automobile);
        foundCategory = categoryRepository.findByName(automobile.getName());
        foundCategory.setName("Electric Automobile");
        categoryRepository.save(foundCategory);

        Category savedCategory = categoryRepository.findByName(foundCategory.getName());

        Assertions.assertThat(savedCategory).isNotNull();
        Assertions.assertThat(savedCategory.getName()).isEqualTo(foundCategory.getName());
    }

    @Test
    public void CategoryRepository_deleteCategory() {

        Category automobile = new Category("Automobile");
        Category foundCategory;

        categoryRepository.save(automobile);
        categoryRepository.delete(automobile);

        foundCategory = categoryRepository.findByName(automobile.getName());


        Assertions.assertThat(foundCategory).isNull();
    }


}

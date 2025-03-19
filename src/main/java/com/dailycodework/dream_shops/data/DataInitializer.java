package com.dailycodework.dream_shops.data;

import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.model.Users;
import com.dailycodework.dream_shops.model.Role;
import com.dailycodework.dream_shops.repository.CategoryRepository;
import com.dailycodework.dream_shops.repository.ProductRepository;
import com.dailycodework.dream_shops.repository.RoleRepository;
import com.dailycodework.dream_shops.repository.UsersRepository;
import com.dailycodework.dream_shops.request.product.AddProductRequest;
import com.dailycodework.dream_shops.request.user.CreateUserRequest;
import com.dailycodework.dream_shops.service.category.ICategoryService;
import com.dailycodework.dream_shops.service.product.IProductService;
import com.dailycodework.dream_shops.service.user.IUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {


    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final IProductService productService;
    private final ICategoryService categoryService;
    private final IUserService userService;


    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        //createDefaultRoleIfNotExists(defaultRoles);
        //createDefaultCategories();

        //createDefaultProducts();
        //createDefaultUserIfNotExists();
        //createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user"+i+"@email.com";
            CreateUserRequest user = new CreateUserRequest();
            user.setFirstName("User " + i);
            user.setLastName("Lastname " + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456" + i));
            user.setRoles(Set.of(userRole));
            userService.createUser(user);
            System.out.println("Default user " + i + " created successfully!");
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin"+i+"@email.com";
            CreateUserRequest user = new CreateUserRequest();
            user.setFirstName("Admin " + i);
            user.setLastName("ADMIN " + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456" + i));
            user.setRoles(Set.of(adminRole));
            userService.createUser(user);
            System.out.println("Default admin user " + i + " created successfully!");
        }
    }

    private void createDefaultProducts() {

       Category swCat = new Category("Smartwatch");
       Category spCat = new Category("Smartphone");
       Category stvCat = new Category("Smart TV");

        AddProductRequest sw1 = new AddProductRequest("Samsung 1 Pro Watch", "Samsung", new BigDecimal(350), 10, "Samsung smart watch", swCat);
        AddProductRequest sw2 = new AddProductRequest("Samsung 2 Pro Watch", "Samsung", new BigDecimal(450), 8, "Samsung smart watch", swCat);
        AddProductRequest sp1 = new AddProductRequest("Samsung 20 Pro", "Samsung", new BigDecimal(1300), 20, "Samsung smartphones", spCat);
        AddProductRequest sp2 = new AddProductRequest("Samsung 20 Pro Plus", "Samsung", new BigDecimal(1450), 10, "Samsung smartphones", spCat);
        AddProductRequest tv1 = new AddProductRequest("Samsung Smart TV 1", "Samsung", new BigDecimal(450), 20, "Samsung smart TV", stvCat);
        AddProductRequest tv2 = new AddProductRequest("Samsung Smart TV 2", "Samsung", new BigDecimal(550), 10, "Samsung smart TV", stvCat);

        AddProductRequest appleSw1 = new AddProductRequest("Apple 1 Pro Watch", "Apple", new BigDecimal(350), 15, "Apple smart watch", swCat);
        AddProductRequest appleSw2 = new AddProductRequest("Apple 2 Pro Watch", "Apple", new BigDecimal(450), 10, "Apple smart watch", swCat);
        AddProductRequest appleSp1 = new AddProductRequest("Apple 20 Pro", "Apple", new BigDecimal(1300), 20, "Apple smartphones", spCat);
        AddProductRequest appleSp2 = new AddProductRequest("Apple 20 Pro Plus", "Apple", new BigDecimal(1450), 10, "Apple smartphones", spCat);
        AddProductRequest appleTv1 = new AddProductRequest("Apple Smart TV 1", "Apple", new BigDecimal(450), 20, "Apple smart TV", stvCat);
        AddProductRequest appleTv2 = new AddProductRequest("Apple Smart TV 2", "Apple", new BigDecimal(550), 10, "Apple smart TV", stvCat);

        List<AddProductRequest> products = List.of(sw1, sw2, sp1, sp2, tv1, tv2, appleSw1, appleSw2, appleSp1, appleSp2, appleTv1, appleTv2);

        products.forEach(productService::addProduct);
    }

    private void createDefaultCategories() {
        Category tv = new Category("Smart TV");
        Category accessories = new Category("Accessories");
        Category smartphone = new Category("Smartphone");
        Category smartwatch = new Category("Smartwatch");

        List<Category> categories = List.of(tv, accessories, smartphone, smartwatch);

        categories.forEach(categoryService::addCategory);
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }



}

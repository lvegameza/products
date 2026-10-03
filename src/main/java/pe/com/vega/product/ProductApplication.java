package pe.com.vega.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import pe.com.vega.product.model.Product;
import pe.com.vega.product.service.ProductCacheService;

import java.math.BigDecimal;

@EnableCaching
@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

//    @Bean
//    CommandLineRunner testRedis(ProductCacheService cacheService) {
//        return args -> {
//            Product product = new Product(1L, "Laptop", new BigDecimal(30000));
//
//            cacheService.save(product);
//
//            Product cacheProduct = cacheService.get(product.getId());
//
//            System.out.println("Producto desde redis" + cacheProduct.getName() +""+ cacheProduct.getPrice());
//        };
//    }
}

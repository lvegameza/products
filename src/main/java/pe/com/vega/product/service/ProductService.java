package pe.com.vega.product.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pe.com.vega.product.dto.ProductRequest;
import pe.com.vega.product.dto.ProductResponse;
import pe.com.vega.product.dto.ProductUpdateRequest;
import pe.com.vega.product.exception.ProductNotFoundException;
import pe.com.vega.product.model.Product;
import pe.com.vega.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductCacheService cacheService;
    private final ProductRepository repository;
    private Long nextId = 1L;

    public ProductService(ProductCacheService cacheService, ProductRepository repository) {
        this.cacheService = cacheService;
        this.repository = repository;
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product(nextId++, request.name(), request.price());

        Product saveProduct = repository.save(product);

        return new ProductResponse(saveProduct.getId(), saveProduct.getName(), saveProduct.getPrice());
    }

    @CachePut(value = "products", key = "#id")
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request){
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Product updateProduct = new Product(id, request.name(), request.price());

        Product saveProduct = repository.update(updateProduct);

        //cacheService.delete(id);

        return new ProductResponse(
                saveProduct.getId(), saveProduct.getName(), saveProduct.getPrice());

    }

    public ProductResponse getProductById(Long id) {

        Product product = Optional.ofNullable(cacheService.get(id))
                .orElseGet(() -> {
                    Product prd = repository.findById(id).orElseThrow(
                            () -> new ProductNotFoundException(id));

                    cacheService.save(prd);
                    return prd;
                });

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    public void deleteProduct(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        repository.deleteById(product.getId());
        cacheService.delete(id);
    }

    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProductByIdWithCache(Long id) {
        System.out.println(">>> EJECUTANDO MÉTODO - consultando repository");
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return new ProductResponse(product.getId(),product.getName(), product.getPrice());
    }
}

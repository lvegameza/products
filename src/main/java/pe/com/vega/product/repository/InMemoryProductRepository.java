package pe.com.vega.product.repository;

import org.springframework.stereotype.Repository;
import pe.com.vega.product.exception.ProductNotFoundException;
import pe.com.vega.product.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = new ArrayList<>();

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public Product update(Product product) {
        for (int i = 0; i < products.size(); i++) {

            if (products.get(i).getId().equals(product.getId())) {
                products.set(i, product);
                return product;
            }
        }

        throw new ProductNotFoundException(product.getId());
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(products);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(Long id) {
        products.removeIf(x -> x.getId().equals(id));
    }
}

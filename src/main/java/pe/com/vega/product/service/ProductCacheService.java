package pe.com.vega.product.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import pe.com.vega.product.model.Product;

import java.time.Duration;

@Service
public class ProductCacheService {
    //private final StringRedisTemplate redisTemplate;
    private final RedisTemplate<String, Product> redisTemplate;

    public ProductCacheService(RedisTemplate<String, Product> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(Product product) {
        String key = "product:" + product.getId();
        redisTemplate.opsForValue().set(key, product, Duration.ofMinutes(10));
    }

    public Product get(Long id){
        String key = "product:"+id;
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(Long id) {
        String key = "product:"+id;
        redisTemplate.delete(key);
    }
}

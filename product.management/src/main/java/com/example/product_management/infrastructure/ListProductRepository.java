package com.example.product_management.infrastructure;

import com.example.product_management.domain.EntityNotFoundException;
import com.example.product_management.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("test")
public class ListProductRepository implements ProductRepository {
    private List<Product> products = new CopyOnWriteArrayList<>();
    private AtomicLong sequence = new AtomicLong(1L);


    @Override
    public Product add(Product product) {
        product.setId(sequence.getAndIncrement());
        products.add(product);
        System.out.println(products);
        return product;
    }

    @Override
    public Product findById(Long id) {

        return products.stream()
                .filter(p -> p.sameId(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Product를 찾기 못했습니다."));

//        for (Product product : products) {
//            if (product.sameId(id)) {
//                return product;
//            }
//        }
//        throw new RuntimeException("product not found");
    }

    public List<Product> findAll() {
        return products;
    }

    public List<Product> findByNameContaining(String name)
    {
//        -for문-
//        for(Product product : products) {
//            if (product.containsName(name)) {
//                return products;
//            }
//        }
//        throw new RuntimeException("product not found");

        return products.stream()
                .filter(product -> product.containsName(name))
                .toList();
    }

    public Product update(Product product)
    {
        Integer indexToModify = products.indexOf(product);

        products.set(indexToModify, product);
        return product;

    }

    public void delete(Long id)
    {
        Product product = this.findById(id);
        products.remove(product);
    }
}

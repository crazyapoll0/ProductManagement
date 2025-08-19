package com.example.product_management.infrastructure;


import com.example.product_management.domain.Product;

import java.util.List;

public interface ProductRepository
{
    public Product add(Product product);
    public Product findById(Long id);
    public List<Product> findAll();
    public List<Product> findByNameContaining(String name);
    public Product update(Product product);
    public void delete(Long id);
}

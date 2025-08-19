package com.example.product_management.infrastructure;

import com.example.product_management.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Profile("prod")
public class DatabaseProductRepository implements ProductRepository{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SqlParameterSource namedParameter;

    public Product add(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate
        .update("INSERT INTO products (name, price, amount) VALUES (:name, :price, :amount)", namedParameter, keyHolder);

        Long generated = keyHolder.getKey().longValue();
        product.setId(generated);

        return product;
    }
    public Product findById(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        Product product = namedParameterJdbcTemplate.queryForObject("select id, name, price, amount from products where id=:id",
                namedParameter, new BeanPropertyRowMapper<>(Product.class)
        );

        return product;
    }
    public List<Product> findAll() {
        List<Product> products = namedParameterJdbcTemplate.query(
                "select * from products", new BeanPropertyRowMapper<>(Product.class)
        );
        return products;
    }

    public List<Product> findByNameContaining(String name) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", "%" +  name + "%");

        List<Product> products = namedParameterJdbcTemplate.query(
                "select * from products where name like :name", namedParameter, new BeanPropertyRowMapper<>(Product.class)
        );
        return products;
    }

    public Product update(Product product) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update("update products set name=:name, price=:price, amount=:amount where id=:id", namedParameter);
        return product;
    }
    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        namedParameterJdbcTemplate.update("delete from products where id=:id", namedParameter);
    }

    public Product findByName(Product product) {
        return null;
    }

}
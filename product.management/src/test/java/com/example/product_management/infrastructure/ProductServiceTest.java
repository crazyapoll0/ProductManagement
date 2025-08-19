package com.example.product_management.infrastructure;

import com.example.product_management.application.ProductService;
import com.example.product_management.domain.Product;
import com.example.product_management.presentation.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("prod")
class ProductServiceTest {

        @Autowired
        ProductService ProductService;

        @Test
        @Transactional
        @DisplayName("상품을 추가한 후 id로 조회하면 해당 상품이 조회되어야 한다.")
        void productAddAndFindById() {
            ProductDto productDto = new ProductDto("연필", 300, 20);

            ProductDto savedProductDto = ProductService.add(productDto);
            Long savedProductId = savedProductDto.getId();

            ProductDto foundProductDto = ProductService.findId(savedProductId);

            assertTrue(Objects.equals(savedProductDto.getId(), foundProductDto.getId()));
            assertTrue(Objects.equals(savedProductDto.getName(), foundProductDto.getName()));
            assertTrue(Objects.equals(savedProductDto.getPrice(), foundProductDto.getPrice()));
            assertTrue(Objects.equals(savedProductDto.getAmount(), foundProductDto.getAmount()));
        }
    }


package com.example.product_management.infrastructure;

import com.example.product_management.application.ProductService;
import com.example.product_management.application.ValidationService;
import com.example.product_management.domain.Product;
import com.example.product_management.presentation.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ValidationService validationService;

    @Test
    @DisplayName("상품 추가 후에는 추가된 상품이 반환되어야한다.")
    void productAdd() {
        ProductDto productDto= new ProductDto("연필", 300, 20);
        Long productId = 1L;

        Product product = productDto.toEntity();
        product.setId(productId);
        when(productRepository.add(any())).thenReturn(product);
        ProductDto savedProductDto = productService.add(productDto);

        assertTrue(savedProductDto.getId().equals(productId));
        assertTrue(savedProductDto.getName().equals(productDto.getName()));
        assertTrue(savedProductDto.getPrice().equals(productDto.getPrice()));
        assertTrue(savedProductDto.getAmount().equals(productDto.getAmount()));
    }
}

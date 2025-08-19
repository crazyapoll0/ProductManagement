package com.example.product_management.application;

import com.example.product_management.domain.Product;
import com.example.product_management.infrastructure.DatabaseProductRepository;
import com.example.product_management.infrastructure.ListProductRepository;
import com.example.product_management.infrastructure.ProductRepository;
import com.example.product_management.presentation.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService
{
//    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ValidationService validationService;

    public ProductDto add(ProductDto productDto)
    {
//        // Dto ==> Entity 변환(Dto to entity)
//        Product product = ProductDto.toEntity(productDto);
//        if(product.getName().length() > 100 || product.getName())
//            throw new IllegalAccessException("상품 이름의 길이가 맞지 않습니다.");
        Product product =  productDto.toEntity(productDto);
        validationService.checkValid(product);
        Product savedProduct = productRepository.add(product);
//        // Entity ==> Dto 변환 (Entity to Dto)
        return savedProduct.toDto();
    }

    public ProductDto findId(Long id)
    {
        Product product = productRepository.findById(id);
        return product.toDto();
    }

    public List<ProductDto> findAll()
    {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> product.toDto())
                .toList();

//        -for문으로 사용할때 아래코드-
//        List<ProductDto> productDtos = new ArrayList<>();
//        for(Product product : products)
//        {
//            ProductDto productDto = modelMapper.map(product, ProductDto.class);
//            productDtos.add(productDto);
//        }

        return productDtos;
    }

    public List<ProductDto> findByNameContaining(String name)
    {
        List<Product> products = productRepository.findByNameContaining(name);
        List<ProductDto> productDtos = products.stream()
                .map(product -> product.toDto())
                .toList();
        return productDtos;
    }

    public ProductDto update(ProductDto productDto)
    {
        Product product = productDto.toEntity();
        Product updatedProduct = productRepository.update(product);
        return updatedProduct.toDto();
    }

    public void delete(Long id)
    {
        productRepository.delete(id);
    }

}

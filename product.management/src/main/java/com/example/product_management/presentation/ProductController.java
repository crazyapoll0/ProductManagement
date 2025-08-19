package com.example.product_management.presentation;


import com.example.product_management.application.ProductService;
import com.example.product_management.application.ValidationService;
import com.example.product_management.presentation.dto.ProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController
{
    private final ProductService productService;


    // (/products) POST HTTP REQ 메서드를 매핑하는 메서드
    // 상품에 대한 json 데이터 ==> BODY에 담아서 요청
    // 성공 ==> 200(ok) + 응답 BODY에 저장된 데이터를 다시 반환
    @PostMapping("/products")
    public ProductDto creatProduct(@Valid @RequestBody ProductDto productDto)
    {
        // 매개변수로 받은 product를 영구적으로 저장하는 어떤 행위

        return productService.add(productDto);
    }

    @GetMapping("/products/{id}")
    public ProductDto findById(@PathVariable Long id){
        return productService.findId(id);
    }

//    @GetMapping("/products")
//    public List<ProductDto> findAllProduct(){
//        return productService.findAll();
//    }

    @GetMapping("/products")
    public List<ProductDto> findProducts(@RequestParam(required = false) String name)
    {
        if (null == name)
            return productService.findAll();

        return productService.findByNameContaining(name);
    }

    @PutMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto)
    {
        productDto.setId(id);
        return productService.update(productDto);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id)
    {
        productService.delete(id);
    }
}

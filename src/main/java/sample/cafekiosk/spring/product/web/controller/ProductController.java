package sample.cafekiosk.spring.product.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.product.application.service.ProductService;
import sample.cafekiosk.spring.product.domain.ProductSellingType;
import sample.cafekiosk.spring.product.web.dto.ProductResponse;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;


    @GetMapping("/products")
    public List<ProductResponse> getSellingProducts(@RequestParam(required = false)List<ProductSellingType> productSellingTypes) {
        return productService.getSellingProducts(productSellingTypes);
    }
}

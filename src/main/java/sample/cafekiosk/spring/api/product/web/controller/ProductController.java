package sample.cafekiosk.spring.api.product.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sample.cafekiosk.spring.api.product.application.service.ProductService;
import sample.cafekiosk.spring.api.product.domain.ProductSellingType;
import sample.cafekiosk.spring.api.product.web.dto.ProductResponse;
import sample.cafekiosk.spring.api.product.web.dto.request.ProductCreateRequest;

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

    @PostMapping("/products")
    public ProductResponse createProduct(@RequestBody @Valid ProductCreateRequest request) {
        return productService.createProduct(request.toServiceRequest());
    }
}

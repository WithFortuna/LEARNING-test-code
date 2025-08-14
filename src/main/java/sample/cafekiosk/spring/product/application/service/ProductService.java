package sample.cafekiosk.spring.product.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.domain.ProductSellingType;
import sample.cafekiosk.spring.product.repository.ProductRepository;
import sample.cafekiosk.spring.product.web.dto.ProductResponse;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts(List<ProductSellingType> productSellingTypes) {
        productSellingTypes = productSellingTypes.isEmpty() ? List.of((ProductSellingType.values())) : productSellingTypes;
        List<Product> sellingProducts = productRepository.findByProductSellingTypeIn(productSellingTypes);

        return sellingProducts.stream()
                .map(e -> ProductResponse.from(e))
                .toList();
    }
}

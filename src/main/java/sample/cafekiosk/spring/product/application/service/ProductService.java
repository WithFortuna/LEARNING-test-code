package sample.cafekiosk.spring.product.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.domain.ProductSellingType;
import sample.cafekiosk.spring.product.repository.ProductRepository;
import sample.cafekiosk.spring.product.web.dto.ProductResponse;
import sample.cafekiosk.spring.product.web.dto.request.ProductCreateRequest;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts(List<ProductSellingType> productSellingTypes) {
        productSellingTypes = productSellingTypes == null ? List.of((ProductSellingType.values())) : productSellingTypes;
        List<Product> sellingProducts = productRepository.findByProductSellingTypeIn(productSellingTypes);

        return sellingProducts.stream()
                .map(e -> ProductResponse.from(e))
                .toList();
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        String nextProductNumber = createNextProductNumber();
        
        Product product = Product.create(
                request.productType(),
                request.productSellingType(),
                request.name(),
                request.price(),
                nextProductNumber
        );
        
        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }
        
        int latestNumber = Integer.parseInt(latestProductNumber);
        return String.format("%03d", latestNumber + 1);
    }
}

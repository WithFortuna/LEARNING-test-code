package sample.cafekiosk.spring.api.product.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.cafekiosk.spring.api.IntegrationTestSupport;
import sample.cafekiosk.spring.api.product.application.dto.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.product.domain.Product;
import sample.cafekiosk.spring.api.product.domain.ProductSellingType;
import sample.cafekiosk.spring.api.product.domain.ProductType;
import sample.cafekiosk.spring.api.product.repository.ProductRepository;
import sample.cafekiosk.spring.api.product.utility.ProductUtils;
import sample.cafekiosk.spring.api.product.web.dto.ProductResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest extends IntegrationTestSupport {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void getSellingProducts() {
    }

    @DisplayName("상품을 등록한다.")
    @Test
    void should_hasSize_1_when_createProduct_called() {
        //given
        ProductCreateServiceRequest req = new ProductCreateServiceRequest(ProductType.BOTTLE, ProductSellingType.SELLING, "item1", 1000);

        //when
        ProductResponse saved = productService.createProduct(req);

        //then
        assertThat(saved).isNotNull()
                .extracting(ProductResponse::productType, ProductResponse::productSellingType, ProductResponse::name, ProductResponse::price)
                .contains(req.productType(), req.productSellingType(), req.name(), req.price());
    }


    @DisplayName("처음으로 상품을 등록하면 상품번호는 001이다.")
    @Test
    public void should_return_001_when_create_first_product() {
        // given
        ProductCreateServiceRequest req = new ProductCreateServiceRequest(ProductType.BOTTLE, ProductSellingType.SELLING, "item1", 1000);

        // when
        ProductResponse saved = productService.createProduct(req);

        // then
        assertThat(saved).isNotNull()
                .extracting(ProductResponse::productNumber)
                .isEqualTo("001");
    }

    @DisplayName("기존 상품이 있는 경우 새로운 상품을 등록하면 상품번호는 가장 큰 상품번호의 다음번호이다.")
    @Test
    public void should_return_nextProductNumber_when_create_product() {
        // given
        Product item1 = ProductUtils.createProduct(ProductType.BOTTLE, 1000, "001");
        Product item2 = ProductUtils.createProduct(ProductType.BOTTLE, 1000, "002");
        Product item3 = ProductUtils.createProduct(ProductType.BOTTLE, 1000, "003");
        productRepository.saveAll(List.of(item1, item2, item3));
        ProductCreateServiceRequest req = new ProductCreateServiceRequest(ProductType.BOTTLE, ProductSellingType.SELLING, "item4", 1000);

        // when
        ProductResponse saved = productService.createProduct(req);

        // then
        assertThat(saved).isNotNull()
                .extracting(ProductResponse::productNumber)
                .isEqualTo("004");
    }
}
package sample.cafekiosk.spring.product.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.domain.ProductSellingType;
import sample.cafekiosk.spring.product.domain.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("내가 원하는 SellingType 목록을 지정하면 그에 해당하는 Product를 반환한다.")
    @Test
    public void should_hasSize_2_when_findAllByProductSellingTypeIn_called_with_SELLING_HOLDING() {
        // given
        Product item1 = createProduct_BOTTLE_SELLING();
        Product item2 = createProduct_BOTTLE_NOT_SELLING();
        Product item3 = createProduct_BOTTLE_HOLDING();
        productRepository.saveAll(List.of(item1, item2, item3));

        // when
        List<Product> products = productRepository.findByProductSellingTypeIn(List.of(ProductSellingType.SELLING, ProductSellingType.HOLDING));

        // then
        assertThat(products).hasSize(2)
                .extracting(Product::getProductSellingType, Product::getName)
                .containsExactlyInAnyOrder(tuple(ProductSellingType.SELLING, item1.getName()), tuple(ProductSellingType.HOLDING, item3.getName()))
        ;
    }

    private static Product createProduct_BOTTLE_SELLING() {
        return Product.create(ProductType.BOTTLE, ProductSellingType.SELLING, "아메리카노", 1000);
    }
    private static Product createProduct_BOTTLE_NOT_SELLING() {
        return Product.create(ProductType.BOTTLE, ProductSellingType.NOT_SELLING, "카페라떼", 1000);
    }
    private static Product createProduct_BOTTLE_HOLDING() {
        return Product.create(ProductType.BOTTLE, ProductSellingType.HOLDING, "아인슈페너", 1000);
    }
}
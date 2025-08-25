package sample.cafekiosk.spring.api.product.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.cafekiosk.spring.api.IntegrationTestSupport;
import sample.cafekiosk.spring.api.product.domain.Product;
import sample.cafekiosk.spring.api.product.domain.ProductSellingType;
import sample.cafekiosk.spring.api.product.domain.ProductType;
import sample.cafekiosk.spring.api.product.utility.ProductUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.api.product.utility.ProductUtils.*;


//@DataJpaTest
//@Transactional
//@SpringBootTest
class ProductRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("SellingType 목록을 지정하면 그에 해당하는 Product를 반환한다.")
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
                .containsExactlyInAnyOrder(
                        tuple(ProductSellingType.SELLING, item1.getName()),
                        tuple(ProductSellingType.HOLDING, item3.getName())
                );
    }

    @DisplayName("상품번호 리스트에 속한 상품들을 조회한다.")
    @Test
    public void should_hasSize_1_when_findALlByProductNumberIn() {
        // given
        Product item1 = createProduct_BOTTLE_SELLING();
        Product item2 = createProduct_BOTTLE_NOT_SELLING();
        Product item3 = createProduct_BOTTLE_HOLDING();
        productRepository.saveAll(List.of(item1, item2, item3));

        // when
        List<Product> findProducts = productRepository.findAllByProductNumberIn(List.of(item1.getProductNumber(), item2.getProductNumber()));

        // then
        Assertions.assertThat(findProducts).hasSize(2)
                .extracting(Product::getProductNumber, Product::getName)
                .containsExactlyInAnyOrder(
                        tuple(item1.getProductNumber(), item1.getName()),
                        tuple(item2.getProductNumber(), item2.getName())
                )
        ;

    }

    @DisplayName("가장 높은 상품번호를 조회한다.")
    @Test
    public void should_return_003_when_findLatestProductNumber_called() {
        // given
        Product item1 = ProductUtils.createProduct(ProductType.BOTTLE, 1000, "001");
        Product item2 = ProductUtils.createProduct(ProductType.BOTTLE, 1000, "002");
        Product item3 = ProductUtils.createProduct(ProductType.BOTTLE, 1000, "003");
        productRepository.saveAll(List.of(item1, item2, item3));

        // when
        String latestProductNumber = productRepository.findLatestProductNumber();

        // then
        Assertions.assertThat(latestProductNumber).isEqualTo("003");
    }

}
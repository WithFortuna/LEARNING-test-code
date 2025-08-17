package sample.cafekiosk.spring.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.domain.ProductSellingType;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductSellingType(ProductSellingType productSellingType);

    List<Product> findByProductSellingTypeIn(Collection<ProductSellingType> productSellingTypes);

    List<Product> findAllByProductNumberIn(Collection<String> productNumbers);

    @Query("SELECT p.productNumber FROM Product p ORDER BY p.id DESC LIMIT 1")
    String findLatestProductNumber();
}

package sample.cafekiosk.spring.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.stock.domain.Stock;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    List<Stock> findByProduct(Product product);
}

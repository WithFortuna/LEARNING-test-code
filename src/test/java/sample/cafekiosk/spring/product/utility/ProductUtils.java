package sample.cafekiosk.spring.product.utility;

import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.domain.ProductSellingType;
import sample.cafekiosk.spring.product.domain.ProductType;
import sample.cafekiosk.spring.product.web.controller.ProductController;

public class ProductUtils {
    public static Product createProduct_BOTTLE_SELLING() {
        return Product.create(ProductType.BOTTLE, ProductSellingType.SELLING, "아메리카노", 1000, "001");
    }
    public static Product createProduct_BOTTLE_NOT_SELLING() {
        return Product.create(ProductType.BOTTLE, ProductSellingType.NOT_SELLING, "카페라떼", 1000, "002");
    }
    public static Product createProduct_BOTTLE_HOLDING() {
        return Product.create(ProductType.BOTTLE, ProductSellingType.HOLDING, "아인슈페너", 1000, "003");
    }

    public static Product createProduct(ProductType productType, int price, String productNumber) {
        return Product.create(productType, ProductSellingType.SELLING, "defaultName", price, productNumber);
    }
}

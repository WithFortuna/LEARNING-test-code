package sample.cafekiosk.spring.api.product.web.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sample.cafekiosk.spring.api.product.web.dto.request.ProductCreateRequest;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.api.product.domain.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.api.product.domain.ProductType.BOTTLE;

public class ProductCreateRequestTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void valid_request_should_pass() {
        var req = new ProductCreateRequest(BOTTLE, SELLING, "item1", 1000);
        Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }

    //TODO: 나중에 나오면 공부
    @ParameterizedTest(name = "{index} -> {0}")
    @MethodSource("invalidRequests")
    void invalid_requests(String caseName, ProductCreateRequest req, String expectedField, String expectedConstraintSimpleName) {
        Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(req);

        assertThat(violations)
                .as("Expect a violation on field '%s'", expectedField)
                .anySatisfy(v -> {
                    assertThat(v.getPropertyPath().toString()).isEqualTo(expectedField);
                    // @NotNull, @NotBlank, @Positive ...
                    String actual = v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
                    assertThat(actual).isEqualTo(expectedConstraintSimpleName);
                });
    }

    static Stream<Arguments> invalidRequests() {
        return Stream.of(
                Arguments.of("type is null", new ProductCreateRequest(null, SELLING, "item1", 1000), "productType", "NotNull"),
                Arguments.of("name is blank", new ProductCreateRequest(BOTTLE, SELLING, " ", 1000), "name", "NotBlank"),
                Arguments.of("price is negative", new ProductCreateRequest(BOTTLE, SELLING, "item1", -1), "price", "Positive")
        );
    }

}

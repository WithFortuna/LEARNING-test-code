package sample.cafekiosk.spring.docs.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.api.product.application.dto.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.product.application.service.ProductService;
import sample.cafekiosk.spring.api.product.domain.ProductSellingType;
import sample.cafekiosk.spring.api.product.domain.ProductType;
import sample.cafekiosk.spring.api.product.web.controller.ProductController;
import sample.cafekiosk.spring.api.product.web.dto.ProductResponse;
import sample.cafekiosk.spring.api.product.web.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.docs.RestDocsSupport;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static sample.cafekiosk.spring.api.product.domain.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.api.product.domain.ProductType.BOTTLE;

public class ProductControllerDocsTest extends RestDocsSupport {
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @DisplayName("")
    @Test
    public void testDocs() throws Exception {
        ProductCreateRequest req = new ProductCreateRequest(BOTTLE, SELLING, "item1", 1000);
        given(productService.createProduct(Mockito.any(ProductCreateServiceRequest.class)))
                .willReturn(new ProductResponse(1L,
                        "001",
                        BOTTLE,
                        SELLING,
                        "ice cream",
                        5000)
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document(
                        "product-create-api",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint()),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("productType").type(JsonFieldType.STRING)
                                        .attributes(Attributes.key("hallo").value("gogogo"))
                                        .description("상품 타입: " + Arrays.toString(ProductType.values()))
                                        .attributes(Attributes.key("hallo").value("gogogo")),
                                PayloadDocumentation.fieldWithPath("productSellingType").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("판매 타입" + Arrays.toString(ProductSellingType.values()))
                                        .attributes(Attributes.key("hallo").value("gogogo")),
                                PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("상품 이름")
                                        .attributes(Attributes.key("hallo").value("gogogo")),
                                PayloadDocumentation.fieldWithPath("price").type(JsonFieldType.NUMBER)
                                        .description("상품 가격")
                                        .attributes(Attributes.key("hallo").value("gogogo"))
                        ),
                        PayloadDocumentation.responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER)
                                        .description("식별자")
                                        .attributes(Attributes.key("hallo").value("gogogo")),
                                fieldWithPath("productNumber").type(JsonFieldType.STRING)
                                        .description("상품 고유 번호")
                                        .attributes(Attributes.key("hallo").value("gogogo")),
                                fieldWithPath("productType").type(JsonFieldType.STRING)
                                        .description("상품 타입:" + Arrays.toString(ProductType.values()))
                                        .attributes(Attributes.key("hallo").value("gogogo")),
                                PayloadDocumentation.fieldWithPath("productSellingType").type(JsonFieldType.STRING)
                                        .description("판매 타입" + Arrays.toString(ProductSellingType.values()))
                                        .attributes(Attributes.key("hallo").value("gogogo")),
                                PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("상품 이름")
                                        .attributes(Attributes.key("hallo").value("gogogo")),
                                PayloadDocumentation.fieldWithPath("price").type(JsonFieldType.NUMBER)
                                        .description("상품 가격")
                                        .attributes(Attributes.key("hallo").value("gogogo"))
                        )
                ))
        ;

    }
}

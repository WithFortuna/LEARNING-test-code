package sample.cafekiosk.spring.api.product.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.api.ControllerTestSupport;
import sample.cafekiosk.spring.api.product.domain.ProductType;
import sample.cafekiosk.spring.api.product.web.dto.ProductResponse;
import sample.cafekiosk.spring.api.product.web.dto.request.ProductCreateRequest;

import java.util.List;

import static sample.cafekiosk.spring.api.product.domain.ProductSellingType.SELLING;

//@WebMvcTest(ProductController.class)
class ProductControllerTest extends ControllerTestSupport {

    @DisplayName("상품을 생성한다.")
    @Test
    public void createProduct() throws Exception {
        // given
        ProductCreateRequest req = new ProductCreateRequest(ProductType.BOTTLE, SELLING, "item1", 1000);

        // when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("상품타입을 입력하지 않고 상품을 생성하면 예외가 발생한다.")
    @Test
    public void should_return_4xxException_when_createProduct_withoutType() throws Exception {
        // given
        ProductCreateRequest req = new ProductCreateRequest(null, SELLING, "item1", 1000);

        // when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 타입은 필수입니다"))
                .andDo(MockMvcResultHandlers.print());
    }
    @DisplayName("상품 등록할 때 이름이 공백이면 예외가 발생한다.")
    @Test
    public void should_return_4xxException_when_createProduct_with_blankName() throws Exception {
        // given
        ProductCreateRequest req = new ProductCreateRequest(ProductType.BOTTLE, SELLING, " ", 1000);

        // when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("이름은 공백이면 안됩니다"))
                .andDo(MockMvcResultHandlers.print())
        ;
    }

    @DisplayName("상품 등록할 때 가격이 음수면 예외가 발생한다.")
    @Test
    public void should_return_4xxException_when_createProduct_with_negativePrice() throws Exception{
        // given
        ProductCreateRequest req = new ProductCreateRequest(ProductType.BOTTLE, SELLING, "ol af", -1);

        // when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("가격은 양수여야 합니다"))
        ;
    }

    @DisplayName("판매중인 상품을 조회한다.")
    @Test
    public void should_return_2_when_getSellingProducts_called() throws Exception {
        // given
        List<ProductResponse> response = List.of();
//        Mockito.when(productService.getSellingProducts(List.of(SELLING)))
//                .thenReturn(response);
        BDDMockito.given(productService.getSellingProducts(List.of(SELLING)))
                        .willReturn(response);

        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/products")
                                .queryParam("productSellingTypes", "SELLING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andDo(MockMvcResultHandlers.print())
        ;
    }
}
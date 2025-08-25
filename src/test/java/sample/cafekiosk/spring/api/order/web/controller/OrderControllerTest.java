package sample.cafekiosk.spring.api.order.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.api.ControllerTestSupport;
import sample.cafekiosk.spring.api.order.web.dto.request.OrderCreateRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//@WebMvcTest(OrderController.class)
class OrderControllerTest extends ControllerTestSupport {

    @DisplayName("신규 주문을 등록한다")
    @Test
    public void should_1_when_create_order() throws Exception {
        // given
        OrderCreateRequest req = new OrderCreateRequest(List.of("001"));

        // when & then
        mockMvc.perform(
                        post("/api/v1/orders")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        ;
    }
    @DisplayName("비어있는 주문번호로 주문을 생성하면 예외가 발생한다")
    @Test
    public void should_return_4xxException_when_create_order_with_empty_productNumber() throws Exception {
        // given
        OrderCreateRequest req = new OrderCreateRequest(List.of());

        // when & then
        mockMvc.perform(
                        post("/api/v1/orders")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        ;
    }
}
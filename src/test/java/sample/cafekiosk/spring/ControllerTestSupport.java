package sample.cafekiosk.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.order.application.service.OrderService;
import sample.cafekiosk.spring.order.web.controller.OrderController;
import sample.cafekiosk.spring.product.application.service.ProductService;
import sample.cafekiosk.spring.product.web.controller.ProductController;

@WebMvcTest(controllers = {
        ProductController.class,
        OrderController.class
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected ProductService productService;

    @MockitoBean
    protected OrderService orderService;
}

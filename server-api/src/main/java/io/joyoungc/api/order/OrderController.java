package io.joyoungc.api.order;

import io.joyoungc.api.order.request.CreateOrderRequest;
import io.joyoungc.api.order.request.UpdateOrderRequest;
import io.joyoungc.api.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/***
 * Created by Aiden Jeong on 2022.04.03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public Long createOrder(@Valid @RequestBody CreateOrderRequest requestCreate) {
        return orderService.createOrder(requestCreate.getMemberId(), requestCreate.getProductId());
    }

    @GetMapping("")
    public List<OrderResponse> getMemberOrders(@RequestParam long memberId) {
        return orderService.getMemberOrders(memberId);
    }

    @PatchMapping("/{orderId}")
    public void updateOrder(@PathVariable long orderId, UpdateOrderRequest requestUpdate) {
        orderService.updateOrder(orderId, requestUpdate);
    }


}

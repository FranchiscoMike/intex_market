package uz.pdp.index_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.index_market.entity.Order;
import uz.pdp.index_market.entity.Product;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.OrderDTO;
import uz.pdp.index_market.repository.OrderRepository;
import uz.pdp.index_market.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ApiResponse all() {
        List<Order> all = orderRepository.findAll();

        if (all.isEmpty()) {
            return new ApiResponse("List is empty",false);
        } else {
            return new ApiResponse("All",true,all);
        }
    }

    public ApiResponse one(Long id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isPresent()) {
            Order order = byId.get();
            return new ApiResponse("Found",true,order);
        } else {
            return new ApiResponse("Not found",false);
        }
    }

    public ApiResponse add(OrderDTO dto) {
        Order order = new Order();

        order.setAddress(dto.getAddress());
        order.setUsername(dto.getUsername());

        Optional<Product> optionalProduct = productRepository.findById(dto.getProduct_id());
        if (optionalProduct.isEmpty()) {
            return new ApiResponse("Product not found",false);
        }
        order.setProduct(optionalProduct.get());
        order.setPhone_number(dto.getPhone_number());

        Order save = orderRepository.save(order);

        return new ApiResponse("Ordered successfully!",true,save);
    }

    public ApiResponse deliver(Long id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isPresent()) {
            Order order = byId.get();
            order.setDelivered(true);
            Order save = orderRepository.save(order);
            return new ApiResponse("Delivered!",true,save);
        } else {
            return new ApiResponse("Order not found!",false);
        }
    }
}

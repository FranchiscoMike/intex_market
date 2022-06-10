package uz.pdp.index_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.OrderDTO;
import uz.pdp.index_market.service.OrderService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    // bunda hammasi qaytadi lekin aslida pagination qilinishi kerak
    @GetMapping("/list")
    public HttpEntity<?> all() {
        ApiResponse apiResponse = orderService.all();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> one(@PathVariable Long id) {
        ApiResponse apiResponse = orderService.one(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    // make a order
    @PostMapping("/add")
    public HttpEntity<?> add(@Valid @RequestBody OrderDTO dto) {
        ApiResponse apiResponse = orderService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // deliver an order
    @PatchMapping("/edit/{id}")
    public HttpEntity<?> deliver(@PathVariable Long id) {
        ApiResponse apiResponse = orderService.deliver(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // xatolik bo'lsa requiredlarni ko'rsatadi
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

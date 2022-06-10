package uz.pdp.index_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.ProductDTO;
import uz.pdp.index_market.service.ProductService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    /**
     * getting all products
     */

    @GetMapping("/list")
    public HttpEntity<?> all(){
        ApiResponse apiResponse = service.all();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * getting one using its id
     */

    @GetMapping("/{id}")
    public HttpEntity<?> one(@PathVariable UUID id){
        ApiResponse apiResponse = service.one(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * add new product ⤵️⤵️⤵️⤵️⤵️⤵️
     */
    @PostMapping("/add")
    public HttpEntity<?> add(@Valid @RequestBody ProductDTO dto){
        ApiResponse apiResponse = service.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * editing
     */
    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id,@Valid @RequestBody ProductDTO dto){
        ApiResponse apiResponse = service.edit(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * deleting product ⤵️⤵️⤵️⤵️⤵️⤵️
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        ApiResponse apiResponse = service.delete(id);
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

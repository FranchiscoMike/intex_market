package uz.pdp.index_market.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.CategoryDTO;
import uz.pdp.index_market.service.CategoryService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * return list of categories
     * @return
     */
    @GetMapping("/list")
    public HttpEntity<?> all(){
        ApiResponse apiResponse = categoryService.all();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * getting one category using its id
     * @param id
     * @return
     */

    @GetMapping("/one/{id}")
    public HttpEntity<?> getOne(@PathVariable UUID id){
        ApiResponse apiResponse = categoryService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * adding to the list of categories
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public HttpEntity<?> add(@Valid @RequestBody CategoryDTO dto){
        ApiResponse apiResponse = categoryService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        ApiResponse apiResponse = categoryService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody CategoryDTO dto,@PathVariable UUID id){
        ApiResponse apiResponse = categoryService.edit(dto,id);
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

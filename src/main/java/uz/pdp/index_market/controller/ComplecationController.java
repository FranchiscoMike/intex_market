package uz.pdp.index_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.ComplecationDTO;
import uz.pdp.index_market.service.ComplecationService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/complecation")
public class ComplecationController {
    private final ComplecationService complecationService;

    /**
     * return list of categories
     * @return
     */
    @GetMapping("/list")
    public HttpEntity<?> all(){
        ApiResponse apiResponse = complecationService.all();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * getting one complecation using its id
     * @param id
     * @return
     */

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = complecationService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * adding to the list of categories
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public HttpEntity<?> add(@Valid @RequestBody ComplecationDTO dto){
        ApiResponse apiResponse = complecationService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = complecationService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody ComplecationDTO dto,@PathVariable Integer id){
        ApiResponse apiResponse = complecationService.edit(dto,id);
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

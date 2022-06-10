package uz.pdp.index_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.ConsultationDTO;
import uz.pdp.index_market.service.ConsultationService;

import javax.annotation.security.DeclareRoles;
import javax.management.InstanceNotFoundException;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {
    private final ConsultationService service;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody ConsultationDTO dto){
        ApiResponse apiResponse = service.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PatchMapping("/check/{id}")
    public HttpEntity<?> check (@PathVariable UUID id) throws InstanceNotFoundException {
        ApiResponse apiResponse = service.check(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    // getting all consulatations :
    @GetMapping("/list")
    public HttpEntity<?> all(){
        ApiResponse apiResponse = service.all();
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

package uz.pdp.index_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.service.UserRoleService;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final UserRoleService roleService;

    /**
     * list of roles
     * @return
     */
    @GetMapping("/list")
    public HttpEntity<?> all(){
        ApiResponse apiResponse = roleService.all();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * add new role
     * @param name
     * @return
     */
    @PostMapping("/add") // kamchiliklari bor active false bo'lsa ham olaveradi
    public HttpEntity<?> add(@RequestParam String name){
        ApiResponse apiResponse = roleService.add(name);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    getting one role

    @GetMapping("/{id}")
    public HttpEntity<?> one(@PathVariable UUID id){
        ApiResponse apiResponse = roleService.one(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    // deleting from db (make active false!)

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        ApiResponse apiResponse = roleService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
     // editing one role
    @PatchMapping("/edit/{id}")
    public HttpEntity<?> edit(@RequestParam String new_name , @RequestParam UUID id){
        ApiResponse apiResponse = roleService.edit(id,new_name);
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

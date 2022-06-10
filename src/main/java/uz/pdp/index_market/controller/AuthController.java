package uz.pdp.index_market.controller;

import io.swagger.v3.core.model.ApiDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.index_market.entity.User;
import uz.pdp.index_market.entity.UserRole;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.LoginDTO;
import uz.pdp.index_market.payload.RegisterDTO;
import uz.pdp.index_market.repository.UserRepository;
import uz.pdp.index_market.repository.UserRoleRepository;
import uz.pdp.index_market.security.JwtProvider;

import javax.naming.NameNotFoundException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository roleRepository;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDTO dto) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

            User principal = (User) authentication.getPrincipal();

            String token = jwtProvider.generateToken(dto.getUsername());

            apiResponse = new ApiResponse("Your token :" + token, true, principal);


        } catch (AuthenticationException e) {

            apiResponse = new ApiResponse("something went wrong :" + e.getMessage(), false);

        }

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody RegisterDTO dto) throws NameNotFoundException {
        User user = new User();
        Optional<UserRole> optionalUserRole = roleRepository.findById(UUID.fromString("1565e035-4f3e-487f-b69f-5370bc2f0639"));

        if (optionalUserRole.isPresent()) {

            // username bilan olishdan avval unique bo'lishini aniqlab olishimiza kerak

            Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
            if(optionalUser.isPresent()){
                ApiResponse apiResponse = new ApiResponse("This email already exists, please try again",false);

                return ResponseEntity.badRequest().body(apiResponse);

            }

            user.setUserRole(optionalUserRole.get());  // default holatda bo'ladi 1 id li
            user.setEmail(dto.getEmail());
            user.setUsername(dto.getUsername());

            user.setPassword(passwordEncoder.encode(dto.getPassword())); // password doim encode holda saqlanadi
            User saved_user = userRepository.save(user);

            ApiResponse apiResponse = new ApiResponse("Registered successfully please turn it to login page", true, saved_user);


            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        } else {
            throw new NameNotFoundException("with this name role not found");
        }
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

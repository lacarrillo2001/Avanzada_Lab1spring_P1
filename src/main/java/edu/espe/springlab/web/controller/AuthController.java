package edu.espe.springlab.web.controller;

import edu.espe.springlab.dto.LoginRequest;
import edu.espe.springlab.dto.LoginResponse;
import edu.espe.springlab.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // --- Autenticación Mock (reemplazar con lógica real) ---
        if ("user".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            // Usuario y contraseña válidos, generar token
            String token = jwtService.generate(
                request.getUsername(),
                List.of("USER"), // Roles
                "students:read students:write" // Scopes/Permisos
            );
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            // Credenciales inválidas
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
    }
}



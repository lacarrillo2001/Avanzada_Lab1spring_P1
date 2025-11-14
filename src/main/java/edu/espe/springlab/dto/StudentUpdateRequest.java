package edu.espe.springlab.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class StudentUpdateRequest {

    @Size(min = 3, max = 120, message = "El nombre debe tener entre 3 y 120 caracteres")
    @Pattern(
            regexp = "^(?=.{3,120}$)(?!.*\\s{2,})[A-Za-zÁÉÍÓÚáéíóúÑñ]+(?:\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$",
            message = "El nombre solo debe contener letras y espacios simples (sin dobles)"
    )
    private String fullName; // opcional

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 120, message = "El email no debe exceder 120 caracteres")
    private String email; // opcional

    @PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
    private LocalDate birthDate; // opcional

    private Boolean active; // opcional

    // Getters / Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}

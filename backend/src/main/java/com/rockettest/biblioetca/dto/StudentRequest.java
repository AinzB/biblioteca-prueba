package com.rockettest.biblioetca.dto;

import javax.validation.constraints.*;

public class StudentRequest {

    @NotBlank(message = "El código de estudiante es obligatorio")
    @Size(max = 20, message = "El código no puede superar 20 caracteres")
    private String studentCode;

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String firstName;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    private String lastName;

    @Email(message = "El correo debe ser válido")
    private String email;

    @Pattern(regexp = "^$|\\+[0-9]{1,3}-[0-9]{7,14}$", message = "El teléfono debe tener formato internacional, e.g. +1-1234567890")
    private String phone;

    @NotNull(message = "El estado del estudiante es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estado debe ser ACTIVE o INACTIVE")
    private String status;

    // Getters y Setters
    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
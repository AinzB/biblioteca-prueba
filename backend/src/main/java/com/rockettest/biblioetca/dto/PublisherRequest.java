package com.rockettest.biblioetca.dto;

import javax.validation.constraints.*;

public class PublisherRequest {

    @NotBlank(message = "El nombre de la editorial es obligatorio")
    private String name;

    @Pattern(regexp = "^$|\\+[0-9]{1,3}-[0-9]{7,14}$", message = "El teléfono debe tener formato internacional, e.g. +1-1234567890")
    private String phone;

    @Email(message = "El correo debe ser válido")
    private String email;

    @Size(max = 200, message = "La dirección no puede superar 200 caracteres")
    private String address;

    // Getters y setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}

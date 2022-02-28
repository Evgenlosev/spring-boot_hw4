package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Детали заказа")
public class OrderDetailsDto {
    @Schema(description = "Телефон пользователя", example = "111-222")
    private String address;

    @Schema(description = "Адрес пользователя", example = "ул. Ленина, 1")
    private String phone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }
}

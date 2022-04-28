package com.geekbrains.spring.web.api.carts;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель элемента корзины")
public class CartItemDto {
    @Schema(description = "Id продукта", required = true, example = "1")
    private Long productId;

    @Schema(description = "Название продукта", required = true, maxLength = 255, minLength = 3, example = "Bread")
    private String productTitle;

    @Schema(description = "Количество единиц продукта", required = true, example = "2")
    private int quantity;

    @Schema(description = "Стоимость единицы продукта", required = true, example = "10.50")
    private BigDecimal pricePerProduct;

    @Schema(description = "Общая стоимость элемента корзины", required = true, example = "21")
    private BigDecimal price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String productTitle, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }
}
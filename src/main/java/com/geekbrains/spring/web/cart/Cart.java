package com.geekbrains.spring.web.cart;

import com.geekbrains.spring.web.dto.ProductDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
@Data
public class Cart {
    private List<ProductDto> cartList;

    @PostConstruct
    public void init() {
        cartList = new ArrayList<>();
    }

    public List<ProductDto> getCartList() {
        return cartList;
    }

    public void addProductToCart(ProductDto productDto) {
        cartList.add(productDto);
    }

    public void removeProductFromCart(ProductDto productDto) {
        if (cartList.contains(productDto)) {
            cartList.remove(productDto);
        }
    }

}

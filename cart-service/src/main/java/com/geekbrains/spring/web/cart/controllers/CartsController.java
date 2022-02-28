package com.geekbrains.spring.web.cart.controllers;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.dto.StringResponse;
import com.geekbrains.spring.web.cart.converters.CartConverter;
import com.geekbrains.spring.web.cart.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartsController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/{uuid}")
    @Operation(summary = "Запрос на получение корзины по uuid",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    )
            })
    public CartDto getCart(
            @RequestHeader(required = false) String username,
            @PathVariable @Parameter(description = "uuid корзины", required = true) String uuid
    ) {
        return cartConverter.modelToDto(cartService.getCurrentCart(getCurrentCartUuid(username, uuid)));
    }

    @GetMapping("/generate")
    @Operation(summary = "Запрос на генерацию новой корзины",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            })
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    @Operation(summary = "Запрос добавление продукта по id в корзину текущего пользователя")
    public void add(@RequestHeader(required = false) String username,
                    @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid,
                    @PathVariable @Parameter(description = "Id продукта", required = true) Long productId
    ) {
        cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    @Operation(summary = "Запрос на уменьшение количества продукта по id в корзине на единицу")
    public void decrement(@RequestHeader(required = false) String username,
                          @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid,
                          @PathVariable @Parameter(description = "Id продукта", required = true) Long productId
    ) {
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    @Operation(summary = "Запрос на удаление из корзины продукта по id")
    public void remove(@RequestHeader(required = false) String username,
                       @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid,
                       @PathVariable @Parameter(description = "Id продукта", required = true) Long productId
    ) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    @Operation(summary = "Запрос на очищение корзины по uuid")
    public void clear(@RequestHeader(required = false) String username,
                      @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid
    ) {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    @Operation(summary = "Запрос на объединение корзины пользователя с гостевой корзиной при авторизации")
    public void merge(@RequestHeader String username,
                      @PathVariable @Parameter(description = "Uuid корзины", required = true) String uuid
    ) {
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }
}

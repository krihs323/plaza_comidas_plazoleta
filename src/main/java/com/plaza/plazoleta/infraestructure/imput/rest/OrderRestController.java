package com.plaza.plazoleta.infraestructure.imput.rest;

import com.plaza.plazoleta.application.dto.OrderRequest;
import com.plaza.plazoleta.application.handler.IMenuHandler;
import com.plaza.plazoleta.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/plazoleta/order")
public class OrderRestController {

    private final IOrderHandler orderHandler;

    public OrderRestController(IOrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Order bad request", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> saveOrder(@RequestBody @Valid OrderRequest orderRequest) {
        System.out.println("orderRequest = " + orderRequest);
        orderHandler.saveOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

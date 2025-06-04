package com.plaza.plazoleta.infraestructure.imput.rest;

import com.plaza.plazoleta.application.dto.*;
import com.plaza.plazoleta.application.handler.IMenuHandler;
import com.plaza.plazoleta.application.handler.IOrderHandler;
import com.plaza.plazoleta.application.handler.IRestaurantHandler;
import com.plaza.plazoleta.domain.model.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/plazoleta")
public class SquareMallRestController {

    private final IRestaurantHandler restaurantHandler;
    private final IOrderHandler orderHandler;
    private final IMenuHandler menuHandler;

    public SquareMallRestController(IRestaurantHandler restaurantHandler, IOrderHandler orderHandler, IMenuHandler menuHandler) {
        this.restaurantHandler = restaurantHandler;
        this.orderHandler = orderHandler;
        this.menuHandler = menuHandler;
    }

    @Operation(summary = "Add a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Restaurant already exists", content = @Content)
    })
    @PostMapping("/restaurant/create/")
    public ResponseEntity<?> saveRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        restaurantHandler.saveRestaurant(restaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant All", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping("/restaurant/all")
    public ResponseEntity<PageResult<RestaurantListResponse>> getAllRestaurants(@Parameter(description = "Number of items")
                                                                          @RequestParam(required = false, defaultValue = "0") Integer pages) {
        return ResponseEntity.ok(restaurantHandler.getAllRestaurants(pages));
    }

    @Operation(summary = "Add a new menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Menu created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Menu already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Menu bad request", content = @Content)
    })
    @PostMapping("/menu/")
    public ResponseEntity<?> saveMenu(@RequestBody @Valid MenuRequest menuRequest) {
        menuHandler.saveMenu(menuRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu updated", content = @Content),
            @ApiResponse(responseCode = "409", description = "Menu already exists", content = @Content)
    })
    @PutMapping("/menu/{id}")
    public ResponseEntity<?> updateMenu(@Parameter(description = "Id of the menu to be updated") @PathVariable Long id, @RequestBody @Valid MenuUpdateRequest menuUpdateRequest) {
        menuHandler.updateMenu(id, menuUpdateRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Enable anm Disable menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu updated", content = @Content),
            @ApiResponse(responseCode = "409", description = "Menu already exists", content = @Content)
    })
    @PutMapping("/menu/disable/{id}")
    public ResponseEntity<?> disableMenu(@Parameter(description = "Id of the menu to be updated") @PathVariable Long id, @RequestBody @Valid MenuDisableRequest menuRequest) {
        menuHandler.disableMenu(id, menuRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Hu10
    @Operation(summary = "Get menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu by restaurant", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping("/menu/restaurant/{idRestaurant}")
    public ResponseEntity<PageResult<MenuResponse>> getmenuByRestaurant(@Parameter(description = "Id of the menu's restaurant to consult") @PathVariable Long idRestaurant,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                  @RequestParam(defaultValue = "asc") String sortDir,
                                                                  @RequestParam(defaultValue = "") String category){
        return ResponseEntity.ok(menuHandler.getMenuByRestaurant(idRestaurant, category, page, size, sortBy, sortDir));
    }

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Order bad request", content = @Content)
    })
    @PostMapping("/order/create/")
    public ResponseEntity<?> saveOrder(@RequestBody @Valid OrderRequest orderRequest) {
        orderHandler.saveOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all order by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order by status", content = @Content),
            @ApiResponse(responseCode = "400", description = "Order bad request", content = @Content)
    })
    @GetMapping("/employee/order/filter/")
    public ResponseEntity<PageResult<OrderResponse>> getOrdersByStatus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @Parameter(description = "Status of the order to find") @RequestParam(defaultValue = "") String status) {
        return ResponseEntity.ok(orderHandler.getOrderByStatus(status, page, size, sortBy, sortDir));
    }


    @Operation(summary = "Asigned order to Preparate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order asigned", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order error validation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Order bad request", content = @Content)
    })
    @PutMapping("/order/toprepare/{orderId}")
    public ResponseEntity<?> updateOrderToPreparation(@Parameter(description = "Id of the order to be asigned") @PathVariable Long orderId
                                                    ) {
        orderHandler.updateOrderToPreparation(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Change order to status ready")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order changed to ready", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order error validation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Order bad request", content = @Content)
    })
    @PutMapping("/order/toready/{orderId}")
    public ResponseEntity<?> updateOrderToReady(@Parameter(description = "Id of the order to be changed to status Ready") @PathVariable Long orderId
    ) {
        orderHandler.updateOrderToReady(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Change order to status delivered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order changed to delivered", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order error validation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Order bad request", content = @Content)
    })
    @PutMapping("/order/todeliver/")
    public ResponseEntity<?> updateOrderToDeliver(@RequestBody OrderDeliverRequest orderDeliverRequest
    ) {
        orderHandler.updateOrderToDelivered(orderDeliverRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Change order to status Canceled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order changed to delivered", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order error validation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Order bad request", content = @Content)
    })
    @PutMapping("/order/tocancel/{orderId}")
    public ResponseEntity<?> updateOrderToCancel(@Parameter(description = "Id of the order to be changed to status Canceled") @PathVariable Long orderId
    ) {
        orderHandler.updateOrderToCanceled(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
package com.plaza.plazoleta.infraestructure.imput.rest;

import com.plaza.plazoleta.application.dto.RestaurantListResponse;
import com.plaza.plazoleta.application.dto.RestaurantRequest;
import com.plaza.plazoleta.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/plazoleta/restaurant")
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    public RestaurantRestController(IRestaurantHandler restaurantHandler) {
        this.restaurantHandler = restaurantHandler;
    }

    @Operation(summary = "Add a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Restaurant already exists", content = @Content)
    })
    @PostMapping("/create/")
    public ResponseEntity<?> saveRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        restaurantHandler.saveRestaurant(restaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant All", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping("/all")
        public ResponseEntity<Page<RestaurantListResponse>> getAllRestaurants(@Parameter(description = "Number of items")
                                                       @RequestParam(required = false, defaultValue = "0") Integer pages) {
        return ResponseEntity.ok(restaurantHandler.getAllRestaurants(pages));
    }

}

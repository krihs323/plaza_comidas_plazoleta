package com.plaza.plazoleta.infraestructure.imput.rest;

import com.plaza.plazoleta.application.dto.MenuDisableRequest;
import com.plaza.plazoleta.application.dto.MenuRequest;
import com.plaza.plazoleta.application.dto.MenuResponse;
import com.plaza.plazoleta.application.dto.RestaurantListResponse;
import com.plaza.plazoleta.application.handler.IMenuHandler;
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
@RequestMapping(path = "/api/plazoleta/menu")
public class MenuRestController {

    private final IMenuHandler menuHandler;

    public MenuRestController(IMenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Operation(summary = "Add a new menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Menu created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Menu already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Menu bad request", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> saveMenu(@RequestBody @Valid MenuRequest menuRequest) {
        menuHandler.saveMenu(menuRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu updated", content = @Content),
            @ApiResponse(responseCode = "409", description = "Menu already exists", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenu(@Parameter(description = "Id of the menu to be updated") @PathVariable Long id, @RequestBody @Valid MenuRequest menuRequest) {
        menuHandler.updateMenu(id, menuRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Enable anm Disable menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu updated", content = @Content),
            @ApiResponse(responseCode = "409", description = "Menu already exists", content = @Content)
    })
    @PutMapping("/disable/{id}")
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
    @GetMapping("/restaurant/{idRestaurant}")
    public ResponseEntity<Page<MenuResponse>> getmenuByRestaurant(@Parameter(description = "Id of the menu's restaurant to consult") @PathVariable Long idRestaurant,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                  @RequestParam(defaultValue = "asc") String sortDir,
                                                                  @RequestParam(defaultValue = "") String category){
        return ResponseEntity.ok(menuHandler.getMenuByRestaurant(idRestaurant, category, page, size, sortBy, sortDir));
    }

}

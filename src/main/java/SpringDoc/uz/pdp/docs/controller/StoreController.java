package SpringDoc.uz.pdp.docs.controller;

import SpringDoc.uz.pdp.docs.dto.StoreDTO;
import SpringDoc.uz.pdp.docs.entities.Store;
import SpringDoc.uz.pdp.docs.services.StoreServiceImp;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@OpenAPIDefinition(
        info = @Info(
                title = "INHA university In Tashkent (SpringDoc)",
                version = "10",
                contact = @Contact(
                        name = "Safixon Abdusattorov",
                        email = "safixongg@gmail.com",
                        url = "https://github.com/safixans"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://springdoc.org"
                ),
                termsOfService = "http://swagger.io/terms/",
                description = "This Document Designed For Teaching SpringDOC Project"
        ),
        externalDocs = @ExternalDocumentation(
                description = "SPRING DOC version 2",
                url = "https://springdoc.org/v2"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Production-Server"
                ),
                @Server(
                        url = "http://localhost:9090",
                        description = "Test-Server"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)

@RequestMapping("/api/stores")
public class StoreController {
    private final Logger logger;
    private final StoreServiceImp service;

    public StoreController(StoreServiceImp service) {
        this.service = service;
        this.logger=Logger.getLogger(StoreController.class.getName());
        logger.setLevel(Level.INFO);
    }

    @PostMapping
    @Operation(summary = "Creates New Store", description = "creates  a new store includes all the field but id is exceptional")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "201", description = "Successfully Created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Store.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RuntimeException.class))}),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RuntimeException.class))})})

    public ResponseEntity<Void> create(@Valid @RequestBody Store entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entity)).getBody();
    }

    @Operation(summary = "gets store by its id")
    @GetMapping("/{id}")
    public ResponseEntity<Store> get(@PathVariable Long id) {
        logger.log(Level.INFO,"requested the store ");
        return ResponseEntity.ok(service.getById(id).getBody());
    }

    @GetMapping("/")
    @Operation(description = "gets ALL Stores ")
    public ResponseEntity<List<Store>> getAll(){
        return ResponseEntity.ok(service.getAll()).getBody();
    }

    @PostMapping("/{id}")
    @Operation(description = "updates existed Store then nothing returns ")
    public ResponseEntity<Void> update(@RequestBody StoreDTO dto, @PathVariable Long id){
        service.update(dto,id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(description = "it deletes store by its id")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }



}

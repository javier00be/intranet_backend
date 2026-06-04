package com.example.intranet_school.infrastructure.adapter.web.controller;

import com.example.intranet_school.application.dto.DniResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/reniec")
public class ReniecController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${reniec.api.url}")
    private String apiUrl;

    @Value("${reniec.api.token}")
    private String apiToken;

    @GetMapping("/dni/{numero}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> consultarDni(@PathVariable String numero) {
        if (numero == null || !numero.matches("\\d{8}")) {
            return ResponseEntity.badRequest().body("El DNI debe tener 8 dígitos");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<DniResponseDTO> response = restTemplate.exchange(
                    apiUrl + "?numero=" + numero,
                    HttpMethod.GET,
                    entity,
                    DniResponseDTO.class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DNI no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Error al consultar el servicio RENIEC");
        }
    }
}

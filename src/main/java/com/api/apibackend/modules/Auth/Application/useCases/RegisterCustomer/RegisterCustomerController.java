/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.api.apibackend.modules.Auth.Application.useCases.RegisterCustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.apibackend.modules.Auth.Application.DTOs.response.ResponseMessageDTO;
import com.api.apibackend.modules.Auth.Domain.exception.RegistrationFailedException;
import com.api.apibackend.modules.Customer.Application.DTOs.registration.RegistrationRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
public class RegisterCustomerController {
    private static final String REGISTRATION_PATH = "/registrar";

    private final RegisterCustomerUseCase registerCustomerUseCase;

    @Autowired
    public RegisterCustomerController(RegisterCustomerUseCase registerCustomerUseCase) {
        this.registerCustomerUseCase = registerCustomerUseCase;
    }

    @PostMapping(REGISTRATION_PATH)
    @Tag(name = "Register", description = "Register a new user and generate an access token.")
    @Operation(summary = "Register a new user and generate an authentication token.")
    public ResponseEntity<ResponseMessageDTO> handle(@RequestBody @Validated RegistrationRequest registrationRequest) {
        try {
            if (registrationRequest == null || registrationRequest.getCustomerDTO() == null) {
                log.warn("Requisição de registro inválida: {}", registrationRequest);
                return ResponseEntity.badRequest()
                        .body(new ResponseMessageDTO(null, getClass().getSimpleName(),
                                "Dados de registro são obrigatórios.", null));
            }

            return registerCustomerUseCase.execute(registrationRequest.getCustomerDTO());
        } catch (IllegalArgumentException ex) {
            log.warn("Erro de validação: {}", ex.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ResponseMessageDTO(null, getClass().getSimpleName(),
                            "Erro de validação: " + ex.getMessage(), null));
        } catch (RegistrationFailedException ex) {
            log.error("Falha no registro: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO(null, getClass().getSimpleName(),
                            "Falha ao processar a solicitação de registro: " + ex.getMessage(), null));
        } catch (Exception ex) {
            log.error("Erro inesperado ao processar a solicitação de registro: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO(null, getClass().getSimpleName(),
                            "Erro inesperado: " + ex.getMessage(), null));
        }
    }
}

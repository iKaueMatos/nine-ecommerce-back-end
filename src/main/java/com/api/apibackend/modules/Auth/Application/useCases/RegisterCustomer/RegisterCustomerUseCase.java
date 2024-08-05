/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.api.apibackend.modules.Auth.Application.useCases.RegisterCustomer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.apibackend.modules.Auth.Application.DTOs.response.ResponseMessageDTO;
import com.api.apibackend.modules.Auth.Domain.authentication.AuthenticationRegisterService;
import com.api.apibackend.modules.Customer.Application.DTOs.registration.CustomerDTO;

@Service
public class RegisterCustomerUseCase {
    private AuthenticationRegisterService autheticationRegister;

    @Autowired
    public RegisterCustomerUseCase(AuthenticationRegisterService autheticationRegister) {
        this.autheticationRegister = autheticationRegister;
    }

    public ResponseEntity<ResponseMessageDTO> execute(CustomerDTO customerDTO) {
        try {
            return Optional.ofNullable(customerDTO)
                    .map(dto -> {
                        var response = autheticationRegister.register(dto);
                        return response.getStatusCode() == HttpStatus.CREATED
                                ? ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessageDTO(
                                        "Cliente registrado com sucesso",
                                        this.getClass().getSimpleName(), null, null))
                                : ResponseEntity.badRequest().body(new ResponseMessageDTO(
                                        "Erro: dados de cliente não fornecidos",
                                        this.getClass().getSimpleName(), null, null));
                    })
                    .orElseThrow(() -> new IllegalArgumentException("Erro: dados de cliente não fornecidos"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO(null, this.getClass().getSimpleName(),
                            "Erro ao processar a solicitação de registro: " + e.getMessage(), null));
        }
    }
}

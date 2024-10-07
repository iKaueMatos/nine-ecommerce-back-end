/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.api.apibackend.modules.Auth.Domain.authentication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.apibackend.modules.Auth.Application.DTOs.response.ResponseMessageDTO;
import com.api.apibackend.modules.Auth.Domain.Enum.CustomGrantedAuthority;
import com.api.apibackend.modules.Auth.Domain.repository.IAutheticationRegister;
import com.api.apibackend.modules.Auth.Domain.service.cryptography.AnonymizationService;
import com.api.apibackend.modules.Auth.Infra.persistence.entity.UserEntity;
import com.api.apibackend.modules.Auth.Infra.persistence.repository.UserRepository;
import com.api.apibackend.modules.Auth.Infra.validation.AuthenticationValidationServiceHandler;
import com.api.apibackend.modules.Customer.Application.DTOs.registration.CustomerDTO;
import com.api.apibackend.modules.Customer.Domain.event.CustomerCreated;
import com.api.apibackend.modules.Customer.Domain.helpers.CustomerModelMapper;
import com.api.apibackend.modules.Customer.Domain.service.CustomerService;
import com.api.apibackend.modules.Customer.Infra.persistence.entity.CustomerEntity;
import com.api.apibackend.modules.Customer.Infra.persistence.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationRegisterService implements IAutheticationRegister {
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final CustomerModelMapper customerModelMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationValidationServiceHandler validationServiceHandler;
    private final AnonymizationService anonymizationService;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationRegisterService(
            AuthenticationValidationServiceHandler validationServiceHandler,
            CustomerService customerService,
            CustomerModelMapper customerModelMapper,
            ApplicationEventPublisher eventPublisher,
            PasswordEncoder passwordEncoder,
            AnonymizationService anonymizationService,
            CustomerRepository customerRepository,
            UserRepository userRepository) {
        this.validationServiceHandler = validationServiceHandler;
        this.customerService = customerService;
        this.customerModelMapper = customerModelMapper;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = passwordEncoder;
        this.anonymizationService = anonymizationService;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<ResponseMessageDTO> register(CustomerDTO customerDTO) {
        try {
            List<String> emailValidation = validationServiceHandler.isValidEmail(customerDTO.getEmail());
            String passwordValidation = validationServiceHandler.isValidPassword(customerDTO.getPassword());

            if (emailValidation == null) {
                emailValidation = new ArrayList<>();
            }

            if (!emailValidation.isEmpty() || passwordValidation != null) {
                String errorMessage = "Erro de validação: ";
                if (!emailValidation.isEmpty()) {
                    errorMessage += String.join(", ", emailValidation);
                }

                if (passwordValidation != null) {
                    if (!emailValidation.isEmpty()) {
                        errorMessage += ", ";
                    }
                    errorMessage += passwordValidation;
            }

            return ResponseEntity.badRequest().body(new ResponseMessageDTO(
                    "Erro de validação", this.getClass().getSimpleName(), errorMessage, null));
        }
            if (userRepository.findByEmail(customerDTO.getEmail()) != null) {
                log.warn("Cliente já existe {}", customerDTO.getEmail());
                return ResponseEntity.badRequest()
                        .body(new ResponseMessageDTO("E-mail já está em uso.", this.getClass().getSimpleName(), null, null));
            }

            if (!validationServiceHandler.isValidCPF(customerDTO.getCpf())) {
                log.warn("Erro de validação no CPF do cliente {}", customerDTO.getCpf());
                return ResponseEntity.badRequest()
                        .body(new ResponseMessageDTO("CPF inválido.", this.getClass().getSimpleName(), null, null));
            }

            String emailAnonymization = anonymizationService.encrypt(customerDTO.getEmail());
            String cpfAnonymization = anonymizationService.encrypt(customerDTO.getCpf());
            String hashedPassword = passwordEncoder.encode(customerDTO.getPassword());

            CustomerEntity newCustomerEntity = customerModelMapper.toCustomerDTOFromCustomerEntity(customerDTO);
            newCustomerEntity.setDateCreate(new Date());
            newCustomerEntity.setCpf(cpfAnonymization);
            CustomerEntity savedCustomer = customerRepository.save(newCustomerEntity);

            UserEntity newUserEntity = new UserEntity();
            newUserEntity.setUsername(customerDTO.getName());
            newUserEntity.setEmail(emailAnonymization);
            newUserEntity.setPassword(hashedPassword);
            newUserEntity.setCustomer(savedCustomer);
            userRepository.save(newUserEntity);

            // Publicar evento de cliente criado
            publishCustomerCreatedEvent(savedCustomer.getId());

            log.info("Cliente registrado com sucesso {}");
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseMessageDTO("Cliente registrado com sucesso!",
                            this.getClass().getSimpleName(), null, null));
        } catch (Exception e) {
            log.error("Ocorreu um erro ao processar a requisição!", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO(null, this.getClass().getSimpleName(),
                            "Erro ao registrar o cliente: " + e.getMessage(), null));
        }
    }

    private void publishCustomerCreatedEvent(Long customerId) {
        CustomerCreated newCustomerCreated = new CustomerCreated(this, customerId);
        eventPublisher.publishEvent(newCustomerCreated);
    }

    public Set<CustomGrantedAuthority> convertRolesToCustomAuthorities(Set<CustomGrantedAuthority> roles) {
        Set<CustomGrantedAuthority> authorities = new HashSet<>();
        if (roles != null) {
            authorities.addAll(roles);
        }
        return authorities;
    }
}

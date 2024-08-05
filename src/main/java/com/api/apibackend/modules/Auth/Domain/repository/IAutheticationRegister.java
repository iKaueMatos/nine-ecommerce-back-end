/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

package com.api.apibackend.modules.Auth.Domain.repository;

import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.api.apibackend.modules.Auth.Application.DTOs.response.ResponseMessageDTO;
import com.api.apibackend.modules.Auth.Domain.Enum.CustomGrantedAuthority;
import com.api.apibackend.modules.Customer.Application.DTOs.registration.CustomerDTO;

public interface IAutheticationRegister {
    ResponseEntity<ResponseMessageDTO> register(CustomerDTO customerDTO);

    Set<CustomGrantedAuthority> convertRolesToCustomAuthorities(Set<CustomGrantedAuthority> roles);
}

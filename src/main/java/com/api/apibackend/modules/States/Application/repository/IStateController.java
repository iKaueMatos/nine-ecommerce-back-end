package com.api.apibackend.modules.States.Application.repository;

/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

import org.springframework.http.ResponseEntity;

public interface IStateController {

	ResponseEntity<?> getStates();
}

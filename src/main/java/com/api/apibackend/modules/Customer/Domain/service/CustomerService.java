/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */

package com.api.apibackend.modules.Customer.Domain.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.apibackend.modules.Auth.Infra.persistence.entity.UserEntity;
import com.api.apibackend.modules.Auth.Infra.persistence.repository.UserRepository;
import com.api.apibackend.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.api.apibackend.modules.Customer.Application.DTOs.registration.CustomerDTO;
import com.api.apibackend.modules.Customer.Application.DTOs.response.ResponseMessageDTO;
import com.api.apibackend.modules.Customer.Domain.repository.IClientService;
import com.api.apibackend.modules.Customer.Infra.persistence.entity.CustomerEntity;
import com.api.apibackend.modules.Customer.Infra.persistence.repository.CustomerRepository;
import com.api.apibackend.modules.CustomerAddress.Infra.persistence.entity.CustomerAddressEntity;
import com.api.apibackend.modules.CustomerAddress.Infra.persistence.repository.CustomerAddressRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService implements IClientService {
	private CustomerRepository customerRepository;
	private UserRepository userRepository;
	private CustomerAddressRepository addressRepository;

	@Autowired
	public CustomerService(CustomerRepository customerRepository, CustomerAddressRepository addressRepository) {
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
	}

	public CustomerEntity createClient(CustomerEntity customerEntity, CustomerAddressEntity addressEntity) {
		customerEntity.setAddress(addressEntity);
		CustomerEntity savedClient = customerRepository.save(customerEntity);
		return savedClient;
	}

	@Transactional
	public ResponseEntity<ResponseMessageDTO> update(Long clientId, CustomerDTO updatedClient,
			CustomerAddressDTO updatedAddress) {
		Optional<CustomerEntity> existingClient = customerRepository.findById(clientId);
		Optional<CustomerAddressEntity> existingAddress = addressRepository.findById(clientId);

		if (existingClient.isPresent()) {
			CustomerEntity clientToUpdate = existingClient.get();
			clientToUpdate.setPhone(updatedClient.getPhone());
		}

		if (existingAddress.isPresent()) {
			CustomerAddressEntity addressToUpdate = existingAddress.get();
			addressToUpdate.setCep(updatedAddress.getCep() != addressToUpdate.getCep() ? updatedAddress.getCep()
					: addressToUpdate.getCep());
			addressToUpdate.setRoad(updatedAddress.getRoad() != addressToUpdate.getRoad() ? updatedAddress.getRoad()
					: addressToUpdate.getRoad());
			addressToUpdate.setNeighborhood(updatedAddress.getNeighborhood() != addressToUpdate.getNeighborhood()
					? updatedAddress.getNeighborhood()
					: addressToUpdate.getNeighborhood());
			addressToUpdate.setHousenumber(updatedAddress.getHousenumber() != addressToUpdate.getHousenumber()
					? updatedAddress.getHousenumber()
					: addressToUpdate.getHousenumber());
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseMessageDTO("Dados atualizados com sucesso", this.getClass().getName(), null));
	}

	@Transactional
	public ResponseEntity<ResponseMessageDTO> delete(Long clientId) {
		Optional<CustomerEntity> existingClient = customerRepository.findById(clientId);

		if (existingClient.isPresent()) {
			customerRepository.delete(existingClient.get());
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseMessageDTO("Deletado com sucesso", this.getClass().getName(), null));
	}

	@Override
	public UserEntity getUser() {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			if (Objects.isNull(userName)) {
					return null;
			}

			Optional<UserEntity> user = Optional
			.ofNullable(Optional
			.ofNullable(userRepository.findByEmail(userName)).get());

			return user.get();
	}

	@Override
	public UserEntity getCustomer() {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			if (Objects.isNull(userName)) {
					return null;
			}

			Optional<UserEntity> user = Optional
			.ofNullable(Optional
			.ofNullable(userRepository.findByEmail(userName)).get());

			return user.get();
	}

	public CustomerEntity saveCustomer(CustomerEntity customer) {
		return customerRepository.save(customer);
	}
}

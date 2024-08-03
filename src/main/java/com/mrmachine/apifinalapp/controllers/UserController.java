package com.mrmachine.apifinalapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrmachine.apifinalapp.dto.UserDTO;
import com.mrmachine.apifinalapp.dto.UserRequest;
import com.mrmachine.apifinalapp.models.auth.User;
import com.mrmachine.apifinalapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/final-app/users")
public class UserController {
	
	private UserService service;

	public UserController(UserService service) {
		this.service = service;
	}
	
	@GetMapping("/all")
	public List<?> findAll() {
		return this.service.findAll();
	}
	
	@GetMapping("/page/{page}")
	public Page<UserDTO> findPage(@PathVariable(name = "page") Integer page) {
		Pageable pageable = PageRequest.of(page, 5);
		return this.service.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {
		Optional<?> u = this.service.findById(id);
		if (u.isPresent()) {
			return ResponseEntity.ok(u.orElseThrow());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping // validar con programacion orientado a aspectos el objeto user, agregamos @Valid y con BindingResult verificamos si se cumple la validacion, esta variable debe ir al lado del elemento a validar.
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
		
		if (result.hasErrors()) {
			return validation(result);
		} 
	
		try {
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(this.service.save(user));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable(name = "id") Long id) {
		
		if (result.hasErrors()) {
			return validation(result);
		}
		
		try {
			Optional<UserDTO> optUser = this.service.update(user, id);
			
			if (optUser.isPresent()) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(optUser.orElseThrow());
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
		
		Optional<UserDTO> optUser = this.service.findById(id);
		
		if (optUser.isPresent()) {
			this.service.remove(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	private ResponseEntity<?> validation(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		result.getFieldErrors().forEach( e -> 
			errors.put(e.getField(), 
			String.format("el campo %s %s", e.getField(), e.getDefaultMessage())
		));
		return ResponseEntity.badRequest().body(errors);
	}
}

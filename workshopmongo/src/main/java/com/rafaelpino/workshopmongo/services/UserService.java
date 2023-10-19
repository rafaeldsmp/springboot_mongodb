package com.rafaelpino.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelpino.workshopmongo.domain.User;
import com.rafaelpino.workshopmongo.dtos.UserDTO;
import com.rafaelpino.workshopmongo.repositories.UserRepository;
import com.rafaelpino.workshopmongo.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public User insert(User obj) {
		return repository.insert(obj);
	}

	public void delete(String id) {
		findById(id); 
		repository.deleteById(id);
	}

	public User update(User obj) {
		Optional<User> objData = repository.findById(obj.getId());
		User newObj = objData.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	public User fromDTO(UserDTO objDto) { 
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
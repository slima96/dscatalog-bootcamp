package com.example.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.CategoryDTO;
import com.example.entities.Category;
import com.example.repositories.CategoryRespository;
import com.example.services.exceptions.DatabaseException;
import com.example.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRespository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);

		return list.map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		try {
			Optional<Category> obj = repository.findById(id);
			Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
			return new CategoryDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
		

	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);

		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);

			return new CategoryDTO(entity);
		} 
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

}

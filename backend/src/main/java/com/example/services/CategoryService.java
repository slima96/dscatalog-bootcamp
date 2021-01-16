package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Category;
import com.example.repositories.CategoryRespository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRespository repository;
	
	public List<Category> findAll() {
		return repository.findAll();
	}
	
}

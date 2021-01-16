package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Category;

@Repository
public interface CategoryRespository extends JpaRepository<Category, Long> {

}

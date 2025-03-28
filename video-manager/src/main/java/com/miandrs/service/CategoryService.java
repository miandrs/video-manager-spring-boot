package com.miandrs.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miandrs.models.entity.Category;
import com.miandrs.repository.CategoryRepositoryInterface;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepositoryInterface categoryRepository;
	
	public Category createCategory(Category category) {
		return this.categoryRepository.save(category);
	}
	
	public Iterable<Category> getAllCategory() {
		return this.categoryRepository.findAll();
	}
	
	public Category getCategoryById(UUID id) {
		return this.categoryRepository.findById(id).orElseThrow();
	}

	public void delete(UUID id) {
		categoryRepository.deleteById(id);
	}
}

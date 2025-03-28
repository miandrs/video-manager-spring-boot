package com.miandrs.api;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miandrs.models.entity.Category;
import com.miandrs.service.CategoryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/category")
public class CategoryResource {
	@Autowired
	private CategoryService categoryService;
	
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	public Category createCategory(@RequestBody Category category) {
		return categoryService.createCategory(category);
	}
	
	@GetMapping
	public Iterable<Category> categoryList() {
		return categoryService.getAllCategory();
	}
	
	@GetMapping("/{id}")
	public Category getCategory(@PathVariable("id") UUID id) {
		return categoryService.getCategoryById(id);
	}
	
	@PostMapping("/delete/{id}")
	public void deleteCategory(@PathVariable("id") UUID id) {
		categoryService.delete(id);
	}
}

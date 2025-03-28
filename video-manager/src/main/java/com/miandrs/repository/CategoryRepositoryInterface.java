package com.miandrs.repository;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

import com.miandrs.models.entity.Category;

public interface CategoryRepositoryInterface extends CrudRepository<Category, UUID> {
}

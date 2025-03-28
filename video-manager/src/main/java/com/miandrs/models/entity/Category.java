package com.miandrs.models.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * 
 * @author Miandrs
 * Category
 * example: Electronics, Clothing, Furniture
 *
 */
@Entity(name = "CATEGORY")
public class Category {
	@Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID _id;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(nullable = true,length = 50)
    private String description;
	
	public UUID get_id() {
		return _id;
	}
	public void set_id(UUID _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

package com.miandrs.models.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;

@Entity(name = "PRODUCT")
@NamedEntityGraph(name = "product.category", attributeNodes = {
		@NamedAttributeNode(value = "category")})	
public class Product {
	@Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID _id;
    @Column(nullable = false,length = 50)
    private String title;
    @Column(nullable = false,length = 254)
    private String description;
	@Column(nullable = true,length = 254)
	private String imageUrl;
	@Column(nullable = true,length = 254)
	private String videoUrl;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_CATEGORY", nullable=false)
	private Category category;
	private UUID userId;
	public UUID get_id() {
		return _id;
	}
	public void set_id(UUID _id) {
		this._id = _id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}

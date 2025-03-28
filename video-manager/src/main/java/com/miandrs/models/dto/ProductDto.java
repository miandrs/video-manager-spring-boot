package com.miandrs.models.dto;

import java.util.UUID;

import com.miandrs.models.entity.Category;

public class ProductDto {
    private String title;
    private String description;
	private String imageUrl;
	private String videoUrl;
	private UUID userId;
	private UUID category;
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
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public UUID getCategory() {
		return category;
	}
	public void setCategory(UUID category) {
		this.category = category;
	}
}

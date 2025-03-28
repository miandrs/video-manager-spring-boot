package com.miandrs.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.miandrs.models.dto.ProductDto;
import com.miandrs.models.dto.ProductFetchDto;
import com.miandrs.models.entity.Category;
import com.miandrs.models.entity.Product;
import com.miandrs.models.entity.User;
import com.miandrs.repository.CategoryRepositoryInterface;
import com.miandrs.repository.ProductRepositoryInterface;
import com.miandrs.repository.UserRepositoryInterface;

@Service
public class ProductService {
	@Autowired
	private ProductRepositoryInterface productRepository;
	@Autowired
	private CategoryRepositoryInterface categoryRepository;
	@Autowired
	private UserRepositoryInterface userRepository;

	public ProductFetchDto createProduct(String domain, ProductDto productDto, MultipartFile image, MultipartFile video) {
		Product product = new Product();
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		Category category = categoryRepository.findById(productDto.getCategory()).orElseThrow();
		User user = userRepository.findById(productDto.getUserId()).orElseThrow();
		product.setCategory(category);
		product.setUserId(user.getId());
		
		String imageUrl = uploadFile(image);
		String videoUrl = uploadFile(video);
		
		product.setImageUrl(domain + "/poster/" + imageUrl);
		product.setVideoUrl(domain + "/video/" + videoUrl);
		ProductFetchDto productDtoCreated = new ProductFetchDto();
		Product productCreated = productRepository.save(product);
		productDtoCreated.set_id(productCreated.get_id());
		productDtoCreated.setTitle(productCreated.getTitle());
		productDtoCreated.setDescription(productCreated.getDescription());
		productDtoCreated.setCategory(productCreated.getCategory());
		productDtoCreated.setUserId(productCreated.getUserId());
		productDtoCreated.setImageUrl(productCreated.getImageUrl());
		productDtoCreated.setVideoUrl(productCreated.getVideoUrl());
		return productDtoCreated;
		
	}
	
	public ResponseEntity<Object> updateProduct(String domain, UUID id, ProductDto productDto, MultipartFile image, MultipartFile video) {
		ProductFetchDto productDtoCreated = null;
		Optional<Product> oldProduct = productRepository.findById(id);
		if(oldProduct.isPresent()) {
			Product product = oldProduct.orElseThrow();
			if(!product.getUserId().equals(productDto.getUserId())) {
				return new ResponseEntity<Object>("{ \"message\": \"Not Authorized\" }", HttpStatus.FORBIDDEN);
			}
			product.setTitle(productDto.getTitle());
			product.setDescription(productDto.getDescription());
			Category category = categoryRepository.findById(productDto.getCategory()).orElseThrow();
			User user = userRepository.findById(productDto.getUserId()).orElseThrow();
			product.setCategory(category);
			product.setUserId(user.getId());
			
			if(image != null && video != null) {
				String imageUrl = uploadFile(image);
				String videoUrl = uploadFile(video);
				
				product.setImageUrl(domain + "/poster/" + imageUrl);
				product.setVideoUrl(domain + "/video/" + videoUrl);
			}
			productDtoCreated = new ProductFetchDto();
			
			Product productCreated = productRepository.save(product);
			productDtoCreated.set_id(productCreated.get_id());
			productDtoCreated.setTitle(productCreated.getTitle());
			productDtoCreated.setDescription(productCreated.getDescription());
			productDtoCreated.setCategory(productCreated.getCategory());
			productDtoCreated.setUserId(productCreated.getUserId());
			productDtoCreated.setImageUrl(productCreated.getImageUrl());
			productDtoCreated.setVideoUrl(productCreated.getVideoUrl());
		}
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productDtoCreated);	
	}
	
	public Iterable<Product> getAllProduct() {
		return productRepository.findAll();
	}
	
	public Product getProductById(UUID id) {
		return productRepository.findById(id).orElseThrow();
	}
	
	public void deleteProduct(UUID id) {
		productRepository.deleteById(id);
	}
	
	private String uploadFile(MultipartFile file) {
		String filePath = System.getProperty("user.dir") + "\\medias\\" + file.getName() + File.separator;
		String filename = file.getOriginalFilename().toLowerCase().replaceAll(" ", "_") + Instant.now().getEpochSecond();
		String extension = file.getContentType().replaceFirst(file.getName()+'/', ".");
		String foutName = filename.replaceFirst('.'+extension, "_") + (extension.equals("jpeg") ? "jpg" : extension);
		try {
			FileOutputStream fout = new FileOutputStream(filePath + foutName);
			fout.write(file.getBytes());
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return foutName;
	}
	
	public ResponseEntity<Object> downloadFile(String fileType, String filename) throws IOException {
		String folderPath = System.getProperty("user.dir")+"/medias/"+fileType;
		String filePath = folderPath + File.separator + filename;
		File file = new File(filePath);
		try {
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		
		String contentType = Files.probeContentType(file.toPath());
		String headerValue = "attachement: filename=\"" + resource.getFilename() + "\"";
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
				.body(resource);
		} catch(FileNotFoundException e) {
			return new ResponseEntity<Object>("{ \"message\" : \"File not found\" }", HttpStatus.NOT_FOUND);
		}
	}
}
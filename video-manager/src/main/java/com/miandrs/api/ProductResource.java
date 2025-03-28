package com.miandrs.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miandrs.models.dto.ProductDto;
import com.miandrs.models.dto.ProductFetchDto;
import com.miandrs.models.entity.Product;
import com.miandrs.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/media")
public class ProductResource {
	@Autowired
	private ProductService productService;
	
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductFetchDto createProduct(@RequestParam("image") MultipartFile image, @RequestParam("video") MultipartFile video, @RequestParam("media") String media) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ProductDto product = mapper.readValue(media, ProductDto.class);
		String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.build()
				.toUriString();
		return productService.createProduct(baseUrl, product, image, video);
	}
	
	@PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updateProduct(@RequestParam("id") UUID id,@RequestParam("image") MultipartFile image, @RequestParam("video") MultipartFile video, @RequestParam("media") String media) throws IOException {
		System.out.println("eto");
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(media);
		ProductDto product = mapper.readValue(media, ProductDto.class);
		String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.build()
				.toUriString();
		return productService.updateProduct(baseUrl, id, product, image, video);
	}
	
	@GetMapping
	public Iterable<Product> productList() {
		return productService.getAllProduct();
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable("id") UUID id) {
		return productService.getProductById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteProduct(@PathVariable("id") UUID id) {
		productService.deleteProduct(id);
	}
	
	@GetMapping("/poster/{name}")
	public ResponseEntity<Object> getImageUrl(@PathVariable("name") String filename) throws IOException {
		return productService.downloadFile("image", filename);
	}
	
	@GetMapping("/video/{name}")
	public ResponseEntity<Object> getVideoUrl(@PathVariable("name") String filename) throws IOException {
		return productService.downloadFile("video", filename);
	}
}

package com.karanSpringboot.imageUpDown;

import com.karanSpringboot.imageUpDown.service.ImageUpDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootApplication
@RestController
@RequestMapping("/image")
public class ImageUpDownApplication {

	@Autowired
	private ImageUpDownService service;

	@PostMapping
	public ResponseEntity<?> saveImage(@RequestParam("imageFile") MultipartFile fileData) throws IOException {
		String response = service.uploadImage(fileData);
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<?> getImageFromDb(@PathVariable String fileName){
		byte[] imageData = service.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);
	}

	public static void main(String[] args) {
		SpringApplication.run(ImageUpDownApplication.class, args);

	}

}

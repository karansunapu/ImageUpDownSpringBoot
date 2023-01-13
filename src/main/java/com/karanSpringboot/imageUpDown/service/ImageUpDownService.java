package com.karanSpringboot.imageUpDown.service;


import com.karanSpringboot.imageUpDown.entity.ImageData;
import com.karanSpringboot.imageUpDown.repository.ImageUpDownRepository;
import com.karanSpringboot.imageUpDown.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageUpDownService {

    @Autowired
    private ImageUpDownRepository repository;


    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = repository.save(ImageData.builder()
                .fileType(file.getContentType())
                .fileName(file.getOriginalFilename())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());

        if (imageData!=null)
            return "Image uploaded successfully : "+ file.getOriginalFilename();

        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByFileName(fileName);

        return ImageUtils.decompressImage(dbImageData.get().getImageData());
    }
}

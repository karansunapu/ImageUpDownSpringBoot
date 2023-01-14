package com.karanSpringboot.imageUpDown.service;


import com.karanSpringboot.imageUpDown.entity.FileData;
import com.karanSpringboot.imageUpDown.entity.ImageData;
import com.karanSpringboot.imageUpDown.repository.FileDataRepository;
import com.karanSpringboot.imageUpDown.repository.ImageUpDownRepository;
import com.karanSpringboot.imageUpDown.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageUpDownService {

    @Autowired
    private ImageUpDownRepository repository;
    @Autowired
    private FileDataRepository fileDataRepository;

    private final String fileSystempath = "/Users/sunapukaran/Documents/javaTechie_SpringBoot_Projets/Storage_files/";


    public String uploadImageFileSystem(MultipartFile file) throws IOException {

        String filePathName = fileSystempath + file.getOriginalFilename();

        // store in DB
        FileData fileData = fileDataRepository.save(FileData.builder()
                                .fileName(file.getOriginalFilename())
                                .fileType(file.getContentType())
                                .filePath(filePathName)
                                .build());

        // store in FileSystem
        file.transferTo(new File(filePathName));

        if (fileData!=null)
            return "File uploaded successfully : "+ file.getOriginalFilename();

        return null;
    }

    public byte[] downloadImageFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByFileName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        return file;
    }


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

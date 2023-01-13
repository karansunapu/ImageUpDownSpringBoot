package com.karanSpringboot.imageUpDown.repository;

import com.karanSpringboot.imageUpDown.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageUpDownRepository extends JpaRepository<ImageData, Long> {

    public Optional<ImageData> findByFileName(String fileName);
}

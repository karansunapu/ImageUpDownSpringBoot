package com.karanSpringboot.imageUpDown.repository;

import com.karanSpringboot.imageUpDown.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

    public Optional<FileData> findByFileName(String fileName);
}

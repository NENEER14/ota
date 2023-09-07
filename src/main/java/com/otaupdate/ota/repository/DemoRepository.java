package com.otaupdate.ota.repository;

import com.otaupdate.ota.model.FileData;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface DemoRepository extends MongoRepository<FileData, String> {
    List<FileData> findByFileNameOrderByDateDesc(@Param("fileName") String fileName);
}
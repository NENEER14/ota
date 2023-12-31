package com.otaupdate.ota.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.otaupdate.ota.Utils.CompressAndDecompress;
import com.otaupdate.ota.Utils.EncodeDecode;
import com.otaupdate.ota.Utils.JsonValidation;
import com.otaupdate.ota.model.FileData;
import com.otaupdate.ota.repository.DemoRepository;

import org.springframework.ui.*;

@Controller
public class FileUploadAndValidation {

    @Autowired
    private DemoRepository demoRepository;

    @Value("${localRepo.path}")
    private String LOCAL_REPO;

    private JsonValidation jsonValidation;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FileUploadAndValidation.class);
    
    @GetMapping("/")
    public String showFileUploadForm(Model model){
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model){

        List<String> errors = new ArrayList<>();

        if(file.isEmpty()) {
            errors.add("Please select a file to upload (JSON)");
        } else if(file.getSize() / 1024 > 200) {
            errors.add("File size should not be more than 200 KB");
        } else {
            try {
                    jsonValidation = new JsonValidation();
                    if(jsonValidation.validateJson(file, errors)) { 
                        List<FileData> fileDataList = demoRepository.findByFileNameOrderByDateDesc(file.getOriginalFilename().toLowerCase());
                        int newVersion = !fileDataList.isEmpty() ? (fileDataList.get(0).getVersion() + 1) : 1;
                        CompressAndDecompress.compressFile(file, model, newVersion, LOCAL_REPO);
                        EncodeDecode.encode(model, file.getOriginalFilename(), LOCAL_REPO, newVersion);
                        demoRepository.save(new FileData(file.getOriginalFilename().toLowerCase(), newVersion, new Date(),model.getAttribute("encoded_zip_file_data").toString()));
                    }
            } catch (IOException ioException){
                errors.add("Error reading the JSON file :- " + ioException.getMessage());
            } finally {
                System.gc();
            }
        }
        model.addAttribute("errors", errors);
        logger.info("===========================" + model.toString());
        return "result";
    }
}

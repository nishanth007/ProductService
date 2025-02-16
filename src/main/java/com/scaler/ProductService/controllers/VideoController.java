package com.scaler.ProductService.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class VideoController {

    @GetMapping("/video/{fileName}")
    public ResponseEntity<InputStreamResource> streamVideo(@PathVariable String fileName) throws IOException {
        // File path to the video file
        File file = new File("D:\\Projects\\ProductService\\src\\main\\resources\\static\\" + fileName);

        // Read the video file and send it in response
        FileInputStream videoStream = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(videoStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}

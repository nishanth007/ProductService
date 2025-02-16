package com.scaler.ProductService.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;

//@RestController
public class VideoController2 {

    private static final String VIDEO_PATH = "D:\\Projects\\ProductService\\src\\main\\resources\\static\\";

    //@GetMapping("/video/{fileName}")
    public ResponseEntity<byte[]> streamVideo(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        File videoFile = new File(VIDEO_PATH + fileName);
        long fileLength = videoFile.length();
        long startByte = 0;
        long endByte = fileLength - 1;

        // Check for range header
        String range = request.getHeader(HttpHeaders.RANGE);
        if (range != null && range.startsWith("bytes=")) {
            String[] ranges = range.substring(6).split("-");
            startByte = Long.parseLong(ranges[0]);
            if (ranges.length > 1 && !ranges[1].isEmpty()) {
                endByte = Long.parseLong(ranges[1]);
            }
        }

        // Set content type and headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(endByte - startByte + 1));
        headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + startByte + "-" + endByte + "/" + fileLength);
        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");

        // Stream the video incrementally using BufferedInputStream and OutputStream
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(videoFile));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];  // 8KB buffer size
            int bytesRead;
            long bytesToSkip = startByte;

            // Skip to the requested starting byte
            while (bytesToSkip > 0 && (bytesRead = inputStream.read(buffer, 0, (int) Math.min(buffer.length, bytesToSkip))) != -1) {
                bytesToSkip -= bytesRead;
            }

            // Now read and send the requested byte range
            long bytesRemaining = endByte - startByte + 1;
            while (bytesRemaining > 0 && (bytesRead = inputStream.read(buffer, 0, (int) Math.min(buffer.length, bytesRemaining))) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                bytesRemaining -= bytesRead;
            }

            byte[] videoRangeBytes = outputStream.toByteArray();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .body(videoRangeBytes);
        }
    }
}

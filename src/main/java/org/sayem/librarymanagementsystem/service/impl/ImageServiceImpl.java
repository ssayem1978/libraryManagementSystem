package org.sayem.librarymanagementsystem.service.impl;

import org.sayem.librarymanagementsystem.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String uploadImage(MultipartFile coverImage) {
        if (!coverImage.isEmpty()) {
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String originalFilename = coverImage.getOriginalFilename();
                String uniqueFilename = java.util.UUID.randomUUID() + "_" + originalFilename;
                String filePath = uploadPath.resolve(uniqueFilename).toString();
                File file = new File(filePath);
                coverImage.transferTo(file);
                return uniqueFilename;
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }
        return null;
    }

    @Override
    public void deleteImage(String coverImage) {
        if (coverImage != null && !coverImage.isEmpty()) {
            try {
                Path filePath = Paths.get(uploadDir).resolve(coverImage);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

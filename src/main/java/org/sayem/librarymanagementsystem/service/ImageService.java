package org.sayem.librarymanagementsystem.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile coverImage);

    void deleteImage(String filename);
}

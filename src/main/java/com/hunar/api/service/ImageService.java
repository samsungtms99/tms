package com.hunar.api.service;

import com.hunar.api.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ImageService {

    public String uploadImageToFileSystem(MultipartFile file, int idOrder) throws IOException;

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException;

    List<ImageEntity> findByIdOrder(int idOrder);

    public Map uploadImageToCloudnary(MultipartFile file);
}

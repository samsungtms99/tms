package com.hunar.api.service.impl;

import com.hunar.api.directory.service.DirectoryService;
import com.hunar.api.entity.ImageEntity;
import com.hunar.api.repository.ImageRepository;

import com.hunar.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${upload.dir}")
    private String dirPath;

    @Autowired
    private ImageRepository fileDataRepository;

    @Autowired
    DirectoryService directoryService;

    public String uploadImageToFileSystem(MultipartFile file, int idOrder) throws IOException {
       final String path = directoryService.createDirectoryForDocInOut(dirPath);
        String filePath=path+"\\"+file.getOriginalFilename();

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(file.getOriginalFilename());
        imageEntity.setFilePath(filePath);
        imageEntity.setType(file.getContentType());
        imageEntity.setOrderId(idOrder);

        ImageEntity fileData=fileDataRepository.save(imageEntity);

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<ImageEntity> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    @Override
    public List<ImageEntity> findByIdOrder(int idOrder) {
        if (idOrder!=0){
            List<ImageEntity> imageEntity= fileDataRepository.findAllByOrderId(idOrder);
            if (!imageEntity.isEmpty()){
                return  imageEntity;
            }

        }
        return null;
    }

// BELOW CODE IS TO STORE IMAGE INTO DB.
//    public String uploadImage(MultipartFile file) throws IOException {
//        ImageData imageData = repository.save(ImageData.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .imageData(ImageUtils.compressImage(file.getBytes())).build());
//        if (imageData != null) {
//            return "file uploaded successfully : " + file.getOriginalFilename();
//        }
//        return null;
//    }



//    public byte[] downloadImage(String fileName) {
//        Optional<ImageData> dbImageData = repository.findByName(fileName);
//        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
//        return images;
//    }



}

package com.hunar.api.controller;

import com.hunar.api.constant.Constants;
import com.hunar.api.entity.ImageEntity;
import com.hunar.api.service.ImageService;
import com.hunar.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = Constants.CROSS_ORIGIN)
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService service;

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/fileSystem")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image") MultipartFile[] files, @RequestParam("idOrder") int idOrder) throws IOException {
        List<String> imagesPath = new ArrayList<>();
       for (MultipartFile file: files){
           String uploadImage = service.uploadImageToFileSystem(file, idOrder);
           imagesPath.add(uploadImage);
       }

        return ResponseEntity.status(HttpStatus.OK)
                .body(imagesPath);
    }



    @GetMapping(value = "/fileSystem/{idOrder}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable int idOrder) throws IOException {
        List<ImageEntity> imageEntity = service.findByIdOrder(idOrder);
        List<byte[]> imageDataList=new ArrayList<>();
        for (ImageEntity image:imageEntity){
            byte[] imageData=service.downloadImageFromFileSystem(image.getName());
            imageDataList.add(imageData);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageDataList);

    }

    @GetMapping(value = "/fileSystem/base64/{idOrder}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> downloadBase64ImageFromFileSystem(@PathVariable int idOrder) throws IOException {
        List<ImageEntity> imageEntity = service.findByIdOrder(idOrder);
        List<byte[]> imageDataList=new ArrayList<>();
        for (ImageEntity image:imageEntity){
            byte[] imageData=service.downloadImageFromFileSystem(image.getName());
            imageDataList.add(imageData);
        }
        return imageDataList.stream()
                .map(Base64.getEncoder()::encodeToString)
                .collect(Collectors.toList());

    }


    @PostMapping(value = "uploadImageUsingCloudnary")
    public Map uploadImageUsingCloudnary(@RequestParam("image") MultipartFile file){
        return service.uploadImageToCloudnary(file);
    }

    @GetMapping(value = "getImagesPathByOrderId/{idOrder}")
    public List<ImageEntity> getImagesPathByOrderId(@PathVariable("idOrder") int idOrder){
        return  service.findByIdOrder(idOrder);
    }
}

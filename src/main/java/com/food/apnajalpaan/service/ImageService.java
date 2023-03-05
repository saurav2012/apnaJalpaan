package com.food.apnajalpaan.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.food.apnajalpaan.model.Image;
import com.food.apnajalpaan.repository.ImageRepository;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@Builder
public class ImageService {
    @Autowired
    ImageRepository repository;

    public Mono<Image> saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        Dotenv dotenv = Dotenv.load();
        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        cloudinary.config.secure = true;
        try {
            // Upload the image
            Map params1 = ObjectUtils.asMap(file.getOriginalFilename(), true, "unique_filename", false, "overwrite", true);
            Map uploadResult  = cloudinary.uploader().upload(file.getBytes(), params1);
            String publicId = uploadResult.get("public_id").toString();
            image.setPublicId(publicId);
            image.setName(funtionSplitString(file.getOriginalFilename()));
            image.setUrl(cloudinary.url().secure(true).format("jpg").publicId(publicId).generate());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return repository.insert(image);
    }

    private String funtionSplitString(String originalFilename) {
        String[] arrOfStr = originalFilename.split(".jpg");
        String result = "";
        for (String a : arrOfStr) result = result.concat(a);
        return result;
    }

    public Mono<Image> downloadImage(String imageId){
        return repository.findById(imageId);
    }
    public Mono<Void> deleteImage(String publicId) throws IOException {
        Dotenv dotenv = Dotenv.load();
        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        cloudinary.config.secure = true;
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return repository.deleteByPublicId(publicId);
    }

    public Flux<Image> getAllImages() {
        return repository.findAll();
    }
}

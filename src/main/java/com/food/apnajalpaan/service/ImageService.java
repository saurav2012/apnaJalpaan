package com.food.apnajalpaan.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.food.apnajalpaan.model.Image;
import com.food.apnajalpaan.model.admin.AdminModel;
import com.food.apnajalpaan.repository.ImageRepository;
import com.food.apnajalpaan.utils.ImageUitls;
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

    public Mono<Image> saveImage(Mono<Image> imageMono) {

        return imageMono.flatMap(
                res -> {
                    Dotenv dotenv = Dotenv.load();
                    Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
                    cloudinary.config.secure = true;
                    try {
                        // Upload the image
                        Map params1 = ObjectUtils.asMap("use_filename", true, "unique_filename", false, "overwrite", true);
                        cloudinary.uploader().upload(res.getLocation(), params1);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    res.setUrl(cloudinary.url().generate(res.getName()));
                    return Mono.just(res);
                }).flatMap(repository::insert);
    }
    public Mono<String> deleteImage(String imageId){
        repository.deleteById(imageId);
        return Mono.just("Image is deleted successfully");
    }

    public Flux<Image> getAllImages() {
        return repository.findAll();
    }
}

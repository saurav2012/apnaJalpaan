package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Image;
import com.food.apnajalpaan.repository.ImageRepository;
import com.food.apnajalpaan.utils.ImageUitls;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@Builder
public class ImageService {
    @Autowired
    ImageRepository repository;
    public Mono<Image> uploadImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImageData(file.getBytes());
        return repository.save(image);
    }

    public Mono<byte[]> downloadImage(String fileName) {

        return repository.findByName(fileName).flatMap(
                res -> {
                    return Mono.just(ImageUitls.decompressImage(res.getImageData()));
                }
        );
    }
}

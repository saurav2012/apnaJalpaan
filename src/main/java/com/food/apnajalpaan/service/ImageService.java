package com.food.apnajalpaan.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.food.apnajalpaan.model.Image;
import com.food.apnajalpaan.repository.ImageRepository;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

import java.util.Map;


@Service
@Slf4j
@Builder
public class ImageService {
    @Autowired
    ImageRepository repository;


    public Mono<Image> saveImage(FilePart file) throws IOException, InterruptedException {
        Image image = new Image();
        Dotenv dotenv = Dotenv.load();
        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        cloudinary.config.secure = true;
        final String[] publicId = {""};
        mergeDataBuffers(file.content()).flatMap(
                res -> {
                    try {
                        // Upload the image
                        Map params1 = ObjectUtils.asMap(file.filename(), true, "unique_filename", false, "overwrite", true);
                        Map uploadResult  = cloudinary.uploader().upload(res, params1);
                        publicId[0]=(uploadResult.get("public_id").toString());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    return Mono.empty();
                }
        ).subscribe();

        Thread.sleep(60000);
        image.setPublicId(publicId[0]);
        image.setUrl(cloudinary.url().secure(true).format("jpg").publicId(publicId[0]).generate());
        image.setName(funtionSplitString(file.filename()));
        return repository.insert(image);
    }


    Mono<byte[]> mergeDataBuffers(Flux<DataBuffer> dataBufferFlux) {
        return DataBufferUtils.join(dataBufferFlux)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return bytes;
                });
    }

    private String funtionSplitString(String originalFilename) {
        String[] arrOfStr = new String[0];
        if(originalFilename.contains(".jpg")) arrOfStr = originalFilename.split(".jpg");
        else if(originalFilename.contains(".png")) arrOfStr = originalFilename.split(".png");
        else if(originalFilename.contains(".jpeg")) arrOfStr = originalFilename.split(".jpeg");
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

    public Mono<Image> getByName(String name){
        return repository.findByName(name);
    }
}

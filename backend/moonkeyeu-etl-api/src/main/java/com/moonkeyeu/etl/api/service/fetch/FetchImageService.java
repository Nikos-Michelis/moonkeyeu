package com.moonkeyeu.etl.api.service.fetch;

import com.moonkeyeu.etl.api.service.s3.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FetchImageService {
    @Value("${aws.cloudfront.url}")
    private String cloudFrontUrl;
    private final WebClient webClient;
    private final S3Service s3Service;
    private final CacheManager cacheManager;


    @Autowired
    public FetchImageService(
            WebClient webClient,
            S3Service s3Service,
            CacheManager cacheManager
    ) {
        this.webClient = webClient;
        this.s3Service = s3Service;
        this.cacheManager = cacheManager;
    }

    public String saveLocal(String imageUrl, String localDir, String baseUrl) throws IOException {
        try {
            String fileName = getFileNameFromUrl(imageUrl);
            Path filePath = Paths.get(localDir, fileName);
            System.out.println(filePath);
            if (Files.exists(filePath)) {
                log.info("Image already exists: " + filePath);
                return baseUrl + fileName;
            }
            Files.createDirectories(Paths.get(localDir));
            downloadImage(imageUrl)
                    .flatMap(dataBuffer -> saveToLocalStorage(dataBuffer, filePath))
                    .doOnSuccess(result -> log.info("Image processed and saved successfully"))
                    .doOnError(e -> log.error("Error during processing: " + e.getMessage()))
                    .block();

            return baseUrl + fileName;
        } catch (IOException e) {
            throw new IOException("Failed to download image: " + imageUrl, e);
        }
    }

    public String saveToS3(String imageUrl, String bucketName, String key, boolean skipUpload ) throws IOException {
        try {
            String fileName = getFileNameFromUrl(imageUrl);
            String s3Root = key + fileName;

            if (skipUpload) {
               // log.info("hasAlreadyUploaded: " +  skipUpload + " Image already exists in S3: " + cloudFrontUrl + s3Root);
                return cloudFrontUrl + s3Root;
            }

            Cache cache = cacheManager.getCache("processedImages");
            if (cache != null) {
                Boolean exists = cache.get(s3Root, Boolean.class);
                if (exists != null) {
                    return cloudFrontUrl + s3Root;
                }
            }

            boolean existsInS3 = s3Service.isObjectExists(bucketName, s3Root);
            if (existsInS3) {
                //log.info("Image already exists in S3: " + cloudFrontUrl + s3Root);
                return cloudFrontUrl + s3Root;

            }

            if (cache != null) {
               // log.info("Cache: New image root added - " + cloudFrontUrl + s3Root);
                cache.put(s3Root, existsInS3);
            }
            downloadImage(imageUrl)
                    .flatMap(dataBuffer -> uploadToS3(bucketName, s3Root, dataBuffer))
                    .doOnSuccess(result -> log.info("Uploaded image to S3: " + fileName))
                    .doOnError(e -> log.error("Error during processing: " + e.getMessage()))
                    .block();

            log.info("new url: {}", cloudFrontUrl + s3Root);
            return cloudFrontUrl + s3Root;
        } catch (MalformedURLException e) {
            log.error("Invalid URL generated: " + imageUrl, e);
            throw new MalformedURLException("Failed to save image to S3: " + imageUrl);
        } catch (Exception e) {
            log.error("Unexpected error occurred: " + e.getMessage(), e);
            throw new IOException("Failed to save image to S3: " + imageUrl, e);
        }
    }

    public String getFileNameFromUrl(String imageUrl) throws MalformedURLException {
        if (imageUrl != null
                && !imageUrl.isEmpty()
                && (imageUrl.startsWith("http://")
                || imageUrl.startsWith("https://"))) {

             URL url = new URL(imageUrl);
            // get the image file(eg. https..../example.png)
            return Paths.get(url.getPath()).getFileName().toString();
        }
        throw new MalformedURLException("Invalid url: " + imageUrl);
    }


    private Mono<DataBuffer> downloadImage(String imageUrl) {
        return webClient.get()
                .uri(imageUrl)
                .retrieve()
                .bodyToMono(DataBuffer.class)
                .doOnError(e -> log.error("Error downloading image: " + e.getMessage()));
    }
    public Mono<Path> saveToLocalStorage(DataBuffer dataBuffer, Path filePath){
        try {
            ByteBuffer byteBuffer = dataBuffer.asByteBuffer();
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            Files.write(filePath, bytes);
            log.info("Downloaded and saved image: " + filePath);
        } catch (IOException e) {
            return Mono.error(new RuntimeException("Failed to save image: " + e.getMessage(), e));
        } finally {
            DataBufferUtils.release(dataBuffer);
        }
        return Mono.just(filePath);
    }
    public Mono<Void> uploadToS3(String bucketName, String key, DataBuffer dataBuffer) {
        return Mono.fromRunnable(() -> {
            try {
                RequestBody requestBody = RequestBody.fromInputStream(dataBuffer.asInputStream(true), dataBuffer.readableByteCount());
                s3Service.putObject(bucketName, key, requestBody);
            } finally {
                DataBufferUtils.release(dataBuffer);
            }
        });
    }
}

package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.EnglishAcademyApplication;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
public class FileAudioService {
    @EventListener
    public void init(ApplicationReadyEvent event) {

        // initialize Firebase
        InputStream serviceAccount = EnglishAcademyApplication.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");

        try {
            // config connect for project:
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount)) // config credentials contains data.
                    .setStorageBucket("english-academy-79e1b.appspot.com")// config storage contains files.
                    .build();

            FirebaseApp.initializeApp(options);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"mp3", "wav", "flac"})
                .contains(fileExtension.trim().toLowerCase());
    }

    private String generateFileName(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String generatedFileName = UUID.randomUUID().toString().replace("-", "");
        return generatedFileName + fileExtension;
    }

    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()){
                throw new RuntimeException("Failed to store empty file");
            }
            // check file is image?
            if (!isImageFile(file)){
                throw new RuntimeException("You can upload audio file");
            }
            // check <= 5Mb
            float fileSizeInMegabytes = file.getSize() / 1_000_000f;
            if (fileSizeInMegabytes > 5.0f){
                throw new RuntimeException("File must be <= 5Mb");
            }
            InputStream is = file.getInputStream();
            String filename = file.getOriginalFilename();

            // Upload to cloud storage
            StorageClient storageClient = StorageClient.getInstance();
            Blob blob = storageClient.bucket().create(generateFileName(filename), is, "audio/mpeg");

            // Get URL path
            String bucketName = blob.getBucket();
            String blobName = blob.getName();
            String path = String.format("https://storage.googleapis.com/%s/%s", bucketName, blobName);

            return path;
        } catch (IOException exception){
            throw new RuntimeException("Failed to store file.",exception);
        }
    }

}

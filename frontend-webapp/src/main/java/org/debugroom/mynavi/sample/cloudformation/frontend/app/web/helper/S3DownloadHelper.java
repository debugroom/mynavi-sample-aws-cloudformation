package org.debugroom.mynavi.sample.cloudformation.frontend.app.web.helper;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.s3.AmazonS3;

import org.apache.commons.io.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.core.io.s3.SimpleStorageProtocolResolver;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.stereotype.Component;

import org.debugroom.mynavi.sample.cloudformation.common.apinfra.cloud.aws.S3Info;

@Component
public class S3DownloadHelper{

    private static final String S3_BUCKET_PREFIX = "s3://";
    private static final String DIRECTORY_DELIMITER = "/";

    @Autowired
    S3Info s3Info;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    AmazonS3 amazonS3;

    @Value("classpath:sample.jpg")
    Resource imageResource;

    public BufferedImage getImage(String imageFilePath){
        Resource resource = resourceLoader.getResource(
                new StringBuilder()
                        .append(S3_BUCKET_PREFIX)
                        .append(s3Info.getBucketName())
                        .append(DIRECTORY_DELIMITER)
                        .append(imageFilePath)
                        .toString());
        BufferedImage image = null;
        try(InputStream inputStream = resource.getInputStream()){
            image = ImageIO.read(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public String getTextFileBody(String textFilePath){
        Resource resource = resourceLoader.getResource(
                new StringBuilder()
                        .append(S3_BUCKET_PREFIX)
                        .append(s3Info.getBucketName())
                        .append(DIRECTORY_DELIMITER)
                        .append(textFilePath)
                        .toString());
        String textBody = null;
        try(InputStream inputStream = resource.getInputStream()){
            textBody = IOUtils.toString(inputStream, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        return textBody;
    }

    @PostConstruct
    private void initS3(){
        String objectKey = new StringBuilder()
                .append(S3_BUCKET_PREFIX)
                .append(s3Info.getBucketName())
                .append(DIRECTORY_DELIMITER)
                .append("sample.jpg")
                .toString();
        Resource newResource = resourceLoader.getResource(objectKey);
        if(!newResource.getClass().getName().endsWith("SimpleStorageResource")
                && resourceLoader instanceof DefaultResourceLoader){
            SimpleStorageProtocolResolver simpleStorageProtocolResolver = new SimpleStorageProtocolResolver(amazonS3);
            simpleStorageProtocolResolver.setTaskExecutor(new SyncTaskExecutor());
            newResource = simpleStorageProtocolResolver.resolve(objectKey, resourceLoader);
        }

        WritableResource writableResource = (WritableResource)newResource;
        try(InputStream inputStream = imageResource.getInputStream();
            OutputStream outputStream = writableResource.getOutputStream()){
            IOUtils.copy(inputStream, outputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void exitEvent() {
        amazonS3.deleteObject(s3Info.getBucketName(), "sample.jpg");
    }

}

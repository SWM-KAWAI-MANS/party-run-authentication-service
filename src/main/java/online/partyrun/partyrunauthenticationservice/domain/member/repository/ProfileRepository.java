package online.partyrun.partyrunauthenticationservice.domain.member.repository;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Profile;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.InvalidImageFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileRepository {
    private static final List<String> imageExtensions = List.of("jpg", "jpeg", "png");
    S3Template s3Template;
    String bucketName;

    public ProfileRepository(S3Template s3Template,
                             @Value("${spring.cloud.aws.s3.bucket}") String bucketName) {
        this.s3Template = s3Template;
        this.bucketName = bucketName;
    }

    public Profile save(MultipartFile imageFile) {
        validateFile(imageFile);
        final S3Resource result = s3Template.upload(bucketName, generateName(imageFile), getInputStream(imageFile));
        return new Profile(getUrl(result));
    }
    private void validateFile(MultipartFile imageFile) {
        if(isNotAllowFile(imageFile)) {
            throw new InvalidImageFileException();
        }
    }

    private boolean isNotAllowFile(MultipartFile imageFile) {
        return Objects.isNull(imageFile) ||
                Objects.isNull(imageFile.getOriginalFilename()) ||
                imageFile.isEmpty() ||
                isNotImage(imageFile);
    }

    private boolean isNotImage(MultipartFile file) {
        return imageExtensions.stream()
                .noneMatch(extension -> extension.equals(getExtension(file)));
    }

    private String getExtension(MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        final int lastDotIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastDotIndex + 1);
    }
    private String generateName(MultipartFile file) {
        return UUID.randomUUID() + "." + getExtension(file);
    }

    private InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new InvalidImageFileException();
        }
    }

    private String getUrl(S3Resource s3Resource) {
        try {
            return s3Resource.getURL().toString();
        } catch (IOException e) {
            throw new InvalidImageFileException();
        }
    }
}

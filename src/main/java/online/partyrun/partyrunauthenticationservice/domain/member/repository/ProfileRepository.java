package online.partyrun.partyrunauthenticationservice.domain.member.repository;

import io.awspring.cloud.s3.S3Template;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.InvalidImageFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileRepository {
    S3Template s3Template;
    String bucketName;

    public ProfileRepository(S3Template s3Template,
                             @Value("${spring.cloud.aws.s3.bucket}") String bucketName) {
        this.s3Template = s3Template;
        this.bucketName = bucketName;
    }

    public void save(MultipartFile imageFile, String profileName) {
        s3Template.upload(bucketName, profileName, getInputStream(imageFile));
    }

    private InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new InvalidImageFileException();
        }
    }
}

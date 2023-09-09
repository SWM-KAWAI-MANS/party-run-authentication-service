package online.partyrun.partyrunauthenticationservice.domain.member.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.InvalidMultipartImageException;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.MemberNotFoundException;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.MemberRepository;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberProfileService {
    ProfileRepository profileRepository;
    MemberRepository memberRepository;

    List<String> imageExtensions;

    public MemberProfileService(
            ProfileRepository profileRepository,
            MemberRepository memberRepository,
            @Value("${multipart.profile-image.allow-file-type}") List<String> imageExtensions
    ) {
        this.profileRepository = profileRepository;
        this.memberRepository = memberRepository;
        this.imageExtensions = imageExtensions;
    }


    public void updateProfile(String memberId, MultipartFile imageFile) {
        validateFile(imageFile);
        final String profileName = generateProfileName(imageFile);
        getMember(memberId).updateProfile(profileName);
        profileRepository.save(imageFile, profileName);
    }

    private Member getMember(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    private void validateFile(MultipartFile imageFile) {
        if(isNotAllowFile(imageFile)) {
            throw new InvalidMultipartImageException();
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

    private String generateProfileName(MultipartFile file) {
        return UUID.randomUUID() + "." + getExtension(file);
    }

}

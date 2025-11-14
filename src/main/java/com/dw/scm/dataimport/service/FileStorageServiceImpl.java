package com.dw.scm.dataimport.service;

import com.dw.scm.common.exception.BusinessException;
import com.dw.scm.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageServiceImpl(@Value("${file.upload.path}") String uploadPath) {
        // 상대 경로 대신 사용자의 홈 디렉토리를 기준으로 절대 경로를 설정합니다.
        // 이렇게 하면 권한 문제로부터 더 자유로워집니다.
        Path userHome = Paths.get(System.getProperty("user.home"));
        this.fileStorageLocation = userHome.resolve(uploadPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.DIRECTORY_CREATION_FAILED);
        }
    }

    @Override
    public Path store(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (fileName.contains("..")) {
                throw new BusinessException(ErrorCode.INVALID_FILE_PATH);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation;
        } catch (IOException ex) {
            throw new BusinessException(ErrorCode.FILE_STORAGE_FAILED, "파일 " + fileName + "을(를) 저장할 수 없습니다.");
        }
    }
}

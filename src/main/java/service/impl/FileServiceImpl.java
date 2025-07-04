package service.impl;

import dto.FileDto;
import service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    // сохранение файлов
    @Override
    public String save(FileDto fileDto) {

        // даем каждому файлу уникальное значение
        String originalNameForFile = UUID.randomUUID() + fileDto.getName();

        try {
            Files.copy(fileDto.getInputStream(), Paths.get(fileDto.getStoragePath() + "\\" + originalNameForFile));
            return originalNameForFile;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }
}

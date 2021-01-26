package org.nearmi.core.service.storage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.nearmi.core.exception.MiException;
import org.nearmi.core.resource.GeneralResKey;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * File system storing strategy without encryption
 */
@Component
@Primary
@Slf4j
public class DefaultStorageStrategy implements IStorageStrategy {

    @Override
    public String save(MultipartFile file, String baseDir, String userId, String resourceId) {
        String parentDir = baseDir + File.separator + userId + File.separator + resourceId + File.separator;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generated = RandomStringUtils.randomAlphabetic(8);
        String generatedFileName;
        if (extension != null && !extension.equals("")) {
            generatedFileName = generated + "." + extension;
        } else {
            generatedFileName = generated;
        }
        log.info("generated name {} for file {} :", file.getOriginalFilename(), generatedFileName);
        String dest = parentDir + File.separator + generatedFileName;
        File destFile = new File(dest);
        try {
            FileUtils.forceMkdirParent(destFile);
            destFile.createNewFile();
            Path filepath = Paths.get(parentDir + File.separator, generatedFileName);
            file.transferTo(filepath);
            return dest;
        } catch (IOException e) {
            log.error("error when saving file", e);
            throw new MiException(GeneralResKey.NMI_G_0008, file.getOriginalFilename(), dest);
        }
    }
}

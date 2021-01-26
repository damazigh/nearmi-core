package org.nearmi.core.service.storage;

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
public class DefaultStorageStrategy implements IStorageStrategy {

    @Override
    public String save(MultipartFile file, String baseDir, String userId, String resourceId) {
        String parentDir = baseDir + File.separator + userId + File.separator + resourceId + File.separator;
        String generatedFileName = RandomStringUtils.random(8);
        Path filepath = Paths.get(parentDir + File.separator, generatedFileName);
        String dest = parentDir + File.separator + generatedFileName;
        try {
            file.transferTo(filepath);
            return dest;
        } catch (IOException e) {
            throw new MiException(GeneralResKey.NMI_G_0008, file.getOriginalFilename(), dest);
        }
    }
}

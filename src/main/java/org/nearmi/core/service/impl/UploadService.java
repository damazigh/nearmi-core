package org.nearmi.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.nearmi.core.exception.FileUploadException;
import org.nearmi.core.exception.MiException;
import org.nearmi.core.resource.GeneralResKey;
import org.nearmi.core.service.IUploadService;
import org.nearmi.core.service.storage.IStorageStrategy;
import org.nearmi.core.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Service
public class UploadService implements IUploadService {
    @Value("${nearmi.config.dataPath:/nearmi/data}")
    private String dataDir;
    @Autowired
    private IStorageStrategy storageStrategy;

    @Override
    public String upload(MultipartFile file, String userId, String resourceId, String... acceptedMime) {
        File parentDir = new File(dataDir);
        if (!parentDir.exists()) {
            throw new FileUploadException(String.format("parent dir %1s doesn't exist", parentDir));
        }

        if (!parentDir.isDirectory()) {
            throw new FileUploadException(String.format("%1s is not a directory", parentDir));
        }
        Validator.validateFileMime(file, acceptedMime);
        return storageStrategy.save(file, dataDir, userId, resourceId);
    }

    @Override
    public byte[] load(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                return IOUtils.toByteArray(new FileInputStream(file));
            } catch (IOException e) {
                throw new MiException(GeneralResKey.NMI_G_0001);
            }
        }
        throw new MiException(GeneralResKey.NMI_G_0001);
    }

    @Override
    public void deleteIfExist(String path) {
        log.debug("deleting file : {} if exists", path);
        File file = new File(path);
        if (file.exists()) {
            log.debug("file {} exist", path);
            boolean res = file.delete();
            if (!res) {
                log.warn("cannot delete file {}", path);
                try {
                    log.debug("force delete file {}", path);
                    FileUtils.forceDelete(file);
                } catch (IOException e) {
                    log.error("Error occured when forcing file deletion", e);
                    throw new MiException(GeneralResKey.NMI_G_0001);
                }
            }
        }
    }
}

package org.nearmi.core.service.impl;

import org.nearmi.core.exception.FileUploadException;
import org.nearmi.core.service.IUploadService;
import org.nearmi.core.service.storage.IStorageStrategy;
import org.nearmi.core.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
}

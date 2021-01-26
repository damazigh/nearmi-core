package org.nearmi.core.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageStrategy {
    /**
     * save file on file system
     *
     * @param file       file to store
     * @param baseDir    parent directory
     * @param userId     authenticated user id
     * @param resourceId resource id (example shop id)
     * @return path to stored file
     */
    String save(MultipartFile file, String baseDir, String userId, String resourceId);
    
}

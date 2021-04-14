package org.nearmi.core.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * Service handling file upload / load
 * </p>
 *
 * @author adjebarri
 */
public interface IUploadService {
    String upload(MultipartFile file, String userId, String resourceId, String... acceptedMime);

    String uploadInSubFolder(MultipartFile file, String userId, String resourceId, String[] subDirs, String... acceptedMime);

    byte[] load(String path);

    void deleteIfExist(String path);

}

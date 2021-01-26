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
}

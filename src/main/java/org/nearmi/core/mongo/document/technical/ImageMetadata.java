package org.nearmi.core.mongo.document.technical;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ImageMetadata {
    private String path;
    private String name;
    private boolean rootImage;
    private LocalDateTime uploadedAt;
    private LocalDateTime lastUpdatedAt;
    private String lastUpdatedBy;

    public ImageMetadata(String userId, boolean root, String path) {
        this.lastUpdatedBy = userId;
        this.path = path;
        this.rootImage = root;
        this.name = FilenameUtils.getName(path);
        this.lastUpdatedAt = LocalDateTime.now();
        this.uploadedAt = LocalDateTime.now();
    }

    public void switchRootState(String userId) {
        this.lastUpdatedBy = userId;
        this.lastUpdatedAt = LocalDateTime.now();
        this.rootImage = !this.rootImage;
    }
}

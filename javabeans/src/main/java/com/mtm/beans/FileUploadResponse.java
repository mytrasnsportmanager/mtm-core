package com.mtm.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 12/25/2019.
 */
public class FileUploadResponse {
    @JsonProperty
    public String getUploadedEntityId() {
        return uploadedEntityId;
    }

    public void setUploadedEntityId(String uploadedEntityId) {
        this.uploadedEntityId = uploadedEntityId;
    }

    @JsonProperty
    public String getUploadedFileType() {
        return uploadedFileType;
    }

    public void setUploadedFileType(String uploadedFileType) {
        this.uploadedFileType = uploadedFileType;
    }

    @JsonProperty
    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    private String uploadedEntityId;
    private String uploadedFileType;
    private String fileURL;
    private int status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

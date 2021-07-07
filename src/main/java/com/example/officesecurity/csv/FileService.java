package com.example.officesecurity.csv;

import java.io.File;
import java.util.UUID;

public interface FileService {
    void saveAsyncFile(UUID fileId);
    File getAsyncFile(UUID fileId);
}

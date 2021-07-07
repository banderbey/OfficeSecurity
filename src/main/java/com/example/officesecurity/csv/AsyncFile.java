package com.example.officesecurity.csv;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Service
@Async
@EnableAsync

public class AsyncFile {
    public void saveFile(UUID fileId){
        File csvOutputFile = new File(String.format("files/%s.csv", fileId));
        try(OutputStream outStream = new FileOutputStream(csvOutputFile.getAbsolutePath())) {

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            InputStreamResource isr = new InputStreamResource(new FileInputStream(csvOutputFile));

            while ((bytesRead = isr.getInputStream().read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {

        }

    }
}

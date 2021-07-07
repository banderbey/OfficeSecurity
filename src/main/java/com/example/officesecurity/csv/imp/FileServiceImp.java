package com.example.officesecurity.csv.imp;

import com.example.officesecurity.csv.FileService;
import com.example.officesecurity.model.UserDAO;
import com.example.officesecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.UUID;
@Service
@Async
@EnableAsync
@RequiredArgsConstructor
public class FileServiceImp implements FileService {
    private final UserRepository userRepository;

    @Override
    public void saveAsyncFile(UUID fileId) {
        List<UserDAO> dataLines = userRepository.findAll();
        File csvOutputFile = new File(String.format("files/%s.csv", fileId));
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {

            dataLines.forEach(userDAO -> pw.write(userDAO.getId() + "," + userDAO.getUserName() + "," + userDAO.isLoggedIn() + "\n"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    @Override
    public File getAsyncFile(UUID fileId) {
      return new File(String.format("files/%s.csv", fileId));
    }
}

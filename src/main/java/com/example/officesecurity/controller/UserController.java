package com.example.officesecurity.controller;


import com.example.officesecurity.csv.CSVUtils;
import com.example.officesecurity.csv.FileService;
import com.example.officesecurity.model.EventEntity;
import com.example.officesecurity.model.ResponseMessage;
import com.example.officesecurity.model.UserDAO;
import com.example.officesecurity.repository.TimeRepository;
import com.example.officesecurity.repository.UserRepository;
import com.example.officesecurity.service.UserService;
import com.example.officesecurity.service.impl.UserDTO;
import lombok.RequiredArgsConstructor;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController // This means that this class is a RestController
@RequiredArgsConstructor// This means to get the bean called userRepository
@RequestMapping(path = "/demo") // This means URL's start with /demo (after Application path)
public class UserController {

    // Which is auto-generated by Spring, we will use it to handle the data
    private final UserService userService;
    private final UserRepository userRepository;
    private final TimeRepository timeRepository;
    private final FileService fileService;
    //@Autowired
  //  public FileService fileService;


    @PostMapping(path = "/users/register")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody UserDTO newUser) {


        userService.addUser(newUser);
        return ResponseEntity.ok(ResponseMessage.builder().message("Good").build());
    }

    //!!!
    @PostMapping("/users/login")
    public ResponseEntity<ResponseMessage> loginUsers(@RequestBody UserDTO userDTO) {

        userService.loginUser(userDTO);

        return ResponseEntity.ok(ResponseMessage.builder().message("Good").build());
    }

    @PostMapping("/users/logout")
    public ResponseEntity<ResponseMessage> logUserOut(@RequestBody UserDTO userDTO) {

        userService.logoutUser(userDTO);

        return ResponseEntity.ok(ResponseMessage.builder().message("Good").build());
    }


    @GetMapping(path = "/all")
    public List<UserDAO> getAllUsers(@RequestParam Integer pageNo, Integer pageSize, String sortBy) {
        return userService.getAllUsersByPages(pageNo, pageSize, sortBy);

    }

    @GetMapping(path = "/time")
    public List<EventEntity> getTimeLogs() {
        return userService.getTimeLogs();
    }

    @GetMapping(path = "/getTimeLogs")
    public List<EventEntity> getLogsForSpecificTime(String start, String end) {

        return userService.getLogsForSpecificTime(start, end);

    }

    @GetMapping(path = "/all/remove")
    public ResponseEntity<ResponseMessage> removeAllUsers() {
        // This returns a JSON or XML with the users
        userRepository.deleteAll();
        timeRepository.deleteAll();
        return ResponseEntity.ok(ResponseMessage.builder().message("Data was deleted successfully!").build());
    }

    @GetMapping(path = "getCSVFile/userDAO.csv")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=usersDAO.csv");
        CSVUtils.downloadCsv(response.getWriter(), createTestData());
    }

    private List<UserDAO> createTestData() {
        List<UserDAO> userDAOList = userService.findAll();
        return userDAOList;
    }

    @GetMapping(path = "/saveFileSyncWithUUID")

    public ResponseEntity<InputStreamResource> downloadCsvWithUUID() throws IOException {
        List<UserDAO> dataLines = userRepository.findAll();
        UUID fileId = UUID.randomUUID();
        File csvOutputFile = new File(String.format("%s.csv", fileId));
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {

            dataLines.forEach(userDAO -> pw.write(userDAO.getId() + "," + userDAO.getUserName() + "," + userDAO.isLoggedIn() + "\n"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentDispositionFormData("attachment", csvOutputFile.getPath());

        InputStreamResource isr = new InputStreamResource(new FileInputStream(csvOutputFile));
        return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);
    }

    @GetMapping(path = "storeFileAsync")
    public ResponseEntity<String> creatAndStoreUUIDForAsyncFile() throws IOException {
        UUID fileId = UUID.randomUUID();
        fileService.saveAsyncFile(fileId);
        return ResponseEntity.ok(fileId.toString());
    }

    @GetMapping(path = "getFileAsync")
    public ResponseEntity<InputStreamResource> getAsyncFile(@RequestParam String fileId) throws IOException, ExecutionException, InterruptedException {
        File file = CompletableFuture.completedFuture(fileService.getAsyncFile(UUID.fromString(fileId))).get();

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentDispositionFormData("attachment", file.getPath());

        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);
    }


}






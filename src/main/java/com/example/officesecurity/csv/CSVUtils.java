package com.example.officesecurity.csv;

import com.example.officesecurity.model.UserDAO;
import com.example.officesecurity.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;



public class CSVUtils {

    public static void downloadCsv(PrintWriter writer, List<UserDAO> userDAOList) {
        writer.write("Employee ID, First Name, IsLoggedIn \n");
        for (UserDAO userDAO : userDAOList) {
            writer.write(userDAO.getId() + "," + userDAO.getUserName() + "," +userDAO.isLoggedIn() + "\n");
        }
    }

}
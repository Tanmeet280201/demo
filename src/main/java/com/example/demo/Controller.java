package com.example.demo;
import com.google.gson.Gson;
import kong.unirest.ContentType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {


    @Autowired
    private LicensePlateService licensePlateService;



    // Endpoint for the main page, using Thymeleaf template "index"
    @GetMapping("/")
    public String index(Model model) {
        List<LicensePlate> licensePlates = licensePlateService.getAllLicensePlates();
        model.addAttribute("licensePlates", licensePlates);
        return "index";
    }

    // RESTful endpoint to get all license plates
    @GetMapping("/licensePlates")
    @ResponseBody
    public ResponseEntity<List<LicensePlate>> getAllLicensePlates() {
        List<LicensePlate> licensePlates = licensePlateService.getAllLicensePlates();
        return ResponseEntity.ok(licensePlates);
    }



    // Endpoint to handle file upload
    @PostMapping("/uploadSnapshot")
    @ResponseBody
    public void uploadSnapshot(@RequestParam("snapshot") MultipartFile snapshotFile) {
        System.out.println("Received");

        // Check if the file is empty
        if (snapshotFile.isEmpty()) {
            System.out.println("Received empty file");
        }

        // Perform ANPR (Automatic Number Plate Recognition) on the file
        System.out.println(ANPR(snapshotFile));

    }



    // Method to call an external API for number plate recognition
    public String ANPR(MultipartFile snapshotFile) {
        String token = "0608227bf3aefdb91be63de043a0959eb28332ae"; // API token

        try {
            InputStream fileStream = snapshotFile.getInputStream();

            // Post request to the ANPR API
            HttpResponse<String> response = Unirest.post("https://api.platerecognizer.com/v1/plate-reader/")
                    .header("Authorization", "Token " + token)
                    .field("upload", fileStream, ContentType.IMAGE_JPEG, snapshotFile.getOriginalFilename())
                    .asString();

            String jsonResponse = response.getBody();

            // Parse the JSON response
            Gson gson = new Gson();
            ApiResponse apiResponse = gson.fromJson(jsonResponse, ApiResponse.class);

            // Extract the first license plate number
            String firstPlateNumber = apiResponse.getFirstPlateNumber();
            String sjofor = "Ukjent";

            // Assign driver names based on known license plates
            if ("beg00d".equals(firstPlateNumber)){
                sjofor = "Petter";
            } else if ("ez10120".equals(firstPlateNumber)){
                sjofor = "Marit";
            }

            // Save the license plate information
            licensePlateService.saveLicensePlate(firstPlateNumber, sjofor);

            return "redirect:/index";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error in sending file to API: " + e.getMessage();
        }
    }

}



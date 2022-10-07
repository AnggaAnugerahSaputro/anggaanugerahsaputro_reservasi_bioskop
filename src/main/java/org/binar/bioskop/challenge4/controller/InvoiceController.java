package org.binar.bioskop.challenge4.controller;

import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.binar.bioskop.challenge4.request.FileDataDB;
import org.binar.bioskop.challenge4.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Operation(summary = "this is to Generate File Pdf from Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "generate File Pdf into from Database User",
                    content = {@Content(mediaType="application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Request Not Avilable",
                    content = @Content)
    })
    @GetMapping("/downloadFile")
    public ResponseEntity<?> fileDownloadJasper(@RequestParam(value = "filename") String filename) throws IOException, DocumentException, JRException {
          try{
              FileDataDB fileDataDB = invoiceService.generateFileInvoice(filename);
              System.out.println(fileDataDB+"file ada");
              return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileDataDB.getFileType()))
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + filename + "\"")
              .body(new ByteArrayResource(fileDataDB.getData()));
          }catch(JRException e){
              System.out.println("file not found: "+e.getMessage());
              return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
          }

    }
}



package com.antra.evaluation.reporting_system.endpoint;

import com.antra.evaluation.reporting_system.pojo.api.PDFRequest;
import com.antra.evaluation.reporting_system.pojo.api.PDFResponse;
import com.antra.evaluation.reporting_system.pojo.report.PDFFile;
import com.antra.evaluation.reporting_system.service.PDFService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PDFGenerationController {

    private static final Logger log = LoggerFactory.getLogger(PDFGenerationController.class);
    private static final String DOWNLOAD_API_URI = "/pdf/{id}/content";

    private PDFService pdfService;

    @Autowired
    public PDFGenerationController(PDFService pdfService) {
        this.pdfService = pdfService;
    }

    private String generateFileDownloadLink(String fileId) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("localhost:8080").path(DOWNLOAD_API_URI) // localhost:8080 need to be externalized as parameter
                .buildAndExpand(fileId);
        return uriComponents.toUriString();
    }

    //get all existing pdf files
    @GetMapping("/pdf")
    @ApiOperation("List all existing files")
    public ResponseEntity<List<PDFResponse>> listPDFs() {
        log.debug("Got Request to List All Files");
        List<PDFFile> fileList = pdfService.getPDFList();
        var responseList = fileList.stream().map(file -> {
            PDFResponse response = new PDFResponse();
            BeanUtils.copyProperties(file, response);
            response.setFileLocation(this.generateFileDownloadLink(file.getId()));
            //response.setFileLocation(this.generateFileDownloadLink(file.getFileId()));
            return response;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }



    @PostMapping("/pdf")
    public ResponseEntity<PDFResponse> createPDF(@RequestBody @Validated PDFRequest request) {
        log.info("Got request to generate PDF: {}", request);

        PDFResponse response = new PDFResponse();
        PDFFile file = null;
        response.setReqId(request.getReqId());

        try {
            file = pdfService.createPDF(request);
            response.setFileId(file.getId());
            response.setFileLocation(file.getFileLocation());
            response.setFileSize(file.getFileSize());
            log.info("Generated: {}", file);
        } catch (Exception e) {
            response.setFailed(true);
            log.error("Error in generating pdf", e);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    log.debug("Got Request to Create Single Sheet Excel:{}", request);
//    ExcelFile fileInfo = excelService.generateFile(request, false);
//    ExcelResponse response = new ExcelResponse();
//        BeanUtils.copyProperties(fileInfo, response);
//        response.setFileDownloadLink(this.generateFileDownloadLink(fileInfo.getFileId()));
//        response.setFileLocation(fileInfo.getFileLocation());
//        response.setReqId(request.getReqId());
//        return new ResponseEntity<>(response, HttpStatus.OK);

}

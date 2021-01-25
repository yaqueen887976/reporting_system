package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.pojo.api.PDFRequest;
import com.antra.evaluation.reporting_system.pojo.report.PDFFile;

import java.util.List;

public interface PDFService {
    PDFFile createPDF(PDFRequest request);
    List<PDFFile> getPDFList();
}

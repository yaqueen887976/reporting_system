package com.antra.evaluation.reporting_system.repo;

import com.antra.evaluation.reporting_system.pojo.report.PDFFile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PDFRepository extends MongoRepository<PDFFile, String> {
    //List<PDFFile> getFiles();
}

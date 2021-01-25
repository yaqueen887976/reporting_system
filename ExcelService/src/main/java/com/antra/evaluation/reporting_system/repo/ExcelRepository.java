package com.antra.evaluation.reporting_system.repo;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExcelRepository extends MongoRepository<ExcelFile, String> {
//    Optional<ExcelFile> getFileById(String id);
//
//    ExcelFile saveFile(ExcelFile file);
//
//    ExcelFile deleteFile(String id);
//
//    List<ExcelFile> getFiles();

    Optional<ExcelFile> getExcelFileByFileId(String id);

    ExcelFile save(ExcelFile file);

    List<ExcelFile> findAll();

    ExcelFile deleteExcelFileByFileId(String id);



}

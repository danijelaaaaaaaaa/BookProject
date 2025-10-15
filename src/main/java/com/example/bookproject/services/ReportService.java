package com.example.bookproject.services;

import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
public class ReportService {

    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    public ReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Scheduled(fixedRate = 8 * 60 * 60 * 1000)
    public void scheduledBooksReport() {
        try {
            String file = generateBooksReport();
            logger.info("Scheduled Books Report generated successfully at {} → {}",
                    LocalDateTime.now(), file);
        } catch (Exception e) {
            logger.error("Failed to generate scheduled report", e);
        }
    }

    public String generateBooksReport() throws JRException, SQLException {
        String jrxmlPath = "src/main/resources/reports/books_report.jrxml";
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                new HashMap<>(),
                dataSource.getConnection()
        );

        String baseDir = System.getProperty("user.dir") + "/reports";
        File outputDir = new File(baseDir);
        if (!outputDir.exists() ) {
            logger.warn("Failed to create reports directory: {}", outputDir.getAbsolutePath());
            //outputDir.mkdirs();
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String outputPath = baseDir + "/books_report_" + timestamp + ".pdf";

        JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        logger.info("📄 Books report saved to {}", outputPath);
        return outputPath;
    }

    public byte[] generateBooksReportAsBytes() throws JRException, SQLException {
        String jrxmlPath = "src/main/resources/reports/books_report.jrxml";
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                new HashMap<>(),
                dataSource.getConnection()
        );
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}

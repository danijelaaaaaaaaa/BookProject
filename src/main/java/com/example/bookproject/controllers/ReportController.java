package com.example.bookproject.controllers;

import com.example.bookproject.services.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.sql.SQLException;

@Controller
@RequestMapping("/admin/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/books-report")
    public String generateBooksReport(Model model) throws JRException, SQLException {
        String filePath = reportService.generateBooksReport();
        model.addAttribute("successMessage", "Books report generated successfully!");
        model.addAttribute("filePath", filePath);
        return "admin/report-success";
    }

    @GetMapping("/books-report/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadBooksReport() throws JRException, SQLException {
        byte[] data = reportService.generateBooksReportAsBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }

    @GetMapping("/list")
    public String listReports(Model model) {
        String reportsDirPath = System.getProperty("user.dir") + "/reports";
        File reportsDir = new File(reportsDirPath);

        File[] reportFiles = reportsDir.listFiles((dir, name) -> name.endsWith(".pdf"));
        if (reportFiles != null) {
            model.addAttribute("reports", reportFiles);
        } else {
            model.addAttribute("reports", new File[0]);
        }

        return "admin/reports-list";
    }

    @PostMapping("/delete")
    public String deleteReport(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {
        String baseDir = System.getProperty("user.dir") + "/reports";
        File file = new File(baseDir, name);

        if (file.exists() && file.delete()) {
            redirectAttributes.addFlashAttribute("successMessage", "Deleted " + name + " successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete " + name + ".");
        }

        return "redirect:/admin/reports/list";
    }

}

package com.kookee.merchandiser_backend;

import com.kookee.merchandiser_backend.util.GoogleSheetsWriter;
import com.kookee.merchandiser_backend.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "https://stellular-beignet-f4ea84.netlify.app")
public class ReportController {

    private final ReportRepository reportRepository;
    private final GoogleSheetsWriter googleSheetsWriter;

    @Autowired
    public ReportController(ReportRepository reportRepository, GoogleSheetsWriter googleSheetsWriter) {
        this.reportRepository = reportRepository;
        this.googleSheetsWriter = googleSheetsWriter;
    }

    // Save new report
    @PostMapping
    public Report saveReport(@RequestBody Report report) {
        try {
            Report saved = reportRepository.save(report);

            // After saving, send to Google Sheets
            if (saved.getItems() != null && !saved.getItems().isEmpty()) {
                Map<String, Integer> itemsMap = saved.getItems().stream()
                    .collect(Collectors.toMap(
                        Item::getName,
                        Item::getQty
                    ));
                try {
                    googleSheetsWriter.appendReport(
                        saved.getMerchandiser(),
                        saved.getOutlet(),
                        saved.getDate().toString(),
                        itemsMap
                    );
                } catch (Exception e) {
                    System.err.println("Failed to write to Google Sheets: " + e.getMessage());
                }
            }

            return saved;
        } catch (Exception e) {
            System.err.println("Failed to save report: " + e.getMessage());
            throw e;
        }
    }

    // Get all reports
    @GetMapping
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
}
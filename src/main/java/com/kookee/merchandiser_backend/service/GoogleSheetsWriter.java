package com.kookee.merchandiser_backend.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.*;

@Service
public class GoogleSheetsWriter {

    public void appendReport(String merchandiser, String outlet, String date, Map<String, Integer> itemsMap) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URL url = new URL("https://sheet-api-production.up.railway.app/report");

            // ✅ Convert Map to List of { name, qty }
            List<Map<String, Object>> itemsList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : itemsMap.entrySet()) {
                Map<String, Object> itemObj = new HashMap<>();
                itemObj.put("name", entry.getKey());
                itemObj.put("qty", entry.getValue());
                itemsList.add(itemObj);
            }

            // ✅ Build Payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("merchandiser", merchandiser);
            payload.put("outlet", outlet);
            payload.put("date", date);
            payload.put("items", itemsList); // ✅ must be list, not map

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // ✅ Send POST request
            ResponseEntity<String> response = restTemplate.postForEntity(url.toString(), request, String.class);
            System.out.println("✅ Google Sheets response: " + response.getBody());

        } catch (Exception e) {
            System.err.println("❌ Failed to sync with Google Sheets: " + e.getMessage());
        }
    }
}

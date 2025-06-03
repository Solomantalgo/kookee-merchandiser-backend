package com.kookee.merchandiser_backend.util;

import com.google.api.client.http.*;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GoogleSheetsWriter {

    private static final String SPREADSHEET_ID = "1zYKVlNpSL6iwn0-uquG3a0JqwKnM4jk9K__m9XiUg5g";
    private static final String SHEET_NAME = "Sheet1";

    private final HttpRequestFactory requestFactory;

    public GoogleSheetsWriter(HttpRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    public void appendReport(String merchandiser, String outlet, String date, Map<String, Integer> items) {
        try {
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                List<List<Object>> values = Collections.singletonList(
                        Arrays.asList(merchandiser, outlet, date, entry.getKey(), entry.getValue())
                );
                Map<String, Object> data = new HashMap<>();
                data.put("values", values);

                GenericUrl url = new GenericUrl(
                        String.format(
                                "https://sheets.googleapis.com/v4/spreadsheets/%s/values/%s!A1:append?valueInputOption=USER_ENTERED",
                                SPREADSHEET_ID, SHEET_NAME
                        )
                );

                HttpContent content = new com.google.api.client.http.json.JsonHttpContent(
                        GsonFactory.getDefaultInstance(), data
                );

                HttpRequest request = requestFactory.buildPostRequest(url, content);
                HttpResponse response = request.execute();

                System.out.println("Response: " + response.parseAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
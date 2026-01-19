package com.example.ad_personalization_engine.ad_personalization_engine.service;

import com.example.ad_personalization_engine.ad_personalization_engine.dto.AudienceSegment;
import com.google.cloud.bigquery.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BigQueryService {

    private final BigQuery bigQuery;

    public BigQueryService() {
        // Uses GOOGLE_APPLICATION_CREDENTIALS automatically
        this.bigQuery = BigQueryOptions
                .getDefaultInstance()
                .getService();
    }

    public List<String> getAudienceSegments() {

        String query = """
            SELECT segment_id
            FROM `ad-personalization-engine.ad_data_us.audience_segments`
        """;

        QueryJobConfiguration config =
                QueryJobConfiguration.newBuilder(query).build();

        List<String> segments = new ArrayList<>();

        try {
            TableResult result = bigQuery.query(config);
            for (FieldValueList row : result.iterateAll()) {
                segments.add(row.get("segment_id").getStringValue());
            }
        } catch (Exception e) {
            throw new RuntimeException("BigQuery query failed", e);
        }

        return segments;
    }

    public List<AudienceSegment> getAudienceDetails() {
        String query = """
            SELECT segment_id, description
            FROM `ad-personalization-engine.ad_data_us.audience_segments`
        """;
        QueryJobConfiguration config =
                QueryJobConfiguration.newBuilder(query).build();
        List<AudienceSegment> segments = new ArrayList<>();

        try {
            TableResult result = bigQuery.query((config));
            for (FieldValueList row : result.iterateAll()) {
                segments.add(new AudienceSegment(
                        row.get("segment_id").getStringValue(),
                        row.get("description").isNull()
                        ? ""
                                : row.get("description").getStringValue()
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("BigQuery query failed", e);
        }

        return segments;
    }

    public String getAdIfExists(String product, String segmentId){
        String query = """
            SELECT ad_text
            FROM `ad-personalization-engine.ad_data_us.generated_ads`
            WHERE product = @product
            AND segment_id = @segment
            LIMIT 1;
        """;

        QueryJobConfiguration config =
                QueryJobConfiguration.newBuilder(query)
                        .addNamedParameter("product",
                                QueryParameterValue.string(product))
                        .addNamedParameter("segment",
                                QueryParameterValue.string(segmentId))
                        .build();

        try {
            TableResult result = bigQuery.query(config);
            for (FieldValueList row : result.iterateAll()) {
                return row.get("ad_text").getStringValue();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("BigQuery query failed", e);
        }
    }

    public void saveAd(String product, String segmentId, String adText){
        TableId tableId =
                TableId.of("ad-personalization-engine","ad_data_us","generated_ads");

        InsertAllRequest.RowToInsert row =
                InsertAllRequest.RowToInsert.of(
                        Map.of(
                                "product",product,
                                "segment_id",segmentId,
                                "ad_text",adText,
                                "created_at", Instant.now().toString()
                        )
                );
        InsertAllResponse response =
                bigQuery.insertAll(
                        InsertAllRequest.newBuilder(tableId)
                                .addRow(row)
                                .build()
                );

        if(response.hasErrors()){
            throw new RuntimeException("Error in inserting rows to InsertAllResponse");
        }
    }
}

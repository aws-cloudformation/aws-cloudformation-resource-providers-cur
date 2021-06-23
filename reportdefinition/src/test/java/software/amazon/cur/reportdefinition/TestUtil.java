package software.amazon.cur.reportdefinition;

import java.util.Collections;

import software.amazon.awssdk.services.costandusagereport.CostAndUsageReportClient;
import software.amazon.awssdk.services.costandusagereport.model.CompressionFormat;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.awssdk.services.costandusagereport.model.ReportFormat;
import software.amazon.awssdk.services.costandusagereport.model.ReportVersioning;
import software.amazon.awssdk.services.costandusagereport.model.TimeUnit;

public class TestUtil {
    public static final String TEST_REPORT_NAME = "testReportName";
    public static final String TEST_S3_BUCKET = "test-s3-bucket";
    public static final String TEST_S3_PREFIX = "test-s3-prefix";
    public static final String TEST_S3_REGION = "us-east-1";
    // Use a region other than the service region to test endpoint translation
    public static final String TEST_STACK_REGION = "us-west-2";

    public static final ReportDefinition TEST_REPORT_DEFINITION = ReportDefinition.builder()
        .reportName(TEST_REPORT_NAME)
        .s3Bucket(TEST_S3_BUCKET)
        .s3Prefix(TEST_S3_PREFIX)
        .s3Region(TEST_S3_REGION)
        .additionalArtifacts(Collections.emptyList())
        .additionalSchemaElements(Collections.emptyList())
        .compression(CompressionFormat.GZIP)
        .format(ReportFormat.TEXT_OR_CSV)
        .refreshClosedReports(true)
        .reportVersioning(ReportVersioning.CREATE_NEW_REPORT)
        .timeUnit(TimeUnit.HOURLY)
        .build();

    // This should match the properties of the ReportDefinition above
    public static final ResourceModel TEST_RESOURCE_MODEL = ResourceModel.builder()
        .reportName(TEST_REPORT_NAME)
        .s3Bucket(TEST_S3_BUCKET)
        .s3Prefix(TEST_S3_PREFIX)
        .s3Region(TEST_S3_REGION)
        .additionalArtifacts(Collections.emptyList())
        .additionalSchemaElements(Collections.emptyList())
        .compression(CompressionFormat.GZIP.toString())
        .format(ReportFormat.TEXT_OR_CSV.toString())
        .refreshClosedReports(true)
        .reportVersioning(ReportVersioning.CREATE_NEW_REPORT.toString())
        .timeUnit(TimeUnit.HOURLY.toString())
        .build();
}

package software.amazon.cur.reportdefinition;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.costandusagereport.model.CompressionFormat;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.awssdk.services.costandusagereport.model.ReportFormat;
import software.amazon.awssdk.services.costandusagereport.model.ReportVersioning;
import software.amazon.awssdk.services.costandusagereport.model.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
public class TranslatorTest {

    @Test
    public void toReportDefinition_setDefaults() {
        final ResourceModel resourceModel = ResourceModel.builder()
            .reportName(TestUtil.TEST_REPORT_NAME)
            .timeUnit(TimeUnit.HOURLY.toString())
            .format(ReportFormat.TEXT_OR_CSV.toString())
            .compression(CompressionFormat.GZIP.toString())
            .s3Bucket(TestUtil.TEST_S3_BUCKET)
            .s3Prefix(TestUtil.TEST_S3_PREFIX)
            .s3Region(TestUtil.TEST_S3_REGION)
            .refreshClosedReports(true)
            .reportVersioning(ReportVersioning.CREATE_NEW_REPORT.toString())
            .build();

        final ReportDefinition reportDefinition = Translator.toReportDefinition(resourceModel);

        // Check that the list fields default to an empty list if not specified
        assertThat(reportDefinition.additionalSchemaElements()).isEmpty();
        assertThat(reportDefinition.additionalArtifacts()).isEmpty();
    }
}

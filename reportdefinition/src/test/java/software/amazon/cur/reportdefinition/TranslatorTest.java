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
            .s3Bucket(TestUtil.TEST_S3_BUCKET)
            .s3Prefix(TestUtil.TEST_S3_PREFIX)
            .s3Region(TestUtil.TEST_S3_REGION)
            .build();

        final ReportDefinition reportDefinition = Translator.toReportDefinition(resourceModel);

        // All of the fields that are required are equal to what was passed in
        assertThat(reportDefinition.reportName()).isEqualTo(TestUtil.TEST_REPORT_NAME);
        assertThat(reportDefinition.s3Bucket()).isEqualTo(TestUtil.TEST_S3_BUCKET);
        assertThat(reportDefinition.s3Prefix()).isEqualTo(TestUtil.TEST_S3_PREFIX);
        assertThat(reportDefinition.s3RegionAsString()).isEqualTo(TestUtil.TEST_S3_REGION);

        // And that defaults are set for everything else
        assertThat(reportDefinition.timeUnit()).isEqualTo(TimeUnit.HOURLY);
        assertThat(reportDefinition.format()).isEqualTo(ReportFormat.TEXT_OR_CSV);
        assertThat(reportDefinition.compression()).isEqualTo(CompressionFormat.GZIP);
        assertThat(reportDefinition.additionalSchemaElements()).isEmpty();
        assertThat(reportDefinition.additionalArtifacts()).isEmpty();
        assertThat(reportDefinition.refreshClosedReports()).isTrue();
        assertThat(reportDefinition.reportVersioning()).isEqualTo(ReportVersioning.CREATE_NEW_REPORT);
    }
}

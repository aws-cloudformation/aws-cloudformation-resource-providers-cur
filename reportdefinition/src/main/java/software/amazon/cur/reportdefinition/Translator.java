package software.amazon.cur.reportdefinition;

import java.util.Collections;
import java.util.Optional;

import software.amazon.awssdk.services.costandusagereport.model.CompressionFormat;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsRequest;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.awssdk.services.costandusagereport.model.ReportFormat;
import software.amazon.awssdk.services.costandusagereport.model.ReportVersioning;
import software.amazon.awssdk.services.costandusagereport.model.TimeUnit;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

public class Translator {
    private static final ReportDefinition defaultReportDefinition = ReportDefinition.builder()
        .timeUnit(TimeUnit.HOURLY)
        .format(ReportFormat.TEXT_OR_CSV)
        .compression(CompressionFormat.GZIP)
        .additionalSchemaElementsWithStrings(Collections.emptyList())
        .additionalArtifactsWithStrings(Collections.emptyList())
        .refreshClosedReports(true)
        .reportVersioning(ReportVersioning.CREATE_NEW_REPORT)
        .build();

    static ResourceModel toResourceModel(final ReportDefinition reportDefinition) {
        return ResourceModel.builder()
            .additionalArtifacts(reportDefinition.additionalArtifactsAsStrings())
            .additionalSchemaElements(reportDefinition.additionalSchemaElementsAsStrings())
            .compression(reportDefinition.compressionAsString())
            .format(reportDefinition.formatAsString())
            .refreshClosedReports(reportDefinition.refreshClosedReports())
            .reportName(reportDefinition.reportName())
            .reportVersioning(reportDefinition.reportVersioningAsString())
            .s3Bucket(reportDefinition.s3Bucket())
            .s3Prefix(reportDefinition.s3Prefix())
            .s3Region(reportDefinition.s3RegionAsString())
            .timeUnit(reportDefinition.timeUnitAsString())
            .billingViewArn(reportDefinition.billingViewArn())
            .build();
    }

    static ReportDefinition toReportDefinition(final ResourceModel resourceModel, final ReportDefinition baseReportDefinition) {
        return ReportDefinition.builder()
            .reportName(resourceModel.getReportName())
            .timeUnit(Optional.ofNullable(resourceModel.getTimeUnit()).orElse(baseReportDefinition.timeUnitAsString()))
            .format(Optional.ofNullable(resourceModel.getFormat()).orElse(baseReportDefinition.formatAsString()))
            .compression(Optional.ofNullable(resourceModel.getCompression()).orElse(baseReportDefinition.compressionAsString()))
            .additionalSchemaElementsWithStrings(Optional.ofNullable(resourceModel.getAdditionalSchemaElements()).orElse(Collections.emptyList()))
            .s3Bucket(resourceModel.getS3Bucket())
            .s3Prefix(resourceModel.getS3Prefix())
            .s3Region(resourceModel.getS3Region())
            .additionalArtifactsWithStrings(Optional.ofNullable(resourceModel.getAdditionalArtifacts()).orElse(Collections.emptyList()))
            .refreshClosedReports(Optional.ofNullable(resourceModel.getRefreshClosedReports()).orElse(true))
            .reportVersioning(Optional.ofNullable(resourceModel.getReportVersioning()).orElse(ReportVersioning.CREATE_NEW_REPORT.toString()))
            .billingViewArn(resourceModel.getBillingViewArn())
            .build();
    }

    static ReportDefinition toReportDefinition(final ResourceModel resourceModel) {
        return toReportDefinition(resourceModel, defaultReportDefinition);
    }

    static DescribeReportDefinitionsRequest toDescribeReportDefinitionsRequest(final ResourceHandlerRequest<ResourceModel> request) {
        return DescribeReportDefinitionsRequest.builder()
            .nextToken(request.getNextToken())
            .build();
    }
}

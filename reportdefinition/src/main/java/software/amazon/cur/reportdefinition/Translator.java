package software.amazon.cur.reportdefinition;

import java.util.Collections;
import java.util.Optional;

import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsRequest;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

public class Translator {

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

    static ReportDefinition toReportDefinition(final ResourceModel resourceModel) {
        return ReportDefinition.builder()
            .reportName(resourceModel.getReportName())
            .timeUnit(resourceModel.getTimeUnit())
            .format(resourceModel.getFormat())
            .compression(resourceModel.getCompression())
            .additionalSchemaElementsWithStrings(Optional.ofNullable(resourceModel.getAdditionalSchemaElements()).orElse(Collections.emptyList()))
            .s3Bucket(resourceModel.getS3Bucket())
            .s3Prefix(resourceModel.getS3Prefix())
            .s3Region(resourceModel.getS3Region())
            .additionalArtifactsWithStrings(Optional.ofNullable(resourceModel.getAdditionalArtifacts()).orElse(Collections.emptyList()))
            .refreshClosedReports(resourceModel.getRefreshClosedReports())
            .reportVersioning(resourceModel.getReportVersioning())
            .billingViewArn(resourceModel.getBillingViewArn())
            .build();
    }

    static DescribeReportDefinitionsRequest toDescribeReportDefinitionsRequest(final ResourceHandlerRequest<ResourceModel> request) {
        return DescribeReportDefinitionsRequest.builder()
            .nextToken(request.getNextToken())
            .build();
    }
}

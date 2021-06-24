package software.amazon.cur.reportdefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.costandusagereport.CostAndUsageReportClient;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsResponse;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsRequest;
import software.amazon.cloudformation.exceptions.CfnNotFoundException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

public abstract class CurBaseHandler extends BaseHandler<CallbackContext> {
    private static final Map<String, Region> PARTITION_TO_SERVICE_REGION_MAP = ImmutableMap.of(
        Region.CN_NORTHWEST_1.metadata().partition().name(), Region.CN_NORTHWEST_1,
        Region.US_EAST_1.metadata().partition().name(), Region.US_EAST_1
    );

    protected final Map<String, CostAndUsageReportClient> clientMap = new HashMap<>();

    protected CostAndUsageReportClient getClient(ResourceHandlerRequest<ResourceModel> request) {
        return clientMap.computeIfAbsent(
            // awsPartition is a field of the ResourceHandlerRequest, but doesn't appear to be populated for contract tests currently
            // so we can get it via the region instead
            Region.of(request.getRegion()).metadata().partition().name(),
            partition -> CostAndUsageReportClient.builder()
                .region(PARTITION_TO_SERVICE_REGION_MAP.get(partition))
                .build()
        );
    }

    protected ReportDefinition getReport(String reportName, AmazonWebServicesClientProxy proxy, Logger logger, CostAndUsageReportClient curClient) {
        DescribeReportDefinitionsResponse describeReportDefinitionsResponse = proxy.injectCredentialsAndInvokeV2(
            DescribeReportDefinitionsRequest.builder().build(),
            curClient::describeReportDefinitions
        );

        List<ReportDefinition> reports = describeReportDefinitionsResponse.reportDefinitions().stream()
            .filter(reportDefinition -> reportDefinition.reportName().equals(reportName))
            .collect(Collectors.toList());

        if (reports.size() == 0) {
            throw new CfnNotFoundException(ResourceModel.TYPE_NAME, reportName);
        }

        if (reports.size() > 1) {
            logger.log(String.format("%d reports found with the same name", reports.size()));
        }

        return reports.get(0);
    }
}

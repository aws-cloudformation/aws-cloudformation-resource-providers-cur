package software.amazon.cur.reportdefinition;

import java.util.List;
import java.util.stream.Collectors;

import software.amazon.awssdk.services.costandusagereport.CostAndUsageReportClient;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsResponse;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsRequest;
import software.amazon.cloudformation.exceptions.CfnNotFoundException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;

public abstract class CurBaseHandler extends BaseHandler<CallbackContext> {
    protected final CostAndUsageReportClient curClient;

    public CurBaseHandler() {
        this.curClient = CostAndUsageReportClient.builder().build();
    }

    protected ReportDefinition getReport(String reportName, AmazonWebServicesClientProxy proxy, Logger logger) {
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

package software.amazon.cur.reportdefinition;

import java.util.List;
import java.util.stream.Collectors;

import software.amazon.awssdk.services.costandusagereport.CostAndUsageReportClient;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsResponse;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsRequest;
import software.amazon.cloudformation.exceptions.CfnNotFoundException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

public abstract class CurBaseHandler extends BaseHandler<CallbackContext> {
    protected final CostAndUsageReportClient curClient;

    public CurBaseHandler() {
        this.curClient = CostAndUsageReportClient.builder().build();
    }

    protected ReportDefinition getReport(String reportName, AmazonWebServicesClientProxy proxy) {
        DescribeReportDefinitionsResponse describeReportDefinitionsResponse = proxy.injectCredentialsAndInvokeV2(
            DescribeReportDefinitionsRequest.builder().build(),
            curClient::describeReportDefinitions
        );

        List<ReportDefinition> reports = describeReportDefinitionsResponse.reportDefinitions().stream()
            .filter(reportDefinition -> reportDefinition.reportName().equals(reportName))
            .collect(Collectors.toList());

        if (reports.size() != 1) {
            throw new CfnNotFoundException(ResourceModel.TYPE_NAME, reportName);
        }

        return reports.get(0);
    }
}

package software.amazon.cur.reportdefinition;

import software.amazon.awssdk.services.costandusagereport.CostAndUsageReportClient;
import software.amazon.awssdk.services.costandusagereport.model.CostAndUsageReportException;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

// https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-test-contract.html#resource-type-test-contract-read
public class ReadHandler extends CurBaseHandler {

    public ReadHandler() {
        super();
    }

    public ReadHandler(CostAndUsageReportClient client) {
        super(client);
    }
    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(
        final AmazonWebServicesClientProxy proxy,
        final ResourceHandlerRequest<ResourceModel> request,
        final CallbackContext callbackContext,
        final Logger logger) {

        String reportName = request.getDesiredResourceState().getReportName();

        try {
            ReportDefinition reportDefinition = getReport(reportName, proxy, logger);

            return ProgressEvent.<ResourceModel, CallbackContext>builder()
                .resourceModel(Translator.toResourceModel(reportDefinition))
                .status(OperationStatus.SUCCESS)
                .build();
        } catch (CostAndUsageReportException e) {
            throw ExceptionTranslator.toCfnException(e, reportName);
        }
    }
}

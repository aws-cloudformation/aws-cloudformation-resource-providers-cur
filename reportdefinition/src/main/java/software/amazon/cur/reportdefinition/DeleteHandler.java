package software.amazon.cur.reportdefinition;

import software.amazon.awssdk.services.costandusagereport.CostAndUsageReportClient;
import software.amazon.awssdk.services.costandusagereport.model.CostAndUsageReportException;
import software.amazon.awssdk.services.costandusagereport.model.DeleteReportDefinitionRequest;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

public class DeleteHandler extends CurBaseHandler {

    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(
        final AmazonWebServicesClientProxy proxy,
        final ResourceHandlerRequest<ResourceModel> request,
        final CallbackContext callbackContext,
        final Logger logger) {

        final ResourceModel model = request.getDesiredResourceState();
        final CostAndUsageReportClient curClient = getClient(request);

        // This will throw the exception needed if the report doesn't exist - we don't need the ReportDefinition it returns
        getReport(model.getReportName(), proxy, logger, curClient);

        try {
            proxy.injectCredentialsAndInvokeV2(
                DeleteReportDefinitionRequest.builder()
                    .reportName(model.getReportName())
                    .build(),
                curClient::deleteReportDefinition
            );

            return ProgressEvent.<ResourceModel, CallbackContext>builder()
                .status(OperationStatus.SUCCESS)
                .build();
        } catch (CostAndUsageReportException e) {
            throw ExceptionTranslator.toCfnException(e, model.getReportName());
        }
    }
}

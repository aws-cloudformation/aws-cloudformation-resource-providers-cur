package software.amazon.cur.reportdefinition;

import software.amazon.awssdk.services.costandusagereport.CostAndUsageReportClient;
import software.amazon.awssdk.services.costandusagereport.model.CostAndUsageReportException;
import software.amazon.awssdk.services.costandusagereport.model.ModifyReportDefinitionRequest;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

public class UpdateHandler extends CurBaseHandler {

    public UpdateHandler() {
        super();
    }

    public UpdateHandler(CostAndUsageReportClient client) {
        super(client);
    }

    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(final AmazonWebServicesClientProxy proxy,
            final ResourceHandlerRequest<ResourceModel> request, final CallbackContext callbackContext,
            final Logger logger) {

        final ResourceModel model = request.getDesiredResourceState();

        // This will throw the exception needed if the report doesn't exist - we don't need the ReportDefinition it returns
        getReport(model.getReportName(), proxy, logger);
        final ReportDefinition updatedReportDefinition = Translator.toReportDefinition(model);

        try {
            proxy.injectCredentialsAndInvokeV2(
                ModifyReportDefinitionRequest.builder()
                    .reportName(model.getReportName())
                    .reportDefinition(updatedReportDefinition)
                .build(),
                curClient::modifyReportDefinition
            );

            return ProgressEvent.<ResourceModel, CallbackContext>builder()
                .resourceModel(Translator.toResourceModel(updatedReportDefinition))
                .callbackContext(callbackContext)
                .status(OperationStatus.SUCCESS)
                .build();
        } catch (CostAndUsageReportException e) {
            throw ExceptionTranslator.toCfnException(e, model.getReportName());
        }
    }
}

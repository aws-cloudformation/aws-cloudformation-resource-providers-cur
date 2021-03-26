package software.amazon.cur.reportdefinition;

import software.amazon.awssdk.services.costandusagereport.model.CostAndUsageReportException;
import software.amazon.awssdk.services.costandusagereport.model.PutReportDefinitionRequest;
import software.amazon.awssdk.services.costandusagereport.model.ReportDefinition;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

public class CreateHandler extends CurBaseHandler {

    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(
        final AmazonWebServicesClientProxy proxy,
        final ResourceHandlerRequest<ResourceModel> request,
        final CallbackContext callbackContext,
        final Logger logger) {

        final ResourceModel model = request.getDesiredResourceState();
        final ReportDefinition reportDefinition = Translator.toReportDefinition(model);

        try {
            proxy.injectCredentialsAndInvokeV2(
                PutReportDefinitionRequest.builder()
                    .reportDefinition(reportDefinition)
                    .build(),
                curClient::putReportDefinition
            );

            return ProgressEvent.<ResourceModel, CallbackContext>builder()
                // Translate the translated ReportDefinition back to a ResourceModel so that the model returned
                // here includes any of the defaults not specified in the request's ResourceModel
                .resourceModel(Translator.toResourceModel(reportDefinition))
                .status(OperationStatus.SUCCESS)
                .build();
        } catch (CostAndUsageReportException e) {
            throw ExceptionTranslator.toCfnException(e, model.getReportName());
        }
    }
}

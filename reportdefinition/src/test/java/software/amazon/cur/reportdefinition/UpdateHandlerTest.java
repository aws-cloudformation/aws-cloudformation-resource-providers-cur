package software.amazon.cur.reportdefinition;

import software.amazon.awssdk.services.costandusagereport.model.CompressionFormat;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsRequest;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsResponse;
import software.amazon.awssdk.services.costandusagereport.model.ModifyReportDefinitionRequest;
import software.amazon.awssdk.services.costandusagereport.model.ModifyReportDefinitionResponse;
import software.amazon.awssdk.services.costandusagereport.model.ReportFormat;
import software.amazon.awssdk.services.costandusagereport.model.ReportVersioning;
import software.amazon.awssdk.services.costandusagereport.model.TimeUnit;
import software.amazon.cloudformation.exceptions.CfnNotFoundException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class UpdateHandlerTest {

    @Mock
    private AmazonWebServicesClientProxy proxy;

    @Mock
    private Logger logger;

    @Test
    void handleRequest_SimpleSuccess() {
        final UpdateHandler handler = new UpdateHandler();

        final ResourceModel model = ResourceModel.builder()
            .reportName(TestUtil.TEST_REPORT_NAME)
            .s3Bucket(TestUtil.TEST_S3_BUCKET)
            .s3Prefix(TestUtil.TEST_S3_PREFIX)
            .s3Region(TestUtil.TEST_S3_REGION)
            .additionalArtifacts(Collections.emptyList())
            .additionalSchemaElements(Collections.emptyList())
            .compression(CompressionFormat.ZIP.toString())
            .format(ReportFormat.TEXT_OR_CSV.toString())
            .refreshClosedReports(true)
            .reportVersioning(ReportVersioning.CREATE_NEW_REPORT.toString())
            .timeUnit(TimeUnit.HOURLY.toString())
            .build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doReturn(DescribeReportDefinitionsResponse.builder().reportDefinitions(TestUtil.TEST_REPORT_DEFINITION).build())
            .when(proxy)
            .injectCredentialsAndInvokeV2(any(DescribeReportDefinitionsRequest.class), any());

        doReturn(ModifyReportDefinitionResponse.builder().build())
            .when(proxy)
            .injectCredentialsAndInvokeV2(any(ModifyReportDefinitionRequest.class), any());

        final ProgressEvent<ResourceModel, CallbackContext> response = handler.handleRequest(proxy, request, null,
                logger);


        final ResourceModel expectedModel = Translator.toResourceModel(
            Translator.toReportDefinition(model)
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isEqualTo(expectedModel);
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }

    @Test
    void handleRequest_DoesNotExist() {
        final UpdateHandler handler = new UpdateHandler();

        final ResourceModel model = ResourceModel.builder().reportName("testReportName").build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
                .desiredResourceState(model).build();

        doReturn(DescribeReportDefinitionsResponse.builder().reportDefinitions(Collections.emptyList()).build())
                .when(proxy)
                .injectCredentialsAndInvokeV2(any(DescribeReportDefinitionsRequest.class), any());

        assertThrows(CfnNotFoundException.class, () -> handler.handleRequest(proxy, request, null, logger));
    }
}

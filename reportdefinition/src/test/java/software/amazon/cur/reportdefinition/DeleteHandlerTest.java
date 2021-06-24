package software.amazon.cur.reportdefinition;

import software.amazon.awssdk.services.costandusagereport.model.DeleteReportDefinitionRequest;
import software.amazon.awssdk.services.costandusagereport.model.DeleteReportDefinitionResponse;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsRequest;
import software.amazon.awssdk.services.costandusagereport.model.DescribeReportDefinitionsResponse;
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
public class DeleteHandlerTest {

    @Mock
    private AmazonWebServicesClientProxy proxy;

    @Mock
    private Logger logger;

    @Test
    void handleRequest_SimpleSuccess() {
        final DeleteHandler handler = new DeleteHandler();

        // The input to a delete handler MUST contain either the primaryIdentifier or an additionalIdentifier. Any other properties MAY NOT be included in the request.
        // https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-test-contract.html#resource-type-test-contract-delete
        final ResourceModel model = ResourceModel.builder()
            .reportName(TestUtil.TEST_REPORT_NAME)
            .build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .region(TestUtil.TEST_STACK_REGION)
            .desiredResourceState(model)
            .build();

        doReturn(DescribeReportDefinitionsResponse.builder().reportDefinitions(TestUtil.TEST_REPORT_DEFINITION).build())
            .when(proxy)
            .injectCredentialsAndInvokeV2(any(DescribeReportDefinitionsRequest.class), any());

        doReturn(DeleteReportDefinitionResponse.builder().build())
            .when(proxy)
            .injectCredentialsAndInvokeV2(any(DeleteReportDefinitionRequest.class), any());

        final ProgressEvent<ResourceModel, CallbackContext> response = handler.handleRequest(proxy, request, null,
                logger);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isNull();
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }

    @Test
    void handleRequest_DoesNotExist() {
        final DeleteHandler handler = new DeleteHandler();

        final ResourceModel model = ResourceModel.builder()
            .reportName(TestUtil.TEST_REPORT_NAME)
            .build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .region(TestUtil.TEST_STACK_REGION)
            .desiredResourceState(model)
            .build();

        doReturn(DescribeReportDefinitionsResponse.builder().reportDefinitions(Collections.emptyList()).build())
                .when(proxy).injectCredentialsAndInvokeV2(any(DescribeReportDefinitionsRequest.class), any());

        assertThrows(CfnNotFoundException.class, () -> handler.handleRequest(proxy, request, null, logger));
    }
}

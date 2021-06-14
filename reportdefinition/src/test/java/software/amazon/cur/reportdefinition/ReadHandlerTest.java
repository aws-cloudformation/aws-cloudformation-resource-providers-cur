package software.amazon.cur.reportdefinition;

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
public class ReadHandlerTest {

    @Mock
    private AmazonWebServicesClientProxy proxy;

    @Mock
    private Logger logger;

    @Test
    void handleRequest_SimpleSuccess() {
        final ReadHandler handler = new ReadHandler(TestUtil.TEST_CLIENT);

        // The input to a read handler MUST contain either the primaryIdentifier or an additionalIdentifier. Any other properties MAY NOT be included in the request.
        // https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-test-contract.html#resource-type-test-contract-read
        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(ResourceModel.builder()
                .reportName(TestUtil.TEST_REPORT_NAME)
                .build()
            ).build();

        doReturn(DescribeReportDefinitionsResponse.builder().reportDefinitions(TestUtil.TEST_REPORT_DEFINITION).build())
            .when(proxy)
            .injectCredentialsAndInvokeV2(any(), any());

        final ProgressEvent<ResourceModel, CallbackContext> response
            = handler.handleRequest(proxy, request, null, logger);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isEqualTo(TestUtil.TEST_RESOURCE_MODEL);
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }

    @Test
    void handleRequest_DoesNotExist() {
        final ReadHandler handler = new ReadHandler(TestUtil.TEST_CLIENT);

        final ResourceModel model = ResourceModel.builder()
            .reportName(TestUtil.TEST_REPORT_NAME)
            .build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doReturn(DescribeReportDefinitionsResponse.builder().reportDefinitions(Collections.emptyList()).build())
            .when(proxy)
            .injectCredentialsAndInvokeV2(any(), any());

        assertThrows(CfnNotFoundException.class, () -> handler.handleRequest(proxy, request, null, logger));
    }
}

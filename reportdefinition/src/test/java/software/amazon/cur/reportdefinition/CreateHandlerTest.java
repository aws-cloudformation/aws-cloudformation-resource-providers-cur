package software.amazon.cur.reportdefinition;

import software.amazon.awssdk.services.costandusagereport.model.DuplicateReportNameException;
import software.amazon.awssdk.services.costandusagereport.model.PutReportDefinitionResponse;
import software.amazon.awssdk.services.costandusagereport.model.ReportLimitReachedException;
import software.amazon.cloudformation.exceptions.CfnAlreadyExistsException;
import software.amazon.cloudformation.exceptions.CfnServiceLimitExceededException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CreateHandlerTest {

    @Mock
    private AmazonWebServicesClientProxy proxy;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setup() {
        proxy = mock(AmazonWebServicesClientProxy.class);
        logger = mock(Logger.class);
    }

    @Test
    public void handleRequest_SimpleSuccess() {
        final CreateHandler handler = new CreateHandler();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(TestUtil.TEST_RESOURCE_MODEL)
            .build();

        final PutReportDefinitionResponse mockResponse = PutReportDefinitionResponse.builder().build();

        doReturn(mockResponse)
            .when(proxy).injectCredentialsAndInvokeV2(any(), any());

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
    public void handleRequest_DuplicateReportName() {
        final CreateHandler handler = new CreateHandler();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(TestUtil.TEST_RESOURCE_MODEL)
            .build();

        doThrow(DuplicateReportNameException.builder().build())
            .when(proxy).injectCredentialsAndInvokeV2(any(), any());

        assertThrows(CfnAlreadyExistsException.class, () -> handler.handleRequest(proxy, request, null, logger));
    }

    @Test
    public void handleRequest_ReportLimitReached() {
        final CreateHandler handler = new CreateHandler();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(TestUtil.TEST_RESOURCE_MODEL)
            .build();

        doThrow(ReportLimitReachedException.builder().build())
            .when(proxy).injectCredentialsAndInvokeV2(any(), any());

        assertThrows(CfnServiceLimitExceededException.class, () -> handler.handleRequest(proxy, request, null, logger));
    }
}

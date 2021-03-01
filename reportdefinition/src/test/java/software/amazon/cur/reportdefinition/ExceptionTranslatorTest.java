package software.amazon.cur.reportdefinition;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.costandusagereport.model.CostAndUsageReportException;
import software.amazon.awssdk.services.costandusagereport.model.DuplicateReportNameException;
import software.amazon.awssdk.services.costandusagereport.model.InternalErrorException;
import software.amazon.awssdk.services.costandusagereport.model.ReportLimitReachedException;
import software.amazon.awssdk.services.costandusagereport.model.ValidationException;
import software.amazon.cloudformation.exceptions.BaseHandlerException;
import software.amazon.cloudformation.exceptions.CfnAlreadyExistsException;
import software.amazon.cloudformation.exceptions.CfnGeneralServiceException;
import software.amazon.cloudformation.exceptions.CfnInternalFailureException;
import software.amazon.cloudformation.exceptions.CfnInvalidRequestException;
import software.amazon.cloudformation.exceptions.CfnServiceLimitExceededException;

public class ExceptionTranslatorTest {
    @Test
    public void toCfnException_DuplicateReportNameException() {
        BaseHandlerException translatedException = ExceptionTranslator.toCfnException(DuplicateReportNameException.builder().build(), TestUtil.TEST_REPORT_NAME);

        assertTrue(translatedException instanceof CfnAlreadyExistsException);
    }

    @Test
    public void toCfnException_ReportLimitReachedException() {
        BaseHandlerException translatedException = ExceptionTranslator.toCfnException(ReportLimitReachedException.builder().build(), TestUtil.TEST_REPORT_NAME);

        assertTrue(translatedException instanceof CfnServiceLimitExceededException);
    }

    @Test
    public void toCfnException_ValidationException() {
        BaseHandlerException translatedException = ExceptionTranslator.toCfnException(ValidationException.builder().build(), TestUtil.TEST_REPORT_NAME);

        assertTrue(translatedException instanceof CfnInvalidRequestException);
    }

    @Test
    public void toCfnException_InternalErrorException() {
        BaseHandlerException translatedException = ExceptionTranslator.toCfnException(InternalErrorException.builder().build(), TestUtil.TEST_REPORT_NAME);

        assertTrue(translatedException instanceof CfnInternalFailureException);
    }

    @Test
    public void toCfnException_GenericException() {
        BaseHandlerException translatedException = ExceptionTranslator.toCfnException(
                (CostAndUsageReportException) CostAndUsageReportException.builder().build(), TestUtil.TEST_REPORT_NAME);

        assertTrue(translatedException instanceof CfnGeneralServiceException);
    }
}

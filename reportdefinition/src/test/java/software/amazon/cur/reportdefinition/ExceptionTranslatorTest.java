package software.amazon.cur.reportdefinition;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @ParameterizedTest
    @MethodSource("exceptionProvider")
    void toCfnException(CostAndUsageReportException exception, Class<? extends BaseHandlerException> translatedExceptionType) {
        BaseHandlerException translatedException = ExceptionTranslator.toCfnException(exception, TestUtil.TEST_REPORT_NAME);
        assertTrue(translatedException.getClass().equals(translatedExceptionType));
    }

    static Stream<Arguments> exceptionProvider() {
        return Stream.of(
            Arguments.of(DuplicateReportNameException.builder().build(), CfnAlreadyExistsException.class),
            Arguments.of(ReportLimitReachedException.builder().build(), CfnServiceLimitExceededException.class),
            Arguments.of(ValidationException.builder().build(), CfnInvalidRequestException.class),
            Arguments.of(InternalErrorException.builder().build(), CfnInternalFailureException.class),
            Arguments.of(CostAndUsageReportException.builder().build(), CfnGeneralServiceException.class)
        );
    }
}

package software.amazon.cur.reportdefinition;

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

public class ExceptionTranslator {
    public static BaseHandlerException toCfnException(final CostAndUsageReportException e, String reportName) {
        if (e instanceof DuplicateReportNameException) {
            return new CfnAlreadyExistsException(ResourceModel.TYPE_NAME, reportName);
        } else if (e instanceof ReportLimitReachedException) {
            return new CfnServiceLimitExceededException(ResourceModel.TYPE_NAME, e.getMessage());
        } else if (e instanceof ValidationException) {
            return new CfnInvalidRequestException(e);
        } else if (e instanceof InternalErrorException) {
            return new CfnInternalFailureException(e);
        }

        return new CfnGeneralServiceException(e);
    }
}

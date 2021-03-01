package software.amazon.cur.reportdefinition;

import software.amazon.cloudformation.proxy.StdCallbackContext;

/*
    CallbackContext is used when a handler returns IN_PROGRESS as the status. The next time that the
    handler is run, it can use the CallbackContext passed in from the previous execution to alter
    its behavior. Since the CUR API is very simple and all of the handlers can be implemented in one
    or two API calls that don't need to stabilize, there's no need to use this class currently.
    https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-test-progressevent.html#progressevent-properties-CallbackContext
*/

@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.EqualsAndHashCode(callSuper = true)
public class CallbackContext extends StdCallbackContext {
}

# AWS::CUR::ReportDefinition

The RPDK will automatically generate the correct resource model from the schema whenever the project is built via Maven. You can also do this manually with the following command: `cfn generate`.

> Please don't modify files under `target/generated-sources/rpdk`, as they will be automatically overwritten.

The code uses [Lombok](https://projectlombok.org/) and [you may have to install IDE integrations](https://projectlombok.org/setup/overview) to enable auto-complete for Lombok-annotated classes.

## Testing
### Unit tests
This project's unit tests are a suite of JUnit tests located in the `src/test` directory and can be run using whatever method you prefer.
### SAM Tests
To test changes to the handlers without needing to deploy them to Lambda, you can run them locally using [SAM](https://aws.amazon.com/serverless/sam/).

To do so, first create a new file in the `sam-tests` directory. This file should contain JSON like the following:

```ts
{
    // These are the credentials that will be used by the local Lambda instance.
    "credentials": {
        "accessKeyId": string,
        "secretAccessKey": string,
        "sessionToken": string
    },
    "action": "CREATE" | "READ" | "UPDATE" | "DELETE" | "LIST",
    "request": {
        "clientRequestToken": string, // Any UUID
        "desiredResourceState": ResourceModel,
        "logicalResourceIdentifier": string
        ... // Any other fields of ResourceHandlerRequest that you want to populate for the simulated requst
    },
    "callbackContext": CallbackContext | null
}
```

Before you run a test, you'll need to make sure that the credentials in the test file are active and valid.

To run the test, you can invoke the handler on the command line using

```
sam local invoke TestEntrypoint --event sam-tests/<filename>
```
### Contract Tests
To ensure that the handlers fulfill the [resource type handler contract](https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-test-contract.html) requirements, you should run the full test suite after making any changes to the code.

> Due to the requirements of the ReportDefinition resource type, the contract tests are run using manually specified input instead of randomly generated input. These files can be found in the `inputs` directory and follow the pattern described in [this documentation](https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-test.html#resource-type-test-inputs). Additionally, because the S3 bucket that is used in the report definition must be owned by the same account that owns the report, you'll need to modify the test files to use valid values for `S3Bucket` and `S3Region`. See [here](https://docs.aws.amazon.com/cur/latest/userguide/cur-s3.html) for more details on how to setup such a bucket.

To run the test, first run

```
sam local start-lambda
```

in the root directory to initialize a local Lambda instance (you may need to run `mvn package` first to include the latests changes in the JAR used by SAM) and then run

```
cfn test
```

to run the full contract test suite (this will use whatever credentials you have setup for the AWS CLI). Running all of the tests can take a few minutes, so if you want to focus on only a single test you can do so by running

```
cfn test -- -k <test_name>
```

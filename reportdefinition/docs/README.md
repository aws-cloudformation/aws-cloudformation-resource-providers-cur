# AWS::CUR::ReportDefinition

The AWS::CUR::ReportDefinition resource creates a Cost & Usage Report with user-defined settings. You can use this resource to define settings like time granularity (hourly, daily, monthly), file format (Parquet, CSV), and S3 bucket for delivery of these reports.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "AWS::CUR::ReportDefinition",
    "Properties" : {
        "<a href="#reportname" title="ReportName">ReportName</a>" : <i>String</i>,
        "<a href="#timeunit" title="TimeUnit">TimeUnit</a>" : <i>String</i>,
        "<a href="#format" title="Format">Format</a>" : <i>String</i>,
        "<a href="#compression" title="Compression">Compression</a>" : <i>String</i>,
        "<a href="#additionalschemaelements" title="AdditionalSchemaElements">AdditionalSchemaElements</a>" : <i>[ String, ... ]</i>,
        "<a href="#s3bucket" title="S3Bucket">S3Bucket</a>" : <i>String</i>,
        "<a href="#s3prefix" title="S3Prefix">S3Prefix</a>" : <i>String</i>,
        "<a href="#s3region" title="S3Region">S3Region</a>" : <i>String</i>,
        "<a href="#additionalartifacts" title="AdditionalArtifacts">AdditionalArtifacts</a>" : <i>[ String, ... ]</i>,
        "<a href="#refreshclosedreports" title="RefreshClosedReports">RefreshClosedReports</a>" : <i>Boolean</i>,
        "<a href="#reportversioning" title="ReportVersioning">ReportVersioning</a>" : <i>String</i>,
        "<a href="#billingviewarn" title="BillingViewArn">BillingViewArn</a>" : <i>String</i>
    }
}
</pre>

### YAML

<pre>
Type: AWS::CUR::ReportDefinition
Properties:
    <a href="#reportname" title="ReportName">ReportName</a>: <i>String</i>
    <a href="#timeunit" title="TimeUnit">TimeUnit</a>: <i>String</i>
    <a href="#format" title="Format">Format</a>: <i>String</i>
    <a href="#compression" title="Compression">Compression</a>: <i>String</i>
    <a href="#additionalschemaelements" title="AdditionalSchemaElements">AdditionalSchemaElements</a>: <i>
      - String</i>
    <a href="#s3bucket" title="S3Bucket">S3Bucket</a>: <i>String</i>
    <a href="#s3prefix" title="S3Prefix">S3Prefix</a>: <i>String</i>
    <a href="#s3region" title="S3Region">S3Region</a>: <i>String</i>
    <a href="#additionalartifacts" title="AdditionalArtifacts">AdditionalArtifacts</a>: <i>
      - String</i>
    <a href="#refreshclosedreports" title="RefreshClosedReports">RefreshClosedReports</a>: <i>Boolean</i>
    <a href="#reportversioning" title="ReportVersioning">ReportVersioning</a>: <i>String</i>
    <a href="#billingviewarn" title="BillingViewArn">BillingViewArn</a>: <i>String</i>
</pre>

## Properties

#### ReportName

The name of the report that you want to create. The name must be unique, is case sensitive, and can't include spaces.

_Required_: Yes

_Type_: String

_Minimum_: <code>1</code>

_Maximum_: <code>256</code>

_Pattern_: <code>[0-9A-Za-z!\-_.*\'()]+</code>

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### TimeUnit

The granularity of the line items in the report.

_Required_: No

_Type_: String

_Allowed Values_: <code>HOURLY</code> | <code>DAILY</code> | <code>MONTHLY</code>

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### Format

The format that AWS saves the report in.

_Required_: No

_Type_: String

_Allowed Values_: <code>textORcsv</code> | <code>Parquet</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Compression

The compression format that AWS uses for the report.

_Required_: No

_Type_: String

_Allowed Values_: <code>ZIP</code> | <code>GZIP</code> | <code>Parquet</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### AdditionalSchemaElements

A list of strings that indicate additional content that Amazon Web Services includes in the report, such as individual resource IDs.

_Required_: No

_Type_: List of String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### S3Bucket

The S3 bucket where AWS delivers the report.

_Required_: Yes

_Type_: String

_Minimum_: <code>1</code>

_Maximum_: <code>256</code>

_Pattern_: <code>[A-Za-z0-9_\.\-]+</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### S3Prefix

The prefix that AWS adds to the report name when AWS delivers the report. Your prefix can't include spaces.

_Required_: Yes

_Type_: String

_Minimum_: <code>1</code>

_Maximum_: <code>256</code>

_Pattern_: <code>[0-9A-Za-z!\-_.*\'()/]*</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### S3Region

The region of the S3 bucket that AWS delivers the report into.

_Required_: Yes

_Type_: String

_Allowed Values_: <code>af-south-1</code> | <code>ap-east-1</code> | <code>ap-south-1</code> | <code>ap-southeast-1</code> | <code>ap-southeast-2</code> | <code>ap-northeast-1</code> | <code>ap-northeast-2</code> | <code>ap-northeast-3</code> | <code>ca-central-1</code> | <code>eu-centrail-1</code> | <code>eu-west-1</code> | <code>eu-west-2</code> | <code>eu-west-3</code> | <code>eu-north-1</code> | <code>eu-south-1</code> | <code>me-south-1</code> | <code>sa-east-1</code> | <code>us-east-1</code> | <code>us-east-2</code> | <code>us-west-1</code> | <code>us-west-2</code> | <code>cn-north-1</code> | <code>cn-northwest-1</code>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### AdditionalArtifacts

A list of manifests that you want Amazon Web Services to create for this report.

_Required_: No

_Type_: List of String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### RefreshClosedReports

Whether you want Amazon Web Services to update your reports after they have been finalized if Amazon Web Services detects charges related to previous months. These charges can include refunds, credits, or support fees.

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### ReportVersioning

Whether you want Amazon Web Services to overwrite the previous version of each report or to deliver the report in addition to the previous versions.

_Required_: No

_Type_: String

_Allowed Values_: <code>CREATE_NEW_REPORT</code> | <code>OVERWRITE_REPORT</code>

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### BillingViewArn

The Amazon resource name of the billing view. You can get this value by using the billing view service public APIs.

_Required_: No

_Type_: String

_Minimum_: <code>1</code>

_Maximum_: <code>128</code>

_Pattern_: <code>(arn:aws(-cn)?:billing::[0-9]{12}:billingview/)?[a-zA-Z0-9_\+=\.\-@].{1,30}</code>

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

## Return Values

### Ref

When you pass the logical ID of this resource to the intrinsic `Ref` function, Ref returns the ReportName.

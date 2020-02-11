#!/usr/bin/env bash

template_path="sample-infra-staging-cfn.yml"
#template_path="sample-infra-dev-cfn.yml"
output_template="sample-infra-staging-package-cfn.yml"
#output_template="sample-infra-dev-package-cfn.yml"
s3_bucket="debugroom-mynavi-sample-cloudformation-package"

aws cloudformation package --template-file ${template_path} --s3-bucket ${s3_bucket} --output-template-file ${output_template}

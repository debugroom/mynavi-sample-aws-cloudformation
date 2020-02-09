#!/usr/bin/env bash

#stack_name="mynavi-sample-infra-staging"
stack_name="mynavi-sample-infra-dev"
#stack_name="mynavi-sample-ecs-redisclient"
#stack_name="mynavi-sample-ecs-cluster"
#stack_name="mynavi-sample-sqs"
#stack_name="mynavi-sample-s3"
#stack_name="mynavi-sample-elasticache"
#stack_name="mynavi-sample-dynamodb"
#stack_name="mynavi-sample-rds"
#stack_name="mynavi-sample-tg-frontend-webapp"
#stack_name="mynavi-sample-tg-backend-serviceA"
#stack_name="mynavi-sample-tg-backend-serviceB"
#stack_name="mynavi-sample-alb"
#stack_name="mynavi-sample-ng"
#stack_name="mynavi-sample-sg"
#stack_name="mynavi-sample-vpc"
#template_path="sample-ecs-task-cfn.yml"
#template_path="sample-infra-staging-package-cfn.yml"
template_path="sample-infra-dev-package-cfn.yml"
#template_path="sample-ecs-redisclient-cfn.yml"
#template_path="sample-ecs-cluster-cfn.yml"
#template_path="sample-sqs-cfn.yml"
#template_path="sample-s3-cfn.yml"
#template_path="sample-elasticache-cfn.yml"
#template_path="sample-dynamodb-cfn.yml"
#template_path="sample-rds-cfn.yml"
#template_path="sample-tg-cfn.yml"
#template_path="sample-alb-cfn.yml"
#template_path="sample-ng-cfn.yml"
#template_path="sample-sg-cfn.yml"
#template_path="sample-vpc-cfn.yml"
parameters="EnvType=Dev"
#parameters="EnvType=Staging"
#parameters="SubnetType=Frontend EnvType=Dev ServiceName=FrontendWebApp"
#parameters="SubnetType=Backend EnvType=Dev ServiceName=BackendServiceA"
#parameters="SubnetType=Backend EnvType=Dev ServiceName=BackendServiceB"
#parameters="VPCCiderBlock=172.200.0.0/16"

#aws cloudformation create-stack --stack-name ${stack_name} --template-body file://${template_path} --capabilities CAPABILITY_IAM
# It is better cloudformation deploy option because command can execute even if stack existing(no need to delete existing stack).

if [ "$parameters" == "" ]; then
    aws cloudformation deploy --stack-name ${stack_name} --template-file ${template_path} --capabilities CAPABILITY_IAM
else
    aws cloudformation deploy --stack-name ${stack_name} --template-file ${template_path} --parameter-overrides ${parameters} --capabilities CAPABILITY_IAM
fi

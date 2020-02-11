#!/usr/bin/env bash

#stack_name="mynavi-sample-infra-staging"
#stack_name="mynavi-sample-infra-dev"
stack_name="mynavi-sample-ecs-service"
#stack_name="mynavi-sample-ecs-taskrole-frontend"
#stack_name="mynavi-sample-ecs-taskrole-backend"
#stack_name="mynavi-sample-ecs-task"
#stack_name="mynavi-sample-ecs-cluster"
#stack_name="mynavi-sample-sqs"
#stack_name="mynavi-sample-s3"
#stack_name="mynavi-sample-elasticache"
#stack_name="mynavi-sample-dynamodb"
#stack_name="mynavi-sample-rds"
#stack_name="mynavi-sample-tg-frontend-webapp"
#stack_name="mynavi-sample-tg-backend-userservice"
#stack_name="mynavi-sample-tg-backend-sampleservice"
#stack_name="mynavi-sample-alb"
#stack_name="mynavi-sample-ng"
#stack_name="mynavi-sample-sg"
#stack_name="mynavi-sample-vpc"

aws cloudformation delete-stack --stack-name ${stack_name}
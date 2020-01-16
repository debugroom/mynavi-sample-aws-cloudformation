#!/usr/bin/env bash

#stack_name="mynavi-sample-tg-frontend-webapp"
#stack_name="mynavi-sample-tg-backend-serviceA"
#stack_name="mynavi-sample-tg-backend-serviceB"
stack_name="mynavi-sample-alb"
#stack_name="mynavi-sample-ng"
#stack_name="mynavi-sample-sg"
#stack_name="mynavi-sample-vpc"

aws cloudformation delete-stack --stack-name ${stack_name}
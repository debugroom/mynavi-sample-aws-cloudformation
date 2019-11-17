#!/usr/bin/env bash

stack_name="mynavi-sample-vpc"

aws cloudformation delete-stack --stack-name ${stack_name}
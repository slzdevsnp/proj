#!/bin/bash

gcloud compute ssh  --zone=us-west2-a  \
      --ssh-flag="-D 1080" --ssh-flag="-N" --ssh-flag="-n" \
      ch6cluster-m
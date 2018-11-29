#!/bin/bash
gcloud sql instances patch fflights \
    --authorized-networks `wget -qO - http://ipecho.net/plain`/32

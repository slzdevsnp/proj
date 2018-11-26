#!/bin/sh

gcloud sql instances set-root-password flights --password $1

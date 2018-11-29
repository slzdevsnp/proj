
#!/bin/bash
gcloud sql instances create fflights \
    --tier=db-n1-standard-1 --activation-policy=ALWAYS --gce-zone=europe-west2-a

echo "Please go to the GCP console and change the root password of the instance"

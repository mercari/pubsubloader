#! /bin/sh

set -eu

for job in ProtobufTimestampLoadTesting
do
    sbt "pubsubloader/runMain com.mercari.data.bench.${job} \
        --runner=DataflowRunner \
        --enableStreamingEngine \
        --project=${GCP_PROJECT} \
        --stagingLocation=gs://${GCS_BUCKET}/staging/${job} \
        --templateLocation=gs://${GCS_BUCKET}/template/${job} \
    ";

    gsutil cp pubsubloader/metadata/PubsubLoadTesting_metadata gs://${GCS_BUCKET}/template/${job}_metadata
done

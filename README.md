# A Cloud Pub/Sub load generator with random data

Cloud Dataflow based Cloud Pub/Sub load generator and integration tester with custom data.
NOTE: It's for mercari internal usage

## How to use

### Write your test scenario

- Implement a load testing class with extending `PubsubLoadTester`
- You can generate randomized data by using scalacheck

  - You can see an example, `ProtobufTimestampLoadTesting`

### Build and publish Cloud Dataflow template job

publish a new Dataflow template job

```
$ GCP_PROJECT=<your_gcp_project_id> GCS_BUCKET=<your_gcs_bucket> ./bin/apply.sh
```

### Execute the job

- You can start the job on Dataflow WebUI, REST API or cli
- see also https://cloud.google.com/dataflow/docs/guides/templates/executing-templates?hl=en

## Requirements

- sbt
- gsutil

## Known issues

- The PubsubLoadTesting is unstable if it generates 20000+ rps

## Contributing

Please read the CLA carefully before submitting your contribution to Mercari.
Under any circumstances, by submitting your contribution, you are deemed to accept and agree to be bound by the terms and conditions of the CLA.

https://www.mercari.com/cla/

## License

Copyright 2019 Mercari, Inc.

Apache License 2.0

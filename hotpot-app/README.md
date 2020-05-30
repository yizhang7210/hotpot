# hotpot-app

This is a demo Spring Boot application for the [hotpot](https://github.com/yizhang7210/hotpot) framework.

## Dependencies
- `hotpot-core`: the core Hotpot framework
- `hotpot-staticdata`: for file (`yaml`) based service, metric, and objective definitions

## Implementations of extension points

#### Services
- Using `hotpot-staticdata` to provide service identity and static meta-data
- Additionally created `ServiceDynamicMetaDataProvider` for more dynamic service meta-data, taking higher precedence
- Implemented `ServiceTransformer` in `SimpleServiceTransformer`

#### Objectives
- Using `hotpot-staticdata` to provide static objectives
- Implemented `ServiceObjectiveTransformer` in `SimpleServiceObjectiveTransformer`
- Implemented `ServiceObjectiveResultTransformer` in `SimpleServiceObjectiveResultTransformer`

#### Metrics
- Using `hotpot-staticdata` to provide static metrics
- Created `ReleaseDataProvider` and `DocumentationDataProvider` to provide release and documentation data respectively
- Implemented `ServiceMetricTransformer` in `SimpleServiceMetricTransformer`
- Implemented `ServiceMetricValueTransformer` in `SimpleServiceMetricValueTransformer`


## Other information
- All custom implementation of `Transformer` interfaces return custom DTO objects
- Full configurations can be found in `application.yaml`

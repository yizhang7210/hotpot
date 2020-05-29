# Documentation
Hotpot is designed to be a framework on top of Spring Boot that makes it easy to pull different types of information
together from multiple places, so you can have a **holistic view** over your microservices.

It has a very **structured** view of a microservices world, expressed over its strong domain model. Meanwhile, it also
exposes a number of well defined **extension points** for the an application to implement.

The most basic usage of hotpot simply allows you to gather your service metadata and serve them up in one place.
More advanced features can be individually toggled on/off via configuration options. 

## Configuration options
Here is the full list of the configuration options to be specified in `application.yaml` or equivalent:
```yaml
hotpot:
  web-api:
    enabled: <enabled>    # default: true. Web API endpoints will be exposed.
    base-url: <base-url>  # default: "". Web API endpoints will be exposed under the <base-url> sub-path
  metrics:
    enabled: <enabled>    # default: false. Enable gathering and surfacing service level metrics and objectives.
```

## Architecture
Hotpot broadly follows a Domain Driven Design.

On a high level, data are first fetched via `Provider` interfaces. They then pass through some `UseCase` classes, which
perform the processing logic, sometimes with the help of some utility classes. After being transformed by `Transformer`
interfaces, data are eventually served on the web API from the `Controller` classes.

The extension points are primarily the `Provider` and the `Transformer` interfaces.

## Extension points
For each feature enabled in Hotpot, there are a number of required and optional interfaces the application can
implement to customize its behaviour. They are as follows:

#### Mandatory
- `ServiceIdentityProvider` (exactly 1): to provide all the service identifiers
- `ServiceMetaDataProvider` (1 or more): to provide service metadata given a service identifier

#### Optional
- `ServiceTransformer` (0 or 1): to transform a `Service` object to a data transfer object (DTO) for the API response,
default to the identity function

#### Mandatory with `hotpot.metrics.enabled=true`
- `ServiceMetricProvider` (exactly 1): to provide all the service metrics
- `ServiceObjectiveProvider` (exactly 1): to provide all the service objectives
- `ServiceDataProvider` (1 or more): to provide the data for each metric defined in the ServiceMetricProvider

#### Optional with `hotpot.metrics.enabled=true`
- `ServiceObjectiveTransformer` (0 or 1): to transform a ServiceObjective object to a DTO for the API response, default
to the identity function
- `ServiceObjectiveResultTransformer` (0 or 1): to transform a ServiceObjectiveResult object to a DTO for the API
response, default to the identity function
- `ServiceMetricTransformer` (0 or 1): to transform a ServiceMetric object to a DTO for the API response, default to
the identity function
- `ServiceMetricValueTransformer` (0 or 1): to transform a ServiceMetricValue object to a DTO for the API response,
default to the identity function

## Web API endpoints
All endpoints are located under the `hotpot.web-api.base-url` sub-path.

#### Defaults
- GET `/serviceIds`: Return a list of defined service IDs
- GET `/services`: Return a list of defined services
- GET `/services/{serviceId}`: Return the details of a single service with given `serviceId`

#### With `hotpot.metrics.enabled=true`
- GET `/objectives`: Return a list of defined service objectives
- GET `/objectives/{objectiveId}`: Return the details of a single service objective with given `objectiveId`
- GET `/objectivesResults?q=objectiveId=oId,serviceId=sId`: Return the service objective results filtered by
`objectiveId` and/or `serviceId`
- GET `/metrics`: Return a list of defined service metrics
- GET `/metrics/{metricId}`: Return the details of a single service metric with given `metricId`
- GET `/metricValues?q=metricId=mId,serviceId=sId`: Return the service metric values filtered by `metricId` and/or
`serviceId`


# Hotpot

Hotpot is a framework on top of `Spring Boot` for building a central console of **Microservices**.

It allows you to quickly build a web app that brings together your services, their statuses, and metrics.

## Main concepts
- `Service`: an individual deployable **artifact** in your microservices universe, e.g. `user-service`
- `Service Metric`: a **measure** of some aspect of a `Service`, for example:
    ```yaml
    - average-releases: average number of releases for the past 30 days
    - percentage-rollback: percentage of rollbacks for the past 30 days
    - doc-location: url of the service's web API documentation
    ```
- `Service Objective`: a **goal**, **rule**, or **guideline** that you want a `Service` to satisfy,
expressed in terms of one or more `Service Metrics`, for example:
    ```yaml
    - high-quality-release: average-releases > 1 AND percentage-rollback < 20%
    - well-documented: doc-location exists
    ```

## Quick start
1. Set up a Spring Boot application, perhaps using [Spring Initializr](https://start.spring.io/), or
[gradle init](https://guides.gradle.org/building-spring-boot-2-projects-with-gradle/).
1. Import `hotpot-core` as a dependency. If you're using gradle:
    ```groovy
       repositories {
           jcenter()
       }
       dependencies {
           implementation 'com.hotpot:hotpot-core:0.0.10'
       }
    ```
1. Add the following configuration to your `application.yaml` or equivalent:
    ```yaml
    hotpot:
      web-api:
        enabled: <enabled>    # default: true. Web API endpoints will be exposed.
        base-url: <base-url>  # default: "". Web API endpoints will be exposed under the <base-url> sub-path
      metrics:
        enabled: <enabled>    # default: false. Enable gathering and surfacing service level metrics and objectives.
    ```
1. Create (and wire) beans that implement the following interfaces:
    ```yaml
    - ServiceIdentityProvider (exactly 1): to provide all the service identifiers
    - ServiceMetaDataProvider (1 or more): to provide service metadata given a service identifier
    ```
1. To enable service metrics, set `hotpot.metrics.enabled` to true, and create beans that implement the following
interfaces:
    ```yaml
    - ServiceMetricProvider (exactly 1): to provide all the service metrics
    - ServiceObjectiveProvider (exactly 1): to provide all the service objectives
    - ServiceDataProvider (1 or more): to provide the actual data for each metric defined in the ServiceMetricProvider
    ```
1. Start up your spring boot application.
1. `curl localhost:8080/<base-ur>/serviceIds` will give you the IDs of all your services.
1. With metrics enabled, `curl localhost:8080/<base-ur>/metrics/values/{serviceId}` will give you all the current
metrics for the service `serviceId`.

## Libraries
Popular libraries that make running a Hotpot app even easier include:
- [hotpot-lib-file](./hotpot-lib-file): Support file (`yaml`) based service, metric, and objective definitions

## More Information
- [Full documentation](./hotpot-core/docs/API.md)
- [An example Hotpot app (backend)](./hotpot-app)
- [An example Hotpot app (frontend)](./hotpot-app-frontend)
- [Contributing guidelines](./CONTRIBUTING.md)

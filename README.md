# Hotpot

Hotpot is a framework for building a control plane and/or service catalogue
in a world of `microservices`.

Hotpot offers a set of strong data model and business logic around the domain
objects that are central to a `microservices` setup, as well as a set of interfaces
that serve as extension points for sourcing and manipulating these objects in their
respective systems.

These functionalities are then served via a default set of web APIs.

An example Hotpot app can be found at the [hotpot-app](./hotpot-app) folder,
and its frontend at [hotpot-front-end](./hotpot-app-frontend).


## Getting started
1. Start with a spring boot application
1. Import the hotpot-starter dependency in your maven/gradle
1. Add the following configuration to your `application.yaml` or equivalent
    ```yaml
    hotpot:
      web-api:
        enabled: <enabled>    # default: true. Web API endpoints will be exposed.
        base-url: <base-url>  # default: "". Web API endpoints will be exposed under the <base-url> subpath

      slo:
        enabled: <enabled>    # default: false. Enable exposing service level objectives.
    ```
1. Create beans that implement the following interfaces:
    ```yaml
    - ServiceIdentityProvider: to provide all the service identifiers
    - ServiceMetaDataProvider (can be multiple): to provide metadata given a service identifier
    - ServiceTransformer (optional): to transform a service object to a data transfer object (DTO) for the API response
    ```
1. If `hotpot.slo.enabled` is true, then create bean that implement the following interfaces:
    ```yaml
    - ServiceObjective (can be multiple): to define a service level objective
    - ServiceObjectiveProvider: to provide all the service level objectives
    - ServiceObjectiveTransformer (optional): to transform a service objective object to a DTO for the API response
    ```
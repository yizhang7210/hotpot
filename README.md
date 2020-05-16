# Hotpot

Hotpot is a framework for building a control plane and/or service catalogue
in a world of `microservices`.

Hotpot offers a set of strong data model and business logic around the domain
objects that are central to a `microservices` setup, as well as a set of interfaces
that serve as extension points for sourcing and manipulating these objects in their
respective systems.

These functionalities are then served via a default set of web APIs.

An example Hotpot app see: https://github.com/yizhang7210/hotpotapp


## Getting started
1. Start with a spring boot application
1. Import the hotpot-starter dependency in your maven/gradle
1. Add the following configuration to your `application.yaml` or equivalent
    ```$yaml
    hotpot:
      web-api:
        enabled: <enabled>    # default: true. Web API endpoints will be exposed.
        base-url: <base-url>  # default: "". Web API endpoints will be exposed at the subpath of <base-url>
    ```
1. Implement the following interfaces:
    ```
    - ServiceIdentityProvider: to provide all the service identifiers
    - ServiceMetaDataProvider: to provide metadata given a service identifier
    - ServiceTransformer (optional): to transform a service object to a data transfer object for the API response
    ```
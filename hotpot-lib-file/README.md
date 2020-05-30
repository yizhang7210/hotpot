# hotpot-lib-file

This is a library for the `hotpot-core` framework. It supports file (currently `yaml`) based service, metric, and
objective definitions.

### Usage
1. Include the following section in your `build.gradle`
    ```groovy
    repositories {
        jcenter()
    }
    dependencies {
        implementation "com.hotpot:hotpot-lib-file:${version}"
    }
    ```

1. Add the following section in your `application.yaml` or equivalent
    ```yaml
    hotpot.static-data:
      enabled: true                           # Required: enable the static-data library
      type: yaml                              # Required: type of file the static data are in
      metrics-location: <metrics.yaml>        # Optional: location of the metrics definition file, under `resources`
      objectives-location: <objectives.yaml>  # Optional: location of the objectives definition file, under `resources`
      services-location: <services.yaml>      # Optional: location of the services definition file, under `resources`
      metadata-precedence: <precedence>       # Required with `services-location`. Precedence of this metadata provider
    ```
    See [hotpot-core](https://github.com/yizhang7210/hotpot/blob/master/README.md) for more info on `precedence`.

1. Write your service, metric, and objective definition files in the above locations according to the schema:

    **For Services**
    ```yaml
    tiers:  # Both name and description are required.
      - name: <tier-name>
        description: <description>
      - name: ...
    services:  # Only the id field is required.
      - id: <service-id>
        tier: <tier-name>  # as defined above
        owner: <owner of this service>
        channel: <owner team's communication channel>
        repository:
          name: <name of this service's code repository>
          location: <location of this service's code repository>
        selfLocation: <where is this service deployed at>
        metricsLocation: <where is this service's metrics dashboard>
        docsLocation: <where is this service's documentation>
        logsLocation: <where is this service's logs>
      - id: ...
    ```

    **For Metrics**
    ```yaml
    - id: <metric-id>  # All fields are required.
      type: <metric data type>  # Only support 'Integer', 'Double', 'Boolean', and 'String'.
      timeSpan: <measuring period of this metric>  # Use ISO8610 duration format, e.g. 'PT4H'. Use 'PT0S' for instant.
      description: <what this metric is about>
    - id: ...
    ```

    **For Objectives**
    ```yaml
    - id: <objective-id>  # Required.
      description: <description>  # Required.
      include:  # This optional section specifies services this objective applies to. All services included by default.
        - field: <field of a service to filter for>  # only support 'id' and 'tier'
          values: <list of values to filter for>  # e.g. [1, 2, 3]
        - field: ...  # Services satisfying `any` of these filters will be included.
      exclude:  # This optional section filters out services for this objective. Applied `after` the `include` section.
        - field: <field of a service that this objective applies to>  # only support 'id' and 'tier'
          values: <list of values to filter out>  # e.g. ['service-abc']
        - field: ...  # Services satisfying `any` of these filters will be excluded.
      criteria:  # This required section defines the success conditions for this objective in terms of metrics.
        - metricId: <metric-id>  # a metric id defined in the metrics file
          description: <description>  # Spell out this criteria in plain words
          transform: <transform>  # Optional. Only support 'length' for metric of type String. 
          condition: <condition>  # In the form '<operator>/<threshold>', e.g. 'gt/1.3'. Only support gt, lt, eq.
        - metricId: ...
    - id: ...
    ```

1. Start up your Spring Boot app and make a request to the `/services` endpoint. You should see your services.

For complete examples, see the demo app [hotpot-app](https://github.com/yizhang7210/hotpot/tree/master/hotpot-app)
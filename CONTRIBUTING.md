# Contributing

All types of contribution are **welcome**, including but not limited to: coding, testing, UI/UX design, bug reporting,
documentation writing, spreading the word, feature requests, monetary sponsoring, and moral support.


## Architecture
This repository hosts multiple different projects. They are:
- `hotpot-core`: The core framework of hotpot that everything else is based on
- `hotpot-lib-file`: A Hotpot library that supports file (`yaml`) based service, metric, and objective definitions
- `hotpot-app`: An example service catalogue app using `hotpot-core`. It demonstrates `hotpot-core`'s capabilities
- `hotpot-app-frontend`: The corresponding single page app frontend. Displays the information on a web site.

### Project setup
1. Make sure you have java 12 or above
1. `./gradlew test` from the project root will run the tests for all the java based projects
1. `./gradlew bootRun` will start the demo app `hotpot-app`
1. Under `hotpot-app-frontend`, do `yarn install` and `yarn run serve` will start a server at localhost:3000

## Process
1. Clone/fork this repo
1. Make changes locally
1. Open a pull requests and let's go from there

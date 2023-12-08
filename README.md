# camunda-bpm-worker



[![incubating](https://img.shields.io/badge/lifecycle-INCUBATING-orange.svg)](https://github.com/holisticon#open-source-lifecycle)
[![Build Status](https://github.com/holunda-io/camunda-bpm-worker/workflows/Development%20branches/badge.svg)](https://github.com/holunda-io/camunda-bpm-worker/actions)
[![sponsored](https://img.shields.io/badge/sponsoredBy-Holisticon-RED.svg)](https://holisticon.de/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.holunda.camunda.worker/camunda-bpm-worker/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.holunda.camunda.worker/camunda-bpm-worker)

## Motivation

Business applications shoould be more or less technology independent, or at least the separation of the business logic from the technical integration code should be provided. Clean architecture promotes this separation. Port and adapter architecture (also sometimes called hexagonal) and onion architecture styles combined together allows to focus on pure business functionality inside of the domain code, focus on main behavioural elements (use cases) in the application layer and finally perform the entire integration inside the infrastructure layer, separated into the driving (primary) and driven (secondary) adapter parts.

The application of the above mentioned principles in a process application has a major improvments to the readability and maintainability of the application. As applying it in several projects, it turned out that event the adapter layer (both dring and driven) used for integration with a process engine can be implemented in a reusable and verndor-independent way. This hides small differences of the API of different engines and allows for creation of business applications in which the choice of the engine can be done and reverted easily.

## Purpose

This library provides an example implmentation of a vendor-independent workflow engine abstractions allowing to execute service and user activites. In addition it provides some sample implementations (currently for the Camunda Platform 7 and Camunda Platform 8).

## Ideas

Main ideas were collected in a time span from October 2022 (CamundaCon 2022) till May 2023 (Camunda SUmmit 2023).



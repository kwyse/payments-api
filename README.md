# Payments API

| Service | Status |
| --- | --- |
| Travis CI | [![Build Status](https://travis-ci.org/kwyse/payments-api.svg?branch=master)](https://travis-ci.org/kwyse/payments-api) |
| Codecov | [![codecov](https://codecov.io/gh/kwyse/payments-api/branch/master/graph/badge.svg)](https://codecov.io/gh/kwyse/payments-api) |
| Codebeat | [![codebeat badge](https://codebeat.co/badges/826c7327-613d-49c9-9837-38766606d3f8)](https://codebeat.co/projects/github-com-kwyse-payments-api-master) |

## Getting Started

```bash
$ gradle build
```

## Design

![Original API design](original_api_design.png)

## Assumptions

### Payment updates supply the full Payment object

Ideally, payments updates could be more granular, but this is dependent on
business logic. For example, it may be the case that the `payment_purpose` will
be frequently updated, so the API should be build around supplying just this
one field to a `PATCH` handler.

### Nested resources aren't required

Currently there is only one resource, `/payments`, but multiple persisted
entities, like `Charges` and `Party`. These could also be exposed as resources,
either as new root resources (`/charges` and `/parties`) or nested under
`/payments` (`/payments/{paymentId}/charges` and `/payments/{paymentId}/parties`).
The API can be easily extended to support either of these approaches.

### Resources will only be queried with their IDs

Query parameters could be added to support more complex querying and filtering,
but each supported parameter will need a corresponding database index. Fields
with indexes have an overhead when persisting them, so they should be limited
to fields that actually need to be queried in this manner. Only entity IDs have
been implemented to fit this criteria, but the API can be extended to support
more.
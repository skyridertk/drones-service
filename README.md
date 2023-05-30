## Drones

---

### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:

- serial number (100 characters max);

- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);

- weight limit (500gr max);

- battery capacity (percentage);

- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 

- name (allowed only letters, numbers, ‘-‘, ‘_’);

- weight;

- code (allowed only upper case letters, underscore and numbers);

- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. dispatch controller). The specific communication with the drone is outside the scope of this task. 

The service should allow:

- registering a drone;

- loading a drone with medication items;

- checking loaded medication items for a given drone; 

- checking available drones for loading;

- check drone battery level for a given drone;

>  Feel free to make assumptions for the design approach. 

---

### Requirements

While implementing your solution **please take care of the following requirements**: 

#### Functional requirements

-  There is no need for UI;

- Prevent the drone from being loaded with more weight that it can carry;

- Prevent the drone from being in LOADING state if the battery level is **below 25%**;

- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;

- Your project must be buildable and runnable;

- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);

- Required data must be preloaded in the database.

- JUnit tests are optional but advisable (if you have time);

- Advice: Show us how you work through your commit history.

---

#### Tools Used
- Java 17
- IntelliJ IDEA
- Insomnia REST
- Postgres 13


### Get Started
1. Create Postgres Database locally
```postgres
create database drones_db
```

2. Configure database credentials
``` postgres
dbuser: postgres
dbpass: 6914

```
4. Clone this repo
5. Create build in the current folder
```bash
mvnw clean install
```

4. Startup Spring
```bash
mvn spring-boot:run
```

Success. Application is now available on

> http://localhost:8081/api


## Available routes
- POST /drones/register
```curl
curl --request POST \
  --url http://localhost:8081/api/drones/register \
  --header 'Content-Type: application/json' \
  --data '{
	"serialNumber": "DRN0013",
	"model": "Lightweight",
	"weightLimit": 300,
	"batteryCapacity": 100.0,
	"state": "IDLE"
}'
```
- POST /drones/load
```curl
curl --request POST \
  --url http://localhost:8081/api/drones/load \
  --header 'Content-Type: application/json' \
  --data '{
	"serialNumber": "DRN0011",
	"medicationCode": "HYDROCO"
}'
```
- GET /drones/check-load
```curl
curl --request GET --url 'http://localhost:8081/api/drones/check-load?serial=DRN0011'
```
- GET /drones/check-available
```curl
curl --request GET --url http://localhost:8081/api/drones/check-available
```
- GET /drones/battery
```curl
curl --request GET --url 'http://localhost:8081/api/drones/battery?serial=DRN0011'
```

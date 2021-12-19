### Brief

We’d like you to create a feature flag service using Spring in Java.

### Tasks

Here are the requirements for this service:

- As an admin user, I want to be able to create a feature which defaults to disabled
- As an admin user, I want to be able to switch on a feature for a user
- As a user, I want to be able to get all the enabled features (a mix of all the globally enabled ones and the ones enabled just for my user)

**********************************************************************************
**********************************************************************************


### Mehmet Aktas's Assignment Solution

### Pre-Requirement
- Java 11
- Postgres 13
   - Start the Postgres server and create a database with any name, username and password
      - Then assign those parameters in to application.yml, by replacing {DBNAME}, {USERNAME}, {PASSWORD};
        ```yaml
        datasource:
        platform: postgres
        url: jdbc:postgresql://localhost:5432/{DBNAME}
        username: {USERNAME}
        password: {PASSWORD}
        ```

### Postman Collections

All api operations are being added as Postman collection format to /postman.
you can import and start using them with predefined request payloads.


### API start

Start the application by running "bootRun"

Serve will start running on http://localhost:8080/

When application starts, it creates default admin user


```json
{

	"logonName" : "admin@yopmail.com",
    "password": "simple"

}
```
"status" field can be one of them  "active", "inactive"

## Admin Operations 

Below apis are only allowed to Admin role 


### Admin Login

Path: login
Method: POST

Request Model :

```json
{

	"logonName" : "admin@yopmail.com",
    "password": "simple"

}
```

Response Model :

```json

{

  "username": "admin@yopmail.com",
  "authorities": [
    {
      "authority": "ADMIN"
    }
  ],
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true,
  "enabled": true,
  "id": 1,
  "firstName": "admin first name",
  "lastName": "last name",
  "role": "ADMIN"

}
```


### Create User

Path: /api/v1/admin/users/
Method: POST

Request Model :

```json
{
  
  "firstName" : "Mehmet",
  "lastName" : "Ak",
  "role" : "user",
  "email" : "mm@yopmail.com",
  "password" : "simple",
  "status": "active"
  
}

```
"role" field can be one of them  "admin", "user", "any"

Response Model :

```json
{
  "id": 1558,
  "firstName": "Mehmet",
  "lastName": "Ak",
  "mobileNumber": null,
  "email": "mm@yopmail.com",
  "status": "ACTIVE",
  "createdAt": "2021-12-19T00:01:29.878966",
  "updatedAt": "2021-12-19T00:01:29.878966"
}
```

### Create Feature

Path: /api/v1/admin/features/
Method: POST

Request Model :

```json
{

  "name" : "payment",
  "status": "active",
  "accessType": "custom",
  "description": "my description"

}

```
- "accessType" field can be one of them  "global", "restricted", "custom"
  
  global: any user can access these features
  restricted: only admin user can access these
  custom: only specific users can access these


Response Model :

```json
{
  "id": 1202,
  "name": "payment",
  "description": "my description",
  "accessType": "CUSTOM",
  "status": "ACTIVE",
  "createdAt": "2021-12-19T14:22:42.764642",
  "updatedAt": "2021-12-19T14:22:42.764642"
}
```


### Create Permission

Path: /api/v1/admin/permissions/
Method: POST

Request Model :

```json
{

  "name" : "permission for user: {{userId}} for feature: {{featureId}}",
  "userId": "{{userId}}",
  "featureId": "{{featureId}}",
  "description":"test"

}

```

Response Model :

```json
{
  "id": 1602,
  "name": "permission for user: 153 for feature: 1202",
  "featureId": 1202,
  "userId": 153,
  "description": "test",
  "status": "ACTIVE",
  "createdAt": "2021-12-19T14:27:32.760485",
  "updatedAt": "2021-12-19T14:27:32.760485"
}
```

### Delete Permission

Path: /api/v1/admin/permissions/{{permissionId}}
Method: DELETE

Response Model :

Status.OK


### Retrieve All Feature List

Path: /api/v1/admin/features
Method: GET

Response Model :


```json
[
  
    {
      "id": 556,
      "name": "payment",
      "description": "my description",
      "accessType": "CUSTOM",
      "status": "ACTIVE",
      "createdAt": "2021-12-18T22:18:44.680368",
      "updatedAt": "2021-12-18T22:18:44.680368"
    },
    {
      "id": 555,
      "name": "payment",
      "description": "my description",
      "accessType": "CUSTOM",
      "status": "ACTIVE",
      "createdAt": "2021-12-18T22:18:33.532819",
      "updatedAt": "2021-12-18T22:18:33.532819"
    }
  ]
```


### Retrieve All Permission List

Path: /api/v1/admin/permissions
Method: GET

Response Model :


```json
[
  {
    "id": 1604,
    "name": "permission for user: 1653 for feature: 1203",
    "featureId": 1203,
    "userId": 1653,
    "description": "test",
    "status": "ACTIVE",
    "createdAt": "2021-12-19T14:32:18.808216",
    "updatedAt": "2021-12-19T14:32:18.808216"
  },
  {
    "id": 1602,
    "name": "permission for user: 153 for feature: 1202",
    "featureId": 1202,
    "userId": 153,
    "description": "test",
    "status": "ACTIVE",
    "createdAt": "2021-12-19T14:27:32.760485",
    "updatedAt": "2021-12-19T14:27:32.760485"
  }
]

```

## User Operations

Below apis are allowed to Admin and User roles

### User Login

Path: login
Method: POST

Request Model :

```json
{

	"logonName" : "mm@yopmail.com",
    "password": "simple"

}
```

Response Model :

```json

{

  "username": "mm@yopmail.com",
  "authorities": [
    {
      "authority": "USER"
    }
  ],
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true,
  "enabled": true,
  "id": 1,
  "firstName": "admin first name",
  "lastName": "last name",
  "role": "USER"

}
```

### Retrieve User Assigned Feature List

Path: /api/v1/users/{{userId}}/features
Method: GET

Response Model :


```json
[
  {
    "id": 1204,
    "name": "payment global",
    "description": "my description",
    "accessType": "GLOBAL",
    "status": "ACTIVE",
    "createdAt": "2021-12-19T14:46:43.060949",
    "updatedAt": "2021-12-19T14:46:43.060949"
  },
  {
    "id": 1203,
    "name": "payment",
    "description": "my description",
    "accessType": "CUSTOM",
    "status": "ACTIVE",
    "createdAt": "2021-12-19T14:31:36.578772",
    "updatedAt": "2021-12-19T14:31:36.578772"
  }
]

```
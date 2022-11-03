# Time Master REST API

[TOC]

## Endpoints

### /api

#### GET

Returns `200 OK` and a message containing status code and current time.  
Used for connection verification with client, and for testing purposes.

### /api/test

#### GET

Returns `200 OK` and a message containing `Hello, World!`.  
Used for testing purposes.

### /api/employees

#### GET

*Request:* No request data needed

*Response:*  
Returns `200 OK` and a json object containing the list of employees stored on the server

Example data:

```json
[
  {
    "id": "e0e676de-39ce-43e5-a6b5-bf8e9fb2f51a",
    "name": "Alice",
    "workdays": []
  },
  {
    "id": "13252b63-f074-490f-b239-39dc0d5e193c",
    "name": "Bob",
    "workdays": []
  }
]
```

#### POST

*Request:*  
Takes in a json object of an employee and appends it to the list stored on the server.

Example data:

```json
{
  "id": "703a215b-a282-4fad-a5d1-ceccb46dd37e",
  "name": "Charlie",
  "workdays": []
}
```

*Response:*  
Returns `201 Created` and a message containing the employee id.

Example response message:

```text/plain
Created employee with id:"703a215b-a282-4fad-a5d1-ceccb46dd37e"
```

### /api/employees/:id

#### GET

*Request:* No request data needed.

*Response:*  
Returns `200 OK` and a json object containing an employee.  
Returns `404 Not Found` if an employee with requested id does not exist.

Example of `200 OK` response message:

```json
{
  "id": "13252b63-f074-490f-b239-39dc0d5e193c",
  "name": "Bob",
  "workdays": []
}
```

#### PUT

*Request:*  
Takes in a json object containing an employee, and updates the matching employee on the server.

Example request data:

```json
{
  "id": "e0e676de-39ce-43e5-a6b5-bf8e9fb2f51a",
  "name": "Alice",
  "workdays": []
}
```

*Response:*  
Returns `200 OK` and a message containing the employee id.  
Returns `404 Not Found` if an employee with requested id does not exist.

Example of `200 OK` response message:

```text/plain
Updated employee with id:"e0e676de-39ce-43e5-a6b5-bf8e9fb2f51a"
```

#### DELETE

*Request:* No request data needed

*Response:*  
Returns `200 OK` and a message containing the employee id.  
Returns `404 Not Found` if an employee with requested id does not exist.

```text/plain
Deleted employee with id:703a215b-a282-4fad-a5d1-ceccb46dd37e
```

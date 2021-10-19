This example code is used for running a Java Lambda using API Gateway trigger.
It uses AWS Lambda Core SDK for RequestStreamHandler implementation which is called by the trigger

### Description
An AWS Lambda API Gateway Java application takes an arbitrary array of arrays with numbers and flattens it into a single array

The AWS service via the API Gateway takes input as application/json:
```json
{"input":[1,[2,3,[4]],[5],6,[7,[8]]]}
```
And return output as application/json:
```json
{"output":[1,2,3,4,5,6,7,8]} 
```
#### Curl URL
```bash
curl --location --request POST 'https://plmc541.execute-api.us-east-1.amazonaws.com/array' \
--header 'Content-Type: application/json' \
--data-raw '{"input":[1,[2,2,3,[4,7,11,8]],[5,4,15],6,[7,[8,9]]]} '
```
Response:
```json 
{"output":[1,2,2,3,4,7,11,8,5,4,15,6,7,8,9]}
```

### How to build

```bash
mvn compile
mvn package
```
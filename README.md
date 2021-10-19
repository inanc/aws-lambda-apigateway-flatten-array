An AWS Lambda API Gateway Java application flattens nested arrays into a single array

### Description
The service takes input as application/json:
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
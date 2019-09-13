Feature: Endpoint validation to add person

Scenario: Add person proccess successfully
Given The endpoint host "http://localhost"
When The client make a "post" request at "persona/guardar"
And Sending the required attribute name "Jorge" lastname "Zevallos"
Then The status response should be 200
And The response message status is "true"
And Verify if the response meets the expected schema "./src/test/java/Schemas/ResponseAddPersonSchema.json"

Scenario: Verifying details from person saved
When The client make a "get" request at "persona/obtener/"
And  Insert the personId on the path
Then The status response should be 200
And The response must be contains name "Jorge" and lastname "Zevallos"

Scenario: Add person proccess fails
Given The endpoint host "http://localhost"
When The client make a "post" request at "persona/guardar"
And Sending the required attribute name "" lastname ""
Then The status response should be 400
And The response message status is "false" 
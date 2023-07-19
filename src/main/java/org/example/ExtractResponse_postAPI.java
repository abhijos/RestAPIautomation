package org.example;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ExtractResponse_postAPI {
    //Add place -> update place with new address -> get place to validate if New address is present in response
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all()
                .queryParam("key","qaclick123")
                .header("ContentType","application/json")
                .body(Payload.AddPlace())
                .when()
                .post("maps/api/place/add/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("scope",equalTo("APP"))
                .header("server","Apache/2.4.52 (Ubuntu)")
                .extract()
                .response()
                .asString();

        System.out.println("Response after adding the place:"+"\n"+ response);
        JsonPath jsonPath = new JsonPath(response); //Class used for parsing Json
        String placeId = jsonPath.getString("place_id");
        System.out.println("The extracted place ID is:" + placeId);
    }
}
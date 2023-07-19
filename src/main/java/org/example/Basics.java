package org.example;

import files.Payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Basics {
    public static void main(String[] args){
        //validating if add place api is working fine
        //3 principles for writing rest api tests
        //given - all input details - contains header requests and body or payload
        //when - submit the api - resource and http method are always defined here
        //then - validate the response - this has the response from the api

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().log().all()
                .queryParam("key","qaclick123")
                .header("ContentType","application/json")
                .body(Payload.AddPlace())
                .when()
                .post("maps/api/place/add/json")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("scope",equalTo("APP"))
                .header("server","Apache/2.4.52 (Ubuntu)");

        //Add place -> update place with new address -> get place to validate if New address is present in response


    }
}

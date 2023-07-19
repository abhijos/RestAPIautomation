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

        //Add Place - POST operation
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

        //Update Place - PUT operation
        given().log().all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\"70 Summer walk, USA\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when()
                .put("maps/api/place/update/json")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("msg",equalTo("Address successfully updated"));
    }
}
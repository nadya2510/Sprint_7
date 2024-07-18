import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import java.util.Map;

import static courier.constants.Url.URL_API;
import static io.restassured.RestAssured.given;

public abstract class ApiScooter {

 private RequestSpecification baseRequestSpec(){
   return new RequestSpecBuilder()
           .setBaseUri(URL_API)
           .addHeader("Content-type", "application/json")
           .setRelaxedHTTPSValidation()
           .addFilter(new RequestLoggingFilter())
           .addFilter(new ResponseLoggingFilter())
           .addFilter(new ErrorLoggingFilter())
           .build();

 }
 protected Response doGetRequest(String path){
   return given()
           .spec(baseRequestSpec())
           .get(path)
           .thenReturn();
 }

  protected Response doGetRequest(String path, Map<String, Integer> params){
    return given()
            .spec(baseRequestSpec())
            .params(params)
            .get(path)
            .thenReturn();
  }

 protected Response doPostRequest(String path, Object body){
   return  given()
           .spec(baseRequestSpec())
           .body(body)
           .post(path)
           .thenReturn();
 }

 protected Response doPostRequest(String path, Object body, Map<String, Integer> params){
   return given()
           .spec(baseRequestSpec())
           .body(body)
           .params(params)
           .post(path)
           .thenReturn();
 }

  protected Response doPutRequest(String path, Object body){
    return  given()
            .spec(baseRequestSpec())
            .body(body)
            .put(path)
            .thenReturn();
  }

  protected Response doPutRequest(String path,  Map<String, Integer> params){
    return  given()
            .spec(baseRequestSpec())
            .params(params)
            .put(path)
            .thenReturn();
  }

  protected Response doDeleteRequest(String path){
    return  given()
            .spec(baseRequestSpec())
            .delete(path)
            .thenReturn();
  }

}

package service.restassured;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import service.user.api.UserApi;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TestHttp {

    @Test
    public void auth() {
        given().relaxedHTTPSValidation().auth().oauth2("fbbce34c09d79de7a4468ff23774f9822656a144")
                .when().log().all().get("https://api.github.com/user/emails")
                .then().log().all().statusCode(200);
    }

    @Test
    void schema_wrong() {
        UserApi user = new UserApi();
        user.get("seveniruby").then().body(matchesJsonSchemaInClasspath("service/user/testcase/user_get_schema.json"));
    }


    @Test
    void schema_right() {
        UserApi user = new UserApi();
        user.get("seveniruby").then().body(matchesJsonSchemaInClasspath("service/user/testcase/user_get_schema_right.json"));
    }

    @Test
    void stringNullSchema() {
        given().when().get("http://127.0.0.1:8000/user.json").then().body(matchesJsonSchemaInClasspath("service/user/testcase/user_get_schema_right.json"));
    }

    @Test
    void filterDemo() {
        given().filter((req, res, ctx) -> {
            System.out.println(req.getURI());
            Response resReal = ctx.next(req, res);
            System.out.println(resReal.getStatusLine());
            return resReal;
        })
                .when().get("http://127.0.0.1:8000/user.json")
                .then().statusCode(200);
    }
}

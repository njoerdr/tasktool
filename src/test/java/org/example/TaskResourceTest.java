package org.example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
class TaskResourceTest {

    @Test
    void show_all_tasks() {
        var response = given()
                .when().get("/tasks")
                .then()
                .statusCode(200)
                .body(containsString("Tasktool"))
                .body("html.body.div.div.list().size()", equalTo(3))
                .extract().response();

        var taskNames = response.htmlPath()
                .getList("html.body.div.div.form.div.input.grep { it.@name == 'name' }.'@value'");
        assertThat(taskNames, hasItems("Accomplish important stuff", "Dispose trash", "Recognize futileness"));
    }

    @Test
    void create_task() {
        var task = "{\"name\":\"testtask\",\"priority\":\"LOW\"}";
        given().body(task).contentType(ContentType.JSON)
                .when().post("/tasks")
                .then()
                .log().all()
                .statusCode(202)
                .body(containsString("testtask"))
                .body("html.body.div.div.list().size()", equalTo(4))
                .extract().response();
    }

    @Test
    void update_task() {
        var task = "{\"done\":true,\"name\":\"Dispose trash\",\"priority\":\"NORMAL\"}";
        given()
                .header("HX-Request", "true")
                .body(task).contentType(ContentType.JSON)
                .when().put("/tasks/1")
                .then()
                .statusCode(202)
                .contentType(ContentType.TEXT)
                .body(containsString("Dispose trash"))
                .body(containsString("checked"))
                .body(containsString("NORMAL"));
    }

    @Test
    void delete_task() {
        var task = "{\"name\":\"testtask\",\"priority\":\"LOW\"}";
        var response = given().body(task).contentType(ContentType.JSON)
                .when().post("/tasks")
                .then()
                .statusCode(202)
                .body(containsString("testtask"))
                .body("html.body.div.div.list().size()", equalTo(4))
                .extract().response();
        String newTaskPath = response.htmlPath().getString("html.body.div.div[3].form.@hx-put");
        given()
                .when().delete(newTaskPath)
                .then().statusCode(202);
        given()
                .when().get(newTaskPath)
                .then().statusCode(404);
    }

}
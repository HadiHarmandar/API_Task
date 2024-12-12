package com.example.api_tests;

import com.example.pojo.Task;
import com.example.utilities.TypicodeTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class API_Task extends TypicodeTestBase {

    @DisplayName("GET user numposts scenario 1")
    @Test
    public void test() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        List<Task> list = jsonPath.getList("", Task.class);

        Set<Integer> ids = new HashSet<>();
        Set<Integer> userIds = new HashSet<>();
        Map<Integer, Integer> userPostMap = new HashMap<>();

        for (Task task : list) {
            ids.add(task.getId());
            userIds.add(task.getUserId());
        }

        for (Task task : list) {
            if (!userPostMap.containsKey(task.getUserId())) {
                userPostMap.put(task.getUserId(), 1);
            } else {
                userPostMap.put(task.getUserId(), userPostMap.get(task.getUserId()) + 1);
            }
        }

        System.out.println("userPostMap = " + userPostMap);

        // scenario 1
        for (int i = 1; i <= userIds.size(); i++) {
            assertThat(userPostMap.get(i), is(10));
        }

        // This is negative test case
//        for (int i = 1; i <= userIds.size(); i++) {
//            assertThat(userPostMap.get(i), is(9));
//        }

    }

    @DisplayName("GET post ids scenario 2")
    @Test
    public void test1() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        List<Task> list = jsonPath.getList("", Task.class);

        Set<Integer> ids = new HashSet<>();
        Set<Integer> userIds = new HashSet<>();

        for (Task task : list) {
            ids.add(task.getId());
            userIds.add(task.getUserId());
        }

//        This is negative test case verification.
//        assertThat(ids.size(), is(userIds.size() * 9));

        // scenario 2
        assertThat(ids.size(), is(userIds.size() * 10));

        // option 2: Total post ids must be equal to total json object if they are unique.
        assertThat(ids.size(), is(list.size()));

    }
}

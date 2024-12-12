package com.example.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class TypicodeTestBase {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
}

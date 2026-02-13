package api;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("JSONPlaceholder API тесты")
@Owner("Анна")  // 👈 Владелец всего класса
public class JsonPlaceholderTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        RestAssured.filters(new AllureRestAssured()
                .setRequestAttachmentName("📤 Запрос")
                .setResponseAttachmentName("📥 Ответ"));
    }

    // ============= ТЕСТЫ =============

    @Test
    @DisplayName("GET /posts - получить все посты")
    @Severity(SeverityLevel.NORMAL)
    void shouldGetAllPosts() {
        Response response = sendGetRequest("/posts");
        verifyStatusCode(response, 200);
        verifyContentType(response, ContentType.JSON);
        verifyResponseNotEmpty(response);
    }

    @Test
    @DisplayName("GET /posts/1 - получить пост по ID")
    @Severity(SeverityLevel.NORMAL)
    void shouldGetPostById() {
        Response response = sendGetPostByIdRequest(1);
        verifyStatusCode(response, 200);
        verifyPostId(response, 1);
        verifyPostHasTitleAndBody(response);
    }

    @Test
    @DisplayName("GET /posts?userId=1 - фильтрация по пользователю")
    @Severity(SeverityLevel.NORMAL)
    void shouldGetPostsByUserId() {
        Response response = sendGetPostsByUserIdRequest(1);
        verifyStatusCode(response, 200);
        verifyAllPostsHaveUserId(response, 1);
    }

    @Test
    @DisplayName("POST /posts - создать новый пост")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Jira", url = "https://jira.example.com/API-123")
    void shouldCreateNewPost() {
        Response response = sendCreatePostRequest(
                "RestAssured тест",
                "Учимся тестировать API",
                1
        );
        verifyStatusCode(response, 201);
        verifyPostCreated(response, "RestAssured тест");
    }

    // ============= STEP-МЕТОДЫ =============

    @Step("📌 GET запрос на {endpoint}")
    Response sendGetRequest(String endpoint) {
        return given()
                .log().all()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("📌 GET /posts/{id}")
    Response sendGetPostByIdRequest(int id) {
        return given()
                .log().all()
                .pathParam("id", id)
                .when()
                .get("/posts/{id}")
                .then()
                .log().all()
                .extract().response();
    }

    @Step("📌 GET /posts?userId={userId}")
    Response sendGetPostsByUserIdRequest(int userId) {
        return given()
                .log().all()
                .queryParam("userId", userId)
                .when()
                .get("/posts")
                .then()
                .log().all()
                .extract().response();
    }

    @Step("📌 POST /posts - создание поста: '{title}'")
    Response sendCreatePostRequest(String title, String body, int userId) {
        String newPost = String.format("""
            {
                "title": "%s",
                "body": "%s",
                "userId": %d
            }
            """, title, body, userId);

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(newPost)
                .when()
                .post("/posts")
                .then()
                .log().all()
                .extract().response();
    }

    // ============= ВЕРИФИКАЦИИ =============

    @Step("✅ Проверка статус кода: {expectedStatusCode}")
    void verifyStatusCode(Response response, int expectedStatusCode) {
        assertThat(response.getStatusCode())
                .as("Статус код ответа")
                .isEqualTo(expectedStatusCode);
    }

    @Step("✅ Проверка Content-Type: {expectedContentType}")
    void verifyContentType(Response response, ContentType expectedContentType) {
        assertThat(response.getContentType())
                .as("Content-Type")
                .contains(expectedContentType.toString());
    }

    @Step("✅ Проверка что ответ не пустой")
    void verifyResponseNotEmpty(Response response) {
        response.then().body("$", not(empty()));
    }

    @Step("✅ Проверка ID поста = {expectedId}")
    void verifyPostId(Response response, int expectedId) {
        response.then().body("id", equalTo(expectedId));
    }

    @Step("✅ Проверка что пост имеет title и body")
    void verifyPostHasTitleAndBody(Response response) {
        response.then()
                .body("title", not(emptyString()))
                .body("body", not(emptyString()));
    }

    @Step("✅ Проверка что все посты принадлежат пользователю {userId}")
    void verifyAllPostsHaveUserId(Response response, int userId) {
        response.then()
                .body("every { it.userId == " + userId + " }", is(true));
    }

    @Step("✅ Проверка что пост создан: title = '{expectedTitle}'")
    void verifyPostCreated(Response response, String expectedTitle) {
        response.then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo(expectedTitle));
    }
}
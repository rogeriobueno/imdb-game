package com.bueno.imdbgame.controlles;

import com.bueno.imdbgame.config.TestConfig;
import com.bueno.imdbgame.dto.MovieVO;
import com.bueno.imdbgame.services.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MovieControllerTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    @Autowired
    private MovieService movieService;

    @BeforeAll
    public static void init() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private static RequestSpecification initSpecification(String url) {
        return new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, "http://localhost")
                .setBasePath(TestConfig.BASE_URL_MOVIE_CONTROLLER.concat(url))
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    public void shouldFindMovieOnGetById() throws JsonProcessingException {

        Long movieId = 238L;

        var content = given().spec(initSpecification("/"))
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .pathParam("id", movieId)
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        JavaType javaType = objectMapper.getTypeFactory()
                .constructType(MovieVO.class);
        MovieVO vo = objectMapper.readValue(content, javaType);

        Assertions.assertNotNull(vo);
        Assertions.assertEquals(vo.id(), 238L);
        Assertions.assertEquals(vo.title(), "The Godfather");
        Assertions.assertEquals(vo.voteAverage(), 87);
    }

}
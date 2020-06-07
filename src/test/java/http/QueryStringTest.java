package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {
    @DisplayName("쿼리스트링을 변수와 값의 쌍 형태로 파싱")
    @Test
    void test_parsing_querystring_should_pass() {
        // given
        String fullQueryString = "userId=javajigi&password=password&name=JaeSung";
        // when
        QueryString queryString = QueryString.from(fullQueryString);
        // then
        assertThat(queryString.getParameters("userId")).hasSize(1).containsExactly("javajigi");
        assertThat(queryString.getParameters("password")).hasSize(1).containsExactly("password");
        assertThat(queryString.getParameters("name")).hasSize(1).containsExactly("JaeSung");
    }

    @DisplayName("키는 존재하나 값이 표기되지 않은 파라미터는 값이 null로 저장")
    @Test
    void parse_querystring_without_value() {
        // given
        String fullQueryString = "userId=";
        // when
        QueryString queryString = QueryString.from(fullQueryString);
        // then
        assertThat(queryString.getParameters("userId")).hasSize(1).containsNull();
    }

    @DisplayName("구분자가 포함되지 않은 경우 그 자체가 키가 되고 값이 null로 저장")
    @Test
    void parse_querystring_without_delimiter() {
        // given
        String fullQueryString = "userId";
        // when
        QueryString queryString = QueryString.from(fullQueryString);
        // then
        assertThat(queryString.getParameters("userId")).hasSize(1).containsNull();
    }

    @DisplayName("구분자가 맨 앞에 위치하는 경우 그 자체가 키가 되고 값이 null로 저장")
    @Test
    void parse_querystring_startswith_delimiter() {
        // given
        String fullQueryString = "=userId";
        // when
        QueryString queryString = QueryString.from(fullQueryString);
        // then
        assertThat(queryString.getParameters("=userId")).hasSize(1).containsNull();
    }

    @DisplayName("빈 문자열의 경우 빈 MultiValueMap으로 초기화")
    @Test
    void test_parsing_empty() {
        // given
        String fullQueryString = "";
        // when
        QueryString queryString = QueryString.from(fullQueryString);
        // then
        assertThat(queryString.isEmpty()).isTrue();
    }
}
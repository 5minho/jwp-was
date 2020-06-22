package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpHeader;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeadersTest {

    @DisplayName("응답헤더 추가")
    @Test
    void addHeader() {

        // given
        HttpHeader name = HttpHeader.CONTENT_TYPE;
        String[] values = {"text/html", "charset=utf-8"};
        ResponseHeaders responseHeaders = new ResponseHeaders();

        // when
        responseHeaders.addHeader(name, values);

        // then
        assertThat(responseHeaders.getResponseHeaders().get(name))
                .isEqualTo(ResponseHeader.of(name, values));
    }
}
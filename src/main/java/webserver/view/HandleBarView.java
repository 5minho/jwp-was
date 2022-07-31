package webserver.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import webserver.http.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

class HandleBarView implements View {

    private final String url;

    private final Handlebars handlebars;

    HandleBarView(String url, String prefix, String suffix) {
        this.url = url;
        this.handlebars = new Handlebars(new ClassPathTemplateLoader(prefix, suffix));
    }

    public void render(Map<String, ?> models, Response response) {
        try {
            Template template = handlebars.compile(url);

            byte[] templateBody = template.apply(models).getBytes(StandardCharsets.UTF_8);
            response.setBody(templateBody);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}

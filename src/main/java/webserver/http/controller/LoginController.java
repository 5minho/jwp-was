package webserver.http.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Cookie;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.View;
import webserver.view.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginController<V extends ViewResolver> extends AbstractController<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private V viewResolver;

    public LoginController(V viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            View view = viewResolver.render(request.getPath());
            response.writeHeader(HttpStatus.OK, request.getAccept(), view.getLength());
            response.writeBody(view.getBody());
        } catch (IOException | URISyntaxException e) {
            logger.error("[PROCESS][LOGIN] failed. {}", e);
        }
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        String userId = request.getBodyParameter("userId");
        String password = request.getBodyParameter("password");

        User user = DataBase.findUserById(userId);

        try {
            if (user == null || !user.isOwner(password)) {
                logger.error("[PROCESS][LOGIN] password not match. {}", userId);
                View view = viewResolver.render("/user/login_failed");
                response.writeHeader(HttpStatus.OK, request.getAccept(), view.getLength());
                response.writeBody(view.getBody());
            } else {
                response.addCookie(new Cookie("logined", "true"));
                response.redirect("/index");
            }
        } catch (IOException | URISyntaxException e) {
            logger.error("[PROCESS][LOGIN] failed. {}", e);
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGetRequest()) {
            doGet(request, response);
        }

        if (request.isPostRequest()) {
            doPost(request, response);
        }
    }
}
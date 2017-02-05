package com.jfonzuer.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Created by pgm on 31/01/17.
 */

public class ConversationHanshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;

        System.out.println("servletRequest.getServletRequest().getQueryString() = " + servletRequest.getServletRequest().getQueryString());

        System.err.println("servletRequest = " + servletRequest);
        System.out.println("servletRequest.getServletRequest() = " + servletRequest.getServletRequest());
        System.out.println("servletRequest.getHeaders().get(\"Authorization\") = " + servletRequest.getHeaders().get("Authorization"));
        System.out.println("servletRequest.getServletRequest().getHeader('Authorization') = " + servletRequest.getServletRequest().getHeader("Authorization"));
        map.put("handshake", "ok");

        //servletRequest.getServletRequest().getParameter()

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    }
}
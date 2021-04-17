package com.example.webSockets;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootApplication
public class WebSocketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketsApplication.class, args);

		try {
			StompSessionHandler sessionHandler = new StompSessionHandler() {

				@Override
				public void handleFrame(StompHeaders headers, Object payload) {
					System.out.println("Received : " + (String)payload.toString());
				}

				@Override
				public Type getPayloadType(StompHeaders headers) {
					// TODO Auto-generated method stub
					System.out.println("A");
					System.out.println(headers);
					
					return String.class;
				}

				@Override
				public void handleTransportError(StompSession session, Throwable exception) {
					// TODO Auto-generated method stub
					exception.printStackTrace();
				}

				@Override
				public void handleException(StompSession session, StompCommand command, StompHeaders headers,
						byte[] payload, Throwable exception) {
					// TODO Auto-generated method stub
					exception.printStackTrace();
				}

				@Override
				public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
					// TODO Auto-generated method stub
					System.out.println("connected now ");
					System.out.println(session.toString());
					System.out.println(connectedHeaders.getDestination());
				//	session.subscribe("/topic/greetings", this);
					//session.send("/topic/greetings22", "Hello new user");
					
				}
			};

			WebSocketClient simpleWebSocketClient = 
					new StandardWebSocketClient();
			List<Transport> transports = new ArrayList<>(1);
			transports.add(new WebSocketTransport(simpleWebSocketClient));

			SockJsClient sockJsClient = new SockJsClient(transports);
			WebSocketStompClient stompClient = 
					new WebSocketStompClient(sockJsClient);
			stompClient.setMessageConverter(new MappingJackson2MessageConverter());

			StompSession stompSession = stompClient.connect(
					"http://localhost:8080/gs-guide-websocket", sessionHandler)
					.get();
			
			
			
			  stompSession.subscribe("/topic/greetings", sessionHandler);
			  stompSession.send("/topic/greetings", "Hello new user");
			  stompSession.send("/topic/greetings", "Hello new user");
			  
			 stompSession.send("/topic/receive","how u doing");
			 
			 try {
				Thread.sleep(8000);
				stompSession.send("/topic/greetings", "Hello new user123");
				stompSession.send("/topic/greetings", "Hello new user123");
				stompSession.send("/topic/greetings", "Hello new user123");
				stompSession.send("/topic/greetings", "Hello new user123");
				stompSession.send("/topic/greetings", "Hello new user123");
				  
			} catch (Exception e) {
				// TODO: handle exception
			}
			//System.exit(0);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}
	


}

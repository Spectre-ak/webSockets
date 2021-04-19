package com.example.webSockets;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@RestController
public class sendMessage {
	StompSession stompSession;

	public sendMessage() {
		try {
			StompSessionHandler sessionHandler = new StompSessionHandler() {

				@Override
				public void handleFrame(StompHeaders headers, Object payload) {
					System.out.println("Received : " + (String) payload.toString());
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
					// session.subscribe("/topic/greetings", this);
					// session.send("/topic/greetings22", "Hello new user");

				}
			};

			WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
			List<Transport> transports = new ArrayList<>(1);
			transports.add(new WebSocketTransport(simpleWebSocketClient));

			SockJsClient sockJsClient = new SockJsClient(transports);
			WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
			stompClient.setMessageConverter(new MappingJackson2MessageConverter());

			stompSession = stompClient.connect("http://localhost:8080/gs-guide-websocket", sessionHandler).get();

			stompSession.subscribe("/topic/greetings", sessionHandler);
			stompSession.send("/topic/greetings", "Hello new user");

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	
	
	@GetMapping("sendGolobal")
	public String sendGlobal() {
		int string=ThreadLocalRandom.current().nextInt();
		if(stompSession==null)
			sendMessage1();
		stompSession.send("/topic/allSubs",string);
		return "do";
	}
	
	public void sendMessage1() {
		try {
			StompSessionHandler sessionHandler = new StompSessionHandler() {

				@Override
				public void handleFrame(StompHeaders headers, Object payload) {
					System.out.println("Received : " + (String) payload.toString());
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
					// session.subscribe("/topic/greetings", this);
					// session.send("/topic/greetings22", "Hello new user");

				}
			};

			WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
			List<Transport> transports = new ArrayList<>(1);
			transports.add(new WebSocketTransport(simpleWebSocketClient));

			SockJsClient sockJsClient = new SockJsClient(transports);
			WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
			stompClient.setMessageConverter(new MappingJackson2MessageConverter());

			stompSession = stompClient.connect("http://localhost:8080/gs-guide-websocket", sessionHandler).get();

			stompSession.subscribe("/topic/greetings", sessionHandler);
			stompSession.send("/topic/greetings", "Hello new user");

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}

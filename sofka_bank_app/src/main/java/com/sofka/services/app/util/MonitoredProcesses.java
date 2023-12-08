package com.sofka.services.app.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sofka.services.app.queue.RabbitMqSender;

@Component
public class MonitoredProcesses {

	private final RabbitMqSender rabbitMqSender;

	private static int line;
	private static String method;
	private static String clazz;

	public MonitoredProcesses(RabbitMqSender rabbitMqSender) {
		this.rabbitMqSender = rabbitMqSender;
	}

	public void info(String message) {

		Map<String, Object> objectToQueue = new HashMap<String, Object>();

		line = Thread.currentThread().getStackTrace()[2].getLineNumber();
		method = Thread.currentThread().getStackTrace()[2].getMethodName();
		clazz = Thread.currentThread().getStackTrace()[2].getClassName();

		objectToQueue.put("Tipo", "INFO");
		objectToQueue.put("Clase", clazz);
		objectToQueue.put("Metodo", method);
		objectToQueue.put("linea", line);
		objectToQueue.put("mensaje", message);

		rabbitMqSender.senderLogs(objectToQueue);

	}

	public void info(String message, Object data) {

		Map<String, Object> objectToQueue = new HashMap<String, Object>();

		line = Thread.currentThread().getStackTrace()[2].getLineNumber();
		method = Thread.currentThread().getStackTrace()[2].getMethodName();
		clazz = Thread.currentThread().getStackTrace()[2].getClassName();

		objectToQueue.put("Tipo", "INFO");
		objectToQueue.put("Clase", clazz);
		objectToQueue.put("Metodo", method);
		objectToQueue.put("linea", line);
		objectToQueue.put("mensaje", message);
		if (data != null)
			objectToQueue.put("datos", data);

		rabbitMqSender.senderLogs(objectToQueue);

	}

	public void error(String message) {

		Map<String, Object> objectToQueue = new HashMap<String, Object>();

		line = Thread.currentThread().getStackTrace()[2].getLineNumber();
		method = Thread.currentThread().getStackTrace()[2].getMethodName();
		clazz = Thread.currentThread().getStackTrace()[2].getClassName();

		objectToQueue.put("Tipo", "ERROR");
		objectToQueue.put("Clase", clazz);
		objectToQueue.put("Metodo", method);
		objectToQueue.put("linea", line);
		objectToQueue.put("mensaje", message);

		rabbitMqSender.senderLogs(objectToQueue);

	}

	public void error(String message, Object data) {

		Map<String, Object> objectToQueue = new HashMap<String, Object>();

		line = Thread.currentThread().getStackTrace()[2].getLineNumber();
		method = Thread.currentThread().getStackTrace()[2].getMethodName();
		clazz = Thread.currentThread().getStackTrace()[2].getClassName();

		objectToQueue.put("Tipo", "ERROR");
		objectToQueue.put("Clase", clazz);
		objectToQueue.put("Metodo", method);
		objectToQueue.put("linea", line);
		objectToQueue.put("mensaje", message);
		if (data != null)
			objectToQueue.put("datos", data);

		rabbitMqSender.senderLogs(objectToQueue);

	}

}

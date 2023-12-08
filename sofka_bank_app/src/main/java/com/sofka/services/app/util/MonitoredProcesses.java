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

		objectToQueue.put("tipo", "INFO");
		objectToQueue.put("clase", clazz);
		objectToQueue.put("metodo", method);
		objectToQueue.put("linea", line);
		objectToQueue.put("mensaje", message);
		objectToQueue.put("nanoSegundos", System.nanoTime());

		rabbitMqSender.senderLogs(objectToQueue);

	}

	public void info(String message, Object data) {

		Map<String, Object> objectToQueue = new HashMap<String, Object>();

		line = Thread.currentThread().getStackTrace()[2].getLineNumber();
		method = Thread.currentThread().getStackTrace()[2].getMethodName();
		clazz = Thread.currentThread().getStackTrace()[2].getClassName();

		objectToQueue.put("tipo", "INFO");
		objectToQueue.put("clase", clazz);
		objectToQueue.put("metodo", method);
		objectToQueue.put("linea", line);
		objectToQueue.put("mensaje", message);
		objectToQueue.put("nanoSegundos", System.nanoTime());
		if (data != null)
			objectToQueue.put("datos", data);

		rabbitMqSender.senderLogs(objectToQueue);

	}

	public void error(String message) {

		Map<String, Object> objectToQueue = new HashMap<String, Object>();

		line = Thread.currentThread().getStackTrace()[2].getLineNumber();
		method = Thread.currentThread().getStackTrace()[2].getMethodName();
		clazz = Thread.currentThread().getStackTrace()[2].getClassName();

		objectToQueue.put("tipo", "ERROR");
		objectToQueue.put("clase", clazz);
		objectToQueue.put("metodo", method);
		objectToQueue.put("linea", line);
		objectToQueue.put("mensaje", message);
		objectToQueue.put("nanoSegundos", System.nanoTime());

		rabbitMqSender.senderLogs(objectToQueue);

	}

	public void error(String message, Object data) {

		Map<String, Object> objectToQueue = new HashMap<String, Object>();

		line = Thread.currentThread().getStackTrace()[2].getLineNumber();
		method = Thread.currentThread().getStackTrace()[2].getMethodName();
		clazz = Thread.currentThread().getStackTrace()[2].getClassName();

		objectToQueue.put("tipo", "ERROR");
		objectToQueue.put("clase", clazz);
		objectToQueue.put("metodo", method);
		objectToQueue.put("linea", line);
		objectToQueue.put("mensaje", message);
		objectToQueue.put("nanoSegundos", System.nanoTime());

		if (data != null)
			objectToQueue.put("datos", data);

		rabbitMqSender.senderLogs(objectToQueue);

	}

}

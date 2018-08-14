package org.camunda.demo.chargecard;

import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class ChargeCardWorker {
	private static final Logger logger = Logger.getLogger(ChargeCardWorker.class.getName());

	public static void main(String[] args) {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest").build();

		client.subscribe("charge-card")
		.lockDuration(1000)
		.handler((externalTask, externalTaskService) -> {
			String item = (String) externalTask.getVariable("item");
			Long amount = (Long) externalTask.getVariable("amount");
			logger.info("Charging credit card with an amount of '" + amount + "'€ for the item '" + item + "'...");

			// Complete the task
			externalTaskService.complete(externalTask);
		})
		.open();
	}
}

package br.com.api.ml.purchase.other_systems;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import br.com.api.ml.purchase.Purchase;

@Service
public class Ranking implements SuccessPurchaseEvent {

	@Override
	public void process(Purchase purchase) {
		Assert.isTrue(purchase.successfullyProcessed(), "A compra não foi processada corretamente!");
		
		RestTemplate restTemplate = new RestTemplate();

		Map<String, Object> request = Map.of("idPurchase", purchase.getId(), "idOwner", purchase.getProductOwnerId());

		restTemplate.postForEntity("http://localhost:8080/ranking", request, String.class);
	}

}

package br.com.api.ml.mailer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import br.com.api.ml.product.Product;
import br.com.api.ml.product.question.Question;
import br.com.api.ml.purchase.Purchase;
import br.com.api.ml.user.User;

@Service
public class Emails implements Mailer {

	public void sendQuestion(@NotNull @Valid Question question, @NotNull @Valid Product product,
			@NotNull @Valid User user) {

	}

	@Override
	public void send(Purchase purchase) {
	}

}

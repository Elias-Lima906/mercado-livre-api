package br.com.api.ml.mailer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.api.ml.product.Product;
import br.com.api.ml.product.question.Question;
import br.com.api.ml.user.User;

public interface Mailer {

	public void sendQuestion(@NotNull @Valid Question question, @NotNull @Valid Product product,
			@NotNull @Valid User user);

}

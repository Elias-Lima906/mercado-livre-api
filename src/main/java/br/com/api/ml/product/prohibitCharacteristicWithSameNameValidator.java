package br.com.api.ml.product;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class prohibitCharacteristicWithSameNameValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProductRequestDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		if (errors.hasErrors()) {
			return;
		}

		ProductRequestDTO request = (ProductRequestDTO) target;
		if (request.hasEqualCharacteristics()) {
			errors.rejectValue("characteristics", null, "Há mais de uma caracteristica com as mesmas informações!");
		}
	}

}

package br.com.api.ml.purchase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.api.ml.product.Product;
import br.com.api.ml.purchase.gateway.GatewayStatus;
import br.com.api.ml.user.User;
import br.com.api.ml.user.UserRepository;
import br.com.api.ml.validation.ExistsById;

public class PurchaseRequestDTO {

	@NotNull
	@Positive
	private Integer amount;

	@NotBlank
	private GatewayStatus gateway;

	@NotNull
	@ExistsById(domainName = Product.class, fieldName = "id", message = "Não foi encontrado nenhum produto com o id especificado!")
	private Long idProduct;

	@Deprecated
	public PurchaseRequestDTO() {
	}
	
	public PurchaseRequestDTO(Integer amount, GatewayStatus gateway, Long idProduct) {
		this.amount = amount;
		this.gateway = gateway;
		this.idProduct = idProduct;
	}

	public Integer getAmount() {
		return amount;
	}

	public GatewayStatus getGateway() {
		return gateway;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public Purchase toModel(UserRepository userRepository, Product product) {
		User user = User.findAuthenticatedUser(userRepository);

		return new Purchase(amount, gateway, product, user);
	}
}

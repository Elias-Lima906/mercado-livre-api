package br.com.api.ml.opinion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.api.ml.product.Product;
import br.com.api.ml.user.User;

@Entity
public class Opinion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Min(1)
	@Max(5)
	@Column(nullable = false)
	private Integer evaluation;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Title title;

	@NotBlank
	@Size(max = 500)
	@Column(nullable = false, length = 500)
	private String description;

	@NotNull
	@ManyToOne
	@Valid
	private User user;

	@NotNull
	@ManyToOne
	@Valid
	private Product product;

	@Deprecated
	public Opinion() {
	}

	public Opinion(@NotNull @Min(1) @Max(5) Integer evaluation, @NotNull Title title,
			@NotBlank @Size(max = 500) String description, @NotNull @Valid User user, @NotNull @Valid Product product) {
		this.evaluation = evaluation;
		this.title = title;
		this.description = description;
		this.user = user;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public Title getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public User getUser() {
		return user;
	}

	public Product getProduct() {
		return product;
	}

}

package br.com.api.ml.product.opinion;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

	public String getUserEmail() {
		return user.getEmail();
	}
	
	public static Set<OpinionResponseDTO> toOpinionResponseDTO(Set<Opinion> opinions) {
		return opinions.stream().map(opinion -> new OpinionResponseDTO(opinion)).collect(Collectors.toSet());
	}

	public void checkIfHasEqualOpinions(Set<Opinion> opinions) {
		if (opinions.contains(this)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma opinião adicionada pelo úsuario: "
					+ this.getUserEmail() + " a este produto, com o titulo: " + this.getTitle() + ".");
		}
	}

	public List<?> findAllOpinions(EntityManager manager) {
		Query query = manager.createQuery("SELECT o FROM Opinion o");
		return query.getResultList();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evaluation == null) ? 0 : evaluation.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Opinion other = (Opinion) obj;
		if (evaluation == null) {
			if (other.evaluation != null)
				return false;
		} else if (!evaluation.equals(other.evaluation))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (title != other.title)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}

package br.com.api.ml.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.api.ml.category.Category;
import br.com.api.ml.product.characteristc.Characteristic;
import br.com.api.ml.product.characteristc.CharacteristicRequestDTO;
import br.com.api.ml.product.image.Image;
import br.com.api.ml.product.opinion.Opinion;
import br.com.api.ml.product.question.Question;
import br.com.api.ml.user.User;
import br.com.api.ml.user.UserRepository;

@Entity
public class Product {

	private static final int MINIMUN_AMOUNT_TO_CALCULATE_AVARAGE = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotNull
	@Positive
	@Column(nullable = false)
	private BigDecimal price;

	@NotNull
	@Min(0)
	@Column(nullable = false)
	private Integer availableQuantity;

	@NotBlank
	@Column(nullable = false, length = 1000)
	private String description;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime timestampSignUp = LocalDateTime.now();

	@NotNull
	@ManyToOne
	private Category category;

	@ManyToOne
	private User owner;

	@JsonManagedReference
	@OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
	Set<Characteristic> characteristics = new HashSet<Characteristic>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
	private Set<Image> images = new HashSet<Image>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
	Set<Opinion> opinions = new HashSet<Opinion>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
	Set<Question> questions = new HashSet<Question>();

	@Deprecated
	public Product() {
	}

	public Product(@NotBlank String name, @NotNull @Positive BigDecimal price,
			@NotNull @Min(0) Integer availableQuantity, @NotBlank String description, @NotNull Category category,
			@NotNull @Valid User owner, @Size(min = 3) Collection<CharacteristicRequestDTO> characteristics) {
		this.name = name;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.description = description;
		this.category = category;
		this.owner = owner;
		this.characteristics.addAll(addCharacteristics(characteristics));
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getTimestampSignUp() {
		return timestampSignUp;
	}

	public Category getCategory() {
		return category;
	}

	public String getOwnerEmail() {
		return owner.getEmail();
	}

	public Long getOwnerId() {
		return owner.getId();
	}

	public Set<Opinion> getOpinions() {
		return opinions;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public Set<Characteristic> getCharacteristics() {
		return characteristics;
	}

	public Set<Image> getImages() {
		return images;
	}

	private Set<Characteristic> addCharacteristics(Collection<CharacteristicRequestDTO> characteristicsDto) {
		return characteristicsDto.stream().map(characteristic -> characteristic.toModel(this))
				.collect(Collectors.toSet());
	}

	public void addImages(Set<String> links) {
		for (String link : links) {
			Image image = new Image(this, link);
			image.checkIfHasEqualImages(this.images, link);
			images.add(image);
		}
	}

	public void addOpinionToProduct(Opinion opinion) {
		opinion.checkIfHasEqualOpinions(this.opinions);
		opinions.add(opinion);
	}

	public void addQuestionToProduct(Question question) {
		question.checkIfHasEqualQuestions(this.questions);
		questions.add(question);
	}

	public boolean userIsProductOwner(UserRepository userRepository) {
		User loggedUser = User.findAuthenticatedUser(userRepository);
		return this.owner.equals(loggedUser);
	}

	public static Product findProduct(EntityManager manager, Long id) {

		Product product = manager.find(Product.class, id);

		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Não foi encontrado, em sua conta, nenhum produto com o id: " + id);
		}

		return product;
	}

	public Integer getNumberOfEvaluation() {
		return opinions.size();
	}

	public Double calculateAvarage() {
		if (opinions.size() < MINIMUN_AMOUNT_TO_CALCULATE_AVARAGE) {
			return null;
		}

		Double avarage = 0D;
		double sum = 0;

		for (Opinion opinion : opinions) {
			sum = (sum + opinion.getEvaluation());
		}
		avarage = sum / opinions.size();
		return avarage;
	}

	public boolean writeOffStock(Integer amount) {
		if (amount <= this.availableQuantity) {
			this.availableQuantity -= amount;
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

}

package br.com.api.ml.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
import br.com.api.ml.category.CategoryResponseDTO;
import br.com.api.ml.characteristc.Characteristic;
import br.com.api.ml.characteristc.CharacteristicRequestDTO;
import br.com.api.ml.characteristc.CharacteristicResponseDTO;
import br.com.api.ml.image.Image;
import br.com.api.ml.image.ImageResponseDTO;
import br.com.api.ml.opinion.Opinion;
import br.com.api.ml.opinion.OpinionResponseDTO;
import br.com.api.ml.question.Question;
import br.com.api.ml.question.QuestionResponseDTO;
import br.com.api.ml.user.User;
import br.com.api.ml.user.UserRepository;

@Entity
public class Product {

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

	@JsonManagedReference
	@OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
	Set<Characteristic> characteristics = new HashSet<Characteristic>();

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
	private Set<Image> images = new HashSet<Image>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
	List<Opinion> opinions = new ArrayList<Opinion>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
	List<Question> questions = new ArrayList<Question>();

	@Deprecated
	public Product() {
	}

	public Product(@NotBlank String name, @NotNull @Positive BigDecimal price,
			@NotNull @Min(0) Integer availableQuantity, @NotBlank String description, @NotNull Category category,
			@NotNull @Valid User user, @Size(min = 3) Collection<CharacteristicRequestDTO> characteristics) {
		this.name = name;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.description = description;
		this.category = category;
		this.user = user;
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

	public String getUserEmail() {
		return user.getEmail();
	}

	public List<Opinion> getOpinions() {
		return opinions;
	}

	public List<Question> getQuestions() {
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

	public void saveImages(Set<String> links) {

		for (String link : links) {
			Image image = new Image(this, link);
			if (images.contains(image)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Já existe uma imagem com as mesmas informações associada a este úsuario!");
			}

			images.add(image);
		}
	}

	public CategoryResponseDTO toCategoryDTO() {
		return new CategoryResponseDTO(this.category);
	}

	public Set<CharacteristicResponseDTO> toCharacteristicDTO() {

		return this.characteristics.stream()
				.map(characteristic -> new CharacteristicResponseDTO(characteristic.getName(),
						characteristic.getDescription()))
				.collect(Collectors.toSet());

	}

	public Set<ImageResponseDTO> toImageResponseDTO() {
		return this.images.stream().map(image -> new ImageResponseDTO(image)).collect(Collectors.toSet());
	}

	public List<OpinionResponseDTO> toOpinionResponseDTO() {
		return this.opinions.stream().map(opinion -> new OpinionResponseDTO(opinion)).collect(Collectors.toList());
	}

	public List<QuestionResponseDTO> toQuestionResponseDTO() {
		return this.questions.stream().map(question -> new QuestionResponseDTO(question)).collect(Collectors.toList());
	}

	public boolean userIsOwner(UserRepository userRepository) {
		User user = User.findAuthenticatedUser(userRepository);
		return this.user.equals(user);
	}

	public static Product findProduct(EntityManager manager, Long id) {

		Product product = manager.find(Product.class, id);

		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Não foi encontrado, em sua conta, nenhum produto com o id: " + id);
		}

		return product;
	}

	public void addOpinionToProduct(Opinion opinion) {
		opinions.add(opinion);
	}

	public void addQuestionToProduct(Question question) {
		questions.add(question);
	}

}

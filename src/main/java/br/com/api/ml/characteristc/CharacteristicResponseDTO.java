package br.com.api.ml.characteristc;

public class CharacteristicResponseDTO {

	private String name;

	private String description;

	@Deprecated
	public CharacteristicResponseDTO() {
	}

	public CharacteristicResponseDTO(Characteristic characteristic) {
		this.name = characteristic.getName();
		this.description = characteristic.getDescription();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}

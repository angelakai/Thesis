package com.example.ptyxiakh.dto;

public class FurnitureDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String furnitureCondition;
    private String furnitureCategory;
    private String state;
    private String imageUrl;
    private UserDTO seller;
    
    public FurnitureDTO(Long id, String name, String description, Double price, String furnitureCondition, String furnitureCategory, String state, String imageUrl, UserDTO seller) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        this.setFurnitureCondition(furnitureCondition);
        this.setFurnitureCategory(furnitureCategory);
        this.setState(state);
        this.setImageUrl(imageUrl);
        this.setSeller(seller);

    }

    public FurnitureDTO(com.example.ptyxiakh.entities.Furniture furniture) {
        this.setId(furniture.getId());
        this.setName(furniture.getName());
        this.setDescription(furniture.getDescription());
        this.setPrice(furniture.getPrice());
        this.setFurnitureCondition(furniture.getFurnitureCondition());
        this.setFurnitureCategory(furniture.getFurnitureCategory());
        this.setState(furniture.getState());
        this.setImageUrl("http://localhost:8081/furniture/furnitures/" + furniture.getId() + "/image");
        this.seller = new UserDTO(furniture.getSeller());
        }

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getFurnitureCondition() {
		return furnitureCondition;
	}
	public void setFurnitureCondition(String furnitureCondition) {
		this.furnitureCondition = furnitureCondition;
	}
	public String getFurnitureCategory() {
		return furnitureCategory;
	}
	public void setFurnitureCategory(String furnitureCategory) {
		this.furnitureCategory = furnitureCategory;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public UserDTO getSeller() {
		return seller;
	}

	public void setSeller(UserDTO seller) {
		this.seller = seller;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}

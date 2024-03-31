package com.recipes.RecipeRating.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class RecipeDTO {
    @NotEmpty(message="The name is required")
    private String name;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public double getCookingtime() {
        return cookingtime;
    }

    public void setCookingtime(double cookingtime) {
        this.cookingtime = cookingtime;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    @NotEmpty(message="The ingredients is required")
    private String ingredients;
    @NotEmpty(message="The instructions is required")
    @Column(length = 1000) // Change the length as needed
    private String instructions;
    @Min(0)
    private double cookingtime;

    private int difficulty;
    private MultipartFile imageFile;
}

package com.recipes.RecipeRating.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="products")
public class Recipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(length = 1000) // Change the length as needed
    private String instructions;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String ingredients;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
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

    private double cookingtime;
    private int difficulty;
    private Date createdAt;
    private String imageFileName;
}

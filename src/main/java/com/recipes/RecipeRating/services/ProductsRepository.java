package com.recipes.RecipeRating.services;

import com.recipes.RecipeRating.models.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Recipes,Integer> {

}

package com.recipes.RecipeRating.controllers;

import com.recipes.RecipeRating.models.RecipeDTO;
import com.recipes.RecipeRating.models.Recipes;
import com.recipes.RecipeRating.services.ProductsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipesController {
    @Autowired
    private ProductsRepository repo;
    @GetMapping({"","/"})
    public String showRecipesList(Model model)
    {
        List<Recipes> products=repo.findAll();
        model.addAttribute("products",products);
        return "recipes/index";
    }
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        RecipeDTO recipeDto =new RecipeDTO();
        model.addAttribute("recipedto", recipeDto);

        return "recipes/CreateRecipe";
    }
    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute RecipeDTO recipesDTO, BindingResult result ) {
        if(recipesDTO.getImageFile().isEmpty()) {
            result.addError(new FieldError("recipedto","imageFile","The image file is required"));
        }
        if(result.hasErrors()) {
            return "recipes/CreateRecipe";
        }

        //save the image file
        MultipartFile image=recipesDTO.getImageFile();
        Date createdAt=new Date();
        String storageFileName=createdAt.getTime()+"_"+image.getOriginalFilename();
        try {
            String uploadDir ="public/images/";
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream =image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }

        }catch(Exception ex) {
            System.out.println("Exception: "+ex.getMessage());
        }

        Recipes recipe =new Recipes();
        recipe.setName(recipesDTO.getName());
        recipe.setIngredients(recipesDTO.getIngredients());
        recipe.setImageFileName(storageFileName);
        recipe.setCreatedAt(createdAt);
        recipe.setCookingtime(recipesDTO.getCookingtime());
        recipe.setDifficulty(recipe.getDifficulty());
        recipe.setInstructions(recipesDTO.getInstructions());

        repo.save(recipe);

        return "redirect:/recipes";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model,@RequestParam int id) {
        try {
            Recipes recipe = repo.findById(id).orElse(null);;
            model.addAttribute("recipe",recipe);
            RecipeDTO recipeDTO= new RecipeDTO();
            recipeDTO.setName(recipe.getName());
            recipeDTO.setIngredients(recipe.getIngredients());
            recipeDTO.setInstructions(recipe.getInstructions());
            recipeDTO.setCookingtime(recipe.getCookingtime());
            recipeDTO.setDifficulty(recipe.getDifficulty());
//            recipeDTO.setImageFile(recipe.getImageFile());
            recipeDTO.setId(recipe.getId());
            model.addAttribute("recipeDTO",recipeDTO);

        }catch(Exception ex){
            System.out.println("Exception: "+ex.getMessage());
            return "redirect:/recipes";
        }
        return "recipes/EditRecipe";
    }

    @PostMapping("/edit")
    public String updateProduct(Model model,@RequestParam int id,@Valid @ModelAttribute RecipeDTO recipeDTO,BindingResult result) {
        try {Recipes recipe=repo.findById(id).get();
            model.addAttribute("recipe",recipe);
            if(result.hasErrors()) {
                return "recipes/EditRecipe";
            }

            if(!recipeDTO.getImageFile().isEmpty()) {
                String uploadDir="public/images/";
                Path oldImagePath=Paths.get(uploadDir+recipe.getImageFileName());
                try {
                    Files.delete(oldImagePath);
                }
                catch(Exception ex) {
                    System.out.println("Exception: "+ex.getMessage());
                }
                MultipartFile image=recipeDTO.getImageFile();
                Date createdAt =new Date();
                String storageFileName=createdAt.getTime() +"_"+image.getOriginalFilename();
                try(InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);
                }
                recipe.setImageFileName(storageFileName);
            }

            recipe.setName(recipeDTO.getName());
            recipe.setIngredients(recipeDTO.getIngredients());
            recipe.setInstructions(recipeDTO.getInstructions());
            recipe.setCookingtime(recipeDTO.getCookingtime());
            recipe.setDifficulty(recipeDTO.getDifficulty());
            recipe.setId(recipeDTO.getId());

            repo.save(recipe);
        }
        catch(Exception ex){System.out.println("Exception:"+ex.getMessage());}
        return "redirect:/recipes";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        try {
            Recipes recipe =repo.findById(id).get();
            Path imagePath=Paths.get("public/image/"+recipe.getImageFileName());
            try {Files.delete(imagePath);}
            catch(Exception ex){
                System.out.println("Exception:"+ex.getMessage());
            }
            repo.delete(recipe);

        }catch(Exception ex) {
            System.out.println("Exception: "+ex.getMessage());
        }
        return "redirect:/recipes";

    }
}

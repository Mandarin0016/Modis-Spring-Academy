package org.example.entities.users;

import org.example.common.InvalidEntityDataException;
import org.example.entities.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HomeCook extends UserImpl {

    private List<Recipe> favoriteRecipes;
    private List<HomeCook> favoriteCooks;

    public HomeCook(String firstName, String lastName, String email, String userName, String password, String gender) throws InvalidEntityDataException {
        super(firstName, lastName, email, userName, password, gender);
        favoriteRecipes = new ArrayList<>();
        favoriteCooks = new ArrayList<>();
    }

    public void addFavouriteRecipe(Recipe recipe){
        this.favoriteRecipes.add(recipe);
    }

    public void addFavouriteCook(HomeCook homeCook){
        this.favoriteCooks.add(homeCook);
    }

    public void removeFavouriteRecipe(Recipe recipe){
        this.favoriteRecipes.remove(recipe);
    }

    public void removeFavouriteCook(HomeCook homeCook){
        this.favoriteCooks.remove(homeCook);
    }

    public List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public List<HomeCook> getFavoriteCooks() {
        return favoriteCooks;
    }
}

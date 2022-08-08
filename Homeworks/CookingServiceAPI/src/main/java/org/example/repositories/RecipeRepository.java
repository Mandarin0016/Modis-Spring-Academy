package org.example.repositories;

import org.example.entities.recipes.Recipe;
import org.example.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class RecipeRepository implements Repository<Recipe> {

    private List<Recipe> recipes;

    RecipeRepository() {
        recipes = new ArrayList<>();
    }

    @Override
    public Recipe getById(Long id) {
        for (Recipe recipe : recipes) {
            if (Objects.equals(recipe.getId(), id)) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public Collection<Recipe> getAll() {
        return this.recipes;
    }

    @Override
    public void add(Recipe unit) {
        this.recipes.add(unit);
    }

    @Override
    public boolean remove(Recipe unit) {
        return this.recipes.remove(unit);
    }
}

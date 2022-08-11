package org.example.repositories;

import org.example.entities.recipes.Recipe;
import org.example.repositories.interfaces.Repository;
import org.example.repositories.interfaces.RepositoryFactory;

public class RecipeRepositoryFactory implements RepositoryFactory<Recipe> {
    @Override
    public Repository<Recipe> createRepository() {
        return new RecipeRepository();
    }
}

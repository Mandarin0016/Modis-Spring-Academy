package org.example;

import org.example.controllers.CategoryController;
import org.example.controllers.RecipeController;
import org.example.controllers.UserController;
import org.example.core.EngineImpl;
import org.example.core.interfaces.Engine;
import org.example.entities.categories.Category;
import org.example.entities.recipes.Recipe;
import org.example.entities.users.User;
import org.example.repositories.CategoryRepositoryFactory;
import org.example.repositories.RecipeRepositoryFactory;
import org.example.repositories.UserRepositoryFactory;
import org.example.repositories.interfaces.Repository;
import org.example.servises.categoryService.CategoryService;
import org.example.servises.categoryService.CategoryServiceImpl;
import org.example.servises.recipeService.RecipeService;
import org.example.servises.recipeService.RecipeServiceImpl;
import org.example.servises.userService.UserService;
import org.example.servises.userService.UserServiceImpl;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Repository<User> userRepository = new UserRepositoryFactory().createRepository();
        Repository<Recipe> recipeRepository = new RecipeRepositoryFactory().createRepository();
        Repository<Category> categoryRepository = new CategoryRepositoryFactory().createRepository();

        UserService userService = new UserServiceImpl(userRepository);
        RecipeService recipeService = new RecipeServiceImpl(recipeRepository);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);

        UserController userController = new UserController(userService);
        RecipeController recipeController = new RecipeController(recipeService);
        CategoryController categoryController = new CategoryController(categoryService);

        Engine engine = new EngineImpl(userController, recipeController, categoryController);
        engine.run();
    }
}
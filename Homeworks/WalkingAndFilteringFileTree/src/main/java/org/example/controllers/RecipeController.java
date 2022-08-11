package org.example.controllers;

import org.example.common.InvalidEntityDataException;
import org.example.common.NonExistingEntityException;
import org.example.common.OutputMessages;
import org.example.entities.categories.Category;
import org.example.entities.recipes.Recipe;
import org.example.entities.users.User;
import org.example.servises.recipeService.RecipeService;

public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public String addRecipeWithDescription(String recipeTitle, User recipeAuthor, Category recipeCategory, String recipeShortDescription, Integer recipeCookingTime, String recipeProducts, String recipePictureURL, String[] recipeTags, String description) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.addRecipe(recipeTitle, recipeAuthor, recipeCategory, recipeShortDescription, recipeCookingTime, recipeProducts, recipePictureURL, recipeTags, description);
        return String.format(OutputMessages.SUCCESSFULLY_ADDED_RECIPE, recipe.getAuthor().getUserName(), recipe.getTitle());
    }

    public String addRecipeWithoutDescription(String recipeTitle, User recipeAuthor, Category recipeCategory, String recipeShortDescription, Integer recipeCookingTime, String recipeProducts, String recipePictureURL, String[] recipeTags) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.addRecipe(recipeTitle, recipeAuthor, recipeCategory, recipeShortDescription, recipeCookingTime, recipeProducts, recipePictureURL, recipeTags);
        return String.format(OutputMessages.SUCCESSFULLY_ADDED_RECIPE, recipe.getAuthor().getUserName(), recipe.getTitle());
    }

    public String editRecipeDescription(User editor, Long recipeId, String newDescription) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.editRecipeDescription(editor, recipeId, newDescription);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_RECIPE_DESCRIPTION, editor.getUserName(), recipe.getTitle());
    }

    public String editRecipeCategory(User editor, Long recipeId, Category newCategory) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.editRecipeCategory(editor, recipeId, newCategory);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_RECIPE_CATEGORY, editor.getUserName(), recipe.getTitle());
    }

    public String editRecipeTitle(User editor, Long recipeId, String newTitle) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.editRecipeTitle(editor, recipeId, newTitle);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_RECIPE_TITLE, editor.getUserName(), recipe.getTitle());
    }

    public String editRecipeShortDescription(User editor, Long recipeId, String newShortDescription) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.editRecipeShortDescription(editor, recipeId, newShortDescription);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_RECIPE_SHORT_DESCRIPTION, editor.getUserName(), recipe.getTitle());
    }

    public String editRecipeCookingTime(User editor, Long recipeId, int newCookingTime) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.editRecipeCookingTime(editor, recipeId, newCookingTime);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_RECIPE_COOKING_TIME, editor.getUserName(), recipe.getTitle());
    }

    public String editRecipeUsedProducts(User editor, Long recipeId, String newProducts) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.editRecipeUsedProducts(editor, recipeId, newProducts);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_RECIPE_PRODUCTS, editor.getUserName(), recipe.getTitle());
    }

    public String editRecipePictureURL(User editor, Long recipeId, String newURL) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.editRecipePictureURL(editor, recipeId, newURL);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_RECIPE_PICTURE_URL, editor.getUserName(), recipe.getTitle());
    }

    public String editRecipeTags(User editor, Long recipeId, String[] newTags) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.editRecipeTags(editor, recipeId, newTags);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_RECIPE_TAGS, editor.getUserName(), recipe.getTitle());
    }

    public String deleteRecipe(User editor, Long recipeId) throws InvalidEntityDataException, NonExistingEntityException {
        Recipe recipe = this.recipeService.deleteRecipe(editor, recipeId);
        return String.format(OutputMessages.SUCCESSFULLY_DELETED_RECIPE, editor.getUserName(), recipe.getTitle());
    }

    public String browseMyRecipes(User user) throws NonExistingEntityException {
        return this.recipeService.browseMyRecipes(user);
    }

    public String admBrowseUserRecipes(User activeUser, User user) throws InvalidEntityDataException, NonExistingEntityException {
        return this.recipeService.admBrowseUserRecipes(activeUser, user);
    }

    public String admDeleteRecipe(User activeUser, Long recipeId) throws InvalidEntityDataException, NonExistingEntityException {
        this.recipeService.admDeleteRecipe(activeUser, recipeId);
        return OutputMessages.RECIPE_DELETED_SUCCESSFULLY;
    }

    public String browseRecipeById(User activeUser, Long recipeId) throws NonExistingEntityException {
        return this.recipeService.browseRecipeById(activeUser, recipeId);
    }

    public String browseRecipeByTitle(User activeUser, String recipeTitle) throws NonExistingEntityException {
        return this.recipeService.browseRecipeByTitle(activeUser, recipeTitle);
    }

    public String browseRecipeByProducts(User activeUser, String[] products) throws NonExistingEntityException {
        return this.recipeService.browseRecipeByProducts(activeUser, products);
    }

    public String browseRecipeByShortDescription(User activeUser, String recipeShortDescription) throws NonExistingEntityException {
        return this.recipeService.browseRecipeByShortDescription(activeUser, recipeShortDescription);
    }

    public String browseRecipeByTags(User activeUser, String[] recipeTags) throws NonExistingEntityException {
        return this.recipeService.browseRecipeByTags(activeUser, recipeTags);
    }

    public String browseRecipeBetween(User activeUser, int minTime, int maxTime) throws NonExistingEntityException {
        return this.recipeService.browseRecipeBetween(activeUser, minTime, maxTime);
    }

    public String getMyFavouriteRecipes(User user) throws NonExistingEntityException {
        return this.recipeService.getMyFavouriteRecipes(user);
    }

    public String addFavouriteRecipe(User user, Long recipeId) throws NonExistingEntityException {
        this.recipeService.addFavouriteRecipe(user, recipeId);
        return String.format(OutputMessages.SUCCESSFULLY_SAVED_RECIPE, user.getUserName(), recipeId);
    }

    public String removeFavouriteRecipe(User user, Long recipeId) throws NonExistingEntityException {
        this.recipeService.removeFavouriteRecipe(user, recipeId);
        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_RECIPE, user.getUserName(), recipeId);
    }
}

package org.example.servises.recipeService;

import org.example.common.InvalidEntityDataException;
import org.example.common.NonExistingEntityException;
import org.example.entities.categories.Category;
import org.example.entities.recipes.Recipe;
import org.example.entities.users.User;

public interface RecipeService {
    Recipe addRecipe(String recipeTitle, User recipeAuthor, Category recipeCategory, String recipeShortDescription, Integer recipeCookingTime, String recipeProducts, String recipePictureURL, String[] recipeTags, String description) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe addRecipe(String recipeTitle, User recipeAuthor, Category recipeCategory, String recipeShortDescription, Integer recipeCookingTime, String recipeProducts, String recipePictureURL, String[] recipeTags) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe editRecipeDescription(User editor, Long recipeId, String newDescription) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe editRecipeCategory(User editor, Long recipeId, Category newCategory) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe editRecipeTitle(User editor, Long recipeId, String newTitle) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe editRecipeShortDescription(User editor, Long recipeId, String newShortDescription) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe editRecipeCookingTime(User editor, Long recipeId, int newCookingTime) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe editRecipeUsedProducts(User editor, Long recipeId, String newProducts) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe editRecipePictureURL(User editor, Long recipeId, String newURL) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe editRecipeTags(User editor, Long recipeId, String[] newTags) throws InvalidEntityDataException, NonExistingEntityException;

    Recipe deleteRecipe(User user, Long recipeId) throws InvalidEntityDataException, NonExistingEntityException;

    String browseMyRecipes(User user) throws NonExistingEntityException;

    void admDeleteRecipe(User activeUser, Long recipeId) throws InvalidEntityDataException, NonExistingEntityException;

    String admBrowseUserRecipes(User activeUser, User user) throws InvalidEntityDataException, NonExistingEntityException;

    String browseRecipeById(User activeUser, Long recipeId) throws NonExistingEntityException;

    String browseRecipeByTitle(User activeUser, String recipeTitle) throws NonExistingEntityException;

    String browseRecipeByProducts(User activeUser, String[] products) throws NonExistingEntityException;

    String browseRecipeByShortDescription(User activeUser, String recipeShortDescription) throws NonExistingEntityException;

    String browseRecipeByTags(User activeUser, String[] recipeTags) throws NonExistingEntityException;

    String browseRecipeBetween(User activeUser, int minTime, int maxTime) throws NonExistingEntityException;

    String getMyFavouriteRecipes(User user) throws NonExistingEntityException;

    void addFavouriteRecipe(User user, Long recipeId) throws NonExistingEntityException;

    void removeFavouriteRecipe(User user, Long recipeId) throws NonExistingEntityException;
}

package org.example.servises.recipeService;

import org.example.common.*;
import org.example.entities.categories.Category;
import org.example.entities.recipes.Recipe;
import org.example.entities.recipes.RecipeImpl;
import org.example.entities.users.Administrator;
import org.example.entities.users.HomeCook;
import org.example.entities.users.User;
import org.example.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeServiceImpl implements RecipeService {

    private final Repository<Recipe> recipeRepository;

    public RecipeServiceImpl(Repository<Recipe> recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe addRecipe(String recipeTitle, User recipeAuthor, Category recipeCategory, String recipeShortDescription, Integer recipeCookingTime, String recipeProducts, String recipePictureURL, String[] recipeTags, String description) throws InvalidEntityDataException, NonExistingEntityException {
        check(recipeAuthor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(recipeAuthor);
        checkValidity(recipeCategory == null, ExceptionMessages.THERE_IS_NO_SUCH_CATEGORY);
        Recipe recipe = new RecipeImpl(recipeCategory, recipeTitle, description, recipeAuthor, recipeShortDescription, recipeCookingTime, recipeProducts, recipePictureURL, recipeTags);
        recipeAuthor.getRecipes().add(recipe);
        this.recipeRepository.add(recipe);
        return recipe;
    }

    @Override
    public Recipe addRecipe(String recipeTitle, User recipeAuthor, Category recipeCategory, String recipeShortDescription, Integer recipeCookingTime, String recipeProducts, String recipePictureURL, String[] recipeTags) throws InvalidEntityDataException, NonExistingEntityException {
        check(recipeAuthor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(recipeAuthor);
        checkValidity(recipeCategory == null, ExceptionMessages.THERE_IS_NO_SUCH_CATEGORY);
        Recipe recipe = new RecipeImpl(recipeCategory, recipeTitle, recipeAuthor, recipeShortDescription, recipeCookingTime, recipeProducts, recipePictureURL, recipeTags);
        recipeAuthor.getRecipes().add(recipe);
        this.recipeRepository.add(recipe);
        return recipe;
    }

    @Override
    public Recipe editRecipeDescription(User editor, Long recipeId, String newDescription) throws InvalidEntityDataException, NonExistingEntityException {
        check(editor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(editor);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        checkValidity(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (recipe.getAuthor().getId().equals(editor.getId()) || editor.getRole().equals(Role.ADMIN)) {
            recipe.setDescription(newDescription);
            recipe.setModified();
        } else {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DOES_NOT_BELONGS_TO_USER);
        }
        return recipe;
    }

    @Override
    public Recipe editRecipeCategory(User editor, Long recipeId, Category newCategory) throws InvalidEntityDataException, NonExistingEntityException {
        check(editor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(editor);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        checkValidity(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        checkValidity(newCategory == null, ExceptionMessages.THERE_IS_NO_SUCH_CATEGORY);
        if (recipe.getAuthor().getId().equals(editor.getId()) || editor.getRole().equals(Role.ADMIN)) {
            recipe.setCategory(newCategory);
            recipe.setModified();
        } else {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DOES_NOT_BELONGS_TO_USER);
        }
        return recipe;
    }

    @Override
    public Recipe editRecipeTitle(User editor, Long recipeId, String newTitle) throws InvalidEntityDataException, NonExistingEntityException {
        check(editor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(editor);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        checkValidity(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (recipe.getAuthor().getId().equals(editor.getId()) || editor.getRole().equals(Role.ADMIN)) {
            recipe.setTitle(newTitle);
            recipe.setModified();
        } else {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DOES_NOT_BELONGS_TO_USER);
        }
        return recipe;
    }

    @Override
    public Recipe editRecipeShortDescription(User editor, Long recipeId, String newShortDescription) throws InvalidEntityDataException, NonExistingEntityException {
        check(editor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(editor);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        checkValidity(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (recipe.getAuthor().getId().equals(editor.getId()) || editor.getRole().equals(Role.ADMIN)) {
            recipe.setShortDescription(newShortDescription);
            recipe.setModified();
        } else {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DOES_NOT_BELONGS_TO_USER);
        }
        return recipe;
    }

    @Override
    public Recipe editRecipeCookingTime(User editor, Long recipeId, int newCookingTime) throws InvalidEntityDataException, NonExistingEntityException {
        check(editor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(editor);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        checkValidity(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (recipe.getAuthor().getId().equals(editor.getId()) || editor.getRole().equals(Role.ADMIN)) {
            recipe.setCookingTime(newCookingTime);
            recipe.setModified();
        } else {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DOES_NOT_BELONGS_TO_USER);
        }
        return recipe;
    }

    @Override
    public Recipe editRecipeUsedProducts(User editor, Long recipeId, String newProducts) throws InvalidEntityDataException, NonExistingEntityException {
        check(editor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(editor);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        checkValidity(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (recipe.getAuthor().getId().equals(editor.getId()) || editor.getRole().equals(Role.ADMIN)) {
            recipe.setUsedProducts(newProducts);
            recipe.setModified();
        } else {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DOES_NOT_BELONGS_TO_USER);
        }
        return recipe;
    }

    @Override
    public Recipe editRecipePictureURL(User editor, Long recipeId, String newURL) throws InvalidEntityDataException, NonExistingEntityException {
        check(editor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(editor);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        checkValidity(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (recipe.getAuthor().getId().equals(editor.getId()) || editor.getRole().equals(Role.ADMIN)) {
            recipe.setPictureURL(newURL);
            recipe.setModified();
        } else {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DOES_NOT_BELONGS_TO_USER);
        }
        return recipe;
    }

    @Override
    public Recipe editRecipeTags(User editor, Long recipeId, String[] newTags) throws InvalidEntityDataException, NonExistingEntityException {
        check(editor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(editor);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        check(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (recipe.getAuthor().getId().equals(editor.getId()) || editor.getRole().equals(Role.ADMIN)) {
            recipe.setTags(newTags);
            recipe.setModified();
        } else {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DOES_NOT_BELONGS_TO_USER);
        }
        return recipe;
    }

    private void check(boolean entity, String messageSentBackToTheClient) throws NonExistingEntityException {
        checkValidity(entity, messageSentBackToTheClient);
        //other check can be placed here...
    }

    private void checkValidity(boolean entity, String exceptionMessage) throws NonExistingEntityException {
        if (entity) {
            throw new NonExistingEntityException(exceptionMessage);
        }
    }

    @Override
    public Recipe deleteRecipe(User editor, Long recipeId) throws InvalidEntityDataException, NonExistingEntityException {
        check(editor == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(editor);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        check(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (recipe.getAuthor().getId().equals(editor.getId()) || editor.getRole().equals(Role.ADMIN)) {
            editor.getRecipes().remove(recipe);
            this.recipeRepository.remove(recipe);
        } else {
            throw new InvalidEntityDataException(ExceptionMessages.RECIPE_DOES_NOT_BELONGS_TO_USER);
        }
        return recipe;
    }

    @Override
    public String browseMyRecipes(User user) throws NonExistingEntityException {
        check(user == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(user);
        if (user.getRecipes().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.ZERO_RECIPES);
        }
        StringBuilder result = new StringBuilder();
        for (Recipe recipe : user.getRecipes()) {
            result.append("Recipe title: ").append(recipe.getTitle()).append("; Recipe ID: ").append(recipe.getId());
            result.append(System.lineSeparator());
        }
        return result.toString().trim();
    }

    @Override
    public void admDeleteRecipe(User activeUser, Long recipeId) throws InvalidEntityDataException, NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        if (!activeUser.getRole().equals(Role.ADMIN)) {
            throw new InvalidEntityDataException(ExceptionMessages.CANT_PERFORM_THIS_OPERATION_NOT_ADMIN);
        }
        Recipe recipe = this.recipeRepository.getById(recipeId);
        check(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        this.recipeRepository.remove(recipe);
        recipe.getAuthor().getRecipes().remove(recipe);
    }

    @Override
    public String admBrowseUserRecipes(User activeUser, User user) throws InvalidEntityDataException, NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        if (!activeUser.getRole().equals(Role.ADMIN)) {
            throw new InvalidEntityDataException(ExceptionMessages.CANT_PERFORM_THIS_OPERATION_NOT_ADMIN);
        }
        check(user == null, ExceptionMessages.THERE_IS_NO_SUCH_USER);
        if (user.getRecipes().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.USER_HAS_NO_RECIPES);
        }
        return browseMyRecipes(user);
    }

    @Override
    public String browseRecipeById(User activeUser, Long recipeId) throws NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(activeUser);
        Recipe recipe = this.recipeRepository.getById(recipeId);
        check(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        return recipe.toString();
    }

    @Override
    public String browseRecipeByTitle(User activeUser, String recipeTitle) throws NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(activeUser);
        List<Recipe> recipes = this.recipeRepository.getAll().stream().filter(recipe -> recipe.getTitle().equals(recipeTitle)).collect(Collectors.toList());
        return producedRecipes(recipes);
    }

    @Override
    public String browseRecipeByTags(User activeUser, String[] tags) throws NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(activeUser);
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : this.recipeRepository.getAll()) {
            for (String tag : recipe.getTags()) {
                if (Arrays.asList(tags).contains(tag)) {
                    recipes.add(recipe);
                    break;
                }
            }
        }
        return producedRecipes(recipes);
    }

    @Override
    public String browseRecipeByShortDescription(User activeUser, String recipeShortDescription) throws NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(activeUser);
        List<Recipe> recipes = this.recipeRepository.getAll().stream().filter(recipe -> recipe.getShortDescription().equals(recipeShortDescription)).collect(Collectors.toList());
        return producedRecipes(recipes);
    }

    @Override
    public String browseRecipeByProducts(User activeUser, String[] recipeProducts) throws NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(activeUser);
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : this.recipeRepository.getAll()) {
            for (String product : recipeProducts) {
                if (recipe.getUsedProducts().contains(product)) {
                    recipes.add(recipe);
                    break;
                }
            }
        }
        return producedRecipes(recipes);
    }

    @Override
    public String browseRecipeBetween(User activeUser, int minTime, int maxTime) throws NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(activeUser);
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : this.recipeRepository.getAll()) {
            if (recipe.getCookingTime() >= minTime && recipe.getCookingTime() <= maxTime) {
                recipes.add(recipe);
            }
        }
        return producedRecipes(recipes);
    }

    @Override
    public String getMyFavouriteRecipes(User user) throws NonExistingEntityException {
        check(user == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(user);
        if (user instanceof Administrator){
            throw new IllegalArgumentException(ExceptionMessages.ADMINISTRATORS_DOES_NOT_HAVE_FAVOURITE_SECTION);
        }
        List<Recipe> favRecipes;
        switch (user){
            case HomeCook homeCook -> favRecipes = homeCook.getFavoriteRecipes();
            default -> throw new IllegalArgumentException(ExceptionMessages.ADMINISTRATORS_DOES_NOT_HAVE_FAVOURITE_SECTION);
        }
        if (favRecipes.isEmpty()){
            throw new NullPointerException(ExceptionMessages.NO_FAVOURITE_RECIPES_SAVED);
        }
        StringBuilder sb = new StringBuilder();
        for (Recipe favRecipe : favRecipes) {
            sb.append("Recipe: ").append(favRecipe.getTitle()).append(" id(").append(favRecipe.getId()).append(")").append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public void addFavouriteRecipe(User user, Long recipeId) throws NonExistingEntityException {
        check(user == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(user);
        Recipe recipe = recipeRepository.getById(recipeId);
        check(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (user instanceof Administrator){
            throw new IllegalArgumentException(ExceptionMessages.ADMINISTRATORS_DOES_NOT_HAVE_FAVOURITE_SECTION);
        }
        if (user instanceof HomeCook homeCook){
            homeCook.getFavoriteRecipes().add(recipe);
        }

    }

    @Override
    public void removeFavouriteRecipe(User user, Long recipeId) throws NonExistingEntityException {
        check(user == null, ExceptionMessages.NOT_LOGGED_IN);
        userStatusChecker(user);
        Recipe recipe = recipeRepository.getById(recipeId);
        check(recipe == null, ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        if (user instanceof Administrator){
            throw new IllegalArgumentException(ExceptionMessages.ADMINISTRATORS_DOES_NOT_HAVE_FAVOURITE_SECTION);
        }
        if (user instanceof HomeCook homeCook){
            if (!homeCook.getFavoriteRecipes().remove(recipe)){
                throw new NonExistingEntityException(ExceptionMessages.THE_USER_IS_NOT_PART_OF_FAVOURITE_COLLECTION);
            }
        }
    }

    private String producedRecipes(List<Recipe> recipes) throws NonExistingEntityException {
        if (recipes.isEmpty()) {
            throw new NonExistingEntityException(ExceptionMessages.THERE_IS_NO_SUCH_RECIPE);
        }
        StringBuilder sb = new StringBuilder();
        for (Recipe recipe : recipes) {
            sb.append(recipe.getTitle()).append(" with ID: ").append(recipe.getId()).append(" by ").append(recipe.getAuthor().getUserName()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    private void userStatusChecker(User user) {
        if (user.getStatus().equals(AccountStatus.DEACTIVATED)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.YOU_CANT_USE_YOUR_ACCOUNT_NOW, user.getUserName(), AccountStatus.DEACTIVATED));
        } else if (user.getStatus().equals(AccountStatus.SUSPENDED)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.YOU_CANT_USE_YOUR_ACCOUNT_NOW, user.getUserName(), AccountStatus.SUSPENDED));
        }
    }
}

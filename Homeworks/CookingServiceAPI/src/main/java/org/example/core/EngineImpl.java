package org.example.core;

import org.example.common.Command;
import org.example.common.ExceptionMessages;
import org.example.common.InvalidEntityDataException;
import org.example.common.NonExistingEntityException;
import org.example.controllers.CategoryController;
import org.example.controllers.RecipeController;
import org.example.controllers.UserController;
import org.example.core.interfaces.Engine;
import org.example.entities.categories.Category;
import org.example.entities.users.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class EngineImpl implements Engine {

    private final UserController userController;
    private final RecipeController recipeController;
    private final CategoryController categoryController;
    private final Scanner scanner;

    public EngineImpl(UserController userController, RecipeController recipeController, CategoryController categoryController) throws FileNotFoundException {
        this.userController = userController;
        this.recipeController = recipeController;
        this.categoryController = categoryController;
        this.scanner = new Scanner(new FileInputStream("src/main/java/org/example/TestInput"));
    }

    @Override
    public void run() {
        while (true) {
            String result;
            try {
                result = processInput();

                if (result.equals(Command.End.name())) {
                    break;
                }

            } catch (IOException | IllegalArgumentException | NullPointerException | InvalidEntityDataException | NonExistingEntityException e) {
                result = e.getMessage();
            } catch (Exception exception) {
                result = ExceptionMessages.INVALID_INPUT;
            }

            System.out.println(result);
        }
    }

    private String processInput() throws IOException, InvalidEntityDataException, NonExistingEntityException {
        String input = scanner.nextLine();
        String[] tokens = input.split("\\s+");

        Command command = Command.valueOf(tokens[0]);
        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);

        String result = null;

        switch (command) {
            case Register:
                result = this.registerUser(data);
                break;
            case Login:
                result = this.login(data);
                break;
            case Logout:
                result = this.logout();
                break;
            case ChangeFirstName:
                result = this.changeFirstName(data);
                break;
            case ChangeLastName:
                result = this.changeLastName(data);
                break;
            case ChangeGender:
                result = this.changeGender();
                break;
            case ChangePassword:
                result = this.changePassword(data);
                break;
            case ADMChangeUserFirstName:
                result = this.admChangeUserFirstName(data);
                break;
            case ADMChangeUserLastName:
                result = this.admChangeUserLastName(data);
                break;
            case ADMChangeUserGender:
                result = this.admChangeUserGender(data);
                break;
            case ADMChangeUserRole:
                result = this.admChangeUserRole(data);
                break;
            case ADMChangeUserStatus:
                result = this.admChangeUserStatus(data);
                break;
            case ADMViewAllUsers:
                result = this.admViewAllUsers();
                break;
            case AddRecipe:
                result = this.addRecipe(input);
                break;
            case EditRecipeDescription:
                result = this.editRecipeDescription(input);
                break;
            case EditRecipeTitle:
                result = this.editRecipeTitle(input);
                break;
            case EditRecipeCategory:
                result = this.editRecipeCategory(data);
                break;
            case EditRecipeShortDescription:
                result = this.editRecipeShortDescription(input);
                break;
            case EditRecipeCookingTime:
                result = this.editRecipeCookingTime(data);
                break;
            case EditRecipeUsedProducts:
                result = this.editRecipeUsedProducts(input);
                break;
            case EditRecipePictureURL:
                result = this.editRecipePictureURL(data);
                break;
            case EditRecipeTags:
                result = this.editRecipeTags(input);
                break;
            case DeleteRecipe:
                result = this.deleteRecipe(data);
                break;
            case BrowseMyRecipes:
                result = this.browseMyRecipes();
                break;
            case ADMBrowseUserAllRecipes:
                result = this.admBrowseUserRecipes(data);
                break;
            case ADMDeleteRecipe:
                result = this.admDeleteRecipe(data);
                break;
            case BrowseRecipeById:
                result = this.browseRecipeById(data);
                break;
            case BrowseRecipeByTitle:
                result = this.browseRecipeByTitle(input);
                break;
            case BrowseRecipeByProducts:
                result = this.browseRecipeByProducts(input);
                break;
            case BrowseRecipeByShortDescription:
                result = this.browseRecipeByShortDescription(input);
                break;
            case BrowseRecipeByTags:
                result = this.browseRecipeByTags(input);
                break;
            case BrowseRecipeBetween:
                result = this.browseRecipeBetween(data);
                break;
            case Info:
                result = this.showUserInfo();
                break;
            case ADMAddCategory:
                result = this.admAddCategory(input);
                break;
            case ADMEditCategoryName:
                result = this.admEditCategoryName(data, input);
                break;
            case ADMEditCategoryDescription:
                result = this.admEditCategoryDescription(input);
                break;
            case ADMEditCategoryTags:
                result = this.admEditCategoryTags(data);
                break;
            case BrowseAllCategories:
                result = this.browseAllCategories();
                break;
            case BrowseCategory:
                result = this.browseCategory(data);
                break;
            case AddFavouriteRecipe:
                result = this.addFavouriteRecipe(data);
                break;
            case RemoveFavouriteRecipe:
                result = this.removeFavouriteRecipe(data);
                break;
            case GetMyFavouriteRecipes:
                result = this.getMyFavouriteRecipes();
                break;
            case AddFavouriteCook:
                result = this.addFavouriteCook(data);
                break;
            case RemoveFavouriteCook:
                result = this.removeFavouriteCook(data);
                break;
            case GetMyFavouriteCooks:
                result = this.getMyFavouriteCooks();
                break;
            case ADMShowMyCategories:
                result = this.admShowMyCategories();
                break;
            case End:
                result = Command.End.name();
                break;
        }

        return result;
    }

    private String admShowMyCategories() throws NonExistingEntityException {
        User user = this.userController.getActiveUser();
        return this.userController.admShowMyCategories(user);
    }

    private String removeFavouriteCook(String[] data) throws NonExistingEntityException {
        //RemoveFavouriteCook {cookId}
        User user = this.userController.getActiveUser();
        Long cookId = Long.parseLong(data[0]);
        return this.userController.removeFavouriteCook(user, cookId);
    }

    private String removeFavouriteRecipe(String[] data) throws NonExistingEntityException {
        //RemoveFavouriteRecipe {recipeId}
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(data[0]);
        return this.recipeController.removeFavouriteRecipe(user, recipeId);
    }

    private String getMyFavouriteCooks() throws NonExistingEntityException {
        //GetMyFavouriteCooks
        User user = this.userController.getActiveUser();
        return this.userController.getMyFavouriteCooks(user);

    }

    private String addFavouriteCook(String[] data) throws NonExistingEntityException {
        //AddFavouriteCook {cookId}
        User user = this.userController.getActiveUser();
        Long cookId = Long.parseLong(data[0]);
        return this.userController.addFavouriteCook(user, cookId);

    }

    private String getMyFavouriteRecipes() throws NonExistingEntityException {
        //GetMyFavouriteRecipes
        User user = this.userController.getActiveUser();
        return this.recipeController.getMyFavouriteRecipes(user);
    }

    private String addFavouriteRecipe(String[] data) throws NonExistingEntityException {
        //AddFavouriteRecipe {recipeId}
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(data[0]);
        return this.recipeController.addFavouriteRecipe(user, recipeId);
    }

    private String browseCategory(String[] data) throws NonExistingEntityException {
        //BrowseCategory {categoryId}
        User user = this.userController.getActiveUser();
        Long categoryId = Long.parseLong(data[0]);
        return this.categoryController.browseCategory(user, categoryId);
    }

    private String browseAllCategories() throws NonExistingEntityException {
        //BrowseAllCategories
        User user = this.userController.getActiveUser();
        return this.categoryController.browseAllCategories(user);
    }

    private String admEditCategoryTags(String[] data) throws NonExistingEntityException {
        //ADMEditCategoryTags {categoryId} {newTags(separatedOnlyBySingleComma)}
        this.userController.verifyAdminStatus();
        User user = this.userController.getActiveUser();
        Long categoryId = Long.parseLong(data[0]);
        Category category = this.categoryController.getCategoryById(user, categoryId);
        String[] newTags = data[1].split(",");
        return this.categoryController.admEditCategoryTags(user, category, newTags);
    }

    private String admEditCategoryDescription(String input) throws InvalidEntityDataException, NonExistingEntityException {
        //ADMEditCategoryDescription {categoryId} {newDescription}
        this.userController.verifyAdminStatus();
        User user = this.userController.getActiveUser();
        String[] data = input.split("\\s+", 3);
        Long categoryId = Long.parseLong(data[1]);
        Category category = this.categoryController.getCategoryById(user, categoryId);
        String newDescription = data[2];
        return this.categoryController.admEditCategoryDescription(user, category, newDescription);
    }

    private String admEditCategoryName(String[] data, String input) throws InvalidEntityDataException, NonExistingEntityException {
        //ADMEditCategoryName {categoryId} {newName}
        this.userController.verifyAdminStatus();
        User user = this.userController.getActiveUser();
        Long categoryId = Long.parseLong(data[0]);
        Category category = this.categoryController.getCategoryById(user, categoryId);
        String newName = input.split("\\s+", 3)[2];
        return this.categoryController.admEditCategoryName(user, category, newName);
    }

    private String admAddCategory(String input) throws InvalidEntityDataException, NonExistingEntityException {
        this.userController.verifyAdminStatus();
        //ADMAddCategory {categoryName};{tags};{description}(optional)
        String[] data = input.split("\\s+", 2);
        User user = this.userController.getActiveUser();
        String categoryName = data[1].split(";")[0];
        String[] tags = data[1].split(";")[1].split(",");
        if (data[1].split(";").length > 2) {
            String description = data[1].split(";")[2];
            return this.categoryController.admAddCategoryWithDescription(user, categoryName, description, tags);
        }
        return this.categoryController.admAddCategoryWithoutDescription(user, categoryName, tags);
    }

    private String editRecipeTitle(String input) throws InvalidEntityDataException, NonExistingEntityException {
        //EditRecipeTitle {recipeId} {newTitle}
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(input.split("\\s+")[1]);
        String newTitle = input.split(String.valueOf(recipeId))[1].trim();
        return this.recipeController.editRecipeTitle(user, recipeId, newTitle);
    }

    private String editRecipeCategory(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        //EditRecipeCategory {recipeId} {categoryId}
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(data[0]);
        Category newCategory = this.categoryController.getCategoryById(user, Long.parseLong(data[1]));
        return this.recipeController.editRecipeCategory(user, recipeId, newCategory);
    }

    private String editRecipeShortDescription(String input) throws InvalidEntityDataException, NonExistingEntityException {
        //EditRecipeShortDescription {recipeId} {newShortDescription}
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(input.split("\\s+")[1]);
        String newShortDescription = input.split(String.valueOf(recipeId))[1].trim();
        return this.recipeController.editRecipeShortDescription(user, recipeId, newShortDescription);
    }

    private String editRecipeCookingTime(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        //EditRecipeCookingTime {recipeId} {newCookingTime}
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(data[0]);
        int newCookingTime = Integer.parseInt(data[1]);
        return this.recipeController.editRecipeCookingTime(user, recipeId, newCookingTime);
    }

    private String editRecipeUsedProducts(String input) throws InvalidEntityDataException, NonExistingEntityException {
        //EditRecipeUsedProducts {recipeId} {product1, product2, product3, productNth... }
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(input.split("\\s+")[1]);
        String newProducts = input.split(String.valueOf(recipeId))[1].trim();
        return this.recipeController.editRecipeUsedProducts(user, recipeId, newProducts);
    }

    private String editRecipePictureURL(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        //EditRecipePictureURL {recipeId} {newPictureUrl}
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(data[0]);
        String newPictureURL = data[1];
        return this.recipeController.editRecipePictureURL(user, recipeId, newPictureURL);
    }

    private String editRecipeTags(String input) throws InvalidEntityDataException, NonExistingEntityException {
        //EditRecipeTags {recipeId} {tag1, tag2, tag3, tagNth...}
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(input.split("\\s+")[1]);
        String[] newProducts = input.split(String.valueOf(recipeId))[1].trim().split(", ");
        return this.recipeController.editRecipeTags(user, recipeId, newProducts);
    }

    private String deleteRecipe(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        //DeleteRecipe {recipeId}
        User user = userController.getActiveUser();
        Long recipeId = Long.parseLong(data[0]);
        return this.recipeController.deleteRecipe(user, recipeId);
    }

    private String browseMyRecipes() throws NonExistingEntityException {
        //BrowseMyRecipes
        User user = this.userController.getActiveUser();
        return this.recipeController.browseMyRecipes(user);
    }

    private String admBrowseUserRecipes(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        //ADMBrowseUserAllRecipes {userId}
        this.userController.verifyAdminStatus();
        User activeUser = this.userController.getActiveUser();
        User user = this.userController.getUserById(Long.parseLong(data[0]));
        return this.recipeController.admBrowseUserRecipes(activeUser, user);
    }

    private String admDeleteRecipe(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        //ADMDeleteRecipe {recipeId}
        this.userController.verifyAdminStatus();
        User activeUser = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(data[0]);
        return this.recipeController.admDeleteRecipe(activeUser, recipeId);
    }

    private String browseRecipeById(String[] data) throws NonExistingEntityException {
        //BrowseRecipeById {recipeId}
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(data[0]);
        return this.recipeController.browseRecipeById(user, recipeId);
    }

    private String browseRecipeByTitle(String input) throws NonExistingEntityException {
        //BrowseRecipeByTitle {recipeTitle}
        User user = this.userController.getActiveUser();
        String newRecipeTitle = input.split(" ", 2)[1];
        return this.recipeController.browseRecipeByTitle(user, newRecipeTitle);
    }

    private String browseRecipeByProducts(String input) throws NonExistingEntityException {
        //BrowseRecipeByProducts {product1} {product2} {product3}
        User user = this.userController.getActiveUser();
        String[] products = input.split("\\s+", 2)[1].split("\\s+");
        return this.recipeController.browseRecipeByProducts(user, products);
    }

    private String browseRecipeByShortDescription(String input) throws NonExistingEntityException {
        //BrowseRecipeByShortDescription {shortDescription}
        User user = this.userController.getActiveUser();
        String shortDescription = input.split("\\s+", 2)[1];
        return this.recipeController.browseRecipeByShortDescription(user, shortDescription);
    }

    private String browseRecipeByTags(String input) throws NonExistingEntityException {
        //BrowseRecipeByTags {tag1},{tag2},tag{3},{tag4}
        User user = this.userController.getActiveUser();
        String[] tags = input.split("\\s+", 2)[1].split(",");
        return this.recipeController.browseRecipeByTags(user, tags);
    }

    private String browseRecipeBetween(String[] data) throws NonExistingEntityException {
        //BrowseRecipeBetween {minCookingTime} {maxCookingTime}
        User user = this.userController.getActiveUser();
        int minTime = Integer.parseInt(data[0]);
        int maxTime = Integer.parseInt(data[1]);
        return this.recipeController.browseRecipeBetween(user, minTime, maxTime);
    }

    private String editRecipeDescription(String input) throws InvalidEntityDataException, NonExistingEntityException {
        //EditRecipeDescription 1000 Better version of the pasta 'Italiano Pecorino'
        User user = this.userController.getActiveUser();
        Long recipeId = Long.parseLong(input.split("\\s+")[1]);
        String newDescription = input.split(String.valueOf(recipeId))[1].trim();
        return this.recipeController.editRecipeDescription(user, recipeId, newDescription);
    }

    private String addRecipe(String input) throws InvalidEntityDataException, NonExistingEntityException {
        //
        String[] data = input.split(";");
        String recipeTitle = data[0].substring(10);
        User recipeAuthor = this.userController.getActiveUser();
        Category recipeCategory = this.categoryController.getCategoryById(recipeAuthor, Long.parseLong(data[1]));
        String recipeShortDescription = data[2];
        Integer recipeCookingTime = Integer.parseInt(data[3]);
        String recipeProducts = data[4];
        String recipePictureURL = data[5];
        String[] recipeTags = data[6].split(",");
        if (data.length > 7) {
            String description = data[7];
            return this.recipeController.addRecipeWithDescription(recipeTitle, recipeAuthor, recipeCategory, recipeShortDescription, recipeCookingTime, recipeProducts, recipePictureURL, recipeTags, description);
        }
        return this.recipeController.addRecipeWithoutDescription(recipeTitle, recipeAuthor, recipeCategory, recipeShortDescription, recipeCookingTime, recipeProducts, recipePictureURL, recipeTags);
    }

    private String admViewAllUsers() throws NonExistingEntityException {
        return this.userController.admViewAllUsers();
    }

    private String admChangeUserStatus(String[] data) throws NonExistingEntityException {
        String id = data[0];
        String newAccountStatus = data[1];
        return this.userController.admChangeUserStatus(id, newAccountStatus);
    }

    private String admChangeUserRole(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        String id = data[0];
        String newRole = data[1];
        return this.userController.admChangeUserRole(id, newRole);
    }

    private String admChangeUserGender(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        String id = data[0];
        return this.userController.admChangeUserGender(id);
    }

    private String admChangeUserLastName(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        String id = data[0];
        String newLastName = data[1];
        return this.userController.admChangeUserLastName(id, newLastName);
    }

    private String admChangeUserFirstName(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        String username = data[0];
        String newFirstName = data[1];
        return this.userController.admChangeUserFirstName(username, newFirstName);
    }

    private String changePassword(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        String newPassword = data[0];
        return this.userController.changePassword(newPassword);
    }

    private String showUserInfo() {
        return this.userController.showUserInfo();
    }

    private String changeGender() throws InvalidEntityDataException, NonExistingEntityException {
        return this.userController.changeGender();
    }

    private String changeLastName(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        String newName = data[0];
        return this.userController.changeLastName(newName);
    }

    private String changeFirstName(String[] data) throws InvalidEntityDataException, NonExistingEntityException {
        String newName = data[0];
        return this.userController.changeFirstName(newName);
    }

    private String login(String[] data) throws FileNotFoundException, InvalidEntityDataException, NonExistingEntityException {
        String username = data[0];
        String password = data[1];
        return this.userController.login(username, password);
    }

    private String logout() throws NonExistingEntityException {
        return this.userController.logout();
    }

    private String registerUser(String[] data) throws InvalidEntityDataException {
        return userController.registerNewUser(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
    }
}

package org.example.controllers;

import org.example.common.InvalidEntityDataException;
import org.example.common.NonExistingEntityException;
import org.example.common.OutputMessages;
import org.example.entities.categories.Category;
import org.example.entities.users.User;
import org.example.servises.categoryService.CategoryService;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    public Category getCategoryById(User activeUser, Long id) throws NonExistingEntityException {
        return this.categoryService.browseCategory(activeUser, id);
    }

    public String admAddCategoryWithoutDescription(User activeUser, String categoryName, String[] tags) throws InvalidEntityDataException, NonExistingEntityException {
        Category category = this.categoryService.admAddCategory(activeUser, categoryName, tags);
        return String.format(OutputMessages.SUCCESSFULLY_CREATED_CATEGORY, category.getId());
    }

    public String admAddCategoryWithDescription(User activeUser, String categoryName, String description, String[] tags) throws InvalidEntityDataException, NonExistingEntityException {
        Category category = this.categoryService.admAddCategory(activeUser, categoryName, tags, description);
        return String.format(OutputMessages.SUCCESSFULLY_CREATED_CATEGORY, category.getId());
    }

    public String admEditCategoryName(User activeUser, Category category, String newName) throws InvalidEntityDataException, NonExistingEntityException {
        this.categoryService.admEditCategoryName(activeUser, category, newName);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_CATEGORY_NAME, activeUser.getUserName(), category.getName());
    }

    public String admEditCategoryDescription(User activeUser, Category category, String newDescription) throws InvalidEntityDataException, NonExistingEntityException {
        this.categoryService.admEditCategoryDescription(activeUser, category, newDescription);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_CATEGORY_DESCRIPTION, activeUser.getUserName(), category.getName());
    }

    public String admEditCategoryTags(User activeUser, Category category, String[] newTags) throws NonExistingEntityException {
        this.categoryService.admEditCategoryTags(activeUser, category, newTags);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_CATEGORY_TAGS, activeUser.getUserName(), category.getName());
    }

    public String browseAllCategories(User activeUser) throws NonExistingEntityException {
        return this.categoryService.browseAllCategories(activeUser);
    }

    public String browseCategory(User activeUser, Long id) throws NonExistingEntityException {
        Category category = this.categoryService.browseCategory(activeUser, id);
        return category.toString();
    }

}

package org.example.servises.categoryService;

import org.example.common.InvalidEntityDataException;
import org.example.common.NonExistingEntityException;
import org.example.entities.categories.Category;
import org.example.entities.users.User;

import java.util.List;

public interface CategoryService {
    Category admAddCategory(User activeUser, String name, String[] tags) throws InvalidEntityDataException, NonExistingEntityException;
    Category admAddCategory(User activeUser, String name, String[] tags, String description) throws InvalidEntityDataException, NonExistingEntityException;
    Category admEditCategoryName(User activeUser, Category category, String newName) throws InvalidEntityDataException, NonExistingEntityException;
    Category admEditCategoryDescription(User activeUser, Category category, String newDescription) throws InvalidEntityDataException, NonExistingEntityException;
    Category admEditCategoryTags(User activeUser, Category category, String[] newTags) throws NonExistingEntityException;
    String browseAllCategories(User activeUser) throws NonExistingEntityException;
    Category browseCategory(User activeUser, Long id) throws NonExistingEntityException;
}

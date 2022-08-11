package org.example.servises.categoryService;

import org.example.common.ExceptionMessages;
import org.example.common.InvalidEntityDataException;
import org.example.common.NonExistingEntityException;
import org.example.entities.categories.Category;
import org.example.entities.categories.CategoryImpl;
import org.example.entities.users.Administrator;
import org.example.entities.users.User;
import org.example.repositories.interfaces.Repository;

public class CategoryServiceImpl implements CategoryService {

    private final Repository<Category> categoryRepository;

    public CategoryServiceImpl(Repository<Category> categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category admAddCategory(User activeUser, String name, String[] tags) throws InvalidEntityDataException, NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        Category category = new CategoryImpl(name, tags);
        switch (activeUser){
            case Administrator administrator -> administrator.addModeratedCategory(category);
            default -> throw new IllegalStateException("Unexpected value: " + activeUser);
        }
        this.categoryRepository.add(category);
        return category;
    }

    @Override
    public Category admAddCategory(User activeUser, String name, String[] tags, String description) throws InvalidEntityDataException, NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        Category category = new CategoryImpl(name, description, tags);
        switch (activeUser){
            case Administrator administrator -> administrator.addModeratedCategory(category);
            default -> throw new IllegalStateException("Unexpected value: " + activeUser);
        }
        this.categoryRepository.add(category);
        return category;
    }

    @Override
    public Category admEditCategoryName(User activeUser, Category category, String newName) throws InvalidEntityDataException, NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        check(category == null, ExceptionMessages.THERE_IS_NO_SUCH_CATEGORY);
        category.setName(newName);
        category.setModified();
        return category;
    }

    @Override
    public Category admEditCategoryDescription(User activeUser, Category category, String newDescription) throws InvalidEntityDataException, NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        check(category == null, ExceptionMessages.THERE_IS_NO_SUCH_CATEGORY);
        category.setDescription(newDescription);
        category.setModified();
        return category;
    }

    @Override
    public Category admEditCategoryTags(User activeUser, Category category, String[] newTags) throws NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        check(category == null, ExceptionMessages.THERE_IS_NO_SUCH_CATEGORY);
        category.setTags(newTags);
        category.setModified();
        return category;
    }

    @Override
    public String browseAllCategories(User activeUser) throws NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        if (this.categoryRepository.getAll().isEmpty()){
            throw new NullPointerException(ExceptionMessages.NO_REGISTERED_CATEGORIES_IN_THE_SYSTEM);
        }
        StringBuilder sb = new StringBuilder();
        for (Category category : this.categoryRepository.getAll()) {
            sb.append("Category '")
                    .append(category.getName())
                    .append("' with ID - ")
                    .append(category.getId())
                    .append(". Created on ")
                    .append(category.getCreated())
                    .append(".")
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public Category browseCategory(User activeUser, Long id) throws NonExistingEntityException {
        check(activeUser == null, ExceptionMessages.NOT_LOGGED_IN);
        Category category = this.categoryRepository.getById(id);
        check(category == null, ExceptionMessages.THERE_IS_NO_SUCH_CATEGORY);
        return category;
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
}

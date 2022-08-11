package org.example.entities.users;

import org.example.common.InvalidEntityDataException;
import org.example.entities.categories.Category;

import java.util.ArrayList;
import java.util.List;

public final class Administrator extends UserImpl{

    private List<Category> categoriesModerated;
    public Administrator(String firstName, String lastName, String email, String userName, String password, String gender) throws InvalidEntityDataException {
        super(firstName, lastName, email, userName, password, gender);
        this.categoriesModerated = new ArrayList<>();
    }

    public List<Category> getCategoriesModerated() {
        return categoriesModerated;
    }

    public void addModeratedCategory(Category category){
        this.categoriesModerated.add(category);
    }

    public void removeModeratedCategory(Category category){
        this.categoriesModerated.remove(category);
    }
}

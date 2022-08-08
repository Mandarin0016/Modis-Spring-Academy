package org.example.repositories;

import org.example.entities.categories.Category;
import org.example.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CategoryRepository implements Repository<Category> {

    private List<Category> categories;

    CategoryRepository() {
        categories = new ArrayList<>();
    }

    @Override
    public Category getById(Long id) {
        for (Category category : categories) {
            if (Objects.equals(category.getId(), id)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public Collection<Category> getAll() {
        return categories;
    }

    @Override
    public void add(Category unit) {
        this.categories.add(unit);
    }

    @Override
    public boolean remove(Category unit) {
        return this.categories.remove(unit);
    }
}


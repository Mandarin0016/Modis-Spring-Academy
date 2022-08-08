package org.example.repositories;

import org.example.entities.categories.Category;
import org.example.repositories.interfaces.Repository;
import org.example.repositories.interfaces.RepositoryFactory;

public class CategoryRepositoryFactory implements RepositoryFactory<Category> {
    @Override
    public Repository<Category> createRepository() {
        return new CategoryRepository();
    }
}

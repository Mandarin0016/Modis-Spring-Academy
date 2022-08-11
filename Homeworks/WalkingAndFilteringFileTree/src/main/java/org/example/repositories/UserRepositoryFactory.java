package org.example.repositories;

import org.example.entities.users.User;
import org.example.repositories.interfaces.Repository;
import org.example.repositories.interfaces.RepositoryFactory;

public class UserRepositoryFactory implements RepositoryFactory<User> {
    @Override
    public Repository<User> createRepository() {
        return new UserRepository();
    }
}

package org.example.repositories;

import org.example.entities.users.User;
import org.example.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserRepository implements Repository<User> {

    private List<User> users;

    UserRepository() {
        users = new ArrayList<>();
    }

    @Override
    public User getById(Long id) {
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return this.users;
    }

    @Override
    public void add(User unit) {
        this.users.add(unit);
    }

    @Override
    public boolean remove(User unit) {
        return this.users.remove(unit);
    }

    public User getByUsername(String username) {
        for (User user : this.users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

}

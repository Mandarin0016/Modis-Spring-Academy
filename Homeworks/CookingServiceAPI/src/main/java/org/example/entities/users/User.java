package org.example.entities.users;

import org.example.common.AccountStatus;
import org.example.common.Gender;
import org.example.common.InvalidEntityDataException;
import org.example.common.Role;
import org.example.entities.recipes.Recipe;

import java.util.List;

public interface User {
    void setId();

    void setFirstName(String firstName) throws InvalidEntityDataException;

    void setLastName(String lastName) throws InvalidEntityDataException;

    void setUserName(String userName) throws InvalidEntityDataException;

    void setPassword(String password) throws InvalidEntityDataException;

    void setGender(String gender) throws InvalidEntityDataException;

    void setRole(Role role);

    void setStatus(AccountStatus status);

    void setModified();

    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getUserName();

    String getPassword();

    Gender getGender();

    Role getRole();

    AccountStatus getStatus();

    String getCreated();

    String getModified();

    List<Recipe> getRecipes();

    //The bellow methods are supportive only! Please use the normal setters and getters!
    void setForceId(Long id);

    void setForceCreated(String created);

    String getForcePassword();

    void setRecipes(List<Recipe> recipes);

}

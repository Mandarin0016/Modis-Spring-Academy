package org.example.entities.users;

import com.google.common.hash.Hashing;
import org.example.common.*;
import org.example.entities.recipes.Recipe;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public abstract class UserImpl implements User {
    private static long counter = 1000;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Gender gender;
    protected Role role;
    private AccountStatus status;
    private List<Recipe> recipes;
    private String created;
    private String modified;

    public UserImpl(String firstName,
                    String lastName,
                    String email,
                    String userName,
                    String password,
                    String gender) throws InvalidEntityDataException {
        setId();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setUserName(userName);
        setPassword(password);
        setGender(gender);
        setRole(Role.HOME_COOK);
        setStatus(AccountStatus.ACTIVE);
        this.recipes = new ArrayList<>();
        this.created = new Timestamp(System.currentTimeMillis()).toString().split("\\.")[0];
        setModified();
    }

    public void setId() {
        this.id = counter++;
    }

    public void setFirstName(String firstName) throws InvalidEntityDataException {
        if (firstName == null || firstName.trim().isEmpty() || firstName.length() < 2 || firstName.length() > 15) {
            throw new InvalidEntityDataException(ExceptionMessages.NAME_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) throws InvalidEntityDataException {
        if (firstName == null || firstName.trim().isEmpty() || firstName.length() < 2 || firstName.length() > 15) {
            throw new InvalidEntityDataException(ExceptionMessages.NAME_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.lastName = lastName;
    }

    private void setEmail(String email) throws InvalidEntityDataException {
        if (!email.matches("^(.+)@(\\S+)(\\.{1}(\\w{1,}))*(\\.{1}(\\w{1,})){1,}$")) {
            throw new InvalidEntityDataException(ExceptionMessages.EMAIL_ADDRESS_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.email = email;
    }

    public void setUserName(String userName) throws InvalidEntityDataException {
        if (userName.length() < 2 || userName.length() > 15 || userName.trim().isEmpty() || !userName.matches("^\\w+$")) {
            throw new InvalidEntityDataException(ExceptionMessages.USERNAME_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.userName = userName;
    }

    public void setPassword(String password) throws InvalidEntityDataException {
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d@$!%*#?&()}{-]{8,15}$")) {
            throw new InvalidEntityDataException(ExceptionMessages.PASSWORD_DOES_NOT_MATCH_REQUIREMENTS);
        }
        this.password = password;
    }

    public void setGender(String gender) throws InvalidEntityDataException {
        try {
            this.gender = Gender.valueOf(gender.toUpperCase());
        } catch (Exception e) {
            throw new InvalidEntityDataException(ExceptionMessages.INVALID_GENDER_INPUT);
        }
    }

    @Override
    public void setRole(Role role) {
        if (this instanceof Administrator) {
            this.role = Role.ADMIN;
        } else {
            this.role = Role.HOME_COOK;
        }
    }

    public void setStatus(AccountStatus status) {
        if (this.status == null) {
            this.status = AccountStatus.ACTIVE;
        } else {
            this.status = status;
        }
    }

    public void setModified() {
        this.modified = new Timestamp(System.currentTimeMillis()).toString().split("\\.")[0];
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return hashPassword(this.password);
    }

    public Gender getGender() {
        return this.gender;
    }

    public Role getRole() {
        return this.role;
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    @Override
    public String getCreated() {
        return this.created;
    }

    @Override
    public String getModified() {
        return this.modified;
    }

    public static String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("User ID = ").append(id).append(System.lineSeparator());
        sb.append("FirstName = ").append(firstName);
        sb.append(", LastName = ").append(lastName).append(System.lineSeparator());
        sb.append("Email = ").append(email).append(System.lineSeparator());
        sb.append("UserName = ").append(userName).append(System.lineSeparator());
        sb.append("Gender = ").append(gender).append(System.lineSeparator());
        sb.append("Role = ").append(role).append(System.lineSeparator());
        sb.append("Recipes = ").append(recipes.size()).append(" added recipes").append(System.lineSeparator());
        sb.append("Account created on = ").append(created).append(System.lineSeparator());
        sb.append("Last modified = ").append(modified).append(System.lineSeparator());
        return sb.toString().trim();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setForceId(Long id){
        this.id = id;
    }

    public void setForceCreated(String created){
        this.created = created;
    }

    public String getForcePassword(){
        return this.password;
    }
}

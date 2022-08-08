package org.example.servises.userService;

import org.example.common.*;
import org.example.entities.categories.Category;
import org.example.entities.recipes.Recipe;
import org.example.entities.users.Administrator;
import org.example.entities.users.HomeCook;
import org.example.entities.users.User;
import org.example.entities.users.UserImpl;
import org.example.repositories.interfaces.Repository;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class UserServiceImpl implements UserService {

    private final Repository<User> userRepository;
    private final Scanner scanner = new Scanner(System.in);

    public UserServiceImpl(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    private void userCheck(boolean user, String exceptionMessage) throws NonExistingEntityException {
        if (user) {
            throw new NonExistingEntityException(exceptionMessage);
        }
    }

    @Override
    public User registerNewUser(String role, String firstName, String lastName, String email, String userName, String password, String gender) throws InvalidEntityDataException {
        if (existingEmail(email)) {
            throw new IllegalArgumentException(ExceptionMessages.REGISTRATION_EXISTING_EMAIL_ADDRESS);
        } else if (existingUsername(userName)) {
            throw new IllegalArgumentException(ExceptionMessages.REGISTRATION_EXISTING_USERNAME);
        }

        User user = switch (role.toLowerCase()) {
            case "homecook" -> new HomeCook(firstName, lastName, email, userName, password, gender);
            case "admin" -> new Administrator(firstName, lastName, email, userName, password, gender);
            default ->
                    throw new IllegalArgumentException(String.format(ExceptionMessages.REGISTRATION_INVALID_ROLE, role));
        };
        userRepository.add(user);
        return user;
    }

    @Override
    public User login(String username, String password, User activeUser) throws InvalidEntityDataException, NonExistingEntityException {
        if (activeUser != null && username.equals(activeUser.getUserName())) {
            throw new IllegalArgumentException(OutputMessages.ALREADY_LOGGED_IN_WITH_THIS_ACCOUNT);
        }
        //There is no User with the given "username"
        userCheck(!existingUsername(username), ExceptionMessages.LOGIN_USERNAME_NOT_FOUND);
        User user = this.getUserByUsername(username);
        String userPassword = user.getPassword();
        if (!UserImpl.hashPassword(password).equals(userPassword)) {
            throw new IllegalArgumentException(ExceptionMessages.LOGIN_INCORRECT_PASSWORD);
        }

        if (checkAccountRequirePasswordReset(user)) {
            forcePasswordReset(user);
            user.setStatus(AccountStatus.ACTIVE);
        }

        return user;
    }

    private void forcePasswordReset(User user) throws InvalidEntityDataException {
        System.out.print(OutputMessages.CHANGE_PASSWORD_REQUIRED);
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
    }

    @Override
    public User logout(User user) throws NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.CANT_LOGOUT);
        return null;
    }

    @Override
    public User changeFirstName(User user, String newName) throws InvalidEntityDataException, NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.NOT_LOGGED_IN);
        checkAccountRestrictions(user);
        user.setFirstName(newName);
        user.setModified();
        return user;
    }

    @Override
    public User changeLastName(User user, String newName) throws InvalidEntityDataException, NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.NOT_LOGGED_IN);
        checkAccountRestrictions(user);
        user.setLastName(newName);
        user.setModified();
        return user;
    }

    @Override
    public User changePassword(String newPassword, User user) throws InvalidEntityDataException, NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.NOT_LOGGED_IN);
        checkAccountRestrictions(user);
        user.setPassword(newPassword);
        user.setModified();
        return user;
    }

    @Override
    public User changeGender(User user) throws InvalidEntityDataException, NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.NOT_LOGGED_IN);
        checkAccountRestrictions(user);
        checkAccountRequirePasswordReset(user);
        if (user.getGender().toString().equalsIgnoreCase("Male")) {
            user.setGender("Female");
        } else {
            user.setGender("Male");
        }
        user.setModified();
        return user;
    }

    @Override
    public User admChangeUserFirstName(User user, String newFirstName) throws InvalidEntityDataException, NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.THERE_IS_NO_SUCH_USER);
        user.setFirstName(newFirstName);
        user.setModified();
        return user;
    }


    @Override
    public User admChangeUserLastName(User user, String newLastName) throws InvalidEntityDataException, NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.THERE_IS_NO_SUCH_USER);
        user.setLastName(newLastName);
        user.setModified();
        return user;
    }

    @Override
    public User admChangeUserGender(User user) throws InvalidEntityDataException, NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.THERE_IS_NO_SUCH_USER);
        if (user.getGender().toString().equalsIgnoreCase("MALE")) {
            user.setGender("FEMALE");
        } else {
            user.setGender("MALE");
        }
        user.setModified();
        return user;
    }

    @Override
    public User admChangeUserRole(User user, String newRole) throws InvalidEntityDataException, NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.THERE_IS_NO_SUCH_USER);
        if (user.getRole().toString().equalsIgnoreCase(newRole)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ALREADY_OWN_THIS_ROLE, user.getId().toString(), user.getRole().toString()));
        }
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String username = user.getUserName();
        String password = user.getForcePassword();
        String genderToString = user.getGender().toString();
        List<Recipe> recipes = user.getRecipes();
        User newUserInstance;
        if (newRole.equalsIgnoreCase("ADMIN")) {
            newUserInstance = new Administrator(firstName, lastName, email, username, password, genderToString);
        } else {
            newUserInstance = new HomeCook(firstName, lastName, email, username, password, genderToString);
        }
        newUserInstance.setForceCreated(user.getCreated());
        newUserInstance.setForceId(user.getId());
        newUserInstance.setRecipes(recipes);
        this.userRepository.remove(user);
        this.userRepository.add(newUserInstance);
        newUserInstance.setModified();
        return newUserInstance;
    }

    @Override
    public User admChangeUserStatus(User user, String newAccountStatus) throws NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.THERE_IS_NO_SUCH_USER);
        if (user.getStatus().toString().equalsIgnoreCase(newAccountStatus)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ALREADY_OWN_THIS_STATUS, user.getId().toString(), user.getStatus().toString()));
        }
        user.setStatus(AccountStatus.valueOf(newAccountStatus));
        return user;
    }

    public User getUserById(Long id) {
        return this.userRepository.getById(id);
    }

    public User getUserByUsername(String username) {
        return this.userRepository.getAll().stream().filter(user -> user.getUserName().equals(username)).findFirst().orElse(null);
    }

    public boolean existingUsername(String username) {
        return this.userRepository.getAll().stream().anyMatch(user -> user.getUserName().equals(username));
    }

    public boolean existingEmail(String email) {
        return this.userRepository.getAll().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public String info(User user) {
        return user.toString();
    }

    @Override
    public String admViewAllUsers() {
        if (this.userRepository.getAll().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.NO_REGISTERED_USERS_IN_THE_SYSTEM);
        }
        StringBuilder result = new StringBuilder();
        AtomicInteger count = new AtomicInteger(1);
        this.userRepository.getAll().forEach(user -> {
            result.append(String.format("------------%d-------------", count.getAndIncrement())).append(System.lineSeparator());
            result.append(user.toString()).append(System.lineSeparator());
        });
        return result.toString().trim();
    }

    @Override
    public void verifyAdminStatus(User currentUser) throws NonExistingEntityException {
        userCheck(currentUser == null, ExceptionMessages.NOT_LOGGED_IN);
        if (currentUser.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException(ExceptionMessages.CANT_PERFORM_THIS_OPERATION_NOT_ADMIN);
        }
    }

    @Override
    public String getMyFavouriteCooks(User user) throws NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.NOT_LOGGED_IN);
        checkAccountRestrictions(user);
        if (user instanceof Administrator){
            throw new IllegalArgumentException(ExceptionMessages.ADMINISTRATORS_DOES_NOT_HAVE_FAVOURITE_SECTION);
        }
        List<HomeCook> favCooks;
        switch (user){
            case HomeCook homeCook -> favCooks = homeCook.getFavoriteCooks();
            default -> throw new IllegalArgumentException(ExceptionMessages.ADMINISTRATORS_DOES_NOT_HAVE_FAVOURITE_SECTION);
        }
        if (favCooks.isEmpty()){
            throw new NullPointerException(ExceptionMessages.NO_FAVOURITE_COOKS_SAVED);
        }
        StringBuilder sb = new StringBuilder();
        for (HomeCook favCook : favCooks) {
            sb.append("Cook: ").append(favCook.getFirstName()).append(" ").append(favCook.getLastName()).append(" id(").append(favCook.getId()).append(")").append(". This user has ").append(favCook.getRecipes().size()).append(" recipes").append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public void addFavouriteCook(User user, Long cookId) throws NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.NOT_LOGGED_IN);
        checkAccountRestrictions(user);
        User cook = getUserById(cookId);
        userCheck(cook == null, ExceptionMessages.THERE_IS_NO_SUCH_USER);
        if (user instanceof Administrator){
            throw new IllegalArgumentException(ExceptionMessages.ADMINISTRATORS_DOES_NOT_HAVE_FAVOURITE_SECTION);
        }
        if (cook instanceof Administrator){
            throw new IllegalArgumentException(ExceptionMessages.CANT_ADMIN_AS_FAVOURITE);
        }
        if (user instanceof HomeCook homeCook && cook instanceof HomeCook favCook){
            homeCook.getFavoriteCooks().add(favCook);
        }
    }

    @Override
    public void removeFavouriteCook(User user, Long cookId) throws NonExistingEntityException {
        userCheck(user == null, ExceptionMessages.NOT_LOGGED_IN);
        checkAccountRestrictions(user);
        User cook = getUserById(cookId);
        userCheck(cook == null, ExceptionMessages.THERE_IS_NO_SUCH_USER);
        if (user instanceof Administrator){
            throw new IllegalArgumentException(ExceptionMessages.ADMINISTRATORS_DOES_NOT_HAVE_FAVOURITE_SECTION);
        }
        if (cook instanceof Administrator){
            throw new IllegalArgumentException(ExceptionMessages.CANT_ADMIN_AS_FAVOURITE);
        }
        if (user instanceof HomeCook homeCook && cook instanceof HomeCook favCook){
            if (!homeCook.getFavoriteCooks().remove(favCook)){
                throw new NonExistingEntityException(ExceptionMessages.THE_RECIPE_IS_NOT_PART_OF_FAVOURITE_COLLECTION);
            }
        }
    }

    @Override
    public String admShowMyCategories(User user) {
        StringBuilder sb = new StringBuilder();
        if (user instanceof Administrator administrator){
            for (Category category : administrator.getCategoriesModerated()) {
                sb.append("Category name: ").append(category.getName()).append(" Category ID: ").append(category.getId()).append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }

    private void checkAccountRestrictions(User activeUser) {
        if (activeUser.getStatus().equals(AccountStatus.DEACTIVATED)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.YOU_CANT_USE_YOUR_ACCOUNT_NOW, activeUser.getUserName(), AccountStatus.DEACTIVATED));
        } else if (activeUser.getStatus().equals(AccountStatus.SUSPENDED)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.YOU_CANT_USE_YOUR_ACCOUNT_NOW, activeUser.getUserName(), AccountStatus.SUSPENDED));
        }
    }

    private boolean checkAccountRequirePasswordReset(User activeUser) {
        return activeUser.getStatus().equals(AccountStatus.CHANGE_PASSWORD);
    }



}

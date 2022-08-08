package org.example.controllers;


import org.example.common.InvalidEntityDataException;
import org.example.common.NonExistingEntityException;
import org.example.common.OutputMessages;
import org.example.entities.users.User;
import org.example.servises.userService.UserService;

import java.io.FileNotFoundException;

public class UserController {
    private final UserService userService;
    private User activeUser;

    public UserController(UserService userService) {
        this.userService = userService;
        this.activeUser = null;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public User getUserById(Long userId){
        return this.userService.getUserById(userId);
    }

    public void verifyAdminStatus() throws NonExistingEntityException {
        this.userService.verifyAdminStatus(this.activeUser);
    }

    public String registerNewUser(String role, String firstName, String lastName, String email, String userName, String password, String gender) throws InvalidEntityDataException {
        User user = userService.registerNewUser(role, firstName, lastName, email, userName, password, gender);
        return String.format(OutputMessages.SUCCESSFULLY_REGISTRATION, user.getUserName(), user.getId());
    }

    public String login(String username, String password) throws FileNotFoundException, InvalidEntityDataException, NonExistingEntityException {
        User user = userService.login(username, password, this.activeUser);
        setActiveUser(user);
        return String.format(OutputMessages.SUCCESSFULLY_LOGGED_IN, user.getUserName(), user.getEmail());
    }

    public String logout() throws NonExistingEntityException {
        setActiveUser(this.userService.logout(this.activeUser));
        return OutputMessages.SUCCESSFULLY_LOGGED_OUT;
    }

    public String showUserInfo() {
        return this.userService.info(this.activeUser);
    }

    public String changeFirstName(String newName) throws InvalidEntityDataException, NonExistingEntityException {
        User user = this.userService.changeFirstName(this.activeUser, newName);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_FIRST_NAME, user.getFirstName());
    }

    public String changeLastName(String newName) throws InvalidEntityDataException, NonExistingEntityException {
        User user = this.userService.changeLastName(this.activeUser, newName);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_LAST_NAME, user.getLastName());
    }

    public String changeGender() throws InvalidEntityDataException, NonExistingEntityException {
        this.userService.changeGender(this.activeUser);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_GENDER, this.activeUser.getGender().toString());
    }

    public String changePassword(String newPassword) throws InvalidEntityDataException, NonExistingEntityException {
        User user = this.userService.changePassword(newPassword, this.activeUser);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_PASSWORD, user.getUserName());
    }

    public String admChangeUserFirstName(String username, String newFirstName) throws InvalidEntityDataException, NonExistingEntityException {
        this.verifyAdminStatus();
        User user = this.userService.getUserByUsername(username);
        this.userService.admChangeUserFirstName(user, newFirstName);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_USER_FIRST_NAME, this.activeUser.getUserName(), user.getUserName(), user.getFirstName());
    }


    public String admChangeUserLastName(String username, String newLastName) throws InvalidEntityDataException, NonExistingEntityException {
        this.verifyAdminStatus();
        User user = this.userService.getUserByUsername(username);
        this.userService.admChangeUserLastName(user, newLastName);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_USER_LAST_NAME, this.activeUser.getUserName(), user.getUserName(), user.getLastName());
    }

    public String admChangeUserGender(String username) throws InvalidEntityDataException, NonExistingEntityException {
        this.verifyAdminStatus();
        User user = this.userService.getUserByUsername(username);
        this.userService.admChangeUserGender(user);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_USER_GENDER, this.activeUser.getUserName(), user.getUserName(), user.getGender().toString());
    }


    public String admChangeUserRole(String username, String newRole) throws InvalidEntityDataException, NonExistingEntityException {
        this.verifyAdminStatus();
        User user = this.userService.getUserByUsername(username);
        user = this.userService.admChangeUserRole(user, newRole);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_USER_ROLE, this.activeUser.getUserName(), user.getUserName(), user.getRole().toString());
    }

    public String admChangeUserStatus(String username, String newAccountStatus) throws NonExistingEntityException {
        this.verifyAdminStatus();
        User user = this.userService.getUserByUsername(username);
        this.userService.admChangeUserStatus(user, newAccountStatus);
        return String.format(OutputMessages.SUCCESSFULLY_CHANGED_USER_ACCOUNT_STATUS, this.activeUser.getUserName(), user.getUserName(), user.getStatus().toString());
    }

    public String admViewAllUsers() throws NonExistingEntityException {
        this.verifyAdminStatus();
        return this.userService.admViewAllUsers();
    }

    public String getMyFavouriteCooks(User user) throws NonExistingEntityException {
        return this.userService.getMyFavouriteCooks(user);
    }

    public String addFavouriteCook(User user, Long cookId) throws NonExistingEntityException {
        this.userService.addFavouriteCook(user, cookId);
        return String.format(OutputMessages.SUCCESSFULLY_SAVED_COOK, user.getUserName(), cookId);
    }

    public String removeFavouriteCook(User user, Long cookId) throws NonExistingEntityException {
        this.userService.removeFavouriteCook(user, cookId);
        return String.format(OutputMessages.SUCCESSFULLY_REMOVED_COOK, user.getUserName(), cookId);
    }

    public String admShowMyCategories(User user) throws NonExistingEntityException {
        this.verifyAdminStatus();
        return this.userService.admShowMyCategories(user);
    }
}

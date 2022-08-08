package org.example.servises.userService;

import org.example.common.InvalidEntityDataException;
import org.example.common.NonExistingEntityException;
import org.example.entities.users.User;

public interface UserService {
    User registerNewUser(String role, String firstName, String lastName, String email, String userName, String password, String gender) throws InvalidEntityDataException;
    User login(String username, String password, User activeUser) throws InvalidEntityDataException, NonExistingEntityException;
    User logout(User activeUser) throws NonExistingEntityException;
    User changeFirstName(User activeUser, String newName) throws InvalidEntityDataException, NonExistingEntityException;
    User changeLastName(User activeUser, String newName) throws InvalidEntityDataException, NonExistingEntityException;
    User changePassword(String newPassword, User user) throws InvalidEntityDataException, NonExistingEntityException;
    User changeGender(User user) throws InvalidEntityDataException, NonExistingEntityException;
    User admChangeUserFirstName(User user, String newFirstName) throws InvalidEntityDataException, NonExistingEntityException;
    User admChangeUserLastName(User user, String newLastName) throws InvalidEntityDataException, NonExistingEntityException;
    User admChangeUserGender(User user) throws InvalidEntityDataException, NonExistingEntityException;
    User admChangeUserRole(User user, String newRole) throws InvalidEntityDataException, NonExistingEntityException;
    User admChangeUserStatus(User user, String newAccountStatus) throws NonExistingEntityException;
    User getUserById(Long id);
    User getUserByUsername(String username);
    boolean existingUsername(String username);
    boolean existingEmail(String email);
    String info(User user);
    String admViewAllUsers();
    void verifyAdminStatus(User currentUser) throws NonExistingEntityException;

    String getMyFavouriteCooks(User user) throws NonExistingEntityException;

    void addFavouriteCook(User user, Long cookId) throws NonExistingEntityException;

    void removeFavouriteCook(User user, Long cookId) throws NonExistingEntityException;

    String admShowMyCategories(User user);
}

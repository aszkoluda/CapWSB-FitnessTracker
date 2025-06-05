package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    User createUser(User user);

    Optional<User> getUser(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> findAllUsers();

    List<User> findUsersByEmailFragment(String emailFragment);

    List<User> findUsersOlderThan(LocalDate date);

    Optional<User> findUserByFullName(String firstName, String lastName);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}

package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Szuka użytkownika po dokładnym emailu (unikalny)
    Optional<User> findByEmail(String email);

    // Szuka użytkowników, których email zawiera dany fragment, ignorując wielkość liter
    List<User> findByEmailContainingIgnoreCase(String emailFragment);

    // Szuka użytkowników starszych niż podana data (czyli data urodzenia wcześniejsza niż ta)
    List<User> findByBirthdateBefore(LocalDate date);

    // Szuka użytkownika po imieniu i nazwisku
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}

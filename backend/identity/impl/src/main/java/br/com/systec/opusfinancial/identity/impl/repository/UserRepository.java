package br.com.systec.opusfinancial.identity.impl.repository;

import br.com.systec.opusfinancial.commons.repository.AbstractRepository;
import br.com.systec.opusfinancial.identity.impl.entities.User;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository extends AbstractRepository<User, UUID> {

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT obj from User obj where obj.username = :username";

        TypedQuery<User> query = getEntityManager().createQuery(sql, User.class);
        query.setParameter("username", username);
        List<User> result = query.getResultList();

        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(result.getFirst());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        String sql = "SELECT obj from User obj WHERE obj.username = :username or obj.email = :email";

        TypedQuery<User> query = getEntityManager().createQuery(sql, User.class);
        query.setParameter("username", username);
        query.setParameter("email", email);

        List<User> result = query.getResultList();

        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(result.getFirst());
    }
}

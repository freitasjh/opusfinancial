package br.com.systec.opusfinancial.identity.impl.repository;

import br.com.systec.opusfinancial.identity.impl.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT obj from UserEntity obj where obj.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query("SELECT obj from UserEntity obj WHERE obj.username = :username or obj.email = :email")
    Optional<UserEntity> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
}

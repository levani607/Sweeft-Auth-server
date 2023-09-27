package me.levani.authorizationserver.repository;

import me.levani.authorizationserver.model.domain.Client;
import me.levani.authorizationserver.model.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("""
            select c from Client c 
            where c.id=:id
            and c.entityStatus=:status
                        """)
    Optional<Client> findByIdAndStatus(@Param("id") Long id,
                                       @Param("status") EntityStatus status);
}

package me.levani.authorizationserver.repository;

import me.levani.authorizationserver.model.domain.Flow;
import me.levani.authorizationserver.model.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Long> {

    @Query("""
            select f from Flow f 
            where f.client.clientId=:clientId
            and f.grantName=:grantName
            and f.entityStatus=:status
            """)
    Optional<Flow> findByClientIdAndGrantName(@Param("clientId")String clientId,
                                              @Param("grantName")String grantName,
                                              @Param("status")EntityStatus entityStatus);

    @Query("""
            select f from Flow f 
            where f.id=:id
            and f.entityStatus = :status
                        """)
    Optional<Flow> findByIdAndStatus(@Param("id") Long id,
                                     @Param("status")EntityStatus status);


}

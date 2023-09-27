package me.levani.authorizationserver.repository;

import me.levani.authorizationserver.model.domain.FlowExecution;
import me.levani.authorizationserver.model.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowExecutionRepository extends JpaRepository<FlowExecution, Long> {

    @Query("""
            select fe from FlowExecution fe
            where fe.flow.client.clientId=:clientId
            and fe.flow.client.realm.realmName=:realm
            and fe.flow.grantName=:grantType
            and fe.flow.entityStatus='ACTIVE'
            and fe.flow.client.entityStatus='ACTIVE'
            and fe.entityStatus='ACTIVE'
                        """)
    List<FlowExecution> findByClientIdAndGrantType(@Param("realm") String realm,
                                                   @Param("clientId") String clientId,
                                                   @Param("grantType") String grantType);

    @Query("""
            select fe from FlowExecution fe
            where fe.id=:id
            and fe.entityStatus=:status
                        """)
    Optional<FlowExecution> findByIdAndStatus(@Param("id")Long id,
                                              @Param("status")EntityStatus status);
}

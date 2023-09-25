package com.example.authorizationserver.repository;

import com.example.authorizationserver.model.domain.Execution;
import com.example.authorizationserver.model.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {


    @Query("""
            select e from Execution e 
            where e.id=:id
            and e.entityStatus=:status
            """)
    Optional<Execution> findByIdAndStatus(@Param("id") Long id,
                                          @Param("status") EntityStatus status);

    @Query("""
            select e from Execution e
            where e.id in :ids
            and e.entityStatus = :status
                        """)
    List<Execution> findByIdsInAndStatus(@Param("ids") List<Long> ids,
                                         @Param("status") EntityStatus status);

    @Query("""
            select e from Execution e 
            where e.entityStatus = :status
                        """)
    List<Execution> findAllByStatus(@Param("status") EntityStatus status);
}

package com.projeto.cupom_api.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CupomRepository extends JpaRepository<CupomEntity, Long>{
	@Query("select c from CupomEntity c where c.id = :id")
	Optional<CupomEntity> findActiveById(Long id);

	@Query("select c from CupomEntity c where c.id = :id")
	Optional<CupomEntity> findAnyByIdDeletado(@Param("id") Long id);
}

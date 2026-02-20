package com.projeto.cupom_api.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CupomInclusoDeletadoRepository extends JpaRepository<CupomEntity, Long>{
	@Query(value = "SELECT * FROM cupom WHERE id = :id", nativeQuery = true)
	Optional<CupomEntity> findByIdIncludingDeleted(Long id);
}

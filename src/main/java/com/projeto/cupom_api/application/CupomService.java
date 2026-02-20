package com.projeto.cupom_api.application;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.projeto.cupom_api.domain.*;
import com.projeto.cupom_api.domain.exceptions.CupomDeletadoException;
import com.projeto.cupom_api.infra.persistence.*;

@Service
public class CupomService {
	
	private final CupomRepository cupomRepository;
	private final CupomInclusoDeletadoRepository cupomInclusoDeletadoRepository;

	public CupomService(CupomRepository cupomRepository,
			CupomInclusoDeletadoRepository cupomInclusoDeletadoRepository) {
		this.cupomRepository = cupomRepository;
		this.cupomInclusoDeletadoRepository = cupomInclusoDeletadoRepository;
	}

	@Transactional
	public Cupom create(Cupom Cupom) {
	  CupomEntity entity = toEntity(Cupom);
	  entity = cupomRepository.save(entity);
	  Cupom.setId(entity.getId());
	  return Cupom;
	}

	@Transactional
	public Cupom getActive(Long id) {
	  CupomEntity entity = cupomRepository.findActiveById(id)
	      .orElseThrow(() -> new EntityNotFoundException("Cupom não encontado!"));
	  return toDomain(entity);
	}
	
	@Transactional
	public void delete(Long id) {

	  CupomEntity entity = cupomInclusoDeletadoRepository
	      .findByIdIncludingDeleted(id)
	      .orElseThrow(() -> new EntityNotFoundException("Cupom não encontrado"));

	  if (entity.getDtDelete() != null || "DELETED".equals(entity.getStatus())) {
	    throw new CupomDeletadoException();
	  }

	  entity.setDtDelete(java.time.Instant.now());
	  entity.setStatus(CupomStatus.DELETED.name());

	  cupomRepository.save(entity);
	}
	
	private CupomEntity toEntity(Cupom c) {
	  CupomEntity e = new CupomEntity();
	  e.setId(c.getId());
	  e.setCode(c.getCode());
	  e.setDescricao(c.getDescricao());
	  e.setValorDesconto(c.getValorDesconto());
	  e.setDtExpiracao(c.getDataExpiracao());
	  e.setStatus(c.getStatus().name());
	  e.setPublicado(c.isPublicado());
	  e.setResgatado(c.isResgatado());
	  e.setDtDelete(c.getDtDelete());
	  return e;
	}
	
	private Cupom toDomain(CupomEntity e) {
	  Cupom c = Cupom.create(
	      e.getCode(),
	      e.getDescricao(),
	      e.getValorDesconto(),
	      e.getDtExpiracao(),
	      e.isPublicado()
	  );
	  c.setId(e.getId());
	  c.setResgatado(e.isResgatado());
	  return c;
	}
}

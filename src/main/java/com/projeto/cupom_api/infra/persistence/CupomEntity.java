package com.projeto.cupom_api.infra.persistence;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cupom")
@Getter
@Setter
@SQLDelete(sql = "UPDATE cupom SET dt_delete = CURRENT_TIMESTAMP, status = 'DELETED' WHERE id = ? AND dt_delete IS NULL")
@SQLRestriction("dt_delete IS NULL")
public class CupomEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 6)
  private String code;

  @Column(nullable = false, length = 255)
  private String descricao;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal valorDesconto;

  @Column(nullable = false, name = "dt_expiracao")
  private LocalDate dtExpiracao;

  @Column(nullable = false, length = 20)
  private String status;

  @Column(nullable = false)
  private boolean publicado;

  @Column(nullable = false)
  private boolean resgatado;

  @Column(name = "dt_delete")
  private Instant dtDelete;
}

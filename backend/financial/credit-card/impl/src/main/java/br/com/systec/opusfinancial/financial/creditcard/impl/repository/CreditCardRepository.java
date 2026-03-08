package br.com.systec.opusfinancial.financial.creditcard.impl.repository;

import br.com.systec.opusfinancial.financial.creditcard.impl.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, UUID>, JpaSpecificationExecutor<CreditCard> {
}

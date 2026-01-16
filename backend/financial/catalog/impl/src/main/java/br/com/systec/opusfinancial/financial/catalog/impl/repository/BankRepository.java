package br.com.systec.opusfinancial.financial.catalog.impl.repository;

import br.com.systec.opusfinancial.financial.catalog.impl.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankRepository extends JpaRepository<Bank, UUID>, JpaSpecificationExecutor<Bank> {

    Optional<Bank> findByCode(String code);

}

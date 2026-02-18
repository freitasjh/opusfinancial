package br.com.systec.opusfinancial.financial.catalog.impl.repository;

import br.com.systec.opusfinancial.financial.catalog.impl.domain.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<FinancialTransaction, UUID>, JpaSpecificationExecutor<FinancialTransaction> {
}

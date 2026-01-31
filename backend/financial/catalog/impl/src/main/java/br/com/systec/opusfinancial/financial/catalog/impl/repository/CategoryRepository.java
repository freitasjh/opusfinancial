package br.com.systec.opusfinancial.financial.catalog.impl.repository;

import br.com.systec.opusfinancial.financial.catalog.impl.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {

    List<Category> findByParentId(UUID parentId);
}

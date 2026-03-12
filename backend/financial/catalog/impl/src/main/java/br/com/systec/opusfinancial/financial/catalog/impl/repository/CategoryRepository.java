package br.com.systec.opusfinancial.financial.catalog.impl.repository;

import br.com.systec.opusfinancial.financial.catalog.impl.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID>, JpaSpecificationExecutor<CategoryEntity> {

    List<CategoryEntity> findByParentId(UUID parentId);
}

package br.com.opusfinancial.tenant.impl.service;

import br.com.opusfinancial.tenant.impl.service.fake.TenantFake;
import br.com.systec.opusfinancial.tenant.api.domain.Tenant;
import br.com.systec.opusfinancial.tenant.api.exceptions.TenantException;
import br.com.systec.opusfinancial.tenant.api.exceptions.TenantNotFoundException;
import br.com.systec.opusfinancial.tenant.impl.entity.TenantEntity;
import br.com.systec.opusfinancial.tenant.impl.repository.TenantRepository;
import br.com.systec.opusfinancial.tenant.impl.service.TenantServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TenantServiceImplTest {

    @Mock
    private TenantRepository repository;

    @InjectMocks
    private TenantServiceImpl tenantService;

    @Test
    void whenSaveNewTenant_thenReturnTenantVO() {
        TenantEntity tenantToReturn = TenantFake.toFake();
        Tenant tenantToSave = TenantFake.toFakeVO();

        Mockito.when(repository.save(Mockito.any(TenantEntity.class))).thenReturn(tenantToReturn);

        Tenant tenantAfterSave = tenantService.create(tenantToSave);

        Assertions.assertNotNull(tenantAfterSave);
        Assertions.assertEquals(tenantToReturn.getId(), tenantAfterSave.getId());
        Assertions.assertEquals(tenantToReturn.getName(), tenantAfterSave.getName());
        Assertions.assertEquals(tenantToReturn.getOwnerId(), tenantAfterSave.getOwnerId());

        Mockito.verify(repository).save(Mockito.any(TenantEntity.class));
    }

    @Test
    void whenUpdateTenant_thenReturnTenantVO() {
        TenantEntity tenantToReturn = TenantFake.toFake();
        TenantEntity tenantFindReturn = TenantFake.toFake();

        Tenant tenantToSave = TenantFake.toFakeVO();

        Mockito.when(repository.save(Mockito.any(TenantEntity.class))).thenReturn(tenantToReturn);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(tenantFindReturn));

        Tenant tenantAfterSave = tenantService.update(tenantToSave);

        Assertions.assertNotNull(tenantAfterSave);
        Assertions.assertEquals(tenantToReturn.getId(), tenantAfterSave.getId());
        Assertions.assertEquals(tenantToReturn.getName(), tenantAfterSave.getName());
        Assertions.assertEquals(tenantToReturn.getOwnerId(), tenantAfterSave.getOwnerId());

        Mockito.verify(repository).save(Mockito.any(TenantEntity.class));
        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void whenUpdateTenant_thenReturnTenantNotFoundException() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(TenantNotFoundException.class, () -> tenantService.update(TenantFake.toFakeVO()));

        Mockito.verify(repository).findById(Mockito.any());
        Mockito.verify(repository, Mockito.never()).save(Mockito.any(TenantEntity.class));
    }

    @Test
    void whenFindTenant_thenReturnTenantVO() {
        TenantEntity tenantFindReturn = TenantFake.toFake();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(tenantFindReturn));

        Tenant tenantAfterFind = tenantService.findById(tenantFindReturn.getId());

        Assertions.assertNotNull(tenantAfterFind);
        Assertions.assertEquals(tenantFindReturn.getId(), tenantAfterFind.getId());
        Assertions.assertEquals(tenantFindReturn.getName(), tenantAfterFind.getName());
        Assertions.assertEquals(tenantFindReturn.getOwnerId(), tenantAfterFind.getOwnerId());

        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void whenFindTenant_thenReturnTenantNotFoundException() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(TenantNotFoundException.class, () -> tenantService.findById(UUID.randomUUID()));

        Mockito.verify(repository).findById(Mockito.any());
    }

}

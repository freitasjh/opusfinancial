package br.com.systec.opusfinancial.identity.impl.service;

import br.com.systec.opusfinancial.identity.api.exceptions.TenantException;
import br.com.systec.opusfinancial.identity.api.exceptions.TenantNotFoundException;
import br.com.systec.opusfinancial.identity.api.vo.TenantVO;
import br.com.systec.opusfinancial.identity.impl.fake.TenantFake;
import br.com.systec.opusfinancial.identity.impl.entities.Tenant;
import br.com.systec.opusfinancial.identity.impl.repository.TenantRepository;
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
        Tenant tenantToReturn = TenantFake.toFake();
        TenantVO tenantToSave = TenantFake.toFakeVO();

        Mockito.when(repository.save(Mockito.any(Tenant.class))).thenReturn(tenantToReturn);

        TenantVO tenantAfterSave = tenantService.create(tenantToSave);

        Assertions.assertNotNull(tenantAfterSave);
        Assertions.assertEquals(tenantToReturn.getId(), tenantAfterSave.getId());
        Assertions.assertEquals(tenantToReturn.getName(), tenantAfterSave.getName());
        Assertions.assertEquals(tenantToReturn.getOwnerId(), tenantAfterSave.getOwnerId());

        Mockito.verify(repository).save(Mockito.any(Tenant.class));
    }

    @Test
    void whenSaveNewTenant_thenReturnTenantException() {

        Mockito.when(repository.save(Mockito.any(Tenant.class))).thenThrow(new RuntimeException("Erro teste de banco"));

        Assertions.assertThrows(TenantException.class, () -> tenantService.create(TenantFake.toFakeVO()));
    }

    @Test
    void whenUpdateTenant_thenReturnTenantVO() {
        Tenant tenantToReturn = TenantFake.toFake();
        Tenant tenantFindReturn = TenantFake.toFake();

        TenantVO tenantToSave = TenantFake.toFakeVO();

        Mockito.when(repository.update(Mockito.any(Tenant.class))).thenReturn(tenantToReturn);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(tenantFindReturn));

        TenantVO tenantAfterSave = tenantService.update(tenantToSave);

        Assertions.assertNotNull(tenantAfterSave);
        Assertions.assertEquals(tenantToReturn.getId(), tenantAfterSave.getId());
        Assertions.assertEquals(tenantToReturn.getName(), tenantAfterSave.getName());
        Assertions.assertEquals(tenantToReturn.getOwnerId(), tenantAfterSave.getOwnerId());

        Mockito.verify(repository).update(Mockito.any(Tenant.class));
        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void whenUpdateTenant_thenReturnTenantNotFoundException() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(TenantNotFoundException.class, () -> tenantService.update(TenantFake.toFakeVO()));

        Mockito.verify(repository).findById(Mockito.any());
        Mockito.verify(repository, Mockito.never()).update(Mockito.any(Tenant.class));
    }

    @Test
    void whenUpdateTenant_thenReturnTenantException() {
        Tenant tenantFindReturn = TenantFake.toFake();

        TenantVO tenantToSave = TenantFake.toFakeVO();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(tenantFindReturn));
        Mockito.when(repository.update(Mockito.any(Tenant.class))).thenThrow(new RuntimeException("Erro teste de banco"));

        Assertions.assertThrows(TenantException.class, () -> tenantService.update(tenantToSave));
        Mockito.verify(repository).findById(Mockito.any());
        Mockito.verify(repository).update(Mockito.any(Tenant.class));
    }

    @Test
    void whenFindTenant_thenReturnTenantVO() {
        Tenant tenantFindReturn = TenantFake.toFake();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(tenantFindReturn));

        TenantVO tenantAfterFind = tenantService.findById(tenantFindReturn.getId());

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

    @Test
    void whenFindTenant_thenReturnTenantException() {
        Mockito.when(repository.findById(Mockito.any())).thenThrow(new RuntimeException("Erro teste de banco"));

        Assertions.assertThrows(TenantException.class, () -> tenantService.findById(UUID.randomUUID()));

        Mockito.verify(repository).findById(Mockito.any());
    }

}

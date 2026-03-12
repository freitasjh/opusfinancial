package br.com.systec.opusfinancial.integration.test.seed;

import br.com.systec.opusfinancial.api.filter.FilterCategory;
import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.domain.CategoryType;
import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.commons.impl.security.TenantContext;
import br.com.systec.opusfinancial.financial.api.filter.FilterAccount;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.service.ExpenseTransactionService;
import br.com.systec.opusfinancial.financial.api.service.IncomingTransactionService;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.tenant.impl.entity.TenantEntity;
import br.com.systec.opusfinancial.tenant.impl.repository.TenantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.util.stream.StreamSupport;

@SpringBootTest
public class FinancialDataSeedIT {

    @Autowired
    private IncomingTransactionService incomingService;

    @Autowired
    private ExpenseTransactionService expenseService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TenantRepository tenantRepository;


    private final Random random = new Random();

    @Test
    @Transactional
    @Commit
    public void seedDatabaseWithOneYearOfData() throws Exception {
        // 1. Garantir um Tenant (Ainda via repositório pois é infraestrutura base)
        Iterable<TenantEntity> result = tenantRepository.findAll();
        TenantEntity tenant = StreamSupport.stream(result.spliterator(), false).toList().stream().findFirst().orElseGet(() -> {
            TenantEntity t = new TenantEntity();
            t.setName("Test Tenant");
            return tenantRepository.save(t);
        });

        System.out.println("Iniciando Seed via VOs para o Tenant: " + tenant.getId());

        TenantContext.runWithTenant(tenant.getId(), () -> {
            runSeed(tenant);
            return null;
        });
    }

    private void runSeed(TenantEntity tenant) {
        // 2. Garantir uma Conta via VO
        Account accountVO = accountService.findByFilter(new FilterAccount("", 30, 0)).stream()
                .findFirst()
                .orElseGet(() -> {
                    Account a = new Account();
                    a.setAccountName("Conta Principal");
                    a.setBalance(BigDecimal.ZERO);
                    return accountService.create(a);
                });

        // 3. Garantir Categorias via VO
        Category catSalario = getOrCreateCategoryVO("Salário", CategoryType.REVENUE);
        Category catMoradia = getOrCreateCategoryVO("Moradia", CategoryType.EXPENSE);
        Category catAlimentacao = getOrCreateCategoryVO("Alimentação", CategoryType.EXPENSE);
        Category catLazer = getOrCreateCategoryVO("Lazer", CategoryType.EXPENSE);

        LocalDate startDate = LocalDate.now().minusMonths(14).withDayOfMonth(1);

        for (int i = 0; i <= 14; i++) {
            LocalDate currentMonth = startDate.plusMonths(i);
            boolean isTightMonth = i % 2 != 0;

            // --- RECEITA: SALÁRIO (R$ 5.800,00) ---
            saveIncoming(accountVO, catSalario, "Salário Mensal", new BigDecimal("5800.00"), currentMonth.withDayOfMonth(5));

            // --- DESPESA FIXA: ALUGUEL (R$ 1.800,00) ---
            saveExpense(accountVO, catMoradia, "Aluguel Apartamento", new BigDecimal("1800.00"), currentMonth.withDayOfMonth(10));

            // --- DESPESA FIXA: INTERNET/CONTAS (R$ 250,00) ---
            saveExpense(accountVO, catMoradia, "Contas Fixas (Luz/Net)", new BigDecimal("250.00"), currentMonth.withDayOfMonth(12));

            // --- DESPESA VARIÁVEL: ALIMENTAÇÃO ---
            BigDecimal foodValue = isTightMonth ? new BigDecimal("1600.00") : new BigDecimal("1100.00");
            saveExpense(accountVO, catAlimentacao, "Supermercado e Restaurantes", foodValue, currentMonth.withDayOfMonth(15));

            // --- DESPESA VARIÁVEL: LAZER OU EXTRA ---
            if (isTightMonth) {
                BigDecimal extraValue = new BigDecimal("1900.00").add(new BigDecimal(random.nextInt(200)));
                saveExpense(accountVO, catLazer, "Gasto Extra / Manutenção", extraValue, currentMonth.withDayOfMonth(20));
                System.out.println("Mês " + currentMonth.getMonth() + "/" + currentMonth.getYear() + " - LIMITE");
            } else {
                saveExpense(accountVO, catLazer, "Lazer e Viagem", new BigDecimal("600.00"), currentMonth.withDayOfMonth(22));
                System.out.println("Mês " + currentMonth.getMonth() + "/" + currentMonth.getYear() + " - SOBROU");
            }
        }

        System.out.println("Seed finalizado com sucesso!");
    }

    private void saveIncoming(Account account, Category category, String desc, BigDecimal amount, LocalDate date) {
        FinancialTransaction vo = new FinancialTransaction();
        vo.setAccount(account);
        vo.setCategory(category);
        vo.setDescription(desc);
        vo.setAmount(amount);
        vo.setPaymentAt(date);
        vo.setProcessed(true);
        vo.setProcessedAt(date.atTime(12, 0));
        incomingService.save(vo);
    }

    private void saveExpense(Account account, Category category, String desc, BigDecimal amount, LocalDate date) {
        FinancialTransaction vo = new FinancialTransaction();
        vo.setAccount(account);
        vo.setCategory(category);
        vo.setDescription(desc);
        vo.setAmount(amount);
        vo.setPaymentAt(date);
        vo.setDueDate(date);
        vo.setProcessed(true);
        vo.setProcessedAt(date.atTime(12, 0));
        expenseService.create(vo);
    }

    private Category getOrCreateCategoryVO(String name, CategoryType type) {
        return categoryService.findByFilter(new FilterCategory("", 30, 0)).getContent().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> {
                    Category c = new Category();
                    c.setName(name);
                    c.setCategoryType(type);
                    c.setColorHex("4f46e5");
                    c.setIconCode("pi pi-tag");
                    return categoryService.create(c);
                });
    }
}

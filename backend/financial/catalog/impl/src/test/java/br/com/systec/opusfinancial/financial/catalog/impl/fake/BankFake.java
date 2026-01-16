package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.financial.catalog.impl.domain.Bank;

import java.util.UUID;

public class BankFake {

    public static Bank bankFake() {
        Bank bank = new Bank();
        bank.setId(UUID.randomUUID());
        bank.setCode("0001");
        bank.setName("Banco teste");
        bank.setLogoUrl("http://bancoteste.com.br/aaaa.jpg");
        bank.setColor("x88881ee");

        return bank;
    }
}

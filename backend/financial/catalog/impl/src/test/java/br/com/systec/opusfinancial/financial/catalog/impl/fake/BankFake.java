package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.financial.catalog.impl.entity.BankEntity;

import java.util.UUID;

public class BankFake {

    public static BankEntity bankFake() {
        BankEntity bank = new BankEntity();
        bank.setId(UUID.randomUUID());
        bank.setCode("0001");
        bank.setName("Banco teste");
        bank.setLogoUrl("https://bancoteste.com.br/aaaa.jpg");

        return bank;
    }
}

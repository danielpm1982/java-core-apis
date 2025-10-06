package com.danielpm1982.networking.webservice01;

@SuppressWarnings("SpellCheckingInspection")
public record MyAddress(String cep, String logradouro, String complemento, String unidade, String bairro,
                         String localidade, String uf, String estado, String regiao, String ibge, String gia,
                         String ddd, String siafi) {
}

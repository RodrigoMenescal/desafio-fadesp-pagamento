package com.fadesp.pagamento.utils;

public class ValidadorCPF {

    public static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0, peso = 10;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }
            int primeiroDigitoVerificador = calcularDigitoVerificador(soma);
            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }
            int segundoDigitoVerificador = calcularDigitoVerificador(soma);
            return cpf.charAt(9) - '0' == primeiroDigitoVerificador &&
                    cpf.charAt(10) - '0' == segundoDigitoVerificador;

        } catch (Exception e) {
            return false;
        }
    }

    private static int calcularDigitoVerificador(int soma) {
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    public static String formatarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido para formatação.");
        }
        return String.format("%s.%s.%s-%s",
                cpf.substring(0, 3),
                cpf.substring(3, 6),
                cpf.substring(6, 9),
                cpf.substring(9, 11));
    }
}

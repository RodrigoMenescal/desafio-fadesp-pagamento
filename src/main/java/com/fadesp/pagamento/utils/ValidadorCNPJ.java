package com.fadesp.pagamento.utils;

public class ValidadorCNPJ {

    public static boolean validarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            int[] pesosPrimeiro = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] pesosSegundo = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = calcularSoma(cnpj, pesosPrimeiro);
            int primeiroDigitoVerificador = calcularDigitoVerificador(soma);

            soma = calcularSoma(cnpj, pesosSegundo);
            int segundoDigitoVerificador = calcularDigitoVerificador(soma);

            return cnpj.charAt(12) - '0' == primeiroDigitoVerificador &&
                    cnpj.charAt(13) - '0' == segundoDigitoVerificador;

        } catch (Exception e) {
            return false;
        }
    }

    private static int calcularSoma(String cnpj, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos[i];
        }
        return soma;
    }

    private static int calcularDigitoVerificador(int soma) {
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    public static String formatarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            throw new IllegalArgumentException("CNPJ inválido para formatação.");
        }
        return String.format("%s.%s.%s/%s-%s",
                cnpj.substring(0, 2),
                cnpj.substring(2, 5),
                cnpj.substring(5, 8),
                cnpj.substring(8, 12),
                cnpj.substring(12, 14));
    }
}

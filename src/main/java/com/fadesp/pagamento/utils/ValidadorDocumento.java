package com.fadesp.pagamento.utils;

public class ValidadorDocumento {

    public static boolean validar(String documento) {
        if (documento == null) {
            return false;
        }

        documento = documento.replaceAll("\\D", ""); // Remove caracteres não numéricos

        if (documento.length() == 11) {
            return ValidadorCPF.validarCPF(documento);
        } else if (documento.length() == 14) {
            return ValidadorCNPJ.validarCNPJ(documento);
        }

        return false;
    }

    public static String formatarDocumento(String documento) {
        if (documento == null) {
            throw new IllegalArgumentException("Documento inválido para formatação.");
        }

        documento = documento.replaceAll("\\D", ""); // Remove caracteres não numéricos

        if (documento.length() == 11) {
            return ValidadorCPF.formatarCPF(documento);
        } else if (documento.length() == 14) {
            return ValidadorCNPJ.formatarCNPJ(documento);
        }

        throw new IllegalArgumentException("Documento deve ser um CPF ou CNPJ válido.");
    }
}
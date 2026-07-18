package br.com.empresa.emprestimos.shared.util;

import java.util.UUID;

public final class UuidUtil {

    private UuidUtil() {
    }

    public static UUID novo() {
        return UUID.randomUUID();
    }

    public static UUID parse(String valor) {
        try {
            return UUID.fromString(valor);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("UUID inválido: " + valor);
        }
    }

    public static boolean isValido(String valor) {
        if (valor == null) {
            return false;
        }
        try {
            UUID.fromString(valor);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}



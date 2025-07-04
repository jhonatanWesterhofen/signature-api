package com.java.hash.domain.Enums;

/**
 *
 * @author Jhonatan
 */
public enum EnumStatusVerification implements IEnum {

    INVALIDO("INVALIDO", "INVALIDO"),
    VALIDO("VALIDO", "VALIDO");

    private final String key, value;

    private EnumStatusVerification(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean containsInEnum(String key) {
        for (EnumStatusVerification en : EnumStatusVerification.values()) {
            if (key.equals(en.getKey())) {
                return true;
            }
        }
        return false;
    }

    public static EnumStatusVerification findByKey(String key) {
        for (EnumStatusVerification en : EnumStatusVerification.values()) {
            if (key.equals(en.getKey())) {
                return en;
            }
        }
        return null;
    }
}
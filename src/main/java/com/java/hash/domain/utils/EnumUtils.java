package com.java.hash.domain.utils;

import com.java.hash.domain.Enums.IEnum;

public class EnumUtils {

    public static <T extends IEnum> T parseByKey(Class<T> enumValue, String key) {
        try {
            if (key != null && !key.trim().isEmpty()) {
                for (T value : enumValue.getEnumConstants()) {
                    if (value.getKey().equalsIgnoreCase(key)) {
                        return value;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

}

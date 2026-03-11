package com.jsp.client.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParentProductTypeHolder {

    private static final Map<String, String> parentTypeMap =
            new ConcurrentHashMap<>();

    public static void put(String parentCode, String productTypeName) {
        parentTypeMap.put(parentCode, productTypeName);
    }

    public static String get(String parentCode) {
        return parentTypeMap.get(parentCode);
    }

    public static void clear() {
        parentTypeMap.clear();
    }
}
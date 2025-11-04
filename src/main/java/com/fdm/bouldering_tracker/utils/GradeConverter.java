package com.fdm.bouldering_tracker.utils;

import java.util.HashMap;
import java.util.Map;

public class GradeConverter {

    private static final Map<String, String> vToFont = new HashMap<>();
    private static final Map<String, String> fontToV = new HashMap<>();

    static {
        // Define V → Font mapping (lower V grade only)
        vToFont.put("V0", "4");
        vToFont.put("V0+", "4+");
        vToFont.put("V1", "5");
        vToFont.put("V2", "5+");
        vToFont.put("V3", "6A");
        vToFont.put("V4", "6B");
        vToFont.put("V5", "6C");
        vToFont.put("V6", "7A");
        vToFont.put("V7", "7A+");
        vToFont.put("V8", "7B");
        vToFont.put("V9", "7C");
        vToFont.put("V10", "7C+");
        vToFont.put("V11", "8A");
        vToFont.put("V12", "8A+");
        vToFont.put("V13", "8B");
        vToFont.put("V14", "8B+");
        vToFont.put("V15", "8C");
        vToFont.put("V16", "8C+");

        // Generate Font → V mapping from V → Font
        for (Map.Entry<String, String> entry : vToFont.entrySet()) {
            // Only add if not already present to preserve lower V grade
            fontToV.putIfAbsent(entry.getValue(), entry.getKey());
        }
    }

    public static String convertToFont(String vGrade) {
        return vToFont.getOrDefault(vGrade, "Unknown");
    }

    public static String convertToV(String fontGrade) {
        return fontToV.getOrDefault(fontGrade, "Unknown");
    }
}
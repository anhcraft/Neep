package dev.anhcraft.neep.utils;

import org.jetbrains.annotations.NotNull;

public class StringUtil {
    public static String repeat(@NotNull String str, int times){
        if(str.isEmpty()) return str;
        if(times <= 0) return "";
        if(times == 1) return str;

        StringBuilder sb = new StringBuilder();
        while(times-- > 0) sb.append(str);
        return sb.toString();
    }

    public static String repeat(char c, int times){
        if(times <= 0) return "";
        if(times == 1) return Character.toString(c);

        StringBuilder sb = new StringBuilder();
        while(times-- > 0) sb.append(c);
        return sb.toString();
    }
}

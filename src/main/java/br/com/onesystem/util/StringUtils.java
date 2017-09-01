/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

/**
 *
 * @author Rauber
 */
public class StringUtils {

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return startsWith(str, prefix, true);
    }

    private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
        if (str == null || prefix == null) {
            return (str == null && prefix == null);
        }
        if (prefix.length() > str.length()) {
            return false;
        }

        return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
    }

    public static boolean containsLetter(String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static String primeiraLetraMaiusculaAposEspaco(String str) {
        String split[] = str.split(" ");
        str = "";
        for (int i = 0; i < split.length; i++) {
            str += split[i].substring(0, 1).toUpperCase() + split[i].substring(1).toLowerCase();
            if (i != (split.length - 1)) {
                str += " ";
            }
        }
        return str;
    }
}

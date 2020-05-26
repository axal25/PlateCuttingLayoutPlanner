package test.utils;

import java.util.HashMap;
import java.util.HashSet;

public class StringComparison {
    public static String compare(String string1, String string2) {
        if (string1.compareTo(string2) == 0) return same(string1, string2);
        else return different(string1, string2);
    }

    private static String same(String string1, String string2) {
        return String.format(
                "string1.compareTo(string2) == 0%s%s",
                String.format("\r\n\tstring1: \r\n%s", string1),
                String.format("\r\n\tstring2: \r\n%s", string2)
        );
    }

    private static String different(String string1, String string2) {
        StringBuilder outputSB = new StringBuilder();
        outputSB.append(String.format("string1.length() - string2.length(): %d", string1.length() - string2.length()));
        outputSB.append("\r\n\tDifferences: [");
        outputSB = appendOutputSBByDifferences(string1, string2, outputSB);
        outputSB.append("]");
        outputSB.append(String.format("\r\n\tstring1: \r\n%s", string1));
        outputSB.append(String.format("\r\n\tstring2: \r\n%s", string2));
        return outputSB.toString();
    }

    private static StringBuilder appendOutputSBByDifferences(String string1, String string2, StringBuilder stringBuilder) {
        char[] charArray1 = string1.toCharArray();
        char[] charArray2 = string2.toCharArray();
        stringBuilder.append(getDifferencesCommonLength(charArray1, charArray2));
        stringBuilder.append(getDifferencesOfLongerArray(charArray1, charArray2));
        stringBuilder.append("\t");
        return stringBuilder;
    }

    private static String getDifferencesCommonLength(char[] charArray1, char[] charArray2) {
        StringBuilder diffCommonLength = new StringBuilder();
        for (int i = 0; i < charArray1.length && i < charArray2.length; i++) {
            if (charArray1[i] != charArray2[i]) {
                diffCommonLength.append(String.format("\r\n%s vs. %s %s", charToRepresentation(charArray1[i]), charToRepresentation(charArray2[i]), indexInformation(i)));
                if (i < charArray1.length - 1 && i < charArray2.length - 1)
                    diffCommonLength.append(charComparisonSeparation());
            }
        }
        return diffCommonLength.toString();
    }

    private static String getDifferencesOfLongerArray(char[] charArray1, char[] charArray2) {
        StringBuilder diffLongerArray = new StringBuilder();
        if (charArray1.length != charArray2.length) {
            char[] longerCharArray = null;
            int shorterLength = -1;
            int numberOfLongerCharArray = -1;
            if (charArray1.length > charArray2.length) {
                longerCharArray = charArray1;
                shorterLength = charArray2.length;
                numberOfLongerCharArray = 1;
            } else {
                longerCharArray = charArray2;
                shorterLength = charArray1.length;
                numberOfLongerCharArray = 2;
            }
            for (int i = shorterLength - 1; i < longerCharArray.length; i++) {
                diffLongerArray.append("\n\r");
                if (numberOfLongerCharArray == 2)
                    diffLongerArray.append(String.format("%s vs. ", charToRepresentation()));
                diffLongerArray.append(charToRepresentation(longerCharArray[i]));
                if (numberOfLongerCharArray == 1)
                    diffLongerArray.append(String.format(" vs. %s", charToRepresentation()));
                diffLongerArray.append(String.format(" %s", indexInformation(i)));
                if (i < longerCharArray.length - 1) diffLongerArray.append(charComparisonSeparation());
            }
            diffLongerArray.append("\r\n");
        }
        return diffLongerArray.toString();
    }

    private static final HashMap<Integer, String> unreadableCharacters;
    static {
        unreadableCharacters = new HashMap<>();
        unreadableCharacters.put(0, ">> NULL <<");
        unreadableCharacters.put(1, ">> SOH <<");
        unreadableCharacters.put(2, ">> STX <<");
        unreadableCharacters.put(3, ">> ETX <<");
        unreadableCharacters.put(4, ">> EOT <<");
        unreadableCharacters.put(5, ">> ENQ <<");
        unreadableCharacters.put(6, ">> ACK <<");
        unreadableCharacters.put(7, ">> BEL <<");
        unreadableCharacters.put(8, ">> BS <<");
        unreadableCharacters.put(9, ">> TAB <<");
        unreadableCharacters.put(10, ">> LF <<");
        unreadableCharacters.put(11, ">> VT <<");
        unreadableCharacters.put(12, ">> FF <<");
        unreadableCharacters.put(13, ">> CR <<");
        unreadableCharacters.put(14, ">> SO <<");
        unreadableCharacters.put(15, ">> SI <<");
        unreadableCharacters.put(16, ">> DLE <<");
        unreadableCharacters.put(17, ">> DC1 <<");
        unreadableCharacters.put(18, ">> DC2 <<");
        unreadableCharacters.put(19, ">> DC3 <<");
        unreadableCharacters.put(20, ">> DC4 <<");
        unreadableCharacters.put(21, ">> NAK <<");
        unreadableCharacters.put(22, ">> SYN <<");
        unreadableCharacters.put(23, ">> ETB <<");
        unreadableCharacters.put(24, ">> CAN <<");
        unreadableCharacters.put(25, ">> EM <<");
        unreadableCharacters.put(26, ">> SUB <<");
        unreadableCharacters.put(27, ">> ESC <<");
        unreadableCharacters.put(28, ">> FS <<");
        unreadableCharacters.put(29, ">> GS <<");
        unreadableCharacters.put(30, ">> RS <<");
        unreadableCharacters.put(31, ">> US <<");
    }

    private static String charToRepresentation() {
        return String.format("''(code: --)");
    }

    private static String charToRepresentation(char c) {
        int charCode = (int) c;
        String representation;
        if(unreadableCharacters.containsKey(charCode)) representation = String.format("%s", unreadableCharacters.get(charCode));
        else representation = String.format("'%c'", c);
        return String.format("%s (code: %d)", representation, charCode);
    }

    private static String indexInformation(int i) {
        return String.format("[index: %d]", i);
    }

    private static String charComparisonSeparation() {
        return "\r\n------ next different char ------";
    }
}

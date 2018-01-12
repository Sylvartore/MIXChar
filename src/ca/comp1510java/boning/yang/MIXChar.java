
package ca.comp1510java.boning.yang;

import java.util.Scanner;

/**
 * Represent MIXChar characters that can be encode and decode to long arrays.
 *
 * @author Boning Yang, set1E
 * @version 1.0
 */
public final class MIXChar {

    /** Maximum packing MIXChar characters. */
    public static final int MAX_PACKING_CHARS = 11;

    /** The packing base. */
    public static final int BASE = 56;

    /** Delta. */
    private static final char DELTA = '\u0394';

    /** Sigma. */
    private static final char SIGMA = '\u03A3';

    /** Pi. */
    private static final char PI = '\u03A0';

    /** The MIXchar characters. */
    private static final char[] CHAR_ARRAY = {' ', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', DELTA, 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            SIGMA, PI, 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9', '.', ',', '(', ')', '+', '-',
            '*', '/', '=', '$', '<', '>', '@', ';', ':', '\'' };

    /** The numerical value of this MIXChar. */
    private int value;

    /**
     * Constructs an object of type MIXChar.
     * 
     * @param value
     *            the numerical value of this MIXChar
     */
    private MIXChar(int value) {
        this.value = value;
    }

    /**
     * Returns true if a char corresponds to a MIXChar character, false
     * otherwise.
     * 
     * @param c
     *            the char
     * @return true if c corresponds to a MIXChar character, false otherwise
     */
    static boolean isMIXChar(char c) {
        for (char a : CHAR_ARRAY) {

            if (c == a) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a char to the corresponding MIXChar character.
     * 
     * @param c
     *            the char
     * @return the corresponding MIXchar character
     */
    static MIXChar toMIXChar(char c) {
        int count = 0;
        while (count < CHAR_ARRAY.length) {
            if (CHAR_ARRAY[count] == c) {
                return new MIXChar(count);
            }
            count++;
        }
        throw new IllegalArgumentException(
                "Can not Convert due to no corresponding MIXChar character.");

    }

    /**
     * Converts a string to an array of corresponding MIXChar characters.
     * 
     * @param s
     *            the string
     * @return the array of corresponding MIXchar characters
     */
    static MIXChar[] toMIXChar(String s) {
        MIXChar[] chars = new MIXChar[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (!MIXChar.isMIXChar(s.charAt(i))) {
                throw new IllegalArgumentException("Can not Convert due to "
                        + "no corresponding MIXChar character.");
            }
            chars[i] = MIXChar.toMIXChar(s.charAt(i));
        }
        return chars;
    }

    /**
     * Converts MIXChar character to corresponding Java char.
     * 
     * @param c
     *            the MIXChar character
     * @return the corresponding Java char
     */
    static char toChar(MIXChar c) {
        return CHAR_ARRAY[c.value];
    }

    /**
     * Converts an array of MIXChar characters to corresponding string.
     * 
     * @param chars
     *            the array of MIXChar characters
     * @return the corresponding string
     */
    static String toString(MIXChar[] chars) {
        String str = "";
        for (MIXChar c : chars) {
            str += MIXChar.toChar(c);
        }
        return str;
    }

    /**
     * Converts the MIXChar array into a long array by packing 11 MIXChar
     * characters in each long, with the last long element having perhaps fewer
     * than 11 characters packed into it. Then stores the long array in a Code 
     * type along with the length of original MIXChar characters.
     * 
     * @param m
     *            the MIXChar array to be encoded
     * @return the Code including the encoded long array and the length of
     *         original MIXChar characters
     */
    static Code encode(MIXChar[] m) {

        int length = (m.length % MAX_PACKING_CHARS == 0
                ? m.length / MAX_PACKING_CHARS
                : m.length / MAX_PACKING_CHARS + 1);

        long[] ary = new long[length];
        if (m.length <= MAX_PACKING_CHARS) {
            int count = 0;
            for (int i = m.length - 1; i >= 0; i--) {
                ary[ary.length - 1] += m[i].value
                        * (long) Math.pow(BASE, count);
                count++;
            }
            return new Code(ary, m.length);
        }
        for (int n = 0; n < (m.length / MAX_PACKING_CHARS); n++) {
            for (int i = 0 + n * MAX_PACKING_CHARS; i <= MAX_PACKING_CHARS - 1
                    + n * MAX_PACKING_CHARS; i++) {
                ary[n] += m[i].value * (long) Math.pow(BASE,
                        MAX_PACKING_CHARS - 1 - i % MAX_PACKING_CHARS);
            }
        }
        if (m.length % MAX_PACKING_CHARS == 0) {
            return new Code(ary, m.length);
        }
        int count = 0;
        for (int i = m.length - 1; i % MAX_PACKING_CHARS != MAX_PACKING_CHARS
                - 1; i--) {

            ary[ary.length - 1] += m[i].value * (long) Math.pow(BASE, count);
            count++;
        }
        return new Code(ary, m.length);
    }

    /**
     * Returns an array of MIXChar characters corresponding to the Code
     * including the encoded long array and the length of original MIXChar
     * characters.
     * 
     * @param c
     *            the Code including the encoded long array and the length of
     *            original MIXChar characters
     * @return the array of MIXChar characters corresponding to the Code
     */
    static MIXChar[] decode(Code c) {

        long[] l = c.getCode();
        int length = c.getLength();
        MIXChar[] ary = new MIXChar[length];
        if (l.length > 1) {
            for (int i = 0; i < l.length - 1; i++) {
                int count = MAX_PACKING_CHARS * (i + 1) - 1;
                while (l[i] != 0) {
                    ary[count] = new MIXChar(
                            (int) Long.remainderUnsigned(l[i], BASE));
                    l[i] = Long.divideUnsigned(l[i], BASE);
                    count--;
                }
                while (count != i * MAX_PACKING_CHARS - 1) {
                    ary[count] = new MIXChar(0);
                    count--;
                }
            }
        }

        int count = length - 1;
        while (l[l.length - 1] != 0) {
            ary[count] = new MIXChar(
                    (int) Long.remainderUnsigned(l[l.length - 1], BASE));
            l[l.length - 1] = Long.divideUnsigned(l[l.length - 1], BASE);
            count--;
        }
        while (count != (length / MAX_PACKING_CHARS) * MAX_PACKING_CHARS - 1) {
            if (count < 0) {
                break;
            }
            ary[count] = new MIXChar(0);
            count--;
        }
        return ary;
    }

    /**
     * Returns numerical value of this MIXChar.
     * 
     * @return the numerical value of this MIXChar
     */
    int ordinal() {
        return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.valueOf(CHAR_ARRAY[value]);
    }

    /**
     * Test the coding system.
     * 
     * @param test
     *            the string to be tested
     */
    private static void test(String test) {
        System.out.println("Original String:");
        System.out.println(test);
        try {
            MIXChar[] before = MIXChar.toMIXChar(test);
            System.out.println("After converted to MIXChar:");
            System.out.println(MIXChar.toString(before));
            Code encoded = MIXChar.encode(before);
            System.out.println("Encoded:");
            System.out.println(encoded);
            MIXChar[] decoded = MIXChar.decode(encoded);
            System.out.println("Decoded:");
            System.out.println(MIXChar.toString(decoded));
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "\n");
        }

    }

    /**
     * Drives the program.
     *
     * @param args
     *            command line arguments.
     */
    public static void main(String[] args) {
        String str1 = "IFEBA";
        test(str1);

        String str2 = " I FE B  A";
        test(str2);

        String str3 = "ABCDEFGHIJKLMGOPQRSTUVWXYZ,.1234567890 @$=+-*/<>:;/'()"
                + DELTA + SIGMA + PI;
        test(str3);

        final int times = 50;
        String str4 = "";
        for (int i = 0; i < times; i++) {
            str4 += "\'";
        }
        test(str4);

        String str5 = "abc";
        test(str5);

        System.out.println("Please enter your test String.");
        Scanner scan = new Scanner(System.in);
        String str6 = scan.nextLine();
        test(str6);
        scan.close();

        System.out.println("Question three was called and ran sucessfully.");

    }

}

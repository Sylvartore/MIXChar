package ca.comp1510java.boning.yang;

/**
 * Representing the Code including the packed long array and the length of the
 * original MIXChar characters.
 * 
 * @author Yang, Boning (Andy)
 * @version 2017
 */
public class Code {
    /** The long array of packed MIXChar characters. */
    private long[] code;
    /** The length of the original MIXChar characters. */
    private int length;

    /**
     * Constructs an object of type Code.
     * 
     * @param code
     *            the long array of packed MIXChar characters
     * @param length
     *            the length of the original MIXChar characters
     */
    public Code(long[] code, int length) {
        this.code = code;
        this.length = length;
    }

    /**
     * Returns the long array as the code.
     * 
     * @return the long array as the code
     */
    public long[] getCode() {
        return code;
    }

    /**
     * Returns the length of the original MIXChars.
     * 
     * @return the length of the original MIXChars
     */
    public int getLength() {
        return length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String str = "The legnth of the original MIXChars: " + length;
        str += "\nThe long array of the code: ";
        for (long l : code) {
            str += "\n" + Long.toUnsignedString(l);
        }
        return str;
    }

}

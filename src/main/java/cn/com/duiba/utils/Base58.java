package cn.com.duiba.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * Created by liuyao on 2017/1/31.
 */
public class Base58 {
    public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int[] INDEXES = new int[128];

    public Base58() {
    }

    public static String encode(byte[] input) {
        if(input.length == 0) {
            return "";
        } else {
            input = copyOfRange(input, 0, input.length);

            int zeroCount;
            for(zeroCount = 0; zeroCount < input.length && input[zeroCount] == 0; ++zeroCount) {
                ;
            }

            byte[] temp = new byte[input.length * 2];
            int j = temp.length;

            byte output;
            for(int startAt = zeroCount; startAt < input.length; temp[j] = (byte)ALPHABET[output]) {
                output = divmod58(input, startAt);
                if(input[startAt] == 0) {
                    ++startAt;
                }

                --j;
            }

            while(j < temp.length && temp[j] == ALPHABET[0]) {
                ++j;
            }

            while(true) {
                --zeroCount;
                if(zeroCount < 0) {
                    byte[] var8 = copyOfRange(temp, j, temp.length);

                    try {
                        return new String(var8, "US-ASCII");
                    } catch (UnsupportedEncodingException var7) {
                        throw new RuntimeException(var7);
                    }
                }

                --j;
                temp[j] = (byte)ALPHABET[0];
            }
        }
    }

    public static byte[] decode(String input) throws IllegalArgumentException {
        if(input.length() == 0) {
            return new byte[0];
        } else {
            byte[] input58 = new byte[input.length()];

            int zeroCount;
            int j;
            for(zeroCount = 0; zeroCount < input.length(); ++zeroCount) {
                char temp = input.charAt(zeroCount);
                j = -1;
                if(temp >= 0 && temp < 128) {
                    j = INDEXES[temp];
                }

                if(j < 0) {
                    throw new IllegalArgumentException("Illegal character " + temp + " at " + zeroCount);
                }

                input58[zeroCount] = (byte)j;
            }

            for(zeroCount = 0; zeroCount < input58.length && input58[zeroCount] == 0; ++zeroCount) {
                ;
            }

            byte[] var7 = new byte[input.length()];
            j = var7.length;

            byte mod;
            for(int startAt = zeroCount; startAt < input58.length; var7[j] = mod) {
                mod = divmod256(input58, startAt);
                if(input58[startAt] == 0) {
                    ++startAt;
                }

                --j;
            }

            while(j < var7.length && var7[j] == 0) {
                ++j;
            }

            return copyOfRange(var7, j - zeroCount, var7.length);
        }
    }

    public static BigInteger decodeToBigInteger(String input) throws IllegalArgumentException {
        return new BigInteger(1, decode(input));
    }

    private static byte divmod58(byte[] number, int startAt) {
        int remainder = 0;

        for(int i = startAt; i < number.length; ++i) {
            int digit256 = number[i] & 255;
            int temp = remainder * 256 + digit256;
            number[i] = (byte)(temp / 58);
            remainder = temp % 58;
        }

        return (byte)remainder;
    }

    private static byte divmod256(byte[] number58, int startAt) {
        int remainder = 0;

        for(int i = startAt; i < number58.length; ++i) {
            int digit58 = number58[i] & 255;
            int temp = remainder * 58 + digit58;
            number58[i] = (byte)(temp / 256);
            remainder = temp % 256;
        }

        return (byte)remainder;
    }

    private static byte[] copyOfRange(byte[] source, int from, int to) {
        byte[] range = new byte[to - from];
        System.arraycopy(source, from, range, 0, range.length);
        return range;
    }

    static {
        int i;
        for(i = 0; i < INDEXES.length; ++i) {
            INDEXES[i] = -1;
        }

        for(i = 0; i < ALPHABET.length; INDEXES[ALPHABET[i]] = i++) {
            ;
        }

    }
}
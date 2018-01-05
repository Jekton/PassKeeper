import java.util.Random;

/**
 * @author Jekton
 */
public class KeyGen {

    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println();
            generate(Integer.parseInt(arg));
            System.out.println();
        }
    }

    private static void generate(int nbytes) {
        byte[] bytes = new byte[nbytes];
        // TODO put your seek
        Random random = new Random();
        random.nextBytes(bytes);
        printBytes(bytes);
    }

    private static void printBytes(byte[] bytes) {
        for (int i = 1; i < bytes.length + 1; ++i) {
            byte signedByte = bytes[i - 1];
            int unsignedValue = signedByte >= 0 ? signedByte
                                                : signedByte + 256; // 0x100
            System.out.printf("(uint8_t) 0x%02x,", unsignedValue);

            if (i % 4 == 0) {
                System.out.println();
            } else {
                System.out.print(" ");
            }
        }
    }
}


import java.util.Random;

/**
 * @author Jekton
 */
public class KeyGen {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java KeyGen [seek] [nbytes]");
            return;
        }
        System.out.println();
        generate(Long.parseLong(args[0]), Integer.parseInt(args[1]));
        System.out.println();
    }

    private static void generate(long seek, int nbytes) {
        byte[] bytes = new byte[nbytes];
        Random random = new Random(seek);
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


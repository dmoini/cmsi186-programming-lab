/**
 * This class demonstrates the use of a large integer to implement integer exponents
 * using a divide-and-conquer algorithm.
 */
public class Exponent {
    /**
     * Calculates the first argument raised to the second argument, and prints
     * the result.
     */
    public static void main(String[] args) {
        // We must have exactly two arguments.
        if (args.length != 2) {
            printUsage();
            return;
        }

        // Parse the two arguments.
        Intzilla base = null;
        Intzilla power = null;
        try {
            base = new Intzilla(args[0]);
            power = new Intzilla(args[1]);
            if (power.isLessThan(new Intzilla())) {
                System.out.println("Sorry, but this program only accepts non-negative exponents.");
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException iaexc) {
            printUsage();
            return;
        }

        // Perform the calculation, and display the answer.
        System.out.println(base.toString() + " raised to the " + power.toString() + "-th power is " +
                exponent(base, power).toString() + ".");
    }

    /**
     * The actual exponent function, separated out because it is called
     * recursively.
     *
     * @param base
     *            The large integer to raise
     * @param power
     *            The large integer by which to raise
     * @return base raised to the power-th power
     */
    private static Intzilla exponent(Intzilla base, Intzilla power) {
        // Initialize two large integers that we'll use a lot.
        final Intzilla zero = new Intzilla();
        final Intzilla two = new Intzilla("2");

        // Base case: zero-th power.
        if (power.equals(zero)) {
            return new Intzilla("1");
        }

        // Divide and conquer: if power is even, then the final power is base
        // raised to HALF of power, squared. If power is odd, we make sure to
        // tack on an extra multiplication.
        Intzilla halfThePower = exponent(base, power.div(two));
        Intzilla halfThePowerSquared = halfThePower.times(halfThePower);
        return power.mod(two).equals(zero) ? halfThePowerSquared : base.times(halfThePowerSquared);
    }

    /**
     * Displays a message that describes how the program is to be used.
     */
    private static void printUsage() {
        System.out.println("Usage: java Exponent <base> <power>, where <base> is an integer and <power> " +
                "is a non-negative integer.");
    }
}

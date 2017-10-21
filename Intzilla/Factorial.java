public class Factorial {

    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            return;
        }

        Intzilla factorial = null;
        try {
            factorial = new Intzilla(args[0]);
            if (factorial.isLessThan(new Intzilla())) {
                System.out.println("Sorry, but this program only accepts non-negative factorials.");
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException iaexc) {
            printUsage();
            return;
        }

        System.out.println(factorial.toString() + "! is " + factorial(factorial).toString() + ".");
    }

    private static Intzilla factorial(Intzilla number) {
        final Intzilla zero = new Intzilla();
        final Intzilla one = new Intzilla("1");

        if (number.equals(zero) || number.equals(one)) {
            return new Intzilla("1");
        }
        return number.times(factorial(number.minus(one)));
    }

    private static void printUsage() {
        System.out.println("Usage: java Exponent <factorial>, where <factorial> is a non-negative integer");
    }
}

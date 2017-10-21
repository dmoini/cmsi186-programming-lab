public class Fibonacci {

    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            return;
        }

        Intzilla number = null;
        try {
            number = new Intzilla(args[0]);
            if (number.isLessThan(new Intzilla())) {
                System.out.println("Sorry, but this program only accepts non-negative integers.");
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException iaexc) {
            printUsage();
            return;
        }

        System.out.println("The " + number + "th number in the Fibonacci sequence is " + fibonacci(number).toString() + ".");
    }

    private static Intzilla fibonacci(Intzilla number) {
        final Intzilla one = new Intzilla("1");
        final Intzilla two = new Intzilla("2");

        if (number.isLessThan(one) || number.equals(one)) {
            return number;
        } else {
            return fibonacci(number.minus(one)).plus(fibonacci(number.minus(two)));
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java Exponent <fibonacci>, where <fibonacci> is a non-negative integer");
    }
}

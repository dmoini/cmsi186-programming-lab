/**
 * This class provides an executable wrapper for an optimal change-making algorithm,
 * implemented via dynamic programming.
 */
public class MakeOptimalChange {

    /**
     * The executable entry point. Arguments include a comma-separated list of denominations
     * (without spaces) and the amount for which to make change.
     *
     * @param args
     *            a comma-separated list of denominations (without spaces) and the amount for which to make change
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            if (amount < 0) {
                System.out.println("Change cannot be made for negative amounts.");
                System.out.println();
                printUsage();
                return;
            }

            String[] denominationStrings = args[0].split(",");
            int[] denominations = new int[denominationStrings.length];

            for (int i = 0; i < denominations.length; i++) {
                denominations[i] = Integer.parseInt(denominationStrings[i]);
                if (denominations[i] <= 0) {
                    System.out.println("Denominations must all be greater than zero.");
                    System.out.println();
                    printUsage();
                    return;
                }

                for (int j = 0; j < i; j++) {
                    if (denominations[j] == denominations[i]) {
                        System.out.println("Duplicate denominations are not allowed.");
                        System.out.println();
                        printUsage();
                        return;
                    }
                }
            }

            Tally change = makeOptimalChange(denominations, amount);
            if (change.isImpossible()) {
                System.out.println("It is impossible to make " + amount + " cents with those denominations.");
            } else {
                int coinTotal = change.total();
                System.out.println(amount + " cents can be made with " + coinTotal + " coin" +
                        getSimplePluralSuffix(coinTotal) + " as follows:");

                for (int i = 0; i < denominations.length; i++) {
                    int coinCount = change.getElement(i);
                    System.out.println("- " + coinCount + " " + denominations[i] + "-cent coin" +
                            getSimplePluralSuffix(coinCount));
                }
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Denominations and amount must all be integers.");
            System.out.println();
            printUsage();
        }
    }

    /**
     * Returns the coin tally that represents the optimal change (fewest coins) for the given
     * amount using the given denominations.
     *
     * @param denominations
     *            Integer array of denomination values
     * @param amount
     *            The amount for which to make change
     * @return the coin tally that represents optimal change (fewest coins)
     */
    public static Tally makeOptimalChange(int[] denominations, int amount) {
        int rows = denominations.length;
        int columns = amount + 1;
        Tally[][] mantra = new Tally[rows][columns];

        int coin;
        int difference;
        Tally currentTally;
        for (int r = 0; r < rows; r++) { // rows
            mantra[r][0] = new Tally(rows);
            coin = denominations[r];
            for (int c = 1; c < columns; c++) { // columns
                difference = c - coin;
                currentTally = new Tally(rows);
                if (r == 0) {
                    if ((c < coin) || (c % coin != 0)) {
                        mantra[r][c] = Tally.IMPOSSIBLE;
                    } else {
                        currentTally.setElement(r, 1);
                        mantra[r][c] = currentTally.add(mantra[r][difference]);
                    }
                } else {
                    if (c < coin) {
                        mantra[r][c] = mantra[r - 1][c];
                    } else {
                        if (mantra[r][difference].equals(Tally.IMPOSSIBLE)) {
                            mantra[r][c] = Tally.IMPOSSIBLE;
                        } else {
                            currentTally.setElement(r, 1);
                            mantra[r][c] = currentTally.add(mantra[r][difference]);
                        }
                    }
                    if (mantra[r - 1][c].total() < mantra[r][c].total() && !(mantra[r - 1][c].equals(Tally.IMPOSSIBLE)) ||
                        mantra[r][c].equals(Tally.IMPOSSIBLE) && mantra[r - 1][c].total() > 0) {
                        mantra[r][c] = mantra[r - 1][c];
                    }
                }
            }
        }
        return mantra[rows - 1][columns - 1];
    }

    private static void printUsage() {
        System.out.println("Usage: java MakeOptimalChange <denominations> <amount>");
        System.out.println("  - <denominations> is a comma-separated list of denominations (no spaces)");
        System.out.println("  - <amount> is the amount for which to make change");
    }

    private static String getSimplePluralSuffix(int count) {
        return count == 1 ? "" : "s";
    }

}

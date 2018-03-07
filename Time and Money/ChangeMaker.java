public class ChangeMaker {
    public static int getQuarters(int cents) {
        return cents/25;
    }

    public static int getDimes(int cents) {
        return cents/10;
    }

    public static int getNickels(int cents) {
        return cents/5;
    }

    public static int getPennies(int cents) {
        return cents;
    }

    public static int[] getCoins(int cents) {
        int[] coinsArray = new int[4];
        coinsArray[0] = getQuarters(cents);
        cents = cents - (25 * getQuarters(cents));
        coinsArray[1] = getDimes(cents);
        cents = cents - (10 * getDimes(cents));
        coinsArray[2] = getNickels(cents);
        cents = cents - (5 * getNickels(cents));
        coinsArray[3] = cents;
        return coinsArray;
    }

    public static int getTotalCents(int[] coins) {
        int totalCents = 0;
        totalCents = totalCents + (25 * coins[0]);
        totalCents = totalCents + (10 * coins[1]);
        totalCents = totalCents + (5 * coins[2]);
        totalCents = totalCents + coins[3];
        return totalCents;
    }

    public static int[] joinCoins(int[] initialCoins, int[] additionalCoins) {
        int[] joinCoinsArray = new int[4];
        for(int i = 0; i < 4; i++) {
            joinCoinsArray[i] = initialCoins[i] + additionalCoins[i];
        }
        return joinCoinsArray;
    }

    public static void main(String[] args) {
        boolean validArguments = true;
        boolean negativeInputs = false;
        boolean noErrors = true;
        int[] coinsArray = new int[args.length];
        try {
            for(int x = 0; x < args.length; x++) {
                coinsArray[x] = Integer.parseInt(args[x]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Supplied values must be integers.");
            noErrors = false;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java ChangeMaker <amount in cents | four coin counts | eight coin counts>");
            noErrors = false;
        }

        if(noErrors) {
            for(int x = 0; x < args.length; x++) {
                coinsArray[x] = Integer.parseInt(args[x]);
            }
            for(int x = 0; x < args.length; x++) {
                if(args[x] == "") {
                    validArguments = false;
                }
            }
            for(int x = 0; x < args.length; x++) {
                if(coinsArray[x] < 0) {
                    negativeInputs = true;
                }
            }

            if(coinsArray.length < 1) {
                System.out.println("Usage: java ChangeMaker <amount in cents | four coin counts | eight coin counts>");
            } else if(!validArguments) {
                System.out.println("Supplied values must be integers.");
            } else if(negativeInputs) {
                System.out.println("Negative amounts are not permitted.");
            } else if((coinsArray.length != 1) && (coinsArray.length != 4) && (coinsArray.length != 8)) {
                System.out.println("You must have either 1, 4, or 8 arguments for function to work properly.");
            } else if(coinsArray.length == 1) {
                int[] individualCoinAmounts = getCoins(coinsArray[0]);
                System.out.println("Quarters: " + individualCoinAmounts[0]);
                System.out.println("Dimes: " + individualCoinAmounts[1]);
                System.out.println("Nickels: " + individualCoinAmounts[2]);
                System.out.println("Pennies: " + individualCoinAmounts[3]);
            } else if(coinsArray.length == 4) {
                System.out.println(getTotalCents(coinsArray) + " cents");
            } else if(coinsArray.length == 8) {
                // return amount of coins and total cents
                int[] firstCoins = new int[4];
                int[] secondCoins = new int[4];
                for(int x = 0; x < 4; x++) {
                    firstCoins[x] = coinsArray[x];
                    secondCoins[x] = coinsArray[x + 4];
                }
                int[] totalCoins = joinCoins(firstCoins, secondCoins);
                System.out.println("Quarters: " + totalCoins[0]);
                System.out.println("Dimes: " + totalCoins[1]);
                System.out.println("Nickels: " + totalCoins[2]);
                System.out.println("Pennies: " + totalCoins[3]);
                System.out.println();
                System.out.println(getTotalCents(totalCoins) + " cents");
            }
        }
    }
}

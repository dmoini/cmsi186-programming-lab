public class AlexanderHamilton {

    static int coinCount;
    static int coinThrows;
    static int partitions;
    static double bias;
    static final Object[] defaultValues = {1000, 1000, 100, 0.5};

    public static void main(String args[]) {
        BagOfCoins defaultBag = new BagOfCoins();
        int minRange;
        int maxRange;
        Object[] user = new Object[4];

        if(args.length != 0 && args.length != 4) {
            System.out.println("Please input zero or four arguments.");
            return;
        }

        if(args.length == 0) {
            for(int i = 0; i < 1000; i++) {
                defaultBag.throwCoins();
            }

            for(int i = 0; i < defaultBag.getFlipHistogram(100).length; i++) {
                if(defaultBag.getFlipHistogram(100)[i] != 0) {
                    minRange = 10 * i;
                    maxRange = 10 * i + 9;
                    if(i == defaultBag.getFlipHistogram(100).length - 1) {
                        System.out.print(minRange + "-" + (maxRange + 1));
                    } else {
                        System.out.print(minRange + "-" + maxRange); // print out range
                    }
                    int asteriksCount = defaultBag.getFlipHistogram(100)[i]/10; // print out asteriks
                    System.out.print("  *");
                    for(int j = 0; j < asteriksCount; j++) {
                        System.out.print("*");
                    }
                    System.out.print(" ");
                    System.out.print(defaultBag.getFlipHistogram(100)[i]); // print out coin head flips amount
                    System.out.println();
                }
            }

        }

        try {
            if (args.length == 4) {
                for(int i = 0; i < args.length; i++) {
                    if(args[i].equals("-")) {
                        user[i] = defaultValues[i];
                    } else {
                        if(i <= 2) {
                            user[i] = Integer.parseInt(args[i]);
                        } else if(i == 3) {
                            user[i] = Double.parseDouble(args[i]);
                        }
                    }
                }

                coinCount = (int) user[0];
                coinThrows = (int) user[1];
                partitions = (int) user[2];
                bias = (double) user[3];

                if((coinCount < 0) || (coinThrows < 0) || (partitions < 0) || (bias < 0)) { // make sure vales are positive
                    System.out.println("Please input positive values.");
                    return;
                }
                if((bias < 0) || (bias > 1)) { // make sure bias is between 0 and 1
                    System.out.println("Please input a bias between zero and one.");
                    return;
                }

                BagOfCoins customBagOfCoins = new BagOfCoins(coinCount, bias);

                for(int i = 0; i < coinThrows; i++) {
                    customBagOfCoins.throwCoins();
                }

                for(int i = 0; i < customBagOfCoins.getFlipHistogram(partitions).length; i++) {
                    if(customBagOfCoins.getFlipHistogram(partitions)[i] != 0) {
                        minRange = (coinThrows/partitions)*i;
                        maxRange = minRange + (coinThrows/partitions - 1);
                        if(i == customBagOfCoins.getFlipHistogram(partitions).length - 1) {
                            System.out.print(minRange + "-" + (maxRange + 1));
                        } else {
                            System.out.print(minRange + "-" + maxRange); // print out range
                        }
                        int asteriksCount = customBagOfCoins.getFlipHistogram(partitions)[i]/10; // print out asteriks
                        System.out.print("  *");
                        for(int j = 0; j < asteriksCount; j++) {
                            System.out.print("*");
                        }
                        System.out.print(" ");
                        System.out.print(customBagOfCoins.getFlipHistogram(partitions)[i]); // print out coin head flips amount
                        System.out.println();
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter valid numbers.");
        } catch (ArithmeticException e) {
            System.out.println("Please do not enter a 0 there.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter only integers and doubles in their respective spots.");
        }
    }
}

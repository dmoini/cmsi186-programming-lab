public class PiEstimator {
    public static void main(String[] args) {
        int totalDarts = 0;
        if (args.length == 0) {
            totalDarts = 1000000;
        } else {
            try {
                totalDarts = Integer.parseInt(args[0]);
                if (totalDarts <= 0) {
                    throw new Exception();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a valid number.");
                return;
            } catch (Exception e) {
                System.out.println("Please input a number greater than 0.");
                return;
            }
        }
        int inCircle = 0;
        for (int i = 0; i < totalDarts; i++) {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;
            if ((x * x) + (y * y) <= 1) {
                inCircle++;
            }
        }
        double piEstimate = ((double)inCircle / (double)totalDarts) * 4;
        System.out.println("Total Darts: " + totalDarts);
        System.out.println("Darts in Circle: " + inCircle);
        System.out.println("Pi Estimate: " + piEstimate);
    }
}

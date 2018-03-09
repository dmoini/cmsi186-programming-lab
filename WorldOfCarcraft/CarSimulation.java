import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CarSimulation {

    public static final String INVALID_INSTRUCTIONS_MESSAGE = "Please input valid instructions: R/L/N";

    public static void main(String[] args) {
        // Free code! File processing has been implemented for you.
        int time = 0;
        boolean running = true;
        boolean carAboutToLeaveGrid;
        boolean carStartsOutOfGrid;
        Car[] cars = processCarInput();
        if (cars == null) { // processCarInput returns null if something went wrong.
            return;
        }

        // check to make sure no two cars have the same id
        for(int i = 0; i < cars.length - 1; i++) {
            for(int j = i + 1; j < cars.length; j++) {
                if(cars[i].getId() == cars[j].getId()) {
                    System.out.println("No two cars may have the same identifier.");
                    return;
                }
            }
        }

        // check to make sure all cars start within bounds
        for(int i = 0; i < cars.length; i++) {
            carStartsOutOfGrid = (cars[i].getStreet() < 1) ||
                            (cars[i].getStreet() > 10) ||
                            (cars[i].getAvenue() < 1) ||
                            (cars[i].getAvenue() > 10);
            if(carStartsOutOfGrid) {
                System.out.println("Cars must be restricted to a 10x10 street/avenue map.");
                return;
            }
        }

        boolean firstLog = true;
        while(running) {
            try {
                // check to make sure instructions consist of only R/L/N
                for(int i = 0; i < cars.length; i++) {
                    if(cars[i].invalidInstructions()) {
                        throw new IllegalArgumentException();
                    }
                }
            } catch(IllegalArgumentException e) {
                System.out.println(INVALID_INSTRUCTIONS_MESSAGE);
                return;
            }

            if(firstLog) {
                System.out.println("time: " + time);
                for(int i = 0; i < cars.length; i++) {
                    System.out.println(cars[i]);
                }
                System.out.println();
                firstLog = false;
            }

            // check for cars intersecting
            for(int i = 0; i < cars.length - 1; i++) {
                for(int j = i + 1; j < cars.length; j++) {
                    if((cars[i].getStreet() == cars[j].getStreet()) && (cars[i].getAvenue() == cars[j].getAvenue())) {
                        System.out.println("Cars " + cars[i].getId() + " and " + cars[j].getId() + " have intersected at Street " + cars[i].getStreet() + " and Avenue " + cars[i].getAvenue() + ".");
                        return;
                    }
                }
            }

            System.out.println("time: " + time);
            for(int i = 0; i < cars.length; i++) {
                cars[i].turn();
                System.out.println(cars[i]);
            }
            System.out.println();

            // checks for cars about to leave grid
            for(int i = 0; i < cars.length; i++) {
                carAboutToLeaveGrid = (cars[i].getNextStreet() < 1) ||
                                        (cars[i].getNextStreet() > 10) ||
                                        (cars[i].getNextAvenue() < 1) ||
                                        (cars[i].getNextAvenue() > 10);
                if(carAboutToLeaveGrid) {
                    System.out.println("Car " + cars[i].getId() + " is leaving the grid.");
                    return;
                }
            }

            System.out.println("time: " + time);
            for(int i = 0; i < cars.length; i++) {
                cars[i].advance();
                System.out.println(cars[i]);
            }
            System.out.println();
            time++;
        }
    }

    /**
     * Note: advanced Java ahead. Input processing is ahead of your current pay grade so this code is
     * given for free.
     */
    private static Car[] processCarInput() {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        try {
            scanner.useDelimiter("\\Z");
            if (!scanner.hasNext()) {
                System.err.println("No cars provided.");
                return null;
            }

            String fullCarSpecification = scanner.next();
            String[] carSpecificationStrings = fullCarSpecification.split("\n");

            Car[] result = new Car[carSpecificationStrings.length];
            for (int i = 0; i < carSpecificationStrings.length; i++) {
                String carSpecificationString = carSpecificationStrings[i];
                String[] carSpecifications = carSpecificationString.split(" ");
                if (carSpecifications.length != 5) {
                    System.err.println("A car specification must have an id, a starting street, " +
                            "a starting avenue, an initial direction (NSEW) and instructions (R/L/N).");
                    return null;
                }

                try {
                    int id = Integer.parseInt(carSpecifications[0]);
                    int street = Integer.parseInt(carSpecifications[1]);
                    int avenue = Integer.parseInt(carSpecifications[2]);
                    Car.Direction direction = Car.Direction.valueOf(carSpecifications[3]);
                    String instructions = carSpecifications[4];
                    result[i] = new Car(id, street, avenue, direction, instructions);
                } catch (NumberFormatException nfe) {
                    System.err.println("The car specification '" + carSpecificationString +
                            "' is not formatted correctly.");
                    return null;
                } catch (IllegalArgumentException iae) {
                    System.err.println("Directions must be one of N/S/E/W and instructions must be a sequence of " +
                            "R/L/Ns.");
                    return null;
                }
            }

            scanner.close();
            return result;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}

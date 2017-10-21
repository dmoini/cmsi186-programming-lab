public class Car {

    public static enum Direction {
        N(-1,0),
        E(0,1),
        S(1,0),
        W(0,-1);

        private int yStreet;
        private int xAvenue;

        Direction(int street, int avenue) {
            this.yStreet = street;
            this.xAvenue = avenue;
        }

        public int nextStreet(int street) {
            street += this.yStreet;
            return street;
        }

        public int nextAvenue(int avenue) {
            avenue += this.xAvenue;
            return avenue;
        }
    }

    public static enum Instruction {
        L(Direction.W, Direction.N, Direction.E, Direction.S),
        N(Direction.N, Direction.E, Direction.S, Direction.W),
        R(Direction.E, Direction.S, Direction.W, Direction.N);

        final Direction directionAfterNorth;
        final Direction directionAfterEast;
        final Direction directionAfterSouth;
        final Direction directionAfterWest;

        Instruction(Direction directionAfterNorth, Direction directionAfterEast, Direction directionAfterSouth, Direction directionAfterWest) {
            this.directionAfterNorth = directionAfterNorth;
            this.directionAfterEast = directionAfterEast;
            this.directionAfterSouth = directionAfterSouth;
            this.directionAfterWest = directionAfterWest;
        }

        Direction nextDirection(Direction direction) {
            if(direction == Direction.N) {
                return directionAfterNorth;
            } else if(direction == Direction.E) {
                return directionAfterEast;
            } else if(direction == Direction.S) {
                return directionAfterSouth;
            } else {
                return directionAfterWest;
            }
        }
    }

    private int id;
    private int street;
    private int avenue;
    private Direction direction;
    private String instructions;
    private String[] instructionsArray;
    private int index = 0;

    public Car(int id, int street, int avenue, Car.Direction direction, String instructions) {
        this.id = id;
        this.street = street;
        this.avenue = avenue;
        this.direction = direction;
        this.instructions = instructions;
    }

    public int getId() {
        return this.id;
    }

    public int getAvenue() {
        return this.avenue;
    }

    public int getStreet() {
        return this.street;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean invalidInstructions() {
        String str;
        boolean invalidInstructions = false;
        for(int i = 0; i < this.instructions.length(); i++) {
            str = this.instructions.substring(i, i + 1);
            if((Instruction.valueOf(str) != Instruction.L) &&
                (Instruction.valueOf(str) != Instruction.N) &&
                (Instruction.valueOf(str) != Instruction.R)) {
                    invalidInstructions = true;
                }
        }
        return invalidInstructions;
    }

    public Instruction getNextInstruction() {
        this.instructionsArray = new String[this.instructions.length()];
        for(int i = 0; i < this.instructions.length(); i++) {
            instructionsArray[i] = this.instructions.substring(i, i + 1);
        }
        String nextInstruction = this.instructionsArray[this.index];
        this.index++;
        if(this.index >= this.instructions.length()) {
            this.index = 0;
        }
        return Instruction.valueOf(nextInstruction);
    }

    public int getNextStreet() {
        return getDirection().nextStreet(this.street);
    }

    public int getNextAvenue() {
        return getDirection().nextAvenue(this.avenue);
    }

    public void advance() {
        this.street = getDirection().nextStreet(this.street);
        this.avenue = getDirection().nextAvenue(this.avenue);
    }

    public void turn() {
        this.direction = getNextInstruction().nextDirection(this.direction);
    }

    public String toString() {
        return "car " + this.id + ": " + this.street + " " + this.avenue + " " + this.direction;
    }
}

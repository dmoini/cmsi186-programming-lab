public class BagOfCoins {

    public static final int DEFAULT_COIN_COUNT = 1000;
    public static final String UNEVEN_PARTITION_MESSAGE = "Partition count must evenly divide the total number of bag throws.";

    private int coinCount;
    private Coin[] bag;

    // Constructors

    public BagOfCoins() {
        this.coinCount = DEFAULT_COIN_COUNT;
        this.bag = new Coin[DEFAULT_COIN_COUNT];
        for(int i = 0; i < DEFAULT_COIN_COUNT; i++) {
            Coin coin = new Coin();
            bag[i] = coin;
        }
    }

    public BagOfCoins(double bias) {
        this.coinCount = DEFAULT_COIN_COUNT;
        this.bag = new Coin[DEFAULT_COIN_COUNT];
        for(int i = 0; i < DEFAULT_COIN_COUNT; i++) {
            Coin coin = new Coin(bias);
            bag[i] = coin;
        }
    }

    public BagOfCoins(int coinCount) {
        this.coinCount = coinCount;
        this.bag = new Coin[coinCount];
        for(int i = 0; i < coinCount; i++) {
            Coin coin = new Coin();
            bag[i] = coin;
        }
    }

    public BagOfCoins(int coinCount, double bias) {
        this.coinCount = coinCount;
        this.bag = new Coin[coinCount];
        for(int i = 0; i < coinCount; i++) {
            Coin coin = new Coin(bias);
            bag[i] = coin;
        }
    }

    // Methods

    public int getCoinCount() {
        return this.coinCount;
    }

    public Coin getCoin(int index) {
        return bag[index];
    }

    public void throwCoins() {
        for(int i = 0; i < bag.length; i++) {
            bag[i].flip();
        }
    }

    public void resetCoins() {
        for(int i = 0; i < bag.length; i++) {
            bag[i].reset();
        }
    }

    public long[] getFlipTotals() {
        long[] flipTotals = new long[2];
        long headCount = 0;
        long tailCount = 0;
        for(int i = 0; i < bag.length; i++) {
            headCount += bag[i].getHeadCount();
            tailCount += bag[i].getTailCount();
        }
        flipTotals[0] = headCount;
        flipTotals[1] = tailCount;
        return flipTotals;
    }

    public int[] getFlipHistogram(int partitionCount) {
        int[] histogram = new int[partitionCount];
        if((int)bag[0].getFlipCount() % partitionCount != 0) {
            throw new IllegalArgumentException(UNEVEN_PARTITION_MESSAGE);
        }
        for(int i = 0; i < bag.length; i++) {
            long thrownAmount = bag[i].getFlipCount();
            int range = (int) (thrownAmount / partitionCount);
            long coinHeads = bag[i].getHeadCount();
            int index = (int) (coinHeads / range);
            histogram[index] += 1;
        }
        return histogram;
    }
}

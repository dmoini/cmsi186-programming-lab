/**
 * This class describes a flippable coin with adjustable bias. The bias influences the relative frequency of
 * "heads" or "tails" flips. The coin can be flipped to produce a heads or tails value, and it also "remembers"
 * its flipping history (total flips, number of heads flips).
 */
public class Coin {
    /**
     * The <code>enum</code> or enumeration is a convenient way to define symbolic values that are discrete
     * but are not necessarily numeric in value.
     */
    public static enum Flip {
        HEADS, TAILS
    }

    /**
     * Constant values can be accomplished using Java’s `final` keyword.
     */
    public static final double FAIR_BIAS = 0.5;

    private double bias;
    private long flipCount;
    private long headCount;

    /**
     * The default constructor produces a fair coin.
     */
    public Coin() {
        this(FAIR_BIAS);
    }

    /**
     * This constructor accepts a bias parameter which influences the proportion of flips that will result
     * in HEADS.
     *
     * @param bias The smaller the number, the more likely a HEADS flip.
     */
    public Coin(double bias) {
        this.bias = bias;
        reset();
    }

    /**
     * Accessor method for revealing a coin's bias.
     */
    public double getBias() {
        return bias;
    }

    /**
     * Accessor method for revealing how many times a coin has been flipped since the last reset.
     */
    public long getFlipCount() {
        return flipCount;
    }

    /**
     * Accessor method for revealing how many times a coin flip has produced HEADS.
     */
    public long getHeadCount() {
        return headCount;
    }

    /**
     * Accessor method for revealing how many times a coin flip has produced TAILS.
     */
    public long getTailCount() {
        return flipCount - headCount;
    }

    /**
     * Helper method that clears a coin's flip history.
     */
    public void reset() {
        flipCount = 0;
        headCount = 0;
    }

    /**
     * The "money" method (no pun intended): this flips the coin and records and returns the result.
     */
    public Flip flip() {
        Flip result = Math.random() >= bias ? Flip.HEADS : Flip.TAILS;
        flipCount++;
        if (result == Flip.HEADS) {
            headCount++;
        }

        return result;
    }
}

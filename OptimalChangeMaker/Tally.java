/**
 * This class represents a tally of integers, indexed from zero.
 */
public class Tally {

    /**
     * Singleton constant representing an impossible tally.
     */
    public static final Tally IMPOSSIBLE = new Tally(new int[0]);

    private int[] data;

    /**
     * Constructs a tally of size n with all integers initialized to zero.
     *
     * @param n
     *            the number of elements in this tally
     */
    public Tally(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = 0;
        }
    }

    /**
     * Constructs a tally of size n from the given data, where n = data.length.
     *
     * @param data
     *            the ints that should populate the tally
     */
    public Tally(int[] data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        this.data = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];
        }
    }

    /**
     * Returns whether the tally is impossible.
     *
     * @return whether the tally is imposible
     */
    public boolean isImpossible() {
        return this == IMPOSSIBLE;
    }

    /**
     * Sets the int at position i to value j.
     *
     * @param i
     *            the position of the int to set
     * @param j
     *            the value to which the position should be set
     */
    public void setElement(int i, int j) {
        checkIndex(i);
        data[i] = j;
    }

    /**
     * Returns the int at position i.
     *
     * @param i
     *            the position whose int is requested
     * @return the int at position i
     */
    public int getElement(int i) {
        checkIndex(i);
        return data[i];
    }

    /**
     * Returns the length (i.e., the number of elements) of this tally.
     *
     * @return the length of this tally
     */
    public int length() {
        return data.length;
    }

    /**
     * Returns the total of the elements in this tally. For example, the
     * 3-element tally (9, 3, 2) has a total of 14.
     *
     * @return the total of the elements of this tally
     */
    public int total() {
        int sum = 0;
        for (int i = 0; i < this.data.length; i++) {
            sum += this.data[i];
        }
        return sum;
    }

    /**
     * Adds tally t to this tally, returning the "sum" as a new tally.
     *
     * @param t
     *            the tally to add to this tally
     * @return the element-wise sum of this and t
     */
    public Tally add(Tally t) {
        Tally sumArray = new Tally(t.data.length);
        for (int i = 0; i < t.data.length; i++) {
            sumArray.data[i] = this.data[i] + t.data[i];
        }
        return sumArray;
    }

    /**
     * Returns true iff this tally is value-identical to t.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object t) {
        if ((t == null) || !(t instanceof Tally) || (length() != ((Tally)t).length())) {
            return false;
        }

        for (int i = 0; i < length(); i++) {
            if (getElement(i) != ((Tally)t).getElement(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the product of this tally&rsquo;s elements as its hash code.
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int product = 0;
        for (int i = 0; i < length(); i++) {
            product = product * getElement(i);
        }

        return (product >= 0) ? product : -product;
    }

    /**
     * Returns a string representation of this tally.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (isImpossible()) {
            return "Impossible tally";
        }

        String result = "<";
        for (int i = 0; i < length(); i++) {
            result += (i > 0 ? "," : "") + data[i];
        }
        return result + ">";
    }

    private void checkIndex(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }

        if (i >= length()) {
            throw new IllegalArgumentException();
        }
    }

}

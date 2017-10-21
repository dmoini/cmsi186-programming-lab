public final class Intzilla {

    private String intzilla;
    private boolean isPositive;

    public Intzilla() {
        this.intzilla = "0";
        this.isPositive = true;
    }

    public Intzilla(String s) {
        String number = s.trim();
        // check if positive or negative
        boolean leadingPlusOrMinus = false;
        if (number.substring(0, 1).equals("+")) {
            this.isPositive = true;
            leadingPlusOrMinus = true;
        } else if (number.substring(0, 1).equals("-")) {
            this.isPositive = false;
            leadingPlusOrMinus = true;
        } else {
            this.isPositive = true;
        }
        int index = 0;
        if (leadingPlusOrMinus) {
            index++;
        }
        for (int i = index; i < number.length(); i++) {
            // check to make sure string consists of all digits (excluding leading +/-)
            for (int j = i; j < number.length(); j++) {
                Integer.parseInt(number.substring(j, j + 1));
            }

            // check for leading zeros
            if (!number.substring(i, i + 1).equals("0")) {
                number = number.substring(i);
                break;
            }

            // if number is all zeros
            if (i == number.length() - 1 && number.substring(i, i + 1).equals("0")) {
                number = "0";
                break;
            }
        }
        this.intzilla = number;
    }

    public String toString() {
        // System.out.println("STRING: " + )
        String s = this.intzilla;
        if (!this.isPositive) {
            s = "-" + s;
        } else if (this.isPositive) {
            if ("0".equals(s)) {
                return s;
            }
            s = "+" + s;
        }
        return s;
    }

    public boolean equals(Object n) {
        String x = this.intzilla;
        if (n instanceof Intzilla) {
            Intzilla y = (Intzilla) n;
            if (y.intzilla.equals(x) && (y.isPositive == this.isPositive)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGreaterThan(Intzilla n) {
        if (this.isPositive && !n.isPositive) {
            return true;
        }
        if (!this.isPositive && n.isPositive) {
            return false;
        }

        boolean bothPositive = this.isPositive && n.isPositive;
        boolean larger = false;

        if (this.intzilla.length() != n.intzilla.length()) {
            larger = this.intzilla.length() > n.intzilla.length();
        } else {
            for (int i = 0; i < this.intzilla.length(); i++) {
                if (Integer.parseInt(this.intzilla.substring(i, i + 1)) > Integer.parseInt(n.intzilla.substring(i, i + 1))) {
                    larger = true;
                    break;
                }
                if (Integer.parseInt(this.intzilla.substring(i, i + 1)) < Integer.parseInt(n.intzilla.substring(i, i + 1))) {
                    larger = false;
                    break;
                }
            }
        }
        return bothPositive == larger;
    }

    public boolean isLessThan(Intzilla n) {
        if (this.equals(n)) {
            return false;
        } else {
            return !this.isGreaterThan(n);
        }
    }

    public Intzilla absoluteValue() {
        String absolute = this.toString();
        if (absolute.substring(0, 1).equals("+") || absolute.substring(0, 1).equals("-")) {
            absolute = absolute.substring(1);
        }
        return new Intzilla(absolute);
    }

    public String barebones(String s) {
        if (s.substring(0, 1).equals("+") || s.substring(0, 1).equals("-")) {
            return s.substring(1);
        }
        return s;
    }

    public Intzilla changeSign() {
        Intzilla oppositeSign;
        String thisString = this.toString();
        if (thisString.substring(0, 1).equals("+")) { // if this is positive
            thisString = "-" + thisString.substring(1);
        } else if (thisString.substring(0, 1).equals("-")) { // if this is negative
            thisString = "+" + thisString.substring(1);
        }
        oppositeSign = new Intzilla(thisString);
        return oppositeSign;
    }

    // converts array into string representation
    public static String arrayToString(int[] array) {
        String string = "";
        for (int i = 0; i < array.length; i++) {
            string = string + Integer.toString(array[i]);
        }
        return string;
    }

    public Intzilla plus(Intzilla addend) {
        Intzilla sum;
        boolean addendHasMoreDigits = barebones(addend.intzilla).length() > barebones(this.intzilla).length();
        int longerLength = addendHasMoreDigits ? barebones(addend.intzilla).length() : barebones(this.intzilla).length();
        int[] sumArray;
        String sumString = "";

        this.intzilla = barebones(this.intzilla);
        addend.intzilla = barebones(addend.intzilla);
        if (this.isPositive == addend.isPositive) { // if both have same signs
            // add leading zeros
            for (int i = 1; i <= longerLength + 1; i++) {
                if (this.intzilla.length() < longerLength + 1) {
                    this.intzilla = "0" + this.intzilla;
                }
                if (addend.intzilla.length() < longerLength + 1) {
                    addend.intzilla = "0" + addend.intzilla;
                }
            }
            sumArray = new int[longerLength + 1];
            // fill in sumArray
            for (int i = 0; i < sumArray.length; i++) {
                sumArray[i] = Integer.parseInt(this.intzilla.substring(i, i + 1))
                    + Integer.parseInt(addend.intzilla.substring(i, i + 1));
            }
            // deal with carryovers
            for (int i = sumArray.length - 1; i >= 0; i--) {
                if (sumArray[i] > 9) {
                    sumArray[i] = sumArray[i] - 10;
                    sumArray[i - 1] = sumArray[i - 1] + 1;
                }
            }
            sumString = arrayToString(sumArray);
            if (!this.isPositive) { // if both numbers are negative
                sumString = "-" + sumString;
            }
            sum = new Intzilla(sumString);
        } else { // if both have opposite signs
            Intzilla absoluteValue;
            boolean positiveSum;
            boolean thisFirst;
            sumArray = new int[longerLength];
            if (!this.isPositive) { //if this is negative
                absoluteValue = this.absoluteValue();
                if (absoluteValue.isGreaterThan(addend)) {
                    positiveSum = false;
                    thisFirst = true;
                } else {
                    positiveSum = true;
                    thisFirst = false;
                }
                this.intzilla = barebones(this.intzilla);
            } else { // if addend is negative
                absoluteValue = addend.absoluteValue();
                if (absoluteValue.isGreaterThan(this)) {
                    positiveSum = false;
                    thisFirst = false;
                } else {
                    positiveSum = true;
                    thisFirst = true;
                }
                addend.intzilla = barebones(addend.intzilla);
            }
            // add leading zeros
            for (int i = 1; i <= longerLength; i++) {
                if (this.intzilla.length() < longerLength) {
                    this.intzilla = "0" + this.intzilla;
                }
                if (addend.intzilla.length() < longerLength) {
                    addend.intzilla = "0" + addend.intzilla;
                }
            }
            // fill in sumArray
            for (int i = 0; i < sumArray.length; i++) {
                if (thisFirst) {
                    sumArray[i] = Integer.parseInt(this.intzilla.substring(i, i + 1))
                        - Integer.parseInt(addend.intzilla.substring(i, i + 1));
                } else {
                    sumArray[i] = Integer.parseInt(addend.intzilla.substring(i, i + 1))
                        - Integer.parseInt(this.intzilla.substring(i, i + 1));
                }
            }
            // deal with carryovers
            for (int i = sumArray.length - 1; i >= 0; i--) {
                if (sumArray[i] < 0) {
                    sumArray[i] = sumArray[i] + 10;
                    sumArray[i - 1] = sumArray[i - 1] - 1;
                }
            }
            sumString = arrayToString(sumArray);
            if (!positiveSum) {
                sumString = "-" + sumString;
            }
            sum = new Intzilla(sumString);
        }
        return sum;
    }

    public Intzilla minus(Intzilla subtrahend) {
        return new Intzilla(this.plus(subtrahend.changeSign()).toString());
    }

    public Intzilla times(Intzilla factor) {
        int totalLength = barebones(this.intzilla).length() + barebones(factor.intzilla).length();
        int[] productArray = new int[totalLength];

        // add leading zeros
        for (int i = 1; i <= totalLength; i++) {
            if (this.intzilla.length() < totalLength) {
                this.intzilla = "0" + this.intzilla;
            }
            if (factor.intzilla.length() < totalLength) {
                factor.intzilla = "0" + factor.intzilla;
            }
        }
        // fill in productArray
        int counter = 0;
        for (int bottom = this.intzilla.length() - 1; bottom >= 0; bottom--) {
            for (int top = factor.intzilla.length() - 1; top >= 0; top--) {
                if (top - counter >= 0) {
                    productArray[top - counter] += Integer.parseInt(this.intzilla.substring(bottom, bottom + 1))
                        * Integer.parseInt(factor.intzilla.substring(top, top + 1));
                }
            }
            counter++;
        }

        // deal with carryovers
        for (int i = productArray.length - 1; i >= 0; i--) {
            int carryover;
            if (productArray[i] > 9) {
                carryover = productArray[i] / 10;
                productArray[i] = productArray[i] % 10;
                productArray[i - 1] += carryover;
            }
        }
        String productString = arrayToString(productArray);
        if (this.isPositive != factor.isPositive) {
            productString = "-" + productString;
        }
        return new Intzilla(productString);
    }

    public Intzilla div(Intzilla divisor) {
        // if divisor is 0
        if (divisor.equals(new Intzilla())) {
            throw new IllegalArgumentException();
        }

        final Intzilla ten = new Intzilla("10");
        final Intzilla one = new Intzilla("1");
        Intzilla originalDivisor = new Intzilla(divisor.toString());
        boolean positiveQuotient = this.isPositive == divisor.isPositive;
        Intzilla newDividend = null;
        Intzilla powerOfTen = new Intzilla("1");
        Intzilla quotient = null;
        Intzilla remainder = null;
        Intzilla dividend = this.absoluteValue();
        Intzilla absoluteDivisor = divisor.absoluteValue();

        if (divisor.equals(one)) {
            return this;
        }
        if (divisor.equals(one.times(new Intzilla("-1")))) {
            return this.times(new Intzilla("-1"));
        }

        if (dividend.isLessThan(absoluteDivisor)) {
            remainder = this;
            return new Intzilla();
        } else {
            if (dividend.equals(absoluteDivisor)) {
                return new Intzilla("1");
            }
            while (dividend.isGreaterThan(absoluteDivisor.times(ten.times(powerOfTen)))) {
                powerOfTen = powerOfTen.times(ten);
            }
            newDividend = dividend.minus(absoluteDivisor.times(powerOfTen));
            quotient = powerOfTen.plus(newDividend.div(originalDivisor.absoluteValue()));
            if (!positiveQuotient) {
                quotient = quotient.times(new Intzilla("-1"));
            }
        }
        return quotient;
    }

    public Intzilla mod(Intzilla divisor) {
        final Intzilla zero = new Intzilla();
        final Intzilla one = new Intzilla("1");

        if (divisor.isLessThan(one) || this.isLessThan(zero)) {
            throw new IllegalArgumentException();
        }

        if (this.equals(zero) || divisor.equals(one)) {
            return zero;
        }

        if (this.isLessThan(divisor)) {
            return this;
        }

        return this.minus((this.div(divisor)).times(divisor));
    }
}

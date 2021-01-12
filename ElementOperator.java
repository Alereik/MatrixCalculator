package linAlgCalc;

/**
 * Performs arithmetic operations on string representations of numerical elements in a matrix.
 * 
 * @author AOsterndorff
 *
 */
public class ElementOperator {
    /*
     * Integer arrays will have a length of 2, with the first element holding the value of the
     * numerator of a fraction and the second element holding the value of the denominator.
     */
    final private int NUMERATOR = 0;
    final private int DENOMINATOR = 1;

    /**
     * Reduces fractions split into an int array of length 2, where the first index of the array is 
     * the numerator and the second index of the array is the denominator.
     * 
     * @param fractionArr The array containing the numerator and denominator to be reduced.
     * @return reducedArr The array containing the reduced numerator and denominator.
     */
    protected int[] reduceFraction(int[] fractionArr) {
        boolean negativeFraction = false;
        int[] reduced = new int[] {fractionArr[NUMERATOR], fractionArr[DENOMINATOR]};
        //remove double negative in fraction if necessary
        if (fractionArr[NUMERATOR] < 0 && fractionArr[DENOMINATOR] < 0) {
            fractionArr[NUMERATOR] *= -1;
            fractionArr[DENOMINATOR] *= -1;
        }
        //temporarily turn fraction positive for reduction
        for (int i = 0; i < fractionArr.length; ++i) {
            if (fractionArr[i] < 0) {
                fractionArr[i] *= -1;
                negativeFraction = true;
            }
        }
        int smallerNum = (fractionArr[NUMERATOR] > fractionArr[DENOMINATOR]) ?
            fractionArr[DENOMINATOR] : fractionArr[NUMERATOR];
            //smaller number is greatest POSSIBLE common factor
            for (int i = 1; i <= smallerNum; ++i) {
                if ((fractionArr[NUMERATOR] % i == 0) && (fractionArr[DENOMINATOR] % i == 0)) {
                    reduced[NUMERATOR] = fractionArr[NUMERATOR] / i;
                    reduced[DENOMINATOR] = fractionArr[DENOMINATOR] / i;
                }
            }
            if (negativeFraction) {
                reduced[NUMERATOR] *= -1;
            }
            return reduced;
    }

    /**
     * Takes a fraction in string form and splits it along the '/' character, parsing the integers 
     * and putting them into an array.
     * 
     * The first element of the resulting array contains the numerator of the fraction, and the 
     * second element contains the denominator.
     * 
     * @param fraction       The string literal of a fraction to be split and parsed.
     * @return splitFraction The array containing the numerator and denominator of the fraction.
     */
    protected int[] splitFraction(String fraction) {
        String[] split = fraction.split("/");
        int[] splitFraction = new int[2];
        splitFraction[NUMERATOR] = Integer.parseInt(split[0]);//splitFraction[0]
        splitFraction[DENOMINATOR] = Integer.parseInt(split[1]);//splitFraction[1]
        //reduce fraction if possible
        splitFraction = reduceFraction(splitFraction);
        //if zero denominator
        if (splitFraction[DENOMINATOR] == 0) {
            return null;
        }
        return splitFraction;
    }

    /**
     * Adds two fractions together. Any parameters not in fraction form are converted
     * to fractions at the start of the method.
     * 
     * @param fraction1 The first fraction to be added.
     * @param fraction2 The second fraction to be added.
     * @return sum      The fraction that is the sum of the parameter fractions.
     */
    protected String fractionAddition(String fraction1, String fraction2) {
        //convert non fractions to fractions
        if (!fraction1.contains("/")) {
            fraction1 += "/1";
        }
        if (!fraction2.contains("/")) {
            fraction2 += "/1";
        }
        int[] firstFraction = splitFraction(fraction1);
        int[] secondFraction = splitFraction(fraction2);
        int[] sumFraction = new int[2];
        String sum = "";
        //same denominator
        if (firstFraction[DENOMINATOR] == secondFraction[DENOMINATOR]) {
            sumFraction[NUMERATOR] = firstFraction[NUMERATOR] + secondFraction[NUMERATOR];
            sumFraction[DENOMINATOR] = firstFraction[DENOMINATOR];
        }
        //different denominators
        else {
            sumFraction[NUMERATOR] = 
                (firstFraction[NUMERATOR] * secondFraction[DENOMINATOR])
                + (secondFraction[NUMERATOR] * firstFraction[DENOMINATOR]);
            sumFraction[DENOMINATOR] = 
                (firstFraction[DENOMINATOR] * secondFraction[DENOMINATOR]);
        }
        //reduce resulting fraction if possible
        sumFraction = reduceFraction(sumFraction);
        //numerator equal to denominator
        if (sumFraction[NUMERATOR] == 0) {
            sum = "0";
        }
        else if (sumFraction[NUMERATOR] == sumFraction[DENOMINATOR]) {
            sum = "1";
        }
        //denominator of 1
        else if (sumFraction[DENOMINATOR] == 1) {
            sum = String.valueOf(sumFraction[NUMERATOR]);
        }
        else {
            sum += sumFraction[NUMERATOR] + "/" + sumFraction[DENOMINATOR];
        }
        return sum;
    }

    /**
     * Multiplies a fraction by another fraction by calling the splitFraction method to convert the 
     * numerical characters in the fractions string to integers, multiplying the numerators and 
     * denominators by each other, and converting the result into a string.
     * 
     * @param fraction1 The string representation of a fraction to multiply with another.
     * @param fraction2 The string representation of the other fraction to be multiplied with.
     * @return product  The resultant product of the two multiplied fractions.
     */
    protected String fractionMultiplication(String fraction1, String fraction2) {
        //convert non fractions to fractions
        if (!fraction1.contains("/")) {
            fraction1 += "/1";
        }
        if (!fraction2.contains("/")) {
            fraction2 += "/1";
        }
        int[] firstFraction = splitFraction(fraction1);
        int[] secondFraction = splitFraction(fraction2);
        String product;
        int[] productFraction = new int[] {firstFraction[NUMERATOR] * secondFraction[NUMERATOR],
            firstFraction[DENOMINATOR] * secondFraction[DENOMINATOR]};
        //reduce fraction if possible
        productFraction = reduceFraction(productFraction);
        //numerator equal to denominator
        if (productFraction[NUMERATOR] == 0) {
            product = "0";
        }
        else if (productFraction[NUMERATOR] == productFraction[DENOMINATOR]) {
            product = "1";
        }
        //denominator equal to 1
        else if (productFraction[DENOMINATOR] == 1) {
            product = String.valueOf(productFraction[NUMERATOR]);
        }
        else {
            product = productFraction[NUMERATOR] + "/" 
                + productFraction[DENOMINATOR];
        }
        return product;
    }

    /**
     * Returns the reciprocal of an element. If element is "0", or the numerator of element is 0, 
     * then "0" is returned. Does not return whole numbers if the parameter numerator is 1. Instead,
     * the fraction version is returned (1/5 does not return 5, instead returns 5/1).
     * 
     * @param element     The element for which a reciprocal is returned.
     * @return reciprocal The multiplicative inverse of the element.
     */
    protected String getReciprocal(String element) {
        if (element.equals("0")) {
            return "0";
        }
        //check if element is negative
        boolean isNegative = element.contains("-") ? true : false;
        int[] splitFraction;
        String reciprocal = "";
        //split into array if fraction
        if (element.contains("/")) {
            splitFraction = splitFraction(element);
            if (splitFraction[NUMERATOR] == 0) {
                return "0";
            }
            int tempVal = splitFraction[NUMERATOR];
            splitFraction[NUMERATOR] = splitFraction[DENOMINATOR];
            splitFraction[DENOMINATOR] = tempVal;
            reciprocal += splitFraction[NUMERATOR] + "/" + splitFraction[DENOMINATOR];
        }
        else {
            reciprocal += 1 + "/" + element;
        }
        //move '-' to numerator if element was negative
        if (isNegative) {
            reciprocal = "-" + reciprocal.replace("-", "");
        }
        return reciprocal;
    }

    /**
     * Checks if a string entered by the user as an element in a vector or matrix is a valid number 
     * entry. Valid number entries are either string literals of whole numbers such as "-1" or 
     * "148", or string literals of fractions such as "3/4" or "-83/37".
     * 
     * @param element  The user entry being checked for validity.
     * @return isValid Returns true if element is valid.
     */
    public boolean checkElementValidity(String element) {
        if (element == null || element.equals("") || element.length() == 0 || element.equals("-")
            || element.equals("/") || element.endsWith("-")) {
            return false;
        }
        for (int i = 0, dashCount = 0, slashCount = 0; i < element.length(); ++i) {
            if (element.charAt(i) != '-' && element.charAt(i) != '/' 
                && !Character.isDigit(element.charAt(i))) {//check for non numbers
                return false;
            }
            if (i < element.length() - 1 && Character.isDigit(element.charAt(i)) 
                && element.charAt(i + 1) == '-') {//check if '-' is after a number
                return false;
            }
            if (element.charAt(i) == '-') {
                ++dashCount;
            }
            if (element.charAt(i) == '/') {
                ++slashCount;
            }
            if (dashCount > 1 || slashCount > 1) {//check for more than one '-' or '/'
                return false;
            }
        }
        if (element.contains("/")) {//ensure valid content exists on both sides of '/'
            String numerator = element.substring(0, element.indexOf('/'));
            String denominator = element.substring(element.indexOf('/') + 1, element.length());
            if (numerator.length() == 0 || denominator.length() == 0 || numerator.equals("-")
                || Integer.parseInt(denominator) == 0) {//ensure no zero denominator
                return false;
            }//ensure content on both sides of '/' consists of only integers
            for (int i = 0; i < element.length(); ++i) {
                if (element.charAt(i) != '/' && element.charAt(i) != '-' 
                    && !Character.isDigit(element.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }
}

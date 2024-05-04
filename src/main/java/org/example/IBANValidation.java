package org.example;

import java.math.BigInteger;

public class IBANValidation {

    public static void main(String[] args) {
        String testIBANValid = "DE23 6005 0101 1111 2222 33";
        String testIBANInvalid = "DE12 345 678 910 234 567 89";
        System.out.println(testIBANValid+ ": is a valid IBAN and the method returns: "  + isValidIBAN(testIBANValid));
        System.out.println(testIBANInvalid+ ": is an invalid IBAN and the method returns: " + isValidIBAN(testIBANInvalid));
    }

    public static boolean isValidIBAN(String iban){
        if(!hasCorrectLength(iban)) return false;
        if(!hasCorrectForm(iban)) return false;
        if(!hasDEAsFirstTwoLetters(iban)) return false;
        return hasValidNumeralsCombination(iban);
    }

    public static boolean hasCorrectForm(String iban){
        String[] strings = iban.split(" ");
        if(strings.length>6) return false;
        boolean hasCorrectForm = true;
        for(int i=0; i< strings.length;i++){
            if(i!=5 && strings[i].length()!=4) hasCorrectForm = false;
            if(i==5 && strings[i].length()!=2) hasCorrectForm = false;
        }
        return hasCorrectForm;
    }

    public static boolean hasDEAsFirstTwoLetters(String iban){
        if(iban.charAt(0) != 'D') return false;
        return iban.charAt(1) == 'E';
    }

    public static boolean hasCorrectLength(String iban){
        return iban.replace(" ","").length() == 22;
    }

    public static boolean hasValidNumeralsCombination(String iban){
        return constructNumberToForValidation(iban).mod(BigInteger.valueOf(97)).equals(BigInteger.ONE);
    }

    public static BigInteger constructNumberToForValidation(String iban){
        char[] chars = constructSequenceToContructNumber(iban);
        int[] numbers = new int[chars.length];
        for(int i = 0; i<chars.length; i++){
            if(Character.isLetter(chars[i])) numbers[i] = (chars[i] - 55);
            else numbers[i] = chars[i] -48;
        }
        StringBuilder strNum = new StringBuilder();
        for (int num : numbers){
            strNum.append(num);
        }
        return new BigInteger(strNum.toString());
    }

    public static char[] constructSequenceToContructNumber(String iban){
        String[] sequence = iban.split(" ");
        String firstString = sequence[0];
        for(int i=0; i<sequence.length-1; i++){
            sequence[i] = sequence[(i+1)];
        }
        sequence[5] = firstString;
        return String.join("",sequence).toCharArray();
    }
}

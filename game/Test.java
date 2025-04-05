//package game;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Test {
//    public String longestPalindrome(String s) {
//        String longest=(string)s.charAt(0);
//        int len = s.length();
//
//        private void checkPalindrome1(int middle, int out) {
//            int left = middle-out;
//            int right = middle+out;
//            if(s.charAt(left)==s.charAt(right)) {
//                if(longest.length()<out*2+1) {
//                    longest=s.subString(left, right)
//                }
//                if(0!=left-1 && len!=right+1) {
//                    checkPalindrome1(middle, out+1);
//                }
//            }
//        }
//
//        void checkPalindrome2(int middle, int out) {
//            int left = middle-out-1;
//            int right = middle+out;
//            if(s.charAt(left)==s.charAt(right)) {
//                if(longest.length()<out*2+2) {
//                    longest=s.subString(left, right+1)
//                }
//                if(0!=left-1 && len!=right+1) {
//                    checkPalindrome2(middle, out+1);
//                }
//            }
//        }
//
//        private void checkPalindrome3(int middle, int out) {
//            int left = middle-out;
//            int right = middle+out+1;
//            if(s.charAt(left)==s.charAt(right)) {
//                if(longest.length()<out*2+2) {
//                    longest=s.subString(left, right+1)
//                }
//                if(0!=left-1 && len!=right+1) {
//                    checkPalindrome3(middle, out+1);
//                }
//            }
//        }
//
//        if(s.length()%2==1) {
//            for(int i=(int)len/2;i>0;i--) {
//                if(longest.length()<2*i+1)){
//                    checkPalindrome1(i, 1);
//                    if(i==(int)len/2) {
//                        if(s.charAt(i)==s.charAt(i-1)) {
//                            checkPalindrome2(i, 1);
//                        }
//                        if(s.charAt(i)==s.charAt(i+1)) {
//                            checkPalindrome3(i, 1);
//                        }
//                    }
//                    else {
//                        if(s.charAt(i)==s.charAt(i-1)) {
//                            checkPalindrome2(i, 1);
//                        }
//                        if(s.charAt(len-i-1)==s.charAt(len-1)) {
//                            checkPalindrome3(i, 1);
//                        }
//                    }
//
//                }
//                else{
//                    break;
//                }
//            }
//        }
//        else {
//
//        }
//    }
//
//    public static void main(String[] args) {
//        String a="aba";
//        System.out.println(a.substring(0, 3));
//    }
//
//}
//

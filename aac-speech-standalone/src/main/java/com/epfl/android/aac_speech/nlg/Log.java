package com.epfl.android.aac_speech.nlg;

public class Log {

    public static void d(String s1, String s2){
        if(Pic2NLG.DEBUG)
            System.out.println(s1 + ": "+s2);
    }
    public static void e(String s1, String s2, Exception exc){
        System.out.println(s1 + ": "+s2+"! "+exc.toString());
    }
}

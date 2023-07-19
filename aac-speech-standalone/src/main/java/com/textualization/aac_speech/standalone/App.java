package com.textualization.aac_speech.standalone;

import com.epfl.android.aac_speech.nlg.Pic2NLG;
import com.epfl.android.aac_speech.nlg.Pic2NLG.ActionType;
import com.epfl.android.aac_speech.data.Pictogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.io.IOException;


public class App {

    public static void main(String[] args) throws IOException {
        String lang = args[0];
        Pic2NLG nlg = new Pic2NLG(lang);

        if(args.length == 2) {
            // sample
            int toGenerate = Integer.parseInt(args[1]);
            Random rnd = new Random(20230608);

            Sampler sampler = new Sampler(lang, nlg);

            int generated = 0;
            int errors = 0;
            while(generated<toGenerate){
                List<Pictogram> phrase = sampler.sample(rnd);
                if(phrase.isEmpty())
                    continue;
                try{ 
                    String output = nlg.convertPhrasesToNLG(phrase);
                    if(output.isEmpty())
                        continue;
                    System.out.print("IN " + String.valueOf(generated) +":");
                    for(Pictogram pic : phrase) {
                        System.out.print(" { " + pic.data + " ; " + pic.type + " ; " + pic.imageFileURI + " } ");
                    }
                    System.out.println();
                    System.out.println("OUT " + String.valueOf(generated) +": " + output);
                    generated++;
                }catch(Exception e){
                    //ignore
                    errors++;
                }
                if(errors > 0)
                    System.err.println("Errors: " + errors);
            }
        }else{
            // generate one
            List<Pictogram> phrase_list = new ArrayList<Pictogram>();

            for(int i=1; i<args.length; i+=2) {
                phrase_list.add(new Pictogram(args[i], Pic2NLG.ActionType.valueOf(args[i+1])));
            }
            System.out.println(nlg.convertPhrasesToNLG(phrase_list));
        }
    }
}

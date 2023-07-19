package com.textualization.aac_speech.standalone;

import com.epfl.android.aac_speech.nlg.Pic2NLG;
import com.epfl.android.aac_speech.nlg.Pic2NLG.ActionType;
import com.epfl.android.aac_speech.data.Pictogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class Sampler {

    private static String U = "https://textualization.com/acc_icons/";

    private Pic2NLG nlg;
    
    private Map<String, Pictogram> dictionary;
    private Map<Integer, List<Pictogram>> categories;
    private Object[]home; // Pictogram or Integer (for category)
    private Object[]homeClitic; // Pictogram or Integer (for category)

    private int verbCatIdx;
    private int firstPageIdx;

    private static int getPoissonRandom(Random rnd, double mean) {
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * rnd.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    public Sampler(String lang, Pic2NLG nlg) throws IOException {
        this.nlg = nlg;
        this.dictionary = new HashMap<>();
        this.categories = new HashMap<>();
        
        InputStream inputStream = Sampler.class.getClassLoader().getResourceAsStream("com/epfl/icon_meanings.data");

        if (inputStream == null)
            throw new IOException("icon meaning not found on classpath");

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            if(parts[5].equals(lang)){
                Pictogram pic = new Pictogram(parts[0], ActionType.valueOf(parts[2].toUpperCase()), U + parts[4].substring("file:///sdcard/acc_icons/".length()));
                Integer cat = Integer.valueOf(parts[6]);
                if(!categories.containsKey(cat)){
                    categories.put(cat, new ArrayList<Pictogram>());
                }
                dictionary.put(parts[0], pic);
                categories.get(cat).add(pic);
            }
        }
        System.err.println("Read " + dictionary.size() + " entries");

        reader.close();

        // redo main screen for sampling
        inputStream = Sampler.class.getClassLoader().getResourceAsStream("com/epfl/strings-" + lang + ".tsv");

        if (inputStream == null)
            throw new IOException("strings not found on classpath");

        reader = new BufferedReader(new InputStreamReader(inputStream));
 
        Map<String,String> ss = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");
            if(parts.length == 2)
                ss.put(parts[0], parts[1]);
        }
        reader.close();
        
        this.home = new Object[39];
        this.homeClitic = new Object[39];
        
        int idx = 0;
        this.homeClitic[idx] = new Pictogram(ss.get("btn_clitic_myself"), ActionType.CLITIC_PRONOUN, U + "je.png"); idx++;
        this.homeClitic[idx] = new Pictogram(ss.get("btn_clitic_you"), ActionType.CLITIC_PRONOUN, U + "tu.png"); idx++;
        this.homeClitic[idx] = new Pictogram(ss.get("btn_clitic_him"), ActionType.CLITIC_PRONOUN, U + "il.png"); idx++;
        this.homeClitic[idx] = new Pictogram(ss.get("btn_clitic_her"), ActionType.CLITIC_PRONOUN, U + "elle.png"); idx++;
        this.homeClitic[idx] = new Pictogram(ss.get("btn_lets"), ActionType.NOUN, U + "on.png"); idx++;
        
        this.homeClitic[idx] = new Pictogram(ss.get("btn_clitic_us"), ActionType.CLITIC_PRONOUN, U + "nous.png"); idx++;
        this.homeClitic[idx] = new Pictogram(ss.get("btn_clitic_you_pl"), ActionType.CLITIC_PRONOUN, U + "vous.png"); idx++;
        this.homeClitic[idx] = new Pictogram(ss.get("btn_clitic_them_m"), ActionType.CLITIC_PRONOUN, U + "ils.png"); idx++;
        this.homeClitic[idx] = new Pictogram(ss.get("btn_clitic_them_f"), ActionType.CLITIC_PRONOUN, U + "elles.png"); idx++;
        this.homeClitic[idx] = new Pictogram(ss.get("btn_that"), ActionType.NOUN, U + "that_one.png"); idx++;

        
        int idx2 = 0;
        this.home[idx2] = new Pictogram(ss.get("btn_I"), ActionType.NOUN, U + "je.png"); idx2++;
        this.home[idx2] = new Pictogram(ss.get("btn_you"), ActionType.NOUN, U + "tu.png"); idx2++;
        this.home[idx2] = new Pictogram(ss.get("btn_he"), ActionType.NOUN, U + "il.png"); idx2++;
        this.home[idx2] = new Pictogram(ss.get("btn_she"), ActionType.NOUN, U + "elle.png"); idx2++;
        this.home[idx2] = new Pictogram(ss.get("btn_lets"), ActionType.NOUN, U + "on.png"); idx2++;
        
        this.home[idx2] = new Pictogram(ss.get("btn_we"), ActionType.NOUN, U + "nous.png"); idx2++;
        this.home[idx2] = new Pictogram(ss.get("btn_you_pl"), ActionType.NOUN, U + "vous.png"); idx2++;
        this.home[idx2] = new Pictogram(ss.get("btn_they_m"), ActionType.NOUN, U + "ils.png"); idx2++;
        this.home[idx2] = new Pictogram(ss.get("btn_they_f"), ActionType.NOUN, U + "elles.png"); idx2++;
        this.home[idx2] = this.homeClitic[idx2]; idx2++;

        this.home[idx] = this.homeClitic[idx] = new Pictogram(ss.get("btn_past"), ActionType.TENSE_PAST, U + "past.png"); idx++;
        this.home[idx] = this.homeClitic[idx] = new Pictogram(ss.get("btn_negated"), ActionType.NEGATED, U + "no.png"); idx++;
        this.home[idx] = this.homeClitic[idx] = new Pictogram(ss.get("btn_to_have"), ActionType.VERB, U + "avoir.png"); idx++;
        this.home[idx] = this.homeClitic[idx] = new Integer(14); idx++;
        this.home[idx] = this.homeClitic[idx] = new Integer(1); idx++;
        
        this.home[idx] = this.homeClitic[idx] = new Integer(19); this.verbCatIdx = idx; idx++;
        this.home[idx] = this.homeClitic[idx] = new Pictogram(ss.get("btn_to_be"), ActionType.VERB, U + "etre.png"); idx++;
        this.home[idx] = this.homeClitic[idx] = new Pictogram(ss.get("btn_can"), ActionType.VERB, U + "pouvoir.png"); idx++;
        this.home[idx] = this.homeClitic[idx] = new Integer(10); idx++;
        this.home[idx] = this.homeClitic[idx] = new Integer(6); idx++;

        this.home[idx] = this.homeClitic[idx] = new Pictogram(ss.get("btn_future"), ActionType.TENSE_FUTURE, U + "future.png"); idx++;
        this.home[idx] = this.homeClitic[idx] = new Pictogram(ss.get("btn_to_want"), ActionType.VERB, U + "vouloir.png"); idx++;
        this.home[idx] = this.homeClitic[idx] = new Pictogram(ss.get("btn_quesion"), ActionType.QUESTION, U + "question.png"); idx++;
        this.home[idx] = this.homeClitic[idx] = new Integer(8); idx++;        
        this.home[idx] = this.homeClitic[idx] = new Pictogram(ss.get("btn_dot"), ActionType.DOT, U + "point.png"); idx++;

        this.firstPageIdx = idx;

        for(Integer cat : new Integer[]{16,3,4,12,11,13,2,18,9,17,5,7,15,0}){
            this.home[idx] = this.homeClitic[idx] = new Integer(cat); idx++;
        }
    }

    public List<Pictogram> sample(Random rnd){

        Object[] home = this.home;

        List<Pictogram> result = new ArrayList<>();

        int toGen = getPoissonRandom(rnd, 3);
        int verbCount = 0;
        for(Object obj : home){
            if(obj instanceof Pictogram && ((Pictogram)obj).type == ActionType.VERB){
                verbCount++;
            }
        }
        int[] verbIdxs = new int[verbCount+1];
        int vIdx = 0;
        for(int i=0;i<home.length; i++){
            if(home[i] instanceof Pictogram && ((Pictogram)home[i]).type == ActionType.VERB){
                verbIdxs[vIdx] = i; vIdx++;
            }
        }
        verbIdxs[vIdx] = this.verbCatIdx;

        for(int i=0; i<toGen; i++){
            if(nlg.hasSubjectBeenSelected(result)) {
                home = this.homeClitic;
            }
            boolean hasVerb = false;
            for(Pictogram pic:result){
                if(pic.type == ActionType.VERB){
                    hasVerb = true;
                    break;
                }
            }
            int top = rnd.nextInt(home.length);
            if(top >= firstPageIdx && rnd.nextDouble() > 0.5) { // first page much more likely
                top = rnd.nextInt(firstPageIdx);
            }
            if(! hasVerb && i>0 && rnd.nextDouble() > 0.5){
                top = rnd.nextInt(verbIdxs.length);
                top = verbIdxs[top];
            }
            if(home[top] instanceof Pictogram && i < 2 && (((Pictogram)home[top]).type == ActionType.DOT || ((Pictogram)home[top]).type == ActionType.QUESTION)){
                i--;
                continue;
            }
            if(home[top] instanceof Pictogram){
                result.add((Pictogram)home[top]);
            }else{
                List<Pictogram> cats = categories.get((Integer)home[top]);
                if(cats == null) {
                    System.out.println("Category not found: " + home[top] + " for " + top);
                    System.out.println("Known categories: " + categories.keySet());
                }else{
                    result.add(cats.get(rnd.nextInt(cats.size())));
                }
            }
        }
        return result;
    }
}



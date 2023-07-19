# AAC SPEECH ANDROID STANDALONE

This is a small fork of https://github.com/vidma/aac-speech-android to work without Android libraries with just the NLG bits.


## Building the code

This project uses Maven:

```
mvn package
```

That'll fetch dependencies from the web and build `target/standalone-1.0.jar`.


## Running the code

The new executable `com.textualization.aac_speech.standalone.App` can be run by specifying a classpath including the standalone jar and a key dependency. Under a Linux system this will be:

```
java -cp ~/.m2/repository/xmlpull/xmlpull/1.1.3.1/xmlpull-1.1.3.1.jar:target/standalone-1.0.jar com.textualization.aac_speech.standalone.App <arguments>
```

Depending on the arguments, the standalone can verbalize a single utterance or produce a sample of multiple utterances.

### Verbalizing a single utterance

Arguments are: <language (`en` or `fr`)> and pairs of <stem, part-of-speech (one of `NOUN`, `NEGATED`, `TENSE_PAST`, `VERB`, `TENSE_FUTURE` and maybe others)>

Example:

```
java -cp ~/.m2/repository/xmlpull/xmlpull/1.1.3.1/xmlpull-1.1.3.1.jar:target/standalone-1.0.jar com.textualization.aac_speech.standalone.App en I NOUN eat VERB past TENSE_PAST apple NOUN
I ate apple
```

(Yes, most of the outputs are ungrammatical.)


### Sampling verbalizations

Arguments are: <language (`en` or `fr`)> <number of utterances to sample>

Example:

```
java -cp ~/.m2/repository/xmlpull/xmlpull/1.1.3.1/xmlpull-1.1.3.1.jar:target/standalone-1.0.jar com.textualization.aac_speech.standalone.App en 5
Read 6790 entries
IN 0: { prompt ; ADJECTIVE ; https://textualization.com/acc_icons/5306_1.png.64x64_q85.png }  { is ; VERB ; https://textualization.com/acc_icons/5581_1.png.64x64_q85.png }  { we ; NOUN ; https://textualization.com/acc_icons/nous.png }  { what is your name? ; NOUN ; https://textualization.com/acc_icons/7061_1.png.64x64_q85.png }  { that ; NOUN ; https://textualization.com/acc_icons/that_one.png }  { you ; CLITIC_PRONOUN ; https://textualization.com/acc_icons/vous.png }  { dot ; DOT ; https://textualization.com/acc_icons/point.png } 
OUT 0: We, what is your name?, that and you are prompt.
IN 1: { crème caramel ; NOUN ; https://textualization.com/acc_icons/3317_1.png.64x64_q85.png }  { can ; VERB ; https://textualization.com/acc_icons/pouvoir.png }  { all ; ADJECTIVE ; https://textualization.com/acc_icons/5596_1.png.64x64_q85.png }  { dot ; DOT ; https://textualization.com/acc_icons/point.png }  { them ; CLITIC_PRONOUN ; https://textualization.com/acc_icons/elles.png }  { you ; CLITIC_PRONOUN ; https://textualization.com/acc_icons/tu.png } 
OUT 1: Crème caramel can all. They and you
IN 2: { courgette ; NOUN ; https://textualization.com/acc_icons/2678_1.png.64x64_q85.png }  { be ; VERB ; https://textualization.com/acc_icons/etre.png } 
OUT 2: Courgette is
IN 3: { up ; ADJECTIVE ; https://textualization.com/acc_icons/5388_1.png.64x64_q85.png }  { a lot/numerous ; ADJECTIVE ; https://textualization.com/acc_icons/5521_1.png.64x64_q85.png }  { want ; VERB ; https://textualization.com/acc_icons/vouloir.png }  { cause ; NOUN ; https://textualization.com/acc_icons/12295_1.png.64x64_q85.png }  { have ; VERB ; https://textualization.com/acc_icons/avoir.png } 
OUT 3: Cause wants to have up a lot/numerous
IN 4: { I ; NOUN ; https://textualization.com/acc_icons/je.png }  { be ; VERB ; https://textualization.com/acc_icons/etre.png }  { behind ; ADJECTIVE ; https://textualization.com/acc_icons/5443_1.png.64x64_q85.png } 
OUT 4: I am behind
```

The inputs are triples of the form { <word stem> ; <part of speech> ; <acc icon url> }.


## LICENSE

The original code and the new additions are subject to this license:

This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/ .

Content licence: The application includes 5000+ ARASAAC pictograms limited to non-commercial use (Creative Commons) that must be redistributed under the same licence.

Author of the pictograms: Sergio Palao / Origin: ARASAAC / License: CC (BY-NC-SA)

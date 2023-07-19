# Data files

This folder contains the data files used in this project:

1. SimpleNLG sampled files
2. HTML rendered version of the sampled files
3. Human-corrected
4. GPT4 generated
5. JSONL for fine-tuning


## SimpleNLG sampled files

Generated using [AAC SPEECH STANDALONE](../aac-speech-standalone), see the repo for details.


## HTML rendered versions

Generated using the following Perl one-liner:

```
cat 2000.out |perl -ne 'if(m/IN/){$i++; print "<li> ($i)";@a=split(/\{/,$_); shift@a;@a=map{s/.*https/https/;s/\s*\}\s*//;$_}@a;foreach(@a){print "<img width=64 height=64 src=\"$_\"/>"}print"<br/>\n"};if(m/OUT/){s/OUT\s*\d+: //;print"<p>";print "($i) "; print;print"</p>";print"</li>"}' > 2000.html
```

## Human-corrected

The Libreoffice files were produced by opening the HTML files in Mozilla Firefox, select all, copy, then paste on a blank Libreoffice document. That took a lot of RAM and time to process.

The texts themselves were corrected by hand, including deleting sequences of icons that were non-sensical or included unsavory themes. 504 total utterances are available for training.

The `annotation1.odt` file contains the 2,000 entries, the `annotation_final.odt` contains only the 504 hand-corrected.

The `.xhtml` file was obtaining by saving the ODT file as XHTML. It is used to produce the JSONL files used for fine-tuning and the TXT file for GPT4.


## GPT4 enhanced

The 504 annotated were sent through GPT4 with the prompt:

```
Rewrite the following outputs for an AAC Communicator to
make them warmer , more familiar and the type of things a
4 year old would say:

```

Given the 8k limit, it required 5 calls to GPT4.

The texts were obtained by using the following Bash one-liner:

```bash
cat annotation_final.xhtml |grep 'Text_20_body'|grep '<p'|perl -pe 's/.*Text_20_body\"\>//'|perl -ne 's/\<span[^>]+>//g;s/\<\/span\>//g;s/\.?\s*\<\/p.*//;print if m/\(\d+\)/' > annotation-504.txt
```


## Fine-tuning instructions

The instructions to fine-tune using the base texts were obtained with the following one-liner:

```
cat 2000.out |perl -e 'open(A, "annotation-504.txt"); @a=<A>;chomp(@a);%a=map { ($d,$t) = m/\((\d+)\) (.*)/; $d=>$t } @a; while(<STDIN>){if(m/IN/){$i++; if($a{$i}){chomp; s/^IN \d+\: //;s-https://textualization.com/acc_icons/--g; print "$_\t".$a{$i}."\n"}}}'|perl -ne 'use JSON; chomp; ($i,$o)=split(/\t/); $i=~s/\{/\n* \{/g;$j={ 'text'=> "<human>: Simulate an AAC communicator given the following icon input: $i\n<bot>: $o\n"};print encode_json $j;print"\n"' > aac-504.jsonl
```

and

```
cat 2000.out |perl -e 'open(A, "annotation-toddler-504.txt"); @a=<A>;chomp(@a);%a=map { ($d,$t) = m/\((\d+)\) (.*)/; $d=>$t } @a; while(<STDIN>){if(m/IN/){$i++; if($a{$i}){chomp; s/^IN \d+\: //;s-https://textualization.com/acc_icons/--g; print "$_\t".$a{$i}."\n"}}}'|perl -ne 'use JSON; chomp; ($i,$o)=split(/\t/); $i=~s/\{/\n* \{/g;$j={ 'text'=> "<human>: Simulate an AAC communicator given the following icon input: $i\n<bot>: $o\n"};print encode_json $j;print"\n"' > OpenChatKit/data/aac-toddler-504.jsonl
```

for the GPT4 ones.

These files should go into OpenChatKit/data



## LICENSE

The pictograms are authored by Sergio Palao / Origin: ARASAAC / License: CC (BY-NC-SA).

The remaining files are authored Textualization Software Ltd., released CC0 (Public Domain).


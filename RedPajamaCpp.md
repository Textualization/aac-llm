# RedPajama.cpp Execution

With the exported, full model from the [fine-tuned version](src), a redpajama.cpp quantized version can be obtained as follows:

Clone redpajama.cpp:

```
git clone https://github.com/togethercomputer/redpajama.cpp/tree/b9e0389a8fee1a5a8fce1a58e5184194990308bd
```

Ignore the `CMakeLists.txt` file and just do:

```
make
```

to obtaint the redpajama.cpp binaries (`quantize-gptneox`, `redpajama` and `redpajama-chat`).

Now go to the scripts folder:

```
cd examples/redpajama/scripts
```

Edit the file `convert_gptneox_to_ggml.py` and change 

```python
tokenizer = AutoTokenizer.from_pretrained(model_name)
```

to

```python
tokenizer = AutoTokenizer.from_pretrained('togethercomputer/RedPajama-INCITE-Chat-3B-v1')
```

(The exported model does not contain the tokenizer.)

Then export it:

```
python convert_gptneox_to_ggml.py /full/path/to/OpenChatKit/outputs/redpajama-incite-chat-3b-aac aac-ggml
mv aac-ggml/ggml-redpajama-incite-chat-3b-aac-f16.bin ../../../models
```

(The model will be 5.2G in size.)

To quantize to 4 bits:

```
cd ../../..
python ./examples/redpajama/scripts/quantize-gptneox.py ./models/ggml-redpajama-incite-chat-3b-aac-f16.bin 
```

(The model will be 1.7G in size.)

To use it:

```
./redpajama-chat -m models/ggml-redpajama-incite-chat-3b-aac-q4_0.bin 
```

# OpenChatKit LoRA fine-tuning files


## Set-up

Set-up a Python virtual environment with the provided `requirements.txt`:

```bash
virtualenv venv
pip install -r requirements.txt
source venv/bin/activate
```


## Fine-tune

To fine-tune, in this folder do:

```
python redpajama-incite-chat-3b.py
```

This will produce `outputs/redpajama-incite-chat-3b-aac-lowrank` (size 2.6M).

(The current files train on CPU-only. First time it'll download the 3B parameter model.)

To fine-tune the GPT4 version use:

```
python redpajama-incite-chat-3b-toddler.py
```
This will produce `outputs/redpajama-incite-chat-3b-aac-toddler-lowrank` (size 2.6M).


## Inference

To run inference:

```
python redpajama-incite-chat-3b-vanilla_inference.py
```

for base model

```
python redpajama-incite-chat-3b_inference.py
```

for fine-tuned model

```
python redpajama-incite-chat-3b-toddler_inference.py
```

for GPT4 model.

These files run the last 50 prompts from [../data/2000.prompts](../data/2000.prompts).


## Export full model

To merge back the LoRA into the base model, use

```
python redpajama-incite-chat-3b_outlora.py
```

This will produce `outputs/redpajama-incite-chat-3b-aac` (size 11G).


## License

These files are derivative of OpenChatKit files, available under the Apache Public License 2.0.

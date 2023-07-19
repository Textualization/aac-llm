# OpenChatKit LoRA fine-tuning files


## Set-up

These files use OpenChatKit in the LoRA branch:

```
git clone https://github.com/togethercomputer/OpenChatKit/tree/cfe4d5d9b5f4b1a533c4468cc1b7e1107b9a819
```

Set-up a Python virtual environment with the provided `requirements.txt` or follow OpenChatKit installation instructions.

Copy the files in this folder to `OpenChatKit/training/lora/`

Copy the `jsonl` files in `../data` to `OpenChatKit/data`


## Fine-tune

To fine-tune, in folder `OpenChatKit` do:

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

import os
os.environ["CUDA_VISIBLE_DEVICES"]=""
import json
import torch
from peft import PeftModel, PeftConfig
from transformers import AutoModelForCausalLM, AutoTokenizer

peft_model_path ='outputs/redpajama-incite-chat-3b-aac-lowrank'
full_model_path ='outputs/redpajama-incite-chat-3b-aac'

config = PeftConfig.from_pretrained(peft_model_path)
model = AutoModelForCausalLM.from_pretrained(config.base_model_name_or_path, return_dict=True, load_in_8bit=False, device_map='auto')
model.config.use_cache = True
tokenizer = AutoTokenizer.from_pretrained(config.base_model_name_or_path)

# Load the Lora model
model = PeftModel.from_pretrained(model, peft_model_path)
model = model.merge_and_unload()
model.save_pretrained(full_model_path)

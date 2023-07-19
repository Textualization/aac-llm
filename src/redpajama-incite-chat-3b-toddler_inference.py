import json
import torch
from peft import PeftModel, PeftConfig
from transformers import AutoModelForCausalLM, AutoTokenizer

prompts=[]
with open("../../data/2000.prompts", 'rb') as p:
  for prompt in p:
    prompts.append(json.loads(prompt))

prompts = prompts[-50:]


peft_model_path ='outputs/redpajama-incite-chat-3b-aac-toddler-lowrank'

config = PeftConfig.from_pretrained(peft_model_path)
model = AutoModelForCausalLM.from_pretrained(config.base_model_name_or_path, return_dict=True, load_in_8bit=True, device_map='auto')
model.config.use_cache = True
tokenizer = AutoTokenizer.from_pretrained(config.base_model_name_or_path)

# Load the Lora model
model = PeftModel.from_pretrained(model, peft_model_path)

for prompt in prompts:
  batch = tokenizer(prompt['text'], return_tensors='pt')
  #batch = tokenizer("<human>: Simulate an AAC communicator given the following icon input: \n* { to go ; VERB ; 2432_2.png.64x64_q85.png }  \n* { broken ; ADJECTIVE ; 4736_1.png.64x64_q85.png }  \n* { air hostesses ; NOUN ; 12062_1.png.64x64_q85.png }  \n* { bird ; NOUN ; 2490_2.png.64x64_q85.png } \n<bot>:", return_tensors='pt')

  with torch.cuda.amp.autocast():
    output_tokens = model.generate(**batch, max_new_tokens=50)

  print('\n\n', tokenizer.decode(output_tokens[0], skip_special_tokens=False))

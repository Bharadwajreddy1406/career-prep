
---


#### code snippet
---
```python
from deepagents import create_deep_agent

from dotenv import load_dotenv

  

load_dotenv()

  

internet_search = {"type": "web_search"}

  

# System prompt to steer the agent to be an expert researcher

research_instructions = """You are an expert researcher.

Your job is to conduct thorough research and then write a polished report.

You have access to an internet search tool as your primary means of gathering information.

  

## `internet_search`

  

Use this to run an internet search for a given query.

You can specify the max number of results to return,

the topic, and whether raw content should be included.

"""

  

agent = create_deep_agent(

model="openai:gpt-5.4",

tools=[internet_search],

system_prompt=research_instructions,

)

  

result = agent.invoke({"messages": [

{"role": "user", "content": "What is Deep Agents and how does it work?"}

]})

  

# Print the agent's response

print(result, " This is the results content\n\n\n")

print(result["messages"][-1].content)
```


### response =>

Below are the response, we can use this for future references

```json
{
  'messages': [
    HumanMessage(content='What is Deep Agents and how does it work?',
    additional_kwargs={
      
    },
    response_metadata={
      
    },
    id='c3f84611-5e06-4e28-9f43-0ed572f71bc5'),
    AIMessage(content=[
      {
        'id': 'ws_04d814fc16389845006a6795dab62481a29083424af54b014c',
        'action': {
          'type': 'search',
          'queries': [
            'site:openai.com Deep Agents OpenAI official documentation',
            'site:openai.com "Deep Agents" OpenAI',
            'site:platform.openai.com/docs deep agents official'
          ],
          'query': 'site:openai.com Deep Agents OpenAI official documentation'
        },
        'status': 'completed',
        'type': 'web_search_call'
      },
      {
        'type': 'text',
        'text': '“Deep Agents” isn’t a single, universally defined OpenAI product name in the public docs I found. In practice, people usually mean **AI agents built for longer, multi-step work**: systems that can reason over a goal, use tools, gather information, take actions, and producea finished result with limited step-by-step supervision. OpenAI’s current docs describe this generally as **agents**, **workspace agents**, **ChatGPT Work**, and agentic workflows in the API/SDK. ([help.openai.com](https://help.openai.com/en/articles/20001143/?utm_source=openai))\n\nAt a high level, an agent works like this:\n1. **You give it a goal**, not just a one-shot prompt.\n2. It **plans or decomposes** the work into substeps.\n3. It **uses tools** as needed, such as web search, file retrieval, app connections, or computer/browser actions.\n4. It **checks intermediate results**, asks for clarification or approval when necessary, and adapts if something fails.\n5. It returns a **deliverable or completed action**, not just text. This description aligns with OpenAI’s guidance that agents are suited to workflows they can perform “on the user’s behalf” with a degree of independence. ([openai.com](https://openai.com/business/guides-and-resources/a-practical-guide-to-building-ai-agents/?utm_source=openai))\n\nA useful way to distinguish an agent from ordinary chat is:\n- **Chat model**: answer this question.\n- **Agent**: accomplish this task.  \nOpenAI’s workspace agents are specifically positioned for **repeatable workflows**, where the agent can be given instructions, connected tools/apps, sharing controls, and even schedules or API triggers. ([help.openai.com](https://help.openai.com/en/articles/20001143/?utm_source=openai))\n\nIf you mean **how OpenAI agents work technically**, the common building blocks are:\n- a **model** that reasons over the task,\n- **instructions** that define behavior and constraints,\n- **tools** the model can call,\n- **memory/context** from the conversation, files, or connected systems,\n- and an **orchestrator loop** that lets the system alternate between reasoning, tool use, and output generation. OpenAI’s developer materials describe building agents with models plus tools like web search, file search, and computer use via the API and Agents SDK. ([platform.openai.com](https://platform.openai.com/docs/quickstart/make-your-first-api-request?utm_source=openai))\n\nA simplified loop looks like this:\n\n```text\nUser goal\n  ↓\nAgent interprets objective\n  ↓\nCreates/updates plan\n  ↓\nChooses next action\n  ├─ ask user a question\n  ├─ search/readdata\n  ├─ call app/API/tool\n  └─ perform computer/browser action\n  ↓\nObserves result\n  ↓\nRepeats until done\n  ↓\nDelivers final output / action\n```\n\nIn ChatGPT-style agent products, this can include browsing public websites, working from uploaded files, and using connected sources or external tools, while pausing for confirmation on sensitive actions. OpenAI’s Help Center describes this “reasoning + action” pattern explicitly for ChatGPT agent experiences. ([help.openai.com](https://help.openai.com/en/articles/11794368?utm_source=openai))\n\nWhy people call them “deep”:\n- they handle **longer task horizons**,\n- perform **multi-step research or execution**,\n- can combine **many tool calls**,\n- and produce more **complete deliverables** than a normal answer.  \nThat’s also the same general idea behind OpenAI’s “deep research” style workflows, though deep research is more specifically about intensive information gathering and synthesis than general task execution. ([help.openai.com](https://help.openai.com/en/articles/10500283-leep-research-faq?utm_source=openai))\n\nWhat makes them work well in practice:\n- clear success criteria,\n- access only to the right tools/data,\n- approval checkpoints for risky actions,\n- structured outputs,\n- and guardrails against failures like prompt injection or acting on bad data. OpenAI explicitly highlights instruction quality, orchestration patterns, and guardrails as core agentdesign concerns. ([openai.com](https://openai.com/business/guides-and-resources/a-practical-guide-to-building-ai-agents/?utm_source=openai))\n\nOne current naming note: OpenAI’s Help Center says **“ChatGPT agent” is no longer available** and points users to **ChatGPT Work** for longer multi-step tasks, while **workspace agents** are the reusable/custom-agent concept for Business and Enterprise contexts. So if you saw “Deep Agents”somewhere, it may be shorthand or third-party terminology rather than the exact current OpenAI product label. ([help.openai.com](https://help.openai.com/en/articles/11752874-chatgpt-agent?utm_source=openai))\n\nIf you want, I can also explain this in one of three more specific ways:\n1. **plain English for non-technical users**,\n2. **technical architecture for developers**, or\n3. **how to build one with the OpenAI API/Agents SDK**.',
        'annotations': [
          {
            'end_index': 554,
            'start_index': 470,
            'title': 'ChatGPT Workspace Agents for Enterprise and Business | OpenAI Help Center',
            'type': 'url_citation',
            'url': 'https://help.openai.com/en/articles/20001143/?utm_source=openai'
          },
          {
            'end_index': 1288,
            'start_index': 1165,
            'title': 'A practical guide to building agents | OpenAI',
            'type': 'url_citation',
            'url': 'https://openai.com/business/guides-and-resources/a-practical-guide-to-building-ai-agents/?utm_source=openai'
          },
          {
            'end_index': 1714,
            'start_index': 1630,
            'title': 'ChatGPT Workspace Agents for Enterprise and Business | OpenAI Help Center',
            'type': 'url_citation',
            'url': 'https://help.openai.com/en/articles/20001143/?utm_source=openai'
          },
          {
            'end_index': 2384,
            'start_index': 2270,
            'title': 'Developer quickstart - OpenAI API',
            'type': 'url_citation',
            'url': 'https://platform.openai.com/docs/quickstart/make-your-first-api-request?utm_source=openai'
          },
          {
            'end_index': 3105,
            'start_index': 3022,
            'title': 'ChatGPT agent - release notes | OpenAIHelp Center',
            'type': 'url_citation',
            'url': 'https://help.openai.com/en/articles/11794368?utm_source=openai'
          },
          {
            'end_index': 3635,
            'start_index': 3534,
            'title': 'Deep research in ChatGPT | OpenAI Help Center',
            'type': 'url_citation',
            'url': 'https://help.openai.com/en/articles/10500283-leep-research-faq?utm_source=openai'
          },
          {
            'end_index': 4127,
            'start_index': 4004,
            'title': 'A practical guide to building agents | OpenAI',
            'type': 'url_citation',
            'url': 'https://openai.com/business/guides-and-resources/a-practical-guide-to-building-ai-agents/?utm_source=openai'
          },
          {
            'end_index': 4627,
            'start_index': 4530,
            'title': 'ChatGPT agent | OpenAI Help Center',
            'type': 'url_citation',
            'url': 'https://help.openai.com/en/articles/11752874-chatgpt-agent?utm_source=openai'
          }
        ],
        'id': 'msg_04d814fc16389845006a6795ddd48c81a2b51b6fbec1f2964d',
        'phase': 'final_answer'
      }
    ],
    additional_kwargs={
      
    },
    response_metadata={
      'id': 'resp_04d814fc16389845006a6795d9903481a2a39a5ee354621f7e',
      'created_at': 1785173465.0,
      'metadata': {
        
      },
      'model': 'gpt-5.4-2026-03-05',
      'object': 'response',
      'service_tier': 'default',
      'status': 'completed',
      'model_provider': 'openai',
      'model_name': 'gpt-5.4-2026-03-05'
    },
    id='resp_04d814fc16389845006a6795d9903481a2a39a5ee354621f7e',
    tool_calls=[
      
    ],
    invalid_tool_calls=[
      
    ],
    usage_metadata={
      'input_tokens': 13861,
      'output_tokens': 1124,
      'total_tokens': 14985,
      'input_token_details': {
        'cache_creation': 0,
        'cache_read': 0
      },
      'output_token_details': {
        'reasoning': 61
      }
    })
  ],
  'files': {
    
  }
}
```



final response : 
```json
[
  {
    'id': 'ws_04d814fc16389845006a6795dab62481a29083424af54b014c',
    'action': {
      'type': 'search',
      'queries': [
        'site:openai.com Deep Agents OpenAI official documentation',
        'site:openai.com "Deep Agents" OpenAI',
        'site:platform.openai.com/docs deep agents official'
      ],
      'query': 'site:openai.com Deep Agents OpenAI official documentation'
    },
    'status': 'completed',
    'type': 'web_search_call'
  },
  {
    'type': 'text',
    'text': '“Deep Agents” isn’t a single, universally defined OpenAI product name in the public docs I found. In practice, people usually mean **AI agents built for longer, multi-step work**: systems that can reason over a goal, use tools, gather information, take actions, and produce a finished result with limited step-by-step supervision. OpenAI’s current docs describe this generally as **agents**, **workspace agents**, **ChatGPT Work**, and agentic workflows in the API/SDK. ([help.openai.com](https://help.openai.com/en/articles/20001143/?utm_source=openai))\n\nAt a high level, an agent works like this:\n1. **You give it a goal**, not just a one-shot prompt.\n2. It **plans or decomposes** the work into substeps.\n3. It **uses tools** as needed, such as web search, file retrieval, app connections, or computer/browser actions.\n4. It **checks intermediate results**, asks for clarification or approval when necessary, and adapts if something fails.\n5. It returns a **deliverable or completed action**, not just text. This description aligns with OpenAI’s guidance that agents are suited to workflows they can perform “on the user’s behalf” with a degree of independence. ([openai.com](https://openai.com/business/guides-and-resources/a-practical-guide-to-building-ai-agents/?utm_source=openai))\n\nA useful way to distinguish an agent from ordinary chat is:\n- **Chat model**: answer this question.\n- **Agent**: accomplish this task.  \nOpenAI’s workspace agents are specifically positioned for **repeatable workflows**, where the agent can be given instructions, connected tools/apps, sharing controls, and even schedules or API triggers. ([help.openai.com](https://help.openai.com/en/articles/20001143/?utm_source=openai))\n\nIf you mean **how OpenAI agents work technically**, the common building blocks are:\n- a **model** that reasons over the task,\n- **instructions** that define behavior and constraints,\n- **tools** the model can call,\n- **memory/context** from the conversation, files, or connected systems,\n- and an **orchestrator loop** that lets the system alternate between reasoning, tool use, and output generation. OpenAI’s developer materials describe building agents with models plus tools like web search, file search, and computer use via the API and Agents SDK. ([platform.openai.com](https://platform.openai.com/docs/quickstart/make-your-first-api-request?utm_source=openai))\n\nA simplified loop looks like this:\n\n```text\nUser goal\n  ↓\nAgent interprets objective\n  ↓\nCreates/updates plan\n  ↓\nChooses next action\n  ├─ ask user a question\n  ├─ search/read data\n  ├─ call app/API/tool\n  └─ perform computer/browser action\n  ↓\nObserves result\n  ↓\nRepeats until done\n  ↓\nDelivers final output / action\n```\n\nIn ChatGPT-style agent products, this can include browsing public websites, working from uploaded files, and using connected sources or external tools, while pausing for confirmation on sensitive actions. OpenAI’s Help Center describes this “reasoning + action” pattern explicitly for ChatGPT agent experiences. ([help.openai.com](https://help.openai.com/en/articles/11794368?utm_source=openai))\n\nWhy people call them “deep”:\n- they handle **longer task horizons**,\n- perform **multi-step research or execution**,\n- can combine **many tool calls**,\n- and produce more **complete deliverables** than a normal answer.  \nThat’s also the same general idea behind OpenAI’s “deep research” style workflows, though deep research is more specifically about intensive information gathering and synthesis than general task execution. ([help.openai.com](https://help.openai.com/en/articles/10500283-leep-research-faq?utm_source=openai))\n\nWhat makes them work well in practice:\n- clear success criteria,\n- access only to the right tools/data,\n- approval checkpoints for risky actions,\n- structured outputs,\n- and guardrails against failures like prompt injection or acting on bad data. OpenAI explicitly highlights instruction quality, orchestration patterns, and guardrails as core agent design concerns. ([openai.com](https://openai.com/business/guides-and-resources/a-practical-guide-to-building-ai-agents/?utm_source=openai))\n\nOne current naming note: OpenAI’s Help Center says **“ChatGPT agent” is no longer available** and points users to **ChatGPT Work** for longer multi-step tasks, while **workspace agents** are the reusable/custom-agent concept for Business and Enterprise contexts. So if you saw “Deep Agents” somewhere, it may be shorthand or third-party terminology rather than the exact current OpenAI product label. ([help.openai.com](https://help.openai.com/en/articles/11752874-chatgpt-agent?utm_source=openai))\n\nIf you want, I can also explain this in one of three more specific ways:\n1. **plain English for non-technical users**,\n2. **technical architecture for developers**, or\n3. **how to build one with the OpenAI API/Agents SDK**.',
    'annotations': [
      {
        'end_index': 554,
        'start_index': 470,
        'title': 'ChatGPT Workspace Agents for Enterprise and Business | OpenAI Help Center',
        'type': 'url_citation',
        'url': 'https://help.openai.com/en/articles/20001143/?utm_source=openai'
      },
      {
        'end_index': 1288,
        'start_index': 1165,
        'title': 'A practical guide to building agents | OpenAI',
        'type': 'url_citation',
        'url': 'https://openai.com/business/guides-and-resources/a-practical-guide-to-building-ai-agents/?utm_source=openai'
      },
      {
        'end_index': 1714,
        'start_index': 1630,
        'title': 'ChatGPT Workspace Agents for Enterprise and Business | OpenAI Help Center',
        'type': 'url_citation',
        'url': 'https://help.openai.com/en/articles/20001143/?utm_source=openai'
      },
      {
        'end_index': 2384,
        'start_index': 2270,
        'title': 'Developer quickstart - OpenAI API',
        'type': 'url_citation',
        'url': 'https://platform.openai.com/docs/quickstart/make-your-first-api-request?utm_source=openai'
      },
      {
        'end_index': 3105,
        'start_index': 3022,
        'title': 'ChatGPT agent - release notes | OpenAI Help Center',
        'type': 'url_citation',
        'url': 'https://help.openai.com/en/articles/11794368?utm_source=openai'
      },
      {
        'end_index': 3635,
        'start_index': 3534,
        'title': 'Deep research in ChatGPT | OpenAI Help Center',
        'type': 'url_citation',
        'url': 'https://help.openai.com/en/articles/10500283-leep-research-faq?utm_source=openai'
      },
      {
        'end_index': 4127,
        'start_index': 4004,
        'title': 'A practical guide to building agents | OpenAI',
        'type': 'url_citation',
        'url': 'https://openai.com/business/guides-and-resources/a-practical-guide-to-building-ai-agents/?utm_source=openai'
      },
      {
        'end_index': 4627,
        'start_index': 4530,
        'title': 'ChatGPT agent | OpenAI Help Center',
        'type': 'url_citation',
        'url': 'https://help.openai.com/en/articles/11752874-chatgpt-agent?utm_source=openai'
      }
    ],
    'id': 'msg_04d814fc16389845006a6795ddd48c81a2b51b6fbec1f2964d',
    'phase': 'final_answer'
  }
]
```
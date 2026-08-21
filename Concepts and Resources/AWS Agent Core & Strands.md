
---
https://dev.to/aws/building-production-ready-ai-agents-with-strands-agents-and-amazon-bedrock-agentcore-3dg0

https://dev.to/aws/building-production-ready-ai-agents-with-strands-agents-and-amazon-bedrock-agentcore-3dg0



```
Progressive disclosure levels:

Level 1: discover_skills() + get_catalog() → skill name + description for system prompt

Level 2: load_instructions() → SKILL.md body content

Level 3: bind_tools() + get_tools() → AgentTool objects for execution via skill_executor
```
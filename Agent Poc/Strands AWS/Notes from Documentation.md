
---
https://strandsagents.com/docs/user-guide/quickstart/typescript/

Create a base project with one file with agent in it and a basic tool
- Contains configuring creds 
- one basic execution
- explains on strands and it's default model
- Bedrock specific details & configs https://strandsagents.com/docs/user-guide/quickstart/typescript/
- Open AI integration via Strands https://strandsagents.com/docs/user-guide/concepts/model-providers/openai/




### event streaming (like yeild)

```ts
for await (const event of agent.stream(prompt)) {

// Events automatically serialize to compact JSON via toJSON().

// Only relevant data fields are included — the full Agent instance,

// Tool classes, and mutable hook flags (cancel/retry) are excluded.

res.write(`${JSON.stringify(event)}\n`)

}

res.end()
```
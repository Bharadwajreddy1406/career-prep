
---

## Commands Cheatsheet
---
If building production-grade Python applications (especially AI applications), `uv` is much more than a faster `pip`. It's effectively a replacement for:

- `pip`
    
- `pip-tools`
    
- `virtualenv`
    
- `venv`
    
- `pyenv` (partially)
    
- `pipx` (partially)
    
- even parts of `poetry`
    

The biggest mistake most people make is using it like:

```bash
uv pip install requests
```

That's basically using a Ferrari to drive in first gear.

---

# 1. Installing Python

Never install Python manually again.

```bash
uv python install 3.13
uv python install 3.12
uv python install 3.11
```

List installed versions

```bash
uv python list
```

Use one

```bash
uv python pin 3.12
```

This creates

```
.python-version
```

similar to pyenv.

---

# 2. Creating a Project

Instead of

```bash
python -m venv .venv
pip install ...
```

do

```bash
uv init
```

or

```bash
uv init my_project
```

You'll get

```
pyproject.toml

README.md

src/

.gitignore
```

already configured.

---

# 3. Creating Virtual Environments

Create

```bash
uv venv
```

Specific Python

```bash
uv venv --python 3.12
```

Activate

Linux

```bash
source .venv/bin/activate
```

Windows

```powershell
.venv\Scripts\activate
```

---

# 4. Adding Packages

Instead of

```bash
pip install fastapi
```

use

```bash
uv add fastapi
```

This automatically

- installs
    
- updates pyproject.toml
    
- updates uv.lock
    

No more requirements.txt editing.

---

Multiple packages

```bash
uv add fastapi sqlalchemy openai
```

---

Development dependencies

```bash
uv add --dev pytest
```

or

```bash
uv add --dev black ruff mypy
```

---

Optional groups

```bash
uv add --group ai openai
```

Then

```bash
uv sync --group ai
```

Very useful for large repos.

---

# 5. Removing Packages

```bash
uv remove fastapi
```

Updates everything automatically.

---

# 6. Syncing Dependencies

Instead of

```bash
pip install -r requirements.txt
```

do

```bash
uv sync
```

This installs exactly what's in

```
uv.lock
```

---

Production deployment

```bash
uv sync --frozen
```

Equivalent to

> Don't change anything.

Perfect for CI.

---

# 7. Lock File

Generate

```bash
uv lock
```

Update

```bash
uv lock --upgrade
```

Update one package

```bash
uv lock --upgrade-package openai
```

---

# 8. Running Programs

Instead of

```bash
python app.py
```

use

```bash
uv run app.py
```

It automatically

- activates environment
    
- picks correct Python
    
- installs missing deps if necessary
    

---

Run module

```bash
uv run -m mypackage
```

---

Run command

```bash
uv run pytest
```

or

```bash
uv run ruff check .
```

No activation needed.

---

# 9. Temporary Tools

Need black once?

Instead of

```bash
pip install black
```

do

```bash
uvx black .
```

Need

```
httpie
```

```bash
uvx http
```

Need

```
cookiecutter
```

```bash
uvx cookiecutter
```

No installation.

Runs in temporary environment.

---

# 10. Installing CLI Applications

Instead of

```bash
pip install --user ruff
```

do

```bash
uv tool install ruff
```

Upgrade

```bash
uv tool upgrade ruff
```

List

```bash
uv tool list
```

Remove

```bash
uv tool uninstall ruff
```

---

# 11. Export Requirements

If someone still wants requirements.txt

```bash
uv export > requirements.txt
```

Without hashes

```bash
uv export --no-hashes > requirements.txt
```

---

# 12. Import Existing Requirements

Existing project

```bash
uv add -r requirements.txt
```

---

# 13. Install From Git

```bash
uv add git+https://github.com/astral-sh/ruff
```

Specific branch

```bash
uv add git+https://...
```

---

# 14. Editable Install

```bash
uv add --editable .
```

Equivalent

```
pip install -e .
```

---

# 15. Dependency Tree

```bash
uv tree
```

Shows

```
fastapi
 ├── starlette
 ├── pydantic
 └── anyio
```

Super useful.

---

# 16. Updating Everything

```bash
uv lock --upgrade
uv sync
```

---

Update one package

```bash
uv add openai@latest
```

---

# 17. Using Different Python Versions

Run with

```bash
uv run --python 3.13 app.py
```

No switching environments.

---

# 18. Reproducible Builds

Commit

```
pyproject.toml

uv.lock
```

Never commit

```
.venv
```

Everyone simply does

```bash
uv sync
```

Done.

---

# 19. Workspaces (Monorepos)

A powerful feature if you're working with multiple related packages.

Example:

```
repo/
│
├── pyproject.toml
├── apps/
│   ├── api/
│   └── worker/
└── libs/
    ├── common/
    └── ai/
```

A workspace lets all packages share dependency resolution and local package references, similar to Cargo (Rust) or npm workspaces.

---

# 20. Useful Environment Variables

Cache location

```bash
UV_CACHE_DIR=~/.cache/uv
```

Offline mode

```bash
UV_OFFLINE=1
```

Use a mirror

```bash
UV_INDEX_URL=https://...
```

---

# 21. Cache Management

See cache

```bash
uv cache dir
```

Clean everything

```bash
uv cache clean
```

---

# 22. Running Scripts With Inline Dependencies

One of the coolest features.

```python
# /// script
# dependencies = [
#     "requests",
#     "rich"
# ]
# ///

import requests
```

Run

```bash
uv run script.py
```

No virtual environment needed.

Excellent for automation scripts.

---

# 23. Common Project Workflow

```bash
uv init

uv add fastapi sqlalchemy openai

uv add --dev pytest ruff mypy

uv run uvicorn app:app --reload

uv run pytest

uv run ruff check .

uv lock

git add pyproject.toml uv.lock
```

---

# 24. CI/CD Workflow

```bash
uv sync --frozen

uv run pytest

uv run ruff check .

uv run mypy
```

Fast, reproducible, and deterministic.

---

# 25. AI/ML Project Workflow (Recommended)

For AI projects like the ones you've been building with LangGraph, DeepAgents, vector stores, and OpenAI SDKs:

```bash
uv init

uv python pin 3.12

uv add \
    openai \
    langchain \
    langgraph \
    fastapi \
    uvicorn \
    pydantic-settings

uv add --dev \
    pytest \
    ruff \
    mypy \
    ipykernel

uv sync

uv run python main.py
```

---

# Things You Probably Don't Need Anymore

|Old Tool|Replace with|
|---|---|
|`pip install`|`uv add`|
|`pip install -r`|`uv sync`|
|`virtualenv`|`uv venv`|
|`python -m venv`|`uv venv`|
|`pip freeze`|`uv export`|
|`pip-tools`|`uv lock`|
|`pipx`|`uv tool` / `uvx`|
|`pyenv`|`uv python`|
|`python script.py`|`uv run script.py`|
|`pip install -e .`|`uv add --editable .`|

---

## Tips that save time

- Use `uv run ...` instead of activating virtual environments whenever possible.
    
- Prefer `uv add` over `uv pip install` for project dependencies so your `pyproject.toml` and `uv.lock` stay in sync.
    
- Commit `uv.lock` to version control for deterministic builds.
    
- Use `uvx` to try or run CLI tools without permanently installing them.
    
- Pin the Python version (`uv python pin`) so every developer and CI job uses the same interpreter.
    
- For CI, always use `uv sync --frozen` to ensure the environment exactly matches the lock file.
    


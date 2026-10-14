
---

### A function is a value in Python
You can pass a function into another function:

```python
def greet(name):
    return f"Hello, {name}"

def call_function(fn):
    return fn("Alice")

print(call_function(greet))
```
we pass `greet`, not `greet()`. We are passing the function itself.

#### Writing Decorators

If we want to know when a function starts and ends
```python
from functools import wraps

def trace(fn):
    @wraps(fn)
    def wrapper(*args, **kwargs):
        print(f"Starting {fn.__name__}")

        result = fn(*args, **kwargs)

        print(f"Finished {fn.__name__}")
        return result

    return wrapper
```

and it's used like this 

```python
@trace
def calculate_total(price, quantity):
    return price * quantity

print(calculate_total(10, 3))
```

It approximately means 
```python
def calculate_total(price, quantity):
    return price * quantity

calculate_total = trace(calculate_total)
```

After decoration, calling `calculate_total()` calls `wrapper()`. The wrapper calls the original function.

| Piece             | Purpose                                                                          |
| ----------------- | -------------------------------------------------------------------------------- |
| `fn`              | The original function                                                            |
| `wrapper`         | The replacement function callers execute                                         |
| `*args, **kwargs` | Forward whatever arguments the caller supplied                                   |
| `return result`   | Preserve the original return value                                               |
| `@wraps(fn)`      | Preserve the original function’s name, documentation, and introspection metadata |

---
Tracing usually needs a start time, end time, outcome, and error category.
```python
from functools import wraps
from time import perf_counter

def trace(fn):
    @wraps(fn)
    def wrapper(*args, **kwargs):
        started = perf_counter()
        status = "finished"

        print(f"START {fn.__name__}")

        try:
            return fn(*args, **kwargs)
        except BaseException as error:
            status = "error"
            print(f"ERROR {type(error).__name__}")
            raise
        finally:
            duration = perf_counter() - started
            print(
                f"END {fn.__name__} "
                f"status={status} duration={duration:.3f}s"
            )

    return wrapper
```

**`raise` preserves the failure.** The decorator observes it; the caller still receives it.

**`finally` runs even when the function returns or raises.** That makes it the place to finish a trace.

### Async functions need an async wrapper
---
if we do the same thing as we've done above like this
```python
result = fn(*args, **kwargs)
```
 It measures coroutine creation, not execution.

so we do this 

```python
def trace_async(fn):
    @wraps(fn)
    async def wrapper(*args, **kwargs):
        started = perf_counter()

        try:
            return await fn(*args, **kwargs)
        finally:
            duration = perf_counter() - started
            print(f"{fn.__name__} took {duration:.3f}s")

    return wrapper
```

and use it as 
```python
@trace_async
async def execute_agent(prompt):
    return await agent.invoke_async(prompt)
```
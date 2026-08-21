
---
# Variables, Values, and Types

## 1. The Core Mental Model

A Go program operates on **values**.

A variable gives a name to a value:

```go
age := 25
```

Conceptually:

```text
variable name
     ↓
   age
     ↓
   value
     ↓
    25
```

The important part is that every value in Go has a **type**.

```go
age := 25
```

Here:

```text
age
 ↓
int
 ↓
25
```

So the fundamental relationship is:

```text
variable → value → type
```

Go makes types explicit because the compiler needs to know **what kind of data exists and how operations on that data are valid**.

---

# 2. What Problem Do Types Solve?

Consider:

```go
age := 25
```

The compiler needs to know:

- how this value is represented
    
- how much space it requires
    
- which operations are valid
    
- what other values it can interact with
    

For example:

```go
age + 10
```

is valid because both values are numeric.

But:

```go
age + "hello"
```

is invalid because an integer and a string aren't operands for the same `+` operation.

Go catches this during compilation.

This is one of Go's major design principles:

> **Push correctness checks toward compile time instead of discovering them during execution.**

---

# 3. Declaring a Variable

The explicit variable declaration syntax is:

```go
var age int
```

This means:

```text
name  = age
type  = int
value = zero value
```

Since no value was provided, Go gives the variable its **zero value**.

For `int`, that is:

```go
0
```

So:

```go
var age int
```

behaves conceptually as:

```go
age = 0
```

---

# 4. Declaration With Initialization

You can declare and initialize simultaneously:

```go
var age int = 25
```

The compiler knows:

```text
age
 ↓
int
 ↓
25
```

Because the type is explicitly specified.

However, inside functions, Go normally lets the compiler infer the type.

---

# 5. Short Variable Declaration

The idiomatic way to create a local variable inside a function is:

```go
age := 25
```

This is called a **short variable declaration**.

Go infers the type from the value:

```go
age := 25
```

becomes conceptually:

```text
age → int → 25
```

Similarly:

```go
name := "Nani"
```

produces:

```text
name → string → "Nani"
```

And:

```go
active := true
```

produces:

```text
active → bool → true
```

### Rule

> **Inside functions, use `:=` when declaring a new local variable.**

### Why?

Go already knows the type from the expression on the right.

Writing:

```go
var age int = 25
```

usually repeats information the compiler can already determine.

---

# 6. `:=` Is Declaration, Not Assignment

This distinction is important.

```go
age := 25
```

**declares** `age`.

Once the variable exists:

```go
age = 30
```

**assigns** a new value to it.

The difference is:

```text
:=  → declare + initialize
=   → assign
```

For example:

```go
age := 25
age = 30
```

The first line creates the variable.

The second line changes its value.

### Failure mode

This is invalid:

```go
age := 25
age := 30
```

because the second `:=` attempts to declare `age` again in the same scope without introducing a new variable.

Use:

```go
age = 30
```

instead.

---

# 7. Go's Basic Types

The fundamental built-in types you should know first are:

```text
bool
string
int
float64
```

There are also explicitly sized numeric types such as:

```text
int8
int16
int32
int64

uint8
uint16
uint32
uint64

float32
float64
```

and complex-number types.

Don't memorize all of them yet.

For normal application code, the important starting point is:

```go
bool
string
int
float64
```

---

# 8. `bool`

A boolean contains one of two values:

```go
true
false
```

Example:

```go
active := true
```

The type is:

```text
bool
```

Booleans are commonly used for decisions:

```go
if active {
    ...
}
```

They represent logical state rather than numeric state.

---

# 9. `string`

A string represents a sequence of bytes interpreted as UTF-8 by convention.

Example:

```go
name := "Nani"
```

The type is:

```text
string
```

Strings are immutable.

That means this does not modify the existing string:

```go
name = name + "!"
```

Instead, a new string value is produced and assigned to `name`.

This distinction becomes important later when we study memory and allocations.

---

# 10. `int`

`int` represents a signed integer.

Example:

```go
age := 25
```

The inferred type is:

```text
int
```

For ordinary integer calculations and indexes, `int` is the idiomatic choice.

For example:

```go
count := 100
index := 0
```

Use `int` unless you specifically need a fixed-width integer.

---

# 11. `float64`

`float64` represents a 64-bit floating-point number.

Example:

```go
price := 99.99
```

The inferred type is:

```text
float64
```

Go does not silently convert integers and floating-point values.

For example:

```go
var x int = 10
var y float64 = 2.5
```

This does not automatically make:

```go
x + y
```

valid.

You must explicitly convert:

```go
float64(x) + y
```

### Why?

Go wants the programmer to explicitly acknowledge a change in representation.

This avoids hidden conversions that can silently change meaning or precision.

---

# 12. Explicit Type Conversion

Go uses a simple syntax for conversion:

```go
float64(x)
```

For example:

```go
count := 10
price := 2.5

total := float64(count) * price
```

The conversion is explicit:

```text
int
 ↓
float64
```

This is different from languages that perform more implicit numeric conversions.

### Rule

> **When Go needs a type conversion, write the conversion explicitly.**

### Failure mode

Don't expect:

```go
var count int = 10
var price float64 = 2.5

total := count * price
```

to compile.

The types are different.

---

# 13. Constants

Go also has constants.

```go
const maxRequests = 100
```

A constant represents a value that cannot be changed after declaration.

```go
const maxRequests = 100

maxRequests = 200 // invalid
```

Constants are useful for values that are conceptually fixed:

```go
const maxConnections = 1000
const serverPort = 8080
```

The distinction is:

```text
variable
    ↓
value can change

constant
    ↓
value cannot change
```

---

# 14. Zero Values

One of Go's important design decisions is that variables automatically receive a **zero value**.

Examples:

```go
var count int
```

gives:

```text
count = 0
```

```go
var active bool
```

gives:

```text
active = false
```

```go
var name string
```

gives:

```text
name = ""
```

This becomes especially important when we reach structs, slices, maps, and pointers.

### Why?

Go avoids leaving variables in an undefined state.

A newly created variable always has a valid default value for its type.

This reduces an entire category of uninitialized-memory bugs common in lower-level languages.

---

# 15. Variables Are Mutable

A variable can be assigned a new value as long as the new value is compatible with its type.

```go
age := 25

age = 26
age = 27
```

The variable remains:

```text
age → int
```

while the value changes:

```text
25 → 26 → 27
```

The **type of the variable doesn't change**.

This is an important distinction:

```text
variable identity
       ↓
      age

type
       ↓
      int

current value
       ↓
      27
```

Go is statically typed.

A variable doesn't dynamically become another type during normal assignment.

---

# 16. Static Typing

Go is **statically typed**.

Consider:

```go
age := 25
```

The compiler determines:

```text
age → int
```

and that type remains fixed.

This is invalid:

```go
age := 25
age = "hello"
```

because:

```text
int ≠ string
```

### Why?

The compiler can reason about operations and memory representation before the program runs.

This is one of the ways Go moves responsibility from runtime behavior into compilation.

---

# 17. Type Inference Does Not Mean Dynamic Typing

This distinction is important coming from Python.

Go allows:

```go
age := 25
```

You don't explicitly write:

```go
age int
```

But that does **not** mean Go is dynamically typed.

The compiler still determines:

```text
age → int
```

and enforces that type.

So:

```text
Python

variable
   ↓
runtime determines type
   ↓
type can change
```

while:

```text
Go

variable
   ↓
compiler determines type
   ↓
type is fixed
```

Go's `:=` is **type inference**, not dynamic typing.

---

# 18. Scope

A variable exists only within its scope.

For example:

```go
func main() {
    age := 25

    if age > 18 {
        message := "adult"
        println(message)
    }
}
```

`message` exists only inside the `if` block.

Conceptually:

```text
main scope
│
├── age
│
└── if scope
    └── message
```

Outside the `if`:

```go
println(message)
```

is invalid.

### Why?

Go uses lexical scopes so the compiler can determine exactly where identifiers are valid.

This makes ownership and visibility predictable.

---

# 19. Shadowing

A nested scope can declare a new variable with the same name:

```go
age := 25

if true {
    age := 30
    println(age)
}

println(age)
```

The inner `age` is a different variable.

Conceptually:

```text
outer scope
    age → 25

inner scope
    age → 30
```

The inner declaration **shadows** the outer variable.

Avoid unnecessary shadowing.

It makes it harder to determine which variable you're modifying.

This becomes particularly important with `:=` and error handling.

---

# 20. Multiple Variable Declarations

Go allows multiple variables to be declared together:

```go
name, age := "Nani", 25
```

This is useful when an operation naturally produces multiple values.

You will see this heavily when we reach functions that return multiple values:

```go
user, err := findUser(id)
```

Here:

```text
user
err
```

are both declared by the same statement.

This is one of the reasons Go's multiple-return-value design works naturally with `:=`.

---

# 21. What Happens Underneath?

At the machine level, variables ultimately correspond to data stored somewhere in memory or represented directly by the machine/runtime.

For:

```go
age := 25
```

you can initially think:

```text
variable
   ↓
memory location
   ↓
25
```

The exact storage location is a compiler decision and can involve the stack, heap, registers, or other runtime/compiler mechanisms.

Don't yet assume:

> "Every variable is always stored on the stack."

That model is too simplistic.

Later, when we study **pointers, stack/heap allocation, and escape analysis**, we'll refine this.

For now, keep:

> **A variable gives a name to a value, and the compiler/runtime decides how that value is represented and stored.**

---

# 22. Why Go Cares About Types So Much

Go's type system isn't just there to prevent mistakes.

It gives the compiler information about the program.

For:

```go
count := 10
```

the compiler knows:

```text
count
  ↓
int
```

Therefore it can determine:

- which operations are legal
    
- what representation is required
    
- what conversions are necessary
    
- which function arguments are compatible
    
- which return values are compatible
    

This makes the program easier for both the **compiler and programmer** to reason about.

---

# 23. The Mental Model to Keep

Keep this model:

```text
                    VARIABLE
                       │
                       ↓
                    VALUE
                       │
                       ↓
                     TYPE
```

Example:

```go
age := 25
```

means:

```text
age
 │
 ├── type  → int
 │
 └── value → 25
```

Then:

```go
age = 26
```

changes:

```text
value: 25 → 26
```

but does not change:

```text
type: int
```

And:

```go
float64(age)
```

creates a value converted to another type.

---

# 24. Core Rules

Remember these rules:

1. **Every Go value has a type.**
    
2. **Go is statically typed.**
    
3. **`:=` declares and initializes a local variable.**
    
4. **`=` assigns a new value to an existing variable.**
    
5. **Type inference does not mean dynamic typing.**
    
6. **Go does not perform arbitrary implicit type conversions.**
    
7. **Every variable receives a zero value when no explicit initial value is provided.**
    
8. **Variables are scoped.**
    
9. **Constants cannot be reassigned.**
    
10. **Use `int` for ordinary integer values unless a specific integer width is required.**
    

---

# 25. What This Sets Up Next

We now have:

```text
Go module
    ↓
packages
    ↓
variables
    ↓
values
    ↓
types
```

The next concept is **structs and user-defined types**.

That is where Go starts becoming substantially different from the Java/Python mental model.

We'll move from:

```go
age := 25
```

to:

```go
type User struct {
    Name string
    Age  int
}
```

and understand what a `struct` actually represents in memory, why Go uses structs so heavily, and how this differs from a Java class.

The important dependency is:

> **Before understanding structs, you need to understand that a type describes the shape and rules of the values belonging to it.**  
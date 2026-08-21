
---
# Go Program Structure

## 1. The Core Mental Model

A Go application is organized around **modules, packages, and files**.

```text
Module
  ↓
Packages
  ↓
Go source files
  ↓
Compiler
  ↓
Executable
```

The important distinction is:

- **Module** = the entire Go project and its dependency boundary.
    
- **Package** = a unit of compiled Go code.
    
- **File** = source code belonging to a package.
    
- **Directory** = normally represents a package, but the two concepts are not identical.
    

Go deliberately makes these boundaries explicit.

This solves a problem that larger languages often hide behind IDEs, build systems, or conventions: **how source code is organized, compiled, and connected together.**

---

## 2. A Go Module

A module is the top-level unit of a Go project.

It is initialized with:

```bash
go mod init example.com/myapp
```

This creates:

```text
myapp/
├── go.mod
└── ...
```

The `go.mod` file identifies the module:

```go
module example.com/myapp

go 1.25
```

The module path becomes the basis for importing packages belonging to your project.

For example:

```text
example.com/myapp/internal/limiter
```

can be imported by another package in the same module.

### Why?

Go needs an explicit identity for the project so the compiler and module system know:

- which packages belong to this project
    
- how packages are imported
    
- where dependencies come from
    
- which module owns a package
    

### Failure mode

If you treat the project as merely "a folder containing `.go` files", you'll eventually run into confusing import and dependency behavior.

The module gives the project a formal identity.

---

# 3. `go.mod`

`go.mod` is the module's definition file.

A minimal example:

```go
module example.com/myapp

go 1.25
```

As dependencies are added, they appear here:

```go
module example.com/myapp

go 1.25

require (
    example.com/some-library v1.2.3
)
```

The module system uses this information to construct the dependency graph.

Conceptually:

```text
                 myapp
                   │
          ┌────────┴────────┐
          ↓                 ↓
     package A         package B
          │
          ↓
   external package
```

The important idea is:

> `go.mod` describes the module's identity and dependency requirements.

---

# 4. Packages

A package is Go's fundamental unit of code organization and compilation.

Example:

```text
internal/
└── limiter/
    ├── limiter.go
    └── middleware.go
```

Both files can belong to:

```go
package limiter
```

They therefore form one package.

You can think of the package as:

```text
limiter package
├── limiter.go
├── middleware.go
└── all identifiers defined by both files
```

The compiler doesn't treat these as two independent pieces of application logic.

It compiles the package as a unit.

### Why?

Packages solve the problem of organizing code into **explicit boundaries**.

Instead of having one giant namespace containing everything, Go lets you define related code inside packages.

---

# 5. Files Belong to Packages

A Go source file begins by declaring its package:

```go
package limiter
```

For example:

```go
package limiter

type Limiter struct {
    tokens int
}
```

Another file in the same directory can say:

```go
package limiter

func (l *Limiter) Allow() bool {
    return l.tokens > 0
}
```

Both files belong to the same package.

Therefore `Allow` can directly access `Limiter`.

You don't import one file from another.

You import **packages**, not individual files.

### Failure mode

This is not how Go works:

```go
import "./limiter.go"
```

Go doesn't treat source files as independently importable modules.

The unit of reuse is the **package**.

---

# 6. Importing a Package

Suppose your project contains:

```text
myapp/
├── go.mod
├── cmd/
│   └── server/
│       └── main.go
└── internal/
    └── limiter/
        └── limiter.go
```

The module is:

```go
module example.com/myapp
```

The limiter package therefore has the import path:

```text
example.com/myapp/internal/limiter
```

You can import it:

```go
package main

import "example.com/myapp/internal/limiter"
```

Then use exported identifiers from that package:

```go
limiter.New(...)
```

The import path is based on:

```text
module path
    +
package location
```

---

# 7. Module vs Package

This distinction is extremely important.

Given:

```text
myapp/
├── go.mod
├── cmd/
│   └── server/
│       └── main.go
└── internal/
    └── limiter/
        ├── limiter.go
        └── middleware.go
```

There is:

**One module**

```text
example.com/myapp
```

and potentially:

**Multiple packages**

```text
main
limiter
```

The module contains packages.

```text
Module
│
├── Package: main
│   └── cmd/server
│
└── Package: limiter
    └── internal/limiter
```

Don't confuse:

```text
module ≠ package
```

---

# 8. `package main`

Executable Go programs use:

```go
package main
```

For example:

```go
package main

func main() {
    println("Hello")
}
```

The special `main` package tells Go:

> This package is intended to produce an executable program.

The executable starts at:

```go
func main()
```

So the basic execution model is:

```text
package main
      ↓
func main()
      ↓
program execution
```

### Why?

The compiler needs to know which package represents the executable entry point.

Go makes this explicit instead of requiring a framework or runtime convention to discover your application's starting point.

---

# 9. `main()` Is the Program Entry Point

Inside the `main` package:

```go
func main() {
    ...
}
```

is the entry point of the executable.

For your server:

```go
package main

func main() {
    server := newServer()
    server.ListenAndServe()
}
```

Conceptually:

```text
Operating system
       ↓
executable
       ↓
Go runtime initialization
       ↓
main.main()
       ↓
your application
```

You don't call `main()` yourself.

The Go runtime/compiler-generated startup machinery eventually invokes it.

---

# 10. Exported vs Unexported Identifiers

Go has a deliberately simple visibility rule:

> **An identifier beginning with an uppercase letter is exported.**

Example:

```go
type Limiter struct {
    tokens int
}
```

`Limiter` is exported.

`tokens` is unexported.

Another package can do:

```go
limiter.NewLimiter()
```

if `NewLimiter` is exported.

But it cannot directly access:

```go
l.tokens
```

if `tokens` belongs to another package.

For example:

```go
package limiter

type Limiter struct {
    tokens int
}

func NewLimiter() *Limiter {
    return &Limiter{}
}
```

Another package can use:

```go
l := limiter.NewLimiter()
```

but not:

```go
l.tokens
```

### Why?

Go makes package boundaries explicit through the identifier itself.

There is no separate keyword such as:

```text
public
private
protected
```

The capitalization communicates visibility.

### Failure mode

If another package needs to use:

```go
limiter
```

but you define:

```go
type limiter struct {}
```

it isn't accessible from that package.

The problem isn't the import.

The identifier itself isn't exported.

---

# 11. Packages Are Boundaries

This is one of the most important ideas in Go.

Suppose your limiter package contains:

```go
package limiter

type Limiter struct {
    tokens       int
    userKey      string
    lastRefillAt int64
}

func (l *Limiter) Allow() bool {
    ...
}
```

Other packages don't need to know how the limiter internally works.

They only need:

```go
l.Allow()
```

So the package creates a boundary:

```text
outside package
      │
      │  exported API
      ↓
┌──────────────────────┐
│      limiter         │
│                      │
│  Limiter             │
│  Allow()             │
│                      │
│  tokens              │
│  userKey             │
│  lastRefillAt        │
└──────────────────────┘
```

The internal state remains inside the package.

This is Go's form of encapsulation.

---

# 12. `cmd/` Is Convention for Executables

A common Go project structure is:

```text
cmd/
├── server/
│   └── main.go
└── worker/
    └── main.go
```

Each directory can represent a separate executable package.

For example:

```text
cmd/server
```

contains:

```go
package main
```

and becomes the server executable.

Meanwhile:

```text
cmd/worker
```

can contain another:

```go
package main
```

and become a worker executable.

Conceptually:

```text
                Module
                  │
          ┌───────┴───────┐
          ↓               ↓
       server           worker
       binary            binary
```

### Why?

Large Go projects often contain multiple programs.

`cmd/` gives those executable entry points a predictable home.

### Important

`cmd/` is **not a special Go language feature**.

It is a widely used project-organization convention.

The compiler doesn't require you to have a `cmd/` directory.

---

# 13. `internal/` Is Different

`internal/` has special meaning to Go.

Example:

```text
internal/
└── limiter/
    └── limiter.go
```

A package inside `internal` can only be imported by code within the appropriate parent module/tree.

For:

```text
example.com/myapp/internal/limiter
```

code outside the allowed parent tree cannot import it.

This lets a project say:

> This package is implementation code for this project. It isn't part of the public API.

So your architecture:

```text
cmd/server
      │
      ↓
internal/limiter
```

communicates:

```text
server executable
      ↓
project-private implementation
```

This is a real package boundary enforced by the Go toolchain.

---

# 14. `cmd/` + `internal/`

An architecture in rate-limiter roughly like:

```text
project/
├── go.mod
│
├── cmd/
│   └── server/
│       ├── main.go
│       ├── config.go
│       └── routes.go
│
└── internal/
    └── limiter/
        ├── limiter.go
        └── middleware.go
```

The dependency direction is:

```text
                 executable
                     │
                     ↓
                  cmd/server
                     │
                     ↓
              internal/limiter
```

The executable is responsible for assembling the application.

The limiter package is responsible for limiter behavior.

That separation keeps the package reusable inside the project without turning it into a public library.

---

# 15. The Compiler's Mental Model

When you run:

```bash
go build
```

you aren't simply asking Go to execute a file.

Go resolves the program through its package structure.

Conceptually:

```text
                go.mod
                  │
                  ↓
              module graph
                  │
          ┌───────┴───────┐
          ↓               ↓
      package A       package B
          │               │
          └───────┬───────┘
                  ↓
              compilation
                  ↓
              executable
```

This is why Go code is organized around packages rather than individual source files.

---

# 16. The Mental Model to Keep

Remember these relationships:

```text
MODULE
  │
  │ contains
  ↓
PACKAGES
  │
  │ contain
  ↓
FILES
  │
  │ define
  ↓
IDENTIFIERS
```

And:

```text
module
  ↓
defines module identity

package
  ↓
defines code boundary

import
  ↓
connects packages

exported identifier
  ↓
creates package API

main package
  ↓
defines executable

main()
  ↓
defines execution entry point
```

---

# 17. Why Go Is Designed This Way

Go deliberately keeps these concepts explicit because **the compiler and toolchain need predictable boundaries**.

Instead of relying heavily on:

- class hierarchies
    
- reflection
    
- runtime discovery
    
- framework conventions
    
- complicated visibility systems
    

Go uses a small set of rules:

```text
module
package
import
uppercase identifier
package main
func main()
```

Those few rules are enough to build large programs with clear dependency boundaries.

The result is that when you look at a Go project, you can reason about its structure directly from the source tree.

---

# 18. Common Mistakes

### Mistake 1: Thinking a directory is always a package

Usually a directory contains one package, but the conceptual unit is the **package**, not the filesystem directory.

### Mistake 2: Importing files

Go imports packages:

```go
import "example.com/myapp/internal/limiter"
```

not:

```text
limiter.go
```

### Mistake 3: Confusing module and package

```text
module
    ↓
contains packages
```

They are not the same thing.

### Mistake 4: Thinking `cmd/` is mandatory

It isn't.

It's a convention for organizing executable packages.

### Mistake 5: Thinking capitalization is cosmetic

It isn't.

```go
Limiter
```

and:

```go
limiter
```

have different visibility across package boundaries.

---

# 19. One Final Mental Picture

When you start a Go project:

```bash
go mod init example.com/myapp
```

you establish the **module**.

Then you organize code into **packages**:

```text
example.com/myapp/
│
├── cmd/server/
│      └── package main
│
└── internal/limiter/
       └── package limiter
```

Packages expose selected identifiers:

```text
limiter
├── Limiter        ← exported
├── NewLimiter()   ← exported
├── Allow()        ← exported
│
├── tokens         ← private to package
├── userKey        ← private to package
└── lastRefillAt   ← private to package
```

And the executable connects everything:

```text
             OS
              │
              ↓
        compiled binary
              │
              ↓
        package main
              │
              ↓
          main()
              │
              ↓
      application setup
              │
              ↓
    internal packages
              │
              ↓
          behavior
```

**The core lesson:**

> **Go makes the boundaries of a program explicit: modules define the project, packages define code boundaries, imports connect packages, exported identifiers define package APIs, and `main` defines an executable.**

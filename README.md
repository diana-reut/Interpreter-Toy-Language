# ⚙️ Toy Language Interpreter (Java)

[![Language](https://img.shields.io/badge/Language-Java-007396.svg?logo=java)](https://www.java.com/)
[![Type Checking](https://img.shields.io/badge/Type%20Checker-Implemented-blue.svg)](https://shields.io)

This project implements a multi-threaded interpreter for a simplified imperative language ("Toy Language"). It covers advanced concepts required for safe execution, including concurrency, explicit memory allocation, and robust pre-execution type checking.

---

## ✨ Key Language Features

### Data Types & Expressions

| Category | Operators/Syntax | Types Handled | Notes |
| :--- | :--- | :--- | :--- |
| **Primitives** | N/A | `int`, `bool`, `string` | Standard primitive types. |
| **References** | N/A | `Ref(T)` | Explicit pointer type for heap memory management. |
| **Arithmetic** | `+`, `-`, `*`, `/` | `int` $\rightarrow$ `int` | Standard integer arithmetic. |
| **Relational** | `<`, `<=`, `==`, `!=`, `>=`, `>` | `int` $\rightarrow$ `bool` | Comparison operations. |
| **Logical** | `&&`, `\|\|` | `bool` $\rightarrow$ `bool` | Boolean logic for conditions. |

### Statements & Control Flow

| Feature | Syntax Example | Description |
| :--- | :--- | :--- |
| **Declaration** | `int v;` | Declares a variable in the Symbol Table. |
| **Assignment** | `v = 10;` | Updates the value of a variable. |
| **Heap Allocation** | `new(v, 20)` | Allocates memory on the Heap and updates the reference variable `v`. |
| **Heap Write** | `wH(v, 30)` | Writes a new value to the address pointed to by reference `v`. |
| **Heap Read** | `rH(v)` | Reads the value stored at the address pointed to by `v`. |
| **Conditional** | `If(cond) Then(...) Else(...)` | Standard conditional execution. |
| **Looping** | `while(cond) {...}` | Executes body while the condition is true. |
| **Concurrency** | `fork(...)` | Creates a new program state (thread) that inherits the Symbol Table and shares the Heap. |
| **File I/O** | `openRFile`, `readFile`, `closeRFile` | Statements for handling file streams. |

---

## 🏗️ Project Structure

The project is structured following a Model-View-Controller (MVC) pattern for clear separation of concerns:

* `controller/`: Manages program execution flow and thread scheduling.
* `garbageCollector/`: Contains the `GarbageCollector` utility for managing the `Heap`.
* `model/`: Holds the core language components: `expression`, `statement`, `state` (like `Heap`, `SymbolTable`), `type`, and `value`.
* `repository/`: Handles `ProgramState` storage and logging to the output file.
* `view/`: Provides the `TextMenu` command-line interface and program definitions (`Interpreter.java`).

---

## ▶️ How to Run

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/diana-reut/Interpreter-Toy-Language
    ```
2.  **Open in IDE:** Load the project into your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
3.  **Run `view.Interpreter`:** Execute the `main` method in `view/Interpreter.java`.
4.  **Follow Prompts:** The application will prompt you for the log file name, then display the execution menu.

### ⚠️ Type Check Validation

All example programs are subjected to a mandatory static type check before initialization.

* If a program is **Type-Safe**, it will be successfully added to the menu and can be executed.
* If a program **Fails** the type check, an error message detailing the type mismatch will be printed to the console, and the example will be omitted from the menu.

---

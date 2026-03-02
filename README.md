# Toy Language Interpreter

A feature-rich, multi-threaded interpreter for a custom-defined programming language. This project implements a complete execution pipeline, from static type checking to concurrent runtime execution with advanced synchronization primitives.

## 🌟 Key Features

### 🧵 Concurrency & Synchronization
This implementation goes beyond basic execution, offering a full suite of thread-safe synchronization mechanisms:
* **Multi-threading**: Create new execution threads using the `fork()` statement.
* **Barriers**: Support for `NewBarrier` and `AwaitBarrier` to synchronize multiple threads.
* **Latches**: Count-down mechanisms using `NewLatch`, `CountDown`, and `AwaitLatch`.
* **Semaphores**: Resource management with `CreateSemaphore`, `Acquire`, and `Release`.
* **Locks**: Critical section management via `Lock` and `Unlock` statements.

### 🧠 Language Logic & Memory
* **Procedures**: Function-like behavior with `CallStmt`, `ReturnStmt`, and a dedicated `ProcTable`.
* **Dynamic Memory (Heap)**: Managed heap with `new`, `readHeap`, and `writeHeap` operations.
* **Garbage Collector**: An automated mechanism that clears unreferenced addresses from the Heap.
* **Flow Control**: Full support for `If`, `While`, `For`, `Switch`, `Sleep`, `Wait`, and `RepeatUntil`.
* **Static Type Checker**: Ensures program validity before execution begins.

### 🖥 User Interfaces
* **JavaFX GUI**: A sophisticated visual debugger that displays:
    * The **Execution Stack**, **Symbol Table**, and **Heap** in real-time.
    * Tables for **Barriers**, **Latches**, and **Semaphores**.
    * A list of active **Program States** (threads).
* **Text Menu**: A classic CLI interface for quick program execution and testing.

---

## 📂 Project Structure

The project follows a strict layered architecture for clean separation of concerns:

| Package | Responsibility |
| :--- | :--- |
| **`model`** | Definitions for `Statements`, `Expressions`, `Types`, and `Values`. |
| **`programState`** | Core data structures: `ExeStack`, `SymTable`, `Heap`, and `Sync Tables`. |
| **`controller`** | Orchestrates `oneStep` and `allStep` execution using an `ExecutorService`. |
| **`repository`** | Manages program states and logs execution history to `execution.txt`. |
| **`gui`** | JavaFX application and `.fxml` controllers for the visual interface. |
| **`view`** | Command-line interface and the `TypeChecker` utility. |

---

## 🚀 Getting Started

### Prerequisites
* **Java JDK 11** or higher.
* **JavaFX SDK** (properly configured in your IDE's library settings).

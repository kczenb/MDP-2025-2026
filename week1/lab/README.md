# Kotlin for Android Development 

This is a shot introduction to Kotlin. The goal is to help you become familiar with the language and its concepts, especially the main features you will encounter when developing Android applications. I will do my best to keep adding new concepts and examples in the coming few weeks. 

## Table of Contents
1. [Basics](#1-basics)
2. [Control Flow](#2-control-flow)
3. [Functions](#3-functions)
4. [Collections & Functional Programming](#4-collections--functional-programming)
5. [Object-Oriented Programming](#5-object-oriented-programming)
6. [Special Kotlin Classes](#6-special-kotlin-classes)
7. [Null Safety & Type System](#7-null-safety--type-system)
8. [Extensions](#8-extensions)
9. [Packages & Imports](#10-packages--imports)

---

## 1. Basics

### val vs var
```kotlin
val name = "Kotlin"  // Read-only (immutable)
var age = 25         // Mutable (can be changed)

age = 26            //  Allowed
// name = "Kotlin"    //  Compilation error
```

[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG5cdHZhbCBuYW1lID0gXCJLb3RsaW5cIiAgLy8gUmVhZC1vbmx5IChpbW11dGFibGUpXG5cdHZhciBhZ2UgPSAyNSAgICAgICAgIC8vIE11dGFibGUgKGNhbiBiZSBjaGFuZ2VkKVxuXG5cdGFnZSA9IDI2IFxuICAgIHByaW50bG4oXCJNeSBuYW1lIGlzICRuYW1lIGFuZCBJIGFtICRhZ2VcIilcbn0ifQ==)

### Data Types
```kotlin
val number: Int = 10
val pi: Double = 3.14
val isStudent: Boolean = true
val name: String = "Android"
val character: Char = 'A'
val longNumber: Long = 100L
```

[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgdmFsIG51bWJlcjogSW50ID0gMTBcbiAgICB2YWwgcGk6IERvdWJsZSA9IDMuMTRcbiAgICB2YWwgaXNTdHVkZW50OiBCb29sZWFuID0gdHJ1ZVxuICAgIHZhbCBuYW1lOiBTdHJpbmcgPSBcIkFuZHJvaWRcIlxuICAgIHZhbCBjaGFyYWN0ZXI6IENoYXIgPSAnQSdcbiAgICB2YWwgbG9uZ051bWJlcjogTG9uZyA9IDEwMExcblxuICAgIHByaW50bG4oXCJJbnRlZ2VyOiAkbnVtYmVyXCIpXG4gICAgcHJpbnRsbihcIkRvdWJsZTogJHBpXCIpXG4gICAgcHJpbnRsbihcIkJvb2xlYW46ICRpc1N0dWRlbnRcIilcbiAgICBwcmludGxuKFwiQ2hhcjogJGNoYXJhY3RlclwiKVxuICAgIHByaW50bG4oXCJMb25nOiAkbG9uZ051bWJlclwiKVxufSJ9)

### String Interpolation & Templates
```kotlin
val name = "Alice"
val age = 25
println("Hello, $name! You are $age years old.")
println("Next year you'll be ${age + 1}")
```

[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG5cdHZhbCBuYW1lID0gXCJBbGljZVwiXG5cdHZhbCBhZ2UgPSAyNVxuXHRwcmludGxuKFwiSGVsbG8sICRuYW1lISBZb3UgYXJlICRhZ2UgeWVhcnMgb2xkLlwiKVxuXHRwcmludGxuKFwiTmV4dCB5ZWFyIHlvdSdsbCBiZSAke2FnZSArIDF9XCIpXG59In0=)

### Null Safety
Kotlin's null safety is a feature designed to eliminate the risk of null pointer exceptions.
- ? (Nullable type): When appended to a type, it indicates that a variable can hold either a value or null

#### Safe Call Operator (?.)
Safely accesses properties or methods on nullable objects. Returns null if the object is null instead of throwing an exception.

#### Elvis Operator (?:)
Provides a default value when the expression on its left is null.

#### Non-null Assertion (!!)
Forces the compiler to treat a nullable type as non-nullable. Use with caution as it throws a NullPointerException if the value is null.


```kotlin
var nullableString: String? = null  //  Can be null
var nonNullableString: String = ""  //  Cannot be null

// Safe call
val length = nullableString?.length  // Returns null if nullableString is null

// Elvis operator
val length = nullableString?.length ?: 0  // Default value if null

// Non-null assertion (use carefully!)
val length = nullableString!!.length  // Throws exception if null
```

[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgdmFyIG51bGxhYmxlU3RyaW5nOiBTdHJpbmc/ID0gbnVsbCAgLy8gIENhbiBiZSBudWxsXG5cdHZhciBub25OdWxsYWJsZVN0cmluZzogU3RyaW5nID0gXCJcIiAgLy8gIENhbm5vdCBiZSBudWxsXG5cblx0Ly8gU2FmZSBjYWxsXG5cdHZhbCBsZW5ndGgxID0gbnVsbGFibGVTdHJpbmc/Lmxlbmd0aCAgLy8gUmV0dXJucyBudWxsIGlmIG51bGxhYmxlU3RyaW5nIGlzIG51bGxcbiAgICBwcmludGxuKGxlbmd0aDEpXG5cblx0Ly8gRWx2aXMgb3BlcmF0b3Jcblx0dmFsIGxlbmd0aDIgPSBudWxsYWJsZVN0cmluZz8ubGVuZ3RoID86IDAgIC8vIERlZmF1bHQgdmFsdWUgaWYgbnVsbFxuICAgIHByaW50bG4obGVuZ3RoMilcbiAgICBcblx0Ly8gTm9uLW51bGwgYXNzZXJ0aW9uICh1c2UgY2FyZWZ1bGx5ISlcblx0dmFsIGxlbmd0aDMgPSBudWxsYWJsZVN0cmluZyEhLmxlbmd0aCAgLy8gVGhyb3dzIGV4Y2VwdGlvbiBpZiBudWxsXG4gICAgXG59In0=)
---


## 2. Control Flow

### if/else as Expressions
```kotlin
val result = if (score > 50) {
    "Pass"
} else {
    "Fail"
}
// result is "Pass" or "Fail"
```

### when Expression (Better than switch)

- Works with any data type (strings, ranges, custom objects)
- Multiple conditions in one branch (```0, 1 -> println("Binary")```)
- Return value
- Complex conditions without argument (```n.isOdd() -> println("n is odd")```)


```kotlin
var score = 10
val grade = when (score) {
    in 90..100 -> "A"
    in 80..89 -> "B"
    in 70..79 -> "C"
    else -> "F"
}

// Also works with types
// This example refers to Android, it will not work if you run it
when (view) {
    is Button -> println("Button clicked")
    is TextView -> println("Text view")
    else -> println("Unknown view")
}
```

[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgdmFyIHNjb3JlID0gMTBcbiAgICB2YWwgZ3JhZGUgPSB3aGVuIChzY29yZSkge1xuICAgIGluIDkwLi4xMDAgLT4gXCJBXCJcbiAgICBpbiA4MC4uODkgLT4gXCJCXCJcbiAgICBpbiA3MC4uNzkgLT4gXCJDXCJcbiAgICBlbHNlIC0+IFwiRlwiXG59XG5cbi8vIEFsc28gd29ya3Mgd2l0aCB0eXBlc1xuLy8gVGhpcyBleGFtcGxlIHJlZmVycyB0byBBbmRyb2lkLCBpdCB3aWxsIG5vdCB3b3JrIGlmIHlvdSBydW4gaXRcbndoZW4gKHZpZXcpIHtcbiAgICBpcyBCdXR0b24gLT4gcHJpbnRsbihcIkJ1dHRvbiBjbGlja2VkXCIpXG4gICAgaXMgVGV4dFZpZXcgLT4gcHJpbnRsbihcIlRleHQgdmlld1wiKVxuICAgIGVsc2UgLT4gcHJpbnRsbihcIlVua25vd24gdmlld1wiKVxufVxuICAgIFxufSJ9)

### Ranges
```kotlin
val range1 = 1..10        // 1 to 10 inclusive
val range2 = 1 until 10   // 1 to 9
val range3 = 10 downTo 1  // 10 to 1
val range4 = 1..10 step 2 // 1, 3, 5, 7, 9

if (5 in range1) {
    println("5 is in range")
}
```
[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgdmFsIHJhbmdlMSA9IDEuLjEwICAgICAgICAvLyAxIHRvIDEwIGluY2x1c2l2ZVxuXHR2YWwgcmFuZ2UyID0gMSB1bnRpbCAxMCAgIC8vIDEgdG8gOVxuXHR2YWwgcmFuZ2UzID0gMTAgZG93blRvIDEgIC8vIDEwIHRvIDFcblx0dmFsIHJhbmdlNCA9IDEuLjEwIHN0ZXAgMiAvLyAxLCAzLCA1LCA3LCA5XG5cblx0aWYgKDUgaW4gcmFuZ2UxKSB7XG4gICAgXHRwcmludGxuKFwiNSBpcyBpbiByYW5nZVwiKVxuXHR9XG4gICAgXG59In0=)

### Loops
```kotlin
// For loop
for (i in 1..5) {
    println(i)
}

val names = listOf("Alice", "Bob", "Charlie")
for (name in names) {
    println(name)
}

// While loop
var count = 0
while (count < 5) {
    println(count)
    count++
}

// Do-while loop
do {
    println(count)
    count--
} while (count > 0)
```

[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgICAgZm9yIChpIGluIDEuLjUpIHtcbiAgICAgICAgcHJpbnRsbihpKVxuICAgIH1cblxuICAgIHZhbCBuYW1lcyA9IGxpc3RPZihcIkFsaWNlXCIsIFwiQm9iXCIsIFwiQ2hhcmxpZVwiKVxuICAgIGZvciAobmFtZSBpbiBuYW1lcykge1xuICAgICAgICBwcmludGxuKG5hbWUpXG4gICAgfVxuXG4gICAgLy8gV2hpbGUgbG9vcFxuICAgIHZhciBjb3VudCA9IDBcbiAgICB3aGlsZSAoY291bnQgPCA1KSB7XG4gICAgICAgIHByaW50bG4oY291bnQpXG4gICAgICAgIGNvdW50KytcbiAgICB9XG5cbiAgICAvLyBEby13aGlsZSBsb29wXG4gICAgZG8ge1xuICAgICAgICBwcmludGxuKGNvdW50KVxuICAgICAgICBjb3VudC0tXG4gICAgfSB3aGlsZSAoY291bnQgPiAwKSAgIFxufSJ9)



---

## 3. Functions

### Defining Functions
```kotlin
fun greet(name: String): String {
    return "Hello, $name!"
}

// Single-expression function
fun square(x: Int): Int = x * x

// Unit return type (like void)
fun printMessage(message: String): Unit {
    println(message)
}
```

[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgIGZ1biBncmVldChuYW1lOiBTdHJpbmcpOiBTdHJpbmcge1xuICAgIHJldHVybiBcIkhlbGxvLCAkbmFtZSFcIlxufVxuXG4gICAgLy8gU2luZ2xlLWV4cHJlc3Npb24gZnVuY3Rpb25cbiAgICBmdW4gc3F1YXJlKHg6IEludCk6IEludCA9IHggKiB4XG5cbiAgICAvLyBVbml0IHJldHVybiB0eXBlIChsaWtlIHZvaWQpXG4gICAgZnVuIHByaW50TWVzc2FnZShtZXNzYWdlOiBTdHJpbmcpOiBVbml0e1xuICAgICAgICBwcmludGxuKG1lc3NhZ2UpXG4gICAgfVxuICAgIFxuICAgIHByaW50bG4oZ3JlZXQoXCJOYWJpbFwiKSlcbiAgICBwcmludGxuKHNxdWFyZSgyKSlcblx0cHJpbnRNZXNzYWdlKFwiSGVsbG8gV29ybGQhXCIpXG59In0=)

### Default & Named Parameters
```kotlin
fun createUser(
    name: String,
    age: Int = 18,           // Default value
    isVerified: Boolean = false
) {
    // Function body
}

// Usage
createUser("Alice")                          // age=18, isVerified=false
createUser("Bob", 25)                        // isVerified=false
createUser("Charlie", isVerified = true)     // age=18
createUser(name = "Diana", age = 30, isVerified = true)
```

### Higher-Order Functions & Lambdas
- Higher-order functions are functions that can take other functions as parameters or return functions as results.
- Lambdas are anonymous functions (functions without a name) that can be treated as values.



```kotlin
// Function as parameter
fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
    return operation(x, y)
}

// Lambda usage
val result = calculate(10, 5) { a, b -> a + b }
val result = calculate(10, 5) { a, b -> a * b }

// Common in Android: click listeners
// This is an Android example, it will not run in the playground
button.setOnClickListener { view ->
    // Handle click
    showMessage("Button clicked!")
}
```
[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgZnVuIGNhbGN1bGF0ZSh4OiBJbnQsIHk6IEludCwgb3BlcmF0aW9uOiAoSW50LCBJbnQpIC0+IEludCk6IEludCB7XG4gICAgcmV0dXJuIG9wZXJhdGlvbih4LCB5KVxufVxuXG5cdC8vIExhbWJkYSB1c2FnZVxuXHR2YWwgc3VtID0gY2FsY3VsYXRlKDEwLCA1KSB7IGEsIGIgLT4gYSArIGIgfVxuICAgIHByaW50bG4oc3VtKVxuICAgIFxuICAgIHZhbCBtdWx0aXBsaWNhdGlvbiA9IGNhbGN1bGF0ZSgxMCwgNSkgeyBhLCBiIC0+IGEgKiBiIH1cbiAgICBwcmludGxuKG11bHRpcGxpY2F0aW9uKVxufSJ9)

**Why**: Functions are used everywhere in Android - callbacks, click listeners, coroutines, etc.

---

## 4. Collections & Functional Programming

### List, MutableList, Set, Map
```kotlin
// Read-only list
val fruits = listOf("Apple", "Banana", "Orange")

// Mutable list
val mutableFruits = mutableListOf("Apple", "Banana")
mutableFruits.add("Orange")

// Set (unique elements)
val uniqueNumbers = setOf(1, 2, 3, 3, 2)  // [1, 2, 3]

// Map
val ages = mapOf("Alice" to 25, "Bob" to 30)
```

[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgdmFsIGZydWl0cyA9IGxpc3RPZihcIkFwcGxlXCIsIFwiQmFuYW5hXCIsIFwiT3JhbmdlXCIpXG4gICAgcHJpbnRsbihmcnVpdHMpXG5cblxuICAgIC8vIE11dGFibGUgbGlzdFxuICAgIHZhbCBtdXRhYmxlRnJ1aXRzID0gbXV0YWJsZUxpc3RPZihcIkFwcGxlXCIsIFwiQmFuYW5hXCIpXG4gICAgbXV0YWJsZUZydWl0cy5hZGQoXCJPcmFuZ2VcIilcblxuICAgIC8vIFNldCAodW5pcXVlIGVsZW1lbnRzKVxuICAgIHZhbCB1bmlxdWVOdW1iZXJzID0gc2V0T2YoMSwgMiwgMywgMywgMikgIC8vIFsxLCAyLCAzXVxuXG4gICAgLy8gTWFwXG4gICAgdmFsIGFnZXMgPSBtYXBPZihcIkFsaWNlXCIgdG8gMjUsIFwiQm9iXCIgdG8gMzApXG59In0=)

### Functional Operations
```kotlin
val numbers = listOf(1, 2, 3, 4, 5)

// map - transform each element
val squared = numbers.map { it * it }  // [1, 4, 9, 16, 25]

// filter - keep elements that match condition
val even = numbers.filter { it % 2 == 0 }  // [2, 4]

// forEach - perform action on each element
numbers.forEach { println(it) }

// reduce - combine elements
val sum = numbers.reduce { acc, num -> acc + num }  // 15

// find - first element matching condition
val firstEven = numbers.find { it % 2 == 0 }  // 2
```

**Why**: Android often requires transforming data (e.g., filtering a list of users, mapping API responses to UI models).

---

## 5. Object-Oriented Programming

### Classes & Objects
```kotlin
class Person(val name: String, var age: Int) {
    fun speak() {
        println("Hello, I'm $name")
    }
}

val person = Person("Alice", 25)
person.speak()
```
Because name and age are declared with ```val``` and ```var```, Kotlin automatically makes them properties of the class. If not, they would be only constructor parameters.

### Constructors
- Sometimes you want multiple ways to construct an object.
- The init block runs immediately after the primary constructor. It is used to put initialisation logic (e.g., validation, logging, setup).
```kotlin
// Primary constructor
class User(val name: String, val email: String) {
    
    // Secondary constructor
    constructor(name: String) : this(name, "$name@example.com")
    
    init {
        println("User created: $name")
    }
}
```

### Properties with Custom Getters/Setters
```kotlin
class Rectangle(val width: Int, val height: Int) {
    val area: Int
        get() = width * height  // Computed property
        
    var text: String = ""
        set(value) {
            field = value.trim()
        }
}
```

### Inheritance
```kotlin
open class Animal(val name: String) {
    open fun makeSound() {
        println("Some sound")
    }
}

class Dog(name: String) : Animal(name) {
    override fun makeSound() {
        println("Woof!")
    }

    val myDog = Dog("Buddy")
    myDog.makeSound()

    val myAnimal: Animal = myDog
    myAnimal.makeSound()
}
```

[![Run on Kotlin Playground](https://img.shields.io/badge/Run-Kotlin_Playground-blue?logo=kotlin)](https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMi4yLjIwIiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiLyoqXG4gKiBZb3UgY2FuIGVkaXQsIHJ1biwgYW5kIHNoYXJlIHRoaXMgY29kZS5cbiAqIHBsYXkua290bGlubGFuZy5vcmdcbiAqL1xuZnVuIG1haW4oKSB7XG4gICAgb3BlbiBjbGFzcyBBbmltYWwodmFsIG5hbWU6IFN0cmluZykge1xuICAgIG9wZW4gZnVuIG1ha2VTb3VuZCgpIHtcbiAgICAgICAgcHJpbnRsbihcIlNvbWUgc291bmRcIilcbiAgICB9XG59XG5cbmNsYXNzIERvZyhuYW1lOiBTdHJpbmcpIDogQW5pbWFsKG5hbWUpIHtcbiAgICBvdmVycmlkZSBmdW4gbWFrZVNvdW5kKCkge1xuICAgICAgICBwcmludGxuKFwiV29vZiFcIilcbiAgICB9XG59XG5cbnZhbCBteURvZyA9IERvZyhcIkJ1ZGR5XCIpXG5teURvZy5tYWtlU291bmQoKVxuXG52YWwgbXlBbmltYWw6IEFuaW1hbCA9IG15RG9nXG4gICAgbXlBbmltYWwubWFrZVNvdW5kKClcbn0ifQ==)

### Abstract Classes & Interfaces
```kotlin
interface Clickable {
    fun onClick()
}

abstract class View {
    abstract fun draw()
}

class Button : View(), Clickable {
    override fun draw() { /* Draw button */ }
    override fun onClick() { /* Handle click */ }
}
```

**Why**: Android apps are structured around models and components (Activities, Fragments, ViewModels).

---

## 6. Special Kotlin Classes


### Data Classes
Data classes are a concise way to create classes that primarily hold data. They automatically generate useful methods, reducing boilerplate code.

```kotlin
data class User(
    val id: Int,
    val name: String,
    val email: String
)

// Auto-generated: toString(), equals(), hashCode(), copy()
val user = User(1, "Alice", "alice@example.com")
val userCopy = user.copy(name = "Alice Smith")
```

### Singletons with Object
```kotlin
object NetworkManager {
    fun makeRequest(url: String) {
        // Network call
    }
}

// Usage - no need to create instance
NetworkManager.makeRequest("https://api.example.com")
```

### Companion Objects
- This is Kotlin’s replacement for Java’s static members.
- Companion objects are used to group  behavior/values that belong to the class rather than to an individual instance.


```kotlin
class DatabaseHelper {
    companion object {
        const val DATABASE_NAME = "app.db"
        
        fun create(): DatabaseHelper {
            return DatabaseHelper()
        }
    }
}

// Usage like static methods
val dbName = DatabaseHelper.DATABASE_NAME
val dbHelper = DatabaseHelper.create()
```

### Enums & Sealed Classes
```kotlin
// Enum for constants
enum class Color { RED, GREEN, BLUE }

// Sealed class for representing states
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

// Usage in UI state handling
when (result) {
    is Result.Success -> showData(result.data)
    is Result.Error -> showError(result.message)
    Result.Loading -> showLoading()
}
```

**Why**: Used heavily in Android architecture (state handling, data models, utility classes).

---

## 7. Null Safety & Type System

### Nullable Types
```kotlin
var nullableName: String? = null
var nonNullableName: String = "Kotlin"  // Cannot be null
```

### Safe Calls & Elvis Operator
```kotlin
val length = nullableName?.length  // Safe call - returns null if nullableName is null

val length = nullableName?.length ?: 0  // Elvis operator - default value

val length = nullableName!!.length  // Non-null assertion - throws exception if null
```

**Why**: Handling nulls correctly avoids app crashes - crucial for stable Android apps.

---

## 8. Extensions

### Extension Functions
```kotlin
// Add function to existing class
fun String.addExclamation(): String {
    return "$this!"
}

// Usage
val greeting = "Hello".addExclamation()  // "Hello!"

// Android example: View extensions
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}
```

### Extension Properties
```kotlin
val String.isEmail: Boolean
    get() = matches(Regex("^\\S+@\\S+\\.\\S+$"))

// Usage
if ("test@example.com".isEmail) {
    println("Valid email")
}
```

**Why**: Common in Android for utility/helper functions without inheritance.


---

## 9. Packages & Imports

### Organizing Code
```kotlin
// File: com/example/app/models/User.kt
package com.example.app.models

data class User(val name: String, val age: Int)

// File: com/example/app/utils/StringExtensions.kt  
package com.example.app.utils

fun String.capitalizeWords(): String { ... }
```

### Importing
```kotlin
// Import specific class
import com.example.app.models.User

// Import all from package
import com.example.app.utils.*

// Import with alias
import com.example.app.models.User as AppUser
```

**Why**: Android projects have multiple packages (activities, models, utils, etc.) for better organization.

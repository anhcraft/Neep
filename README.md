# Neep
Neep is a simple configuration format.

[![Release](https://travis-ci.org/anhcraft/Neep.svg?branch=master)](https://travis-ci.org/anhcraft/Neep)
[![](https://jitpack.io/v/anhcraft/Neep.svg)](https://jitpack.io/#anhcraft/Neep)<br>

## Data types
- Comment
- Expression
- Primitive
  + Boolean
  + Integer
  + Long
  + Double
  + String
- Container
  + List
  + Section

## Example:

```
key value
another_key "value" # with comment

int 1
double 1.0
long 99999999999
bool true
other_bool false

a_section {
  a_list [ "a" "b" "c" ]
  another_list [
    "a"
    b
    true
    false
  ]
}
```

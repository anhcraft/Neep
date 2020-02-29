# Neep
Neep is a simple configuration format.

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
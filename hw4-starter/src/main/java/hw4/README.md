# Discussion

**Document all error conditions you determined and why they are error
 conditions. Do this by including the inputs that you used to test your
  program and what error conditions they exposed:**

# Arithmetic Error
It is possible for the user to enter expressions that do not yield valid
results, such as division by zero. I was able to catch this error by wrapping
any arithmetic operation with user-provided input with a try-catch statement
for ArithmeticExceptions. THis could be triggered by `1 0 /`.

# Not Enough Arguments
The user is allowed to perform an operation when there are not two operands present on the stack.
When this occurs, we do not want any of the currently present elements to be removed off the stack.
We may trigger such an error by entering `1 +`, in which case the stack should be `[1]`. This
error was resolved by wrapping any pop statements in a try-catch block and storing the results.
Any results that were not null could be pushed back onto the stack in the reverse order from which
they were popped.
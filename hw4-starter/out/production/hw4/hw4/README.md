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
The user is allowed to perform a operation when there are not two operands present on the stack.
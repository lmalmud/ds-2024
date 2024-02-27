package hw4;

import exceptions.EmptyException;
import java.util.Scanner;

/**
 * A program for an RPN calculator that uses a stack.
 */
public final class Calc {

  /**
   * Performs an operation according to postfix specification on the given stack.
   * @param s the stack to be operated on
   * @param operation a string representing the operation to be performed
   *                  {+, -, /, *, %}
   * @throws ArithmeticException if division by zero, etc
   */
  public static void performOperation(Stack<Integer> s, String operation) {
    Integer op1 = null; // initialize both values to null
    Integer op2 = null;
    try {
      op1 = getInt(s); // attempt to retrieve two integers
      op2 = getInt(s);

      s.push(calculateResult(operation, op1, op2)); // push the result

    } catch (EmptyException empty) { // there were either 0 or 1 operands present
      System.out.println("ERROR: not enough operands present");
      if (op2 != null) {
        s.push(op2);
      }
      if (op1 != null) {
        s.push(op1);
      }
    } catch (ArithmeticException arithmetic) {
      System.out.println("ERROR: arithmetic error");
    }
  }

  /**
   * Performs the operation specified by the string.
   * @param operation string representing the operation
   * @param op1 first operand
   * @param op2 second operand
   * @return the result of op1 operation op2
   * @throws ArithmeticException if division by zero, etc
   */
  public static Integer calculateResult(String operation, Integer op1, Integer op2) throws ArithmeticException {
    try {
      switch (operation) { // return the result of the operation specified
        case "+":
          return op1 + op2;
        case "-":
          return op2 - op1;
        case "*":
          return op1 * op2;
        case "/":
          return op2 / op1;
        default:
          return op2 % op1; // there are only five possible options
      }
    } catch (ArithmeticException arithmetic) {
      throw new ArithmeticException();
    }
  }

  /**
   * Retrieves and removes the top element of the stack.
   * @param s the stack to be modified
   * @return the popped element
   * @throws EmptyException if there are no items on the stack
   */
  public static Integer getInt(Stack<Integer> s) throws EmptyException {
    Integer element = s.top(); // retrieves element
    s.pop(); // removes element
    return element;
  }

  /**
   * Pushes a valid integer onto the stack.
   * @param s the stack to be modified
   * @param entered the string to parse
   */
  public static void pushInt(Stack<Integer> s, String entered) {
    try { // case push onto the stack
      Integer x = Integer.parseInt(entered);
      s.push(x);

    } catch (NumberFormatException nfe) {
      switch (entered) {
        case "+":
        case "-":
        case "*":
        case "/":
        case "%":
          performOperation(s, entered);
          break;
        default: // if not integer, special operation, or operation, invalid token
          System.out.println("ERROR: invalid token");
      }
    }
  }

  /**
   * The main function.
   *
   * @param args Not used.
   */
  public static void main(String[] args) {
    Scanner myScanner = new Scanner(System.in);
    Stack<Integer> myStack = new LinkedStack<>();

    String entered = myScanner.next();
    while (!"!".equals(entered)) {
      switch (entered) {
        case ".": // print top
          System.out.println(myStack.top());
          break;
        case "?": // print entire stack
          System.out.println(myStack);
          break;
        default: // either operation, integer, or invalid
          pushInt(myStack, entered);
      }
      entered = myScanner.next();
    }
    myScanner.close();
  }
}

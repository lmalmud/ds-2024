package hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hw1.Task.twoSum;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TaskTest {

  @Test
  @DisplayName("twoSum works for input {2, 7, 11, 15} and target 9")
  public void worksForProvidedSample() {
    int[] input = new int[]{2, 7, 11, 15};
    int target = 13;
    int[] expected = new int[]{0, 2};
    int[] actual = twoSum(input, target);
    assertArrayEquals(expected, actual);
  }

  // You are not expected to write tests for this homework, but feel free to do so!
  @Test
  @DisplayName("twoSum works for input {1, 2} and target 3")
  public void trivialSample_case1() {
    int[] input = new int[]{1, 2};
    int target = 3;
    int[] expected = new int[]{0, 1};
    int[] actual = twoSum(input, target);
    assertArrayEquals(expected, actual);
  }

  @Test
  @DisplayName("twoSum works for input {0, 1} and target 1")
  public void trivialSample_case2() {
    int[] input = new int[]{0, 1};
    int target = 1;
    int[] expected = new int[]{0, 1};
    int[] actual = twoSum(input, target);
    assertArrayEquals(expected, actual);
  }

  @Test
  @DisplayName("twoSum works for input {1, 1} and target 2")
  public void trivialSample_case3() {
    int[] input = new int[]{1, 1};
    int target = 2;
    int[] expected = new int[]{0, 1};
    int[] actual = twoSum(input, target);
    assertArrayEquals(expected, actual);
  }

}

package hw1;

import java.util.Arrays;

public class Task {

  /**
   * Solves the two-sum problem.
   *
   * @param nums an array of integers (at least two), sorted in ascending order.
   * @param target an integer value.
   *        There will be exactly one pair of values on nums that sum to target.
   * @return an array of integers with exactly two elements:
   *       indices of the two numbers in the array nums such that they add up to the target.
   */
  public static int[] twoSum(int[] nums, int target) {
    int sum;
    int left = 0;
    int right = nums.length - 1;
    boolean found = false;
    while (left < right && !found) {
      sum = nums[left] + nums[right];
      if (sum == target) {
        found = true;
      } else {
        if (sum < target) {
          left++;
        } else {
          right--;
        }
      }
    }
    return new int[]{left, right};
  }

  /**
   * Execution starts here.
   * @param args command-line arguments (not used here).
   */
  public static void main(String[] args) {
    int[] input = new int[]{2, 7, 11, 15};
    int target = 13;
    int[] result = twoSum(input, target);
    System.out.println(Arrays.toString(result));
  }
}
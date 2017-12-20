package leetcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//[2,7,9,3]  ,target 16 --> [1,2]

/**
 * 
* @ClassName: TwoSumMain 
* @Description: Given an array of integers,return indices of the two numbers such that they add
* up  to a specific target.
* You may assume that each input would have exactly one solution,and you may not use the same element twice.
* Example:
* Given nums = [2, 7, 11, 15], target = 9,
*
* Because nums[0] + nums[1] = 2 + 7 = 9,
* return [0, 1].
* 这里复杂度：
* 时间复杂度：O(n),因为遍历整个数组1..n，对每个数组元素判断是否在哈希表中存在元素匹配值（o(1)）
* 空间复杂度：O(n)
* @author lcy
* @date 2017年12月18日 上午10:08:42 
*  
 */
public class TwoSumMain {
	
	 public static int[] stringToIntegerArray(String input) {
	        input = input.trim();
	        input = input.substring(1, input.length() - 1);
	        if (input.length() == 0) {
	          return new int[0];
	        }
	    
	        String[] parts = input.split(",");
	        int[] output = new int[parts.length];
	        for(int index = 0; index < parts.length; index++) {
	            String part = parts[index].trim();
	            output[index] = Integer.parseInt(part);
	        }
	        return output;
	    }
	    
	    public static String integerArrayToString(int[] nums, int length) {
	        if (length == 0) {
	            return "[]";
	        }
	    
	        String result = "";
	        for(int index = 0; index < length; index++) {
	            int number = nums[index];
	            result += Integer.toString(number) + ", ";
	        }
	        return "[" + result.substring(0, result.length() - 2) + "]";
	    }
	    
	    public static String integerArrayToString(int[] nums) {
	        return integerArrayToString(nums, nums.length);
	    }
	    
	    public static void main(String[] args) throws IOException {
	    	//检索命令行输入
	        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	        String line;
	        while ((line = in.readLine()) != null) {
	            int[] nums = stringToIntegerArray(line);
	            line = in.readLine();
	            int target = Integer.parseInt(line);
	            
	            int[] ret = new TwoSumSolution().twoSum(nums, target);
	            
	            String out = integerArrayToString(ret);
	            
	            System.out.print(out);
	        }
	    }

}

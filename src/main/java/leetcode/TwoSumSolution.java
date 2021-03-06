package leetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSumSolution {
	
	public  int[] twoSum(int[] nums, int target) {
		int[] result = new int[2];
		result[0] = -1;
		result[1] = -1;
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			if (map.containsKey(target - nums[i])) {
				result[1] = i;
				result[0] = map.get(target - nums[i]) - 1;
				return result;
			}
			map.put(nums[i], i + 1);
		}

		return result;

	}

}

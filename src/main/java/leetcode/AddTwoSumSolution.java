package leetcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @ClassName: AddTwoSumSolution 
 * @Description: 链表 You are given two non-emply linked lists representing two
 *               non-nagitive integers. The digits are stored in reverse order
 *               and each of their nodes contain a single digit. And add the two
 *               number and return it as a linked list. You may assume that the
 *               two numbers don't contain any leading zero, except the number 0
 *               itself.
 * 
 *               Example: Input: (2->4->3) + (5->6->4) Output: 7->0->8
 *               Explanation: 342 + 465 = 807
 *               复杂度：O(max(length(l1),length(l2))
 * @author lcy
 * @date 2017年12月19日 上午9:04:20    
 */
public class AddTwoSumSolution {
	 class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public static ListNode addTwoSum(ListNode l1, ListNode l2){
		AddTwoSumSolution solution = new AddTwoSumSolution();
		
		ListNode head = solution.new  ListNode(0);
	    ListNode result = head;
	    int carry = 0;
	    
	    while (l1 != null || l2 != null || carry > 0) {
	        int resVal = (l1 != null? l1.val : 0) + (l2 != null? l2.val : 0) + carry;
	        result.next = solution.new ListNode(resVal % 10);
	        carry = resVal / 10;
	        l1 = (l1 == null ? l1 : l1.next);
	        l2 = (l2 == null ? l2 : l2.next);
	        result = result.next;
	    }
	    return head.next;
		
		
	}
	public static int[] stringToIntegerArray(String input){
		input = input.trim();
		input = input.substring(1, input.length() -1);
		if(input.length() == 0){
			return new int[0];
		}
		String[] parts = input.split(",");
		int[] result = new int[parts.length];
		for(int i = 0; i < parts.length; i++){
			String part = parts[i].trim();
			result[i] = Integer.parseInt(part);
		}
		return result;
	}
	public static ListNode stringToListNode(String input){
		AddTwoSumSolution solution = new AddTwoSumSolution();
		//Generate array from input
		int[] nodeValues = stringToIntegerArray(input);
		//Now convert that list into linked list
		ListNode dummyRoot = solution.new ListNode(0);
		ListNode ptr = dummyRoot;
		for(int item : nodeValues){
			ptr.next = solution.new ListNode(item);
			ptr = ptr.next;
			
		}
		return dummyRoot.next;
	}
	
	private static String ListNodeToString(ListNode node) {
		if(node == null  ){
			return "[]";
		}
		String result = "";
		while(node != null){
			result += Integer.toString(node.val) + ", ";
			node = node.next;
		}
		return "[" + result.substring(0, result.length() - 2) + "]";
	}
	
	public static void main(String[] args) {
		BufferedReader  in = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {
			while((line = in.readLine()) != null){
				ListNode l1 = stringToListNode(line);
				line = in.readLine();
				ListNode l2 = stringToListNode(line);
				ListNode result = addTwoSum(l1,l2);
				String out = ListNodeToString(result);
				System.out.println(out);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

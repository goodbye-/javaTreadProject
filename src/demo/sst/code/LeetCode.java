package demo.sst.code;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * @author shui
 *
 */
public class LeetCode implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    public static void main(String[] args) throws Exception {
        System.out.println(bitSum(12345,123123));
    }
    /*
     * @Override public TestCase clone() throws CloneNotSupportedException{
     * return (TestCase) super.clone();
     * 
     * }
     */

    public Object deepClone() throws IOException, ClassNotFoundException {
        // 将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);
        // 从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (oi.readObject());
    }

    public static int reverse(int x) {

        if (x > Integer.MAX_VALUE || x < Integer.MIN_VALUE) {
            return 0;
        }
        Boolean zfBoolean = true;
        if (x < 0) {
            zfBoolean = false;
        }
        int j = 0;
        while (x != 0) {
            j = j * 10 + x % 10;
            x = x / 10;
        }
        return j;

    }

    public static int romanToInt(String s) {
        int nums[] = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
            case 'M':
                nums[i] = 1000;
                break;
            case 'D':
                nums[i] = 500;
                break;
            case 'C':
                nums[i] = 100;
                break;
            case 'L':
                nums[i] = 50;
                break;
            case 'X':
                nums[i] = 10;
                break;
            case 'V':
                nums[i] = 5;
                break;
            case 'I':
                nums[i] = 1;
                break;
            }
        }
        int sum = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1])
                sum -= nums[i];
            else
                sum += nums[i];
        }
        return sum + nums[nums.length - 1];
    }

    public static String longestCommonPrefix(String[] strs) {
        StringBuffer prefix = new StringBuffer();
        if (strs.length == 1) {
            return strs[0];
        }
        if (strs.length == 0) {
            return "";
        }
        String first = strs[0];
        for (int i = 0; i < first.length(); i++) {
            boolean b = true;
            for (int j = 1; j < strs.length; j++) {
                if (strs[j].length() < i + 1) {
                    return prefix.toString();
                }
                if (first.charAt(i) != strs[j].charAt(i)) {
                    b = false;
                    return prefix.toString();
                }
            }
            if (b) {
                prefix.append(first.charAt(i));
            }
        }

        return prefix.toString();
    }

    // 水平遍历
    public static String longestCommonPrefix1(String[] strs) {
        if (strs.length == 0)
            return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty())
                    return "";
            }
        return prefix;
    }

    // 分而治之-- 将str数组分而治之
    public static String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        return longestCommonPrefix(strs, 0, strs.length - 1);
    }

    private static String longestCommonPrefix(String[] strs, int l, int r) {
        if (l == r) {
            return strs[l];
        } else {
            int mid = (l + r) / 2;
            String lcpLeft = longestCommonPrefix(strs, l, mid);
            String lcpRight = longestCommonPrefix(strs, mid + 1, r);
            return commonPrefix(lcpLeft, lcpRight);
        }
    }

    static String commonPrefix(String left, String right) {
        int min = Math.min(left.length(), right.length());
        for (int i = 0; i < min; i++) {
            if (left.charAt(i) != right.charAt(i))
                return left.substring(0, i);
        }
        return left.substring(0, min);
    }

    // 二分法--将首个string使用二分法
    public static String longestCommonPrefix3(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        int minLen = Integer.MAX_VALUE;
        for (String str : strs)
            minLen = Math.min(minLen, str.length());
        int low = 1;
        int high = minLen;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (isCommonPrefix(strs, middle))
                low = middle + 1;
            else
                high = middle - 1;
        }
        return strs[0].substring(0, (low + high) / 2);
    }

    private static boolean isCommonPrefix(String[] strs, int len) {
        String str1 = strs[0].substring(0, len);
        for (int i = 1; i < strs.length; i++)
            if (!strs[i].startsWith(str1))
                return false;
        return true;
    }

    /**
     * @author : sst HJ
     * @date 创建时间：2017年10月17日 上午11:36:22
     * @version 1.0
     * @param s
     * @return Valid Parentheses
     * @since 括号的对称性 @方法说明：失败例子 "[([]])"
     */
    public static boolean isValid1(String s) {
        int ld = 0, rd = 0, lz = 0, rz = 0, lx = 0, rx = 0;
        char first = s.charAt(0);
        switch (s.charAt(0)) {
        case '{':
            ld++;
            break;
        case '(':
            lx++;
            break;
        case '[':
            lz++;
            break;
        }
        if (first == '}' || first == ']' || first == ')') {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            switch (s.charAt(i)) {
            case '{':
                ld++;
                break;
            case '(':
                lx++;
                break;
            case '[':
                lz++;
                break;
            case '}':
                rd++;
                if (s.charAt(i - 1) == '[' || s.charAt(i - 1) == '(') {
                    return false;
                }
                break;
            case ']':
                rz++;
                if (s.charAt(i - 1) == '{' || s.charAt(i - 1) == '(') {
                    return false;
                }
                break;
            case ')':
                rx++;
                if (s.charAt(i - 1) == '{' || s.charAt(i - 1) == '[') {
                    return false;
                }
                break;
            default:
                break;
            }
        }
        if (ld != rd || lz != rz || lx != rx) {
            return false;
        }
        return true;
    }

    /**
     * @author : sst HJ
     * @date 创建时间：2017年10月17日 上午11:36:22
     * @version 1.0
     * @param s
     * @return Valid Parentheses
     * @since 括号的对称性 @方法说明：移除大法",tmd,为什么不用replaceAll直接替换？？？
     */
    public static boolean isValid(String s) {
        int length = s.length();
        int i = 0;
        while (length > 0) {
            if (s.indexOf("[]") >= 0 || s.indexOf("{}") >= 0 || s.indexOf("()") >= 0) {
                if (s.indexOf("[]") >= 0) {
                    i = s.indexOf("[]");
                }
                if (s.indexOf("{}") >= 0) {
                    i = s.indexOf("{}");
                }
                if (s.indexOf("()") >= 0) {
                    i = s.indexOf("()");
                }
                s = s.substring(0, i) + s.substring(i + 2, s.length());
                length = s.length();
            } else {
                return false;
            }

        }
        return true;
    }

    /**
     * @author : sst HJ
     * @date 创建时间：2017年10月19日 上午8:31:53
     * @version 1.0
     * @param s
     * @return mbd，这个是真心没想到，效率还高，上边的那个效率简直是日了狗了
     * @since @方法说明：
     */
    public static boolean isValid2(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int x) {
            this.val = x;
        }
    }

    /**
     * @author : sst HJ
     * @date 创建时间：2017年10月19日 上午11:39:09
     * @version 1.0
     * @param l1
     * @param l2
     * @return 接近完美，但是被人的都不用考虑空指针，还有一种递归
     *         recursive，这种题不用递归，就是没法炫技了,但是这个效率跟第一效率的代码几乎一样
     * @since @方法说明：
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode refer = result;
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                refer.next = l1;
                refer = refer.next;
                l1 = l1.next;
                if (l1 == null) {
                    refer.next = l2;
                }

            } else {
                refer.next = l2;
                refer = refer.next;
                l2 = l2.next;
                if (l2 == null) {
                    refer.next = l1;
                }
            }
        }
        return result.next;
    }

    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;

        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l2.next, l1);
            return l2;
        }
    }

    /**
     * @方法说明：双指针的理论基础 Since the array is already sorted, we can keep two
     *                pointers ii and jj, where ii is the slow-runner while jj
     *                is the fast-runner. As long as nums[i] =
     *                nums[j]nums[i]=nums[j], we increment jj to skip the
     *                duplicate.
     * 
     *                When we encounter nums[j] \neq nums[i]nums[j]≠nums[i], the
     *                duplicate run has ended so we must copy its value to
     *                nums[i + 1]nums[i+1]. ii is then incremented and we repeat
     *                the same process again until jj reaches the end of array.
     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0)
            return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }

    public static int removeElement(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        return i;
    }

    public static int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < target) {
                continue;
            }
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }

    /**
     * @author : sst HJ
     * @date 创建时间：2017年10月23日 下午2:27:07
     * @version 1.0
     * @param nums
     * @param target
     * @return 这不用二分法简直智障
     * @since @方法说明：
     */
    public static int searchInsert2(int[] nums, int target) {
        if (nums == null)
            return 0;
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target)
                start = mid + 1;
            else
                end = mid - 1;
        }
        return start;
    }

    public static int searchInsert3(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            if (target == nums[(start + end) / 2]) {
                return (start + end) / 2;
            }
            if (target < nums[(start + end) / 2]) {
                end = (start + end) / 2 - 1;
            }
            if (target > nums[(start + end) / 2]) {
                start = (start + end) / 2 + 1;
            }
        }
        if (nums[start] >= target) {
            return start;
        }
        if (nums[start] < target) {
            return start + 1;
        }
        return 0;
    }

    public static String countAndSay(int n) {
        StringBuffer sb = new StringBuffer();
        if (n == 1) {
            return "1";
        } else {
            String n_1 = countAndSay(n - 1);
            int count = 0;
            char val = n_1.charAt(0);
            for (int i = 0; i < n_1.length(); i++) {
                if (n_1.charAt(i) == val) {
                    count++;
                } else {
                    sb.append(count).append(val);
                    val = n_1.charAt(i);
                    count = 1;
                }
            }
            sb.append(count).append(val);
        }
        return sb.toString();
    }

    /**
     * @author : sst HJ
     * @date 创建时间：2017年10月25日 下午4:34:51
     * @version 1.0
     * @param nums
     * @return
     * @since 思路不对 @方法说明：
     */
    public static int maxSubArray1(int[] nums) {
        int sum1 = 0;
        int sum = 0;
        int flag = 0;
        int min = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0) {
                min = min > nums[i] ? min : nums[i];
                if (i == nums.length - 1) {
                    return min;
                }
                continue;
            } else {
                flag = i;
                sum += nums[i];
                sum1 = sum;
                break;
            }
        }
        int fu = 0;
        for (int i = flag + 1; i < nums.length; i++) {
            if (i == nums.length - 1) {
                if (nums[i] <= 0) {
                    return sum > sum1 ? sum : sum1;
                } else {
                    if (nums[i] <= 0) {
                        return sum > sum1 ? sum : sum1;
                    } else {
                        int sssm = sum;
                        int sssm1 = nums[i];
                        int sssm2 = sum + nums[i] + fu;
                        int max = sum;
                        if (sssm1 > max) {
                            max = sssm1;
                        }
                        if (sssm2 > max) {
                            max = sssm2;
                        }
                        return max > sum1 ? max : sum1;
                    }
                }
            } else {
                if (nums[i] <= 0) {
                    fu += nums[i];
                } else {
                    sum += fu;
                    fu = 0;
                    if (sum >= 0) {
                        sum += nums[i];
                    } else {
                        sum = nums[i];
                    }
                }
            }
        }
        return sum > sum1 ? sum : sum1;
    }

    /**
     * @author : sst HJ
     * @date 创建时间：2017年10月30日 下午4:39:54
     * @version 1.0
     * @param s
     * @return 可以先trim一下
     * @since @方法说明：
     */
    public static int lengthOfLastWord(String s) {
        s = " ";
        String[] array = s.split(" ");
        if (array.length == 0) {
            return 0;
        }
        return array[array.length - 1].length();
    }

    /**
     * @author : sst HJ
     * @date 创建时间：2017年10月30日 下午5:21:10
     * @version 1.0
     * @param digits
     * @return
     * @since
     * @方法说明：无法解决999变成1000的问题
     */
    public static int[] plusOne(int[] digits) {
        int j = digits.length - 1;
        for (int i = j; i >= 0; i--) {
            if (digits[i] + 1 >= 10) {
                if (i == 0) {
                    int[] re = new int[j + 2];
                    re[0] = 1;
                    return re;
                }
                digits[i] = 0;
            } else {
                digits[i]++;
                break;
            }
        }
        for (int i = 0; i < digits.length; i++) {
            System.out.print(digits[i]);
        }
        return digits;
    }

    public static int[] plusOne1(int[] digits) {
        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            digits[i] = 0;
        }

        int[] newNumber = new int[n + 1];
        newNumber[0] = 1;
        return newNumber;
    }

    public static int mySqrt(int x) {
        if (x <= 3) {
            return 1;
        } else {
            for (int i = 2; i < x / 2 + 1; i++) {
                if (Long.valueOf(i * i) > Long.valueOf(x)) {
                    return i - 1;
                }
            }
        }
        return x;
    }

    private static Map<Integer, Object> maps = new HashMap<Integer, Object>();

    public static int climbStairs(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        if (maps.get(n) != null) {
            return (int) maps.get(n);
        }
        int result_n = climbStairs(n - 1) + climbStairs(n - 2);
        maps.put(n, result_n);
        return result_n;
    }

    public static boolean asd() {
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("col0", 00000);
        map.put("col1", 11111);
        map.put("col2", 22222);
        map.put("col1", 1111111111111l);
        return true;
    }

    public static void swap(int a, int b) {
        System.out.println(a + "=====" + b);
        a = a ^ b; // 此时的a已经改变
        b = b ^ a;
        a = a ^ b;
        System.out.println(a + "======" + b);
        System.out.println(b >>> 5);
        System.out.println(b << 5);
        Integer.toBinaryString(b);
        /*
         * a1=a^b
         * 
         * b=b^a1=b^a^b=a //此时a1=a^b b=a a=a1^b=a^b^a=b
         * 
         * 核心思想是：a = b^a^b;
         */
        HashMap<String, Object> map = new HashMap<String, Object>();
        System.out.println("asd".hashCode());
    }

    public static boolean checkDateFormate(String str) {
        boolean isValid = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            isValid = false;
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
        }
        return isValid;
    }

    public static String getDatetimeFormat(String date) {
        date = date.trim();
        String a1 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";// yyyy-MM-dd
                                                                            // HH:mm:ss
        String a2 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}";// yyyy-MM-dd
                                                                   // HH:mm
        String a3 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}";// yyyy-MM-dd HH
        String a4 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";// yyyy-MM-dd
        String a5 = "[0-9]{4}-[0-9]{2}";// yyyy-MM
        String a6 = "[0-9]{4}";// yyyy
        String a7 = "[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}";// yyyyMMddHHmmss
        boolean datea1 = Pattern.compile(a1).matcher(date).matches();
        if (datea1) {
            return "yyyy-MM-dd HH:mm:ss";
        }
        boolean datea2 = Pattern.compile(a2).matcher(date).matches();
        if (datea2) {
            return "yyyy-MM-dd HH:mm";
        }
        boolean datea3 = Pattern.compile(a3).matcher(date).matches();
        if (datea3) {
            return "yyyy-MM-dd HH";
        }
        boolean datea4 = Pattern.compile(a4).matcher(date).matches();
        if (datea4) {
            return "yyyy-MM-dd";
        }
        boolean datea5 = Pattern.compile(a5).matcher(date).matches();
        if (datea5) {
            return "yyyy-MM";
        }
        boolean datea6 = Pattern.compile(a6).matcher(date).matches();
        if (datea6) {
            return "yyyy";
        }
        boolean datea7 = Pattern.compile(a7).matcher(date).matches();
        if (datea7) {
            return "yyyyMMddHHmmss";
        }
        return "";
    }

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode node = head;
        if (head == null) {
            return null;
        }
        while (node.next != null) {
            if (node.val == node.next.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }
        return head;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * @author : sst HJ
     * @date 创建时间：2017年12月8日 上午10:42:13
     * @version 1.0
     * @param p
     * @param q
     * @return
     * @since
     * @方法说明：还差一点
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        // if(root){
        // printf("%c",root->data);
        // preOrderTraverse(root->lchild);
        // preOrderTraverse(root->rchild);
        // }
        if (p == null && q == null)
            return true;
        if (p == null || q == null)
            return false;
        if (p.val == q.val)
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        return false;
    }

    /** 
    * @author : sst HJ
    * @date 创建时间：2017年12月8日 上午11:35:23 
    * @version 1.0 
    * @param root
    * @return 
    * @since   
    * @方法说明：对称树
    */
    public static boolean isSymmetric(TreeNode root) {
        if(root == null){return false;}
        TreeNode left = root.left;
        TreeNode right = root.right;
        if(left!=null && right!=null ){
            if(left.val == right.val){
                return isSymmetric(left) && isSymmetric(right);
            }else{
                return false;
            }
        }
        if(left==null || right==null )return false;
        return true;
    }
    
    /** 
    * @author : sst HJ
    * @date 创建时间：2018年4月3日 下午6:49:07 
    * @version 1.0 
    * @param a
    * @param b
    * @return 
    * @since   
    * @方法说明：不用+计算两个数之和
    */
    public static int bitSum(int a,int b){
        if(b == 0){
            return a;
        }
        //不算进位
        int a1 = a^b;
        //一次进位
        int b1 = (a&b) <<1;
        
        return bitSum(a1, b1);
        
    }

}

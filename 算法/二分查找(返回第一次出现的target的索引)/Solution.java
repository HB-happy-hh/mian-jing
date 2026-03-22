public class Solution {

    public static int searchFirstOccurrence(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                result = mid;       // 先记录当前找到的位置
                right = mid - 1;   // 继续向左收缩，寻找更早出现的位置
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int target = 9;
        int[] nums = {5, 7, 7, 8, 8, 10};

        System.out.println(searchFirstOccurrence(nums, target));
    }
}

package algorithms.a02;

public class A02RemoveDuplicates {
    
    public static int removeDuplicates(int[] nums){
        // 空数组直接返回 0，避免读取不存在的第一个元素。
        // 非空数组先保留第一个元素，定义 write 并初始化。
        // 暂时返回 write，循环留到下一步。
        if (nums.length == 0) {
            return 0;
        }
        int write = 1;

        // - read 从下标 1 开始，遍历到数组末尾。
        // - 当前值与最后保留的值不同，才写到 write 所指的位置，然后增加 write。
        // - 相同就跳过，write 不变。
        // 预期：[0,0,1,1,1,2] 返回 3，前三项为 [0,1,2]；[7,7,7] 返回 1。
        for(int read = 1; read<nums.length ; read++){
            if (nums[read]!=nums[write-1]) {
                nums[write] = nums[read];
                write++;
            }
        }

        return write;

    }
}

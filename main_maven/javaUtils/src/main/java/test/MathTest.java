package test;

public class MathTest {

    public static void main(String[] args) {
        int[] arr0 = {8, 5, 3, 2, 4};
        int[] arr1 = {8, 5, 3, 2, 4};
        int[] arr2 = {8, 5, 3, 2, 4};

        int k0 = 0;
        int k1 = 0;
        int k2 = 0;

        //冒泡升序
        for (int i = 0; i < arr0.length; i++) {
            for (int j = 0; j < arr0.length - i - 1; j++) {
                k0++;
                if (arr0[j] > arr0[j + 1]) {
                    int t = arr0[j + 1];
                    arr0[j + 1] = arr0[j];
                    arr0[j] = t;
                }
            }
        }
        StringBuilder sb0 = new StringBuilder();
        for (int a : arr0) {
            sb0.append(a).append(",");
        }
        System.out.println(sb0.toString() + "*****************0*****************" + k0);
        //冒泡降序
        for (int i = 0; i < arr1.length; i++) {
            for (int j = arr1.length - i - 1; j > 0; j--) {
                k1++;
                if (arr1[j] > arr1[j - 1]) {
                    int t = arr1[j - 1];
                    arr1[j - 1] = arr1[j];
                    arr1[j] = t;
                }
            }
        }
        StringBuilder sb1 = new StringBuilder();
        for (int a : arr1) {
            sb1.append(a).append(",");
        }
        System.out.println(sb1.toString() + "*******************1***************" + k1);

        //插入排序
        for (int i = 1; i < arr2.length; i++) {
            //外层循环，从第二个开始比较
            for (int j = i; j > 0; j--) {
                k2++;
                //内存循环，与前面排好序的数据比较，如果后面的数据小于前面的则交换
                if (arr2[j] < arr2[j - 1]) {
                    int temp = arr2[j - 1];
                    arr2[j - 1] = arr2[j];
                    arr2[j] = temp;
                } else {
                    //如果不小于，说明插入完毕，退出内层循环
                    break;
                }
            }
        }
        StringBuilder sb2 = new StringBuilder();
        for (int a : arr2) {
            sb2.append(a).append(",");
        }
        System.out.println(sb2.toString() + "******************2****************" + k2);

        int[] arr4 = {8, 5, 3, 2, 4};
        int k4 = 0;
        //希尔排序（插入排序变种版）
        for (int i = arr4.length / 2; i > 0; i /= 2) {
            //i层循环控制步长
            for (int j = i; j < arr4.length; j++) {
                //j控制无序端的起始位置
                for (int k = j; k > 0 && k - i >= 0; k -= i) {
                    k4++;
                    if (arr4[k] < arr4[k - i]) {
                        int temp = arr4[k - i];
                        arr4[k - i] = arr4[k];
                        arr4[k] = temp;
                    } else {
                        break;
                    }
                }
            }
            //j,k为插入排序，不过步长为i
        }
        StringBuilder sb4 = new StringBuilder();
        for (int a : arr4) {
            sb4.append(a).append(",");
        }
        System.out.println(sb4.toString() + "*******************3***************" + k4);

        int[] arr5 = {8, 5, 3, 2, 4};
        int k5 = 0;
        k5 = quickSort(arr5, 0, arr5.length - 1, k5);
        StringBuilder sb5 = new StringBuilder();
        for (int a : arr5) {
            sb5.append(a).append(",");
        }
        System.out.println(sb5.toString() + "********************4**************" + k4);
    }

    private static int quickSort(int[] arr, int low, int high, int k) {
        //如果指针在同一位置(只有一个数据时)，退出
        if (high - low < 1) {
            return 0;
        }
        //标记，从高指针开始，还是低指针（默认高指针）
        boolean flag = true;
        //记录指针的其实位置
        int start = low;
        int end = high;
        //默认中间值为低指针的第一个值
        int midValue = arr[low];
        while (true) {
            k++;
            //高指针移动
            if (flag) {
                //如果列表右方的数据大于中间值，则向左移动
                if (arr[high] > midValue) {
                    high--;
                } else if (arr[high] < midValue) {
                    //如果小于，则覆盖最开始的低指针值，并且移动低指针，标志位改成从低指针开始移动
                    arr[low] = arr[high];
                    low++;
                    flag = false;
                }
            } else {
                //如果低指针数据小于中间值，则低指针向右移动
                if (arr[low] < midValue) {
                    low++;
                } else if (arr[low] > midValue) {
                    //如果低指针的值大于中间值，则覆盖高指针停留时的数据，并向左移动高指针。切换为高指针移动
                    arr[high] = arr[low];
                    high--;
                    flag = true;
                }
            }
            //当两个指针的位置相同时，则找到了中间值的位置，并退出循环
            if (low == high) {
                arr[low] = midValue;
                break;
            }
        }
        //然后出现有，中间值左边的小于中间值。右边的大于中间值。
        //然后在对左右两边的列表在进行快速排序
        quickSort(arr, start, low - 1, k);
        quickSort(arr, low + 1, end, k);
        return k;
    }
}

package binhnpt.example;

public class OvercatchingExceptionExample {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(OvercatchingExceptionExample.class.getName());

    public static void main(String[] args) {
        int[] arr = new int[5];
        // Gán giá trị cho các phần tử của mảng
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i * 2;
        }
        // Đọc và log giá trị hợp lệ từ mảng
        for (int i = 0; i < arr.length; i++) {
            LOGGER.info("arr[" + i + "] = " + arr[i]);
        }
    }
}

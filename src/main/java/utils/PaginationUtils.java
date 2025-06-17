package utils;

public class PaginationUtils {

    // Tính tổng số trang
    public static int calculateTotalPages(int totalItems, int itemsPerPage) {
        if (totalItems <= 0 || itemsPerPage <= 0) {
            return 1;
        }
        return (int) Math.ceil((double) totalItems / itemsPerPage);
    }

    // Tính chỉ số bắt đầu (offset) cho truy vấn SQL
    public static int getOffset(int currentPage, int itemsPerPage) {
        if (currentPage < 1) {
            currentPage = 1;
        }
        return (currentPage - 1) * itemsPerPage;
    }

    // Lấy danh sách số trang hiển thị
    public static int[] getPageNumbers(int currentPage, int totalPages, int maxVisiblePages) {
        if (totalPages <= 0) {
            return new int[]{1};
        }

        // Tính số trang hiển thị (giới hạn bởi maxVisiblePages)
        int startPage = Math.max(1, currentPage - maxVisiblePages / 2);
        int endPage = Math.min(totalPages, startPage + maxVisiblePages - 1);

        // Điều chỉnh startPage nếu endPage chạm giới hạn
        startPage = Math.max(1, endPage - maxVisiblePages + 1);

        // Tạo mảng số trang
        int[] pageNumbers = new int[endPage - startPage + 1];
        for (int i = 0; i < pageNumbers.length; i++) {
            pageNumbers[i] = startPage + i;
        }
        return pageNumbers;
    }

}

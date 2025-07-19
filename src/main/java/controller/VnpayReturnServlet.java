package controller;

import com.vnpay.common.Config;
import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/vnpay_return")
public class VnpayReturnServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1: Read all parameters from VNPAY
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            fields.put(paramName, paramValue);
        }

        // Step 2: Extract vnp_SecureHash and remove unnecessary fields
        String vnp_SecureHash = fields.get("vnp_SecureHash");

        Map<String, String> vnp_Params = new HashMap<>();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!key.equals("vnp_SecureHash") && !key.equals("vnp_SecureHashType")) {
                vnp_Params.put(key, value);
            }
        }

        // Step 3: Hash fields and compare with received secure hash
        String signValue = Config.hashAllFields(vnp_Params);
        boolean isValidSignature = vnp_SecureHash != null && vnp_SecureHash.equalsIgnoreCase(signValue);
        System.out.println("isValidSignature: " + isValidSignature);


        // 3. Lấy các tham số cần thiết
        String vnp_TxnRef = fields.get("vnp_TxnRef"); // mã đơn hàng của bạn
        String vnp_TransactionNo = fields.get("vnp_TransactionNo"); // mã giao dịch VNPAY
        String vnp_ResponseCode = fields.get("vnp_ResponseCode");   // mã phản hồi
        System.out.println("vnp_TxnRef: " + vnp_TxnRef);
        System.out.println("vnp_TransactionNo: " + vnp_TransactionNo);
        System.out.println("vnp_ResponseCode: " + vnp_ResponseCode);

        OrderDAO dao = new OrderDAO();
        Integer orderId = dao.getOrderIdByTxnRef(vnp_TxnRef); // Lấy ID đơn hàng từ txnRef
        System.out.println("orderId: " + orderId);

        // 4. Kiểm tra chữ ký hợp lệ
        if (!isValidSignature || vnp_TxnRef == null || orderId == null) {
            if (vnp_TxnRef != null && orderId != null) {
                dao.updatePaymentStatus(vnp_TxnRef, "", false); // Đánh dấu thất bại
            }
            request.getRequestDispatcher("/view/checkout/order-canceled.jsp").forward(request, response);
            return;
        }

        if ("00".equals(vnp_ResponseCode)) {
            boolean updated = dao.updatePaymentStatus(vnp_TxnRef, vnp_TransactionNo, true);
            if (updated) {
                // Xoá cookie giỏ hàng
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("cart".equals(cookie.getName())) {
                            cookie.setValue("");
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                }
                response.sendRedirect("order-success?orderId=" + orderId);
                return;
            } else {
                request.getRequestDispatcher("/view/checkout/order-canceled.jsp").forward(request, response);
                return;
            }
        } else {
            dao.updatePaymentStatus(vnp_TxnRef, "", false);
            request.getRequestDispatcher("/view/checkout/order-canceled.jsp").forward(request, response);
        }
    }

}

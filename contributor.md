# 🚦 Quy trình Tạo và Duyệt Pull Request

Để đảm bảo chất lượng code và tránh lỗi khi merge vào nhánh chính (`main`), tất cả các thành viên trong team cần tuân thủ quy trình sau:

---

## 🧑‍💻 Đối với Developer (Người tạo Pull Request)

1. **Tạo Pull Request (PR)** từ nhánh của mình vào nhánh `main`.
2. **Không được tự merge hoặc tự approve PR của chính mình.**
3. Ghi chú rõ ràng nội dung thay đổi trong phần mô tả PR (description).
4. Chờ Team Lead **review và approve**.
5. Sau khi được approve, PR sẽ được Team Lead merge.

---

## 👩‍💼 Đối với Team Lead (Người review & duyệt PR)

1. Truy cập tab **Pull Requests** trong repository.
2. Kiểm tra nội dung thay đổi (code diff, logic, convention, test...).
3. Nếu đạt yêu cầu:
   - Bấm **Approve**.
   - Sau đó bấm **Merge** vào nhánh `main`.

---

## 🔒 Thiết lập bảo vệ nhánh (Branch protection)

> Đã được cấu hình bởi Admin để đảm bảo quy trình bắt buộc:

- Người tạo PR **không thể tự approve hoặc merge PR của mình**.
- **Chỉ Team Lead** mới có quyền merge vào `main`.

---

Cảm ơn bạn đã tuân thủ quy trình giúp team làm việc hiệu quả hơn!

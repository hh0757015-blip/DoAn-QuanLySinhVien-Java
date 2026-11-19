ĐỒ ÁN MÔN LẬP TRÌNH JAVA
ĐỀ TÀI: PHẦN MỀM QUẢN LÝ SINH VIÊN (JAVA SWING + MYSQL)

Sinh viên thực hiện: Trần Hoàng Trường
Lớp: TH30.03

------------------------------------------------------------------------
1. CÔNG CỤ VÀ THƯ VIỆN SỬ DỤNG
------------------------------------------------------------------------
- Ngôn ngữ: Java (Java Swing)
- Phần mềm lập trình: Apache NetBeans IDE
- Hệ quản trị CSDL: MySQL Server 8.0 / MySQL Workbench
- Thư viện kết nối: mysql-connector-j-8.0.33.jar

------------------------------------------------------------------------
2. CẤU TRÚC PROJECT (CÁC FORM VÀ FILE CHÍNH)
------------------------------------------------------------------------
- DoAn_QLSV.sql        : File tạo cơ sở dữ liệu, các bảng và dữ liệu mẫu.
- JavaApplication1.java: File chứa hàm kết nối tới MySQL.
- DangNhap.java        : Form 1 - Màn hình đăng nhập và phân quyền (File chạy chính).
- GiaoDienChinh.java   : Form 2 - Menu điều hướng chọn chức năng quản lý.
- QuanLyKhoa.java      : Form 3 - Thêm, sửa, xóa, tìm kiếm thông tin Khoa.
- QuanLySinhVien.java  : Form 4 - Thêm, sửa, xóa, tìm kiếm thông tin Sinh viên.

------------------------------------------------------------------------
3. HƯỚNG DẪN CÀI ĐẶT VÀ CHẠY CHƯƠNG TRÌNH
------------------------------------------------------------------------
Bước 1: Tạo cơ sở dữ liệu
- Mở MySQL Workbench, mở file "DoAn_QLSV.sql" và bấm biểu tượng tia sét (Execute)
  để tạo database DoAn_QLSV cùng dữ liệu mẫu.

Bước 2: Cấu hình trên NetBeans
- Mở project JavaApplication1 trong NetBeans.
- Kiểm tra mục Libraries xem đã có file "mysql-connector-j-8.0.33.jar" chưa.
  (Nếu báo lỗi thiếu thư viện thì chuột phải vào Libraries -> Add JAR/Folder
  và chọn lại file jar trong máy).
- Mở file "JavaApplication1.java", kiểm tra lại tài khoản và mật khẩu kết nối
  MySQL cho khớp với máy tính đang chạy (mặc định đang để user: root, pass: 123456).

Bước 3: Chạy phần mềm
- Mở file "DangNhap.java" -> nhấn Shift + F6 (hoặc bấm F6) để khởi chạy.

------------------------------------------------------------------------
4. TÀI KHOẢN TEST CHỨC NĂNG PHÂN QUYỀN
------------------------------------------------------------------------
1) Quyền Admin (Được phép Thêm, Sửa, Xóa, Tìm kiếm dữ liệu):
   - Tên đăng nhập: admin
   - Mật khẩu     : 123456

2) Quyền Sinh viên (Chỉ được xem và tìm kiếm, các nút Thêm/Sửa/Xóa bị khóa):
   - Tên đăng nhập: SV300301
   - Mật khẩu     : 123456
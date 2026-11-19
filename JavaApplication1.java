package javaapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JavaApplication1 {

    private static final String DUONG_DAN = "jdbc:mysql://localhost:3306/DoAn_QLSV?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String TAI_KHOAN = "root";
    private static final String MAT_KHAU = "123456"; // Đổi thành mật khẩu root MySQL của bạn

    public static Connection lay_ket_noi() {
        Connection ket_noi = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ket_noi = DriverManager.getConnection(DUONG_DAN, TAI_KHOAN, MAT_KHAU);
            System.out.println("Ket noi CSDL MySQL thanh cong!");
        } catch (ClassNotFoundException e) {
            System.err.println("Loi: Chua add file jar vao Libraries!");
        } catch (SQLException e) {
            System.err.println("Loi ket noi: Kiem tra lai mat khau root hoac MySQL da bat chua!");
            e.printStackTrace();
        }
        return ket_noi;
    }

    public static void main(String[] args) {
        lay_ket_noi();
    }
}
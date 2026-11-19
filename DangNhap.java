package javaapplication1;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class DangNhap extends JFrame {

    private JTextField txt_tai_khoan;
    private JPasswordField txt_mat_khau;
    private JButton btn_dang_nhap, btn_thoat;

    public DangNhap() {
        setTitle("Dang Nhap He Thong - DoAn_QLSV");
        setSize(400, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JLabel lbl_tieu_de = new JLabel("DANG NHAP HE THONG", SwingConstants.CENTER);
        lbl_tieu_de.setFont(new Font("Arial", Font.BOLD, 16));
        lbl_tieu_de.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(lbl_tieu_de, BorderLayout.NORTH);

        JPanel panel_nhap = new JPanel(new GridLayout(2, 2, 10, 10));
        panel_nhap.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        panel_nhap.add(new JLabel("Ten dang nhap:"));
        txt_tai_khoan = new JTextField("admin");
        panel_nhap.add(txt_tai_khoan);

        panel_nhap.add(new JLabel("Mat khau:"));
        txt_mat_khau = new JPasswordField("123456");
        panel_nhap.add(txt_mat_khau);
        add(panel_nhap, BorderLayout.CENTER);

        JPanel panel_nut = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btn_dang_nhap = new JButton("Dang nhap");
        btn_thoat = new JButton("Thoat");
        panel_nut.add(btn_dang_nhap);
        panel_nut.add(btn_thoat);
        add(panel_nut, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btn_dang_nhap);

        btn_dang_nhap.addActionListener(e -> xu_ly_dang_nhap());
        btn_thoat.addActionListener(e -> System.exit(0));
    }

    private void xu_ly_dang_nhap() {
        String tai_khoan = txt_tai_khoan.getText().trim();
        String mat_khau = new String(txt_mat_khau.getPassword()).trim();

        String sql = "SELECT VaiTro FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
        try (Connection ket_noi = JavaApplication1.lay_ket_noi();
             PreparedStatement lenh = ket_noi.prepareStatement(sql)) {

            lenh.setString(1, tai_khoan);
            lenh.setString(2, mat_khau);

            try (ResultSet ket_qua = lenh.executeQuery()) {
                if (ket_qua.next()) {
                    String vai_tro = ket_qua.getString("VaiTro");
                    new GiaoDienChinh(tai_khoan, vai_tro).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Sai tai khoan hoac mat khau!", "Loi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi ket noi: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
        EventQueue.invokeLater(() -> new DangNhap().setVisible(true));
    }
}
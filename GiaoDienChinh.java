package javaapplication1;

import java.awt.*;
import javax.swing.*;

public class GiaoDienChinh extends JFrame {

    private String ten_dang_nhap;
    private String vai_tro;

    public GiaoDienChinh() {
        this("admin", "Admin");
    }

    public GiaoDienChinh(String ten_dang_nhap, String vai_tro) {
        this.ten_dang_nhap = ten_dang_nhap;
        this.vai_tro = vai_tro;

        setTitle("He Thong Quan Ly Sinh Vien - DoAn_QLSV");
        setSize(480, 310);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JLabel lbl_chao = new JLabel("XIN CHAO: " + ten_dang_nhap.toUpperCase() + " (Quyen: " + vai_tro + ")", SwingConstants.CENTER);
        lbl_chao.setFont(new Font("Arial", Font.BOLD, 15));
        lbl_chao.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lbl_chao, BorderLayout.NORTH);

        JPanel panel_menu = new JPanel(new GridLayout(3, 1, 15, 15));
        panel_menu.setBorder(BorderFactory.createEmptyBorder(15, 70, 25, 70));

        JButton btn_ql_khoa = new JButton("1. QUAN LY KHOA");
        JButton btn_ql_sv = new JButton("2. QUAN LY SINH VIEN");
        JButton btn_dang_xuat = new JButton("Dang Xuat");

        btn_ql_khoa.setFont(new Font("Arial", Font.BOLD, 13));
        btn_ql_sv.setFont(new Font("Arial", Font.BOLD, 13));

        panel_menu.add(btn_ql_khoa);
        panel_menu.add(btn_ql_sv);
        panel_menu.add(btn_dang_xuat);
        add(panel_menu, BorderLayout.CENTER);

        btn_ql_khoa.addActionListener(e -> {
            new QuanLyKhoa(ten_dang_nhap, vai_tro).setVisible(true);
            this.dispose();
        });

        btn_ql_sv.addActionListener(e -> {
            new QuanLySinhVien(ten_dang_nhap, vai_tro).setVisible(true);
            this.dispose();
        });

        btn_dang_xuat.addActionListener(e -> {
            new DangNhap().setVisible(true);
            this.dispose();
        });
    }
}
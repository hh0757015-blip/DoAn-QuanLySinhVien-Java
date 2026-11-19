package javaapplication1;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuanLyKhoa extends JFrame {

    private JTextField txt_ma_khoa, txt_ten_khoa, txt_sdt, txt_email, txt_tim_kiem;
    private JButton btn_them, btn_sua, btn_xoa, btn_lam_moi, btn_tim_kiem, btn_quay_lai;
    private JTable bang_du_lieu;
    private DefaultTableModel mo_hinh_bang;
    private String ten_dang_nhap, vai_tro;

    public QuanLyKhoa(String ten_dang_nhap, String vai_tro) {
        this.ten_dang_nhap = ten_dang_nhap;
        this.vai_tro = vai_tro;
        khoi_tao_giao_dien();
        ap_dung_phan_quyen();
        tai_du_lieu_bang("");
    }

    private void khoi_tao_giao_dien() {
        setTitle("Quan Ly Khoa - Nguoi dung: " + ten_dang_nhap + " [" + vai_tro + "]");
        setSize(850, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel_tren = new JPanel(new BorderLayout(5, 5));
        panel_tren.setBorder(BorderFactory.createTitledBorder("Thong tin Khoa"));

        JPanel panel_nhap = new JPanel(new GridLayout(2, 4, 10, 10));
        panel_nhap.add(new JLabel("Ma Khoa:"));
        txt_ma_khoa = new JTextField();
        panel_nhap.add(txt_ma_khoa);

        panel_nhap.add(new JLabel("Ten Khoa:"));
        txt_ten_khoa = new JTextField();
        panel_nhap.add(txt_ten_khoa);

        panel_nhap.add(new JLabel("So Dien Thoai:"));
        txt_sdt = new JTextField();
        panel_nhap.add(txt_sdt);

        panel_nhap.add(new JLabel("Email:"));
        txt_email = new JTextField();
        panel_nhap.add(txt_email);

        JPanel panel_tim = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel_tim.add(new JLabel("Tim kiem (Ma/Ten):"));
        txt_tim_kiem = new JTextField(15);
        panel_tim.add(txt_tim_kiem);
        btn_tim_kiem = new JButton("Tim");
        panel_tim.add(btn_tim_kiem);

        panel_tren.add(panel_nhap, BorderLayout.CENTER);
        panel_tren.add(panel_tim, BorderLayout.SOUTH);
        add(panel_tren, BorderLayout.NORTH);

        mo_hinh_bang = new DefaultTableModel(new String[]{"Ma Khoa", "Ten Khoa", "So Dien Thoai", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bang_du_lieu = new JTable(mo_hinh_bang);
        bang_du_lieu.setRowHeight(24);
        add(new JScrollPane(bang_du_lieu), BorderLayout.CENTER);

        JPanel panel_nut = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btn_them = new JButton("Them");
        btn_sua = new JButton("Sua");
        btn_xoa = new JButton("Xoa");
        btn_lam_moi = new JButton("Lam moi");
        btn_quay_lai = new JButton("<< Quay lai Menu");

        panel_nut.add(btn_them);
        panel_nut.add(btn_sua);
        panel_nut.add(btn_xoa);
        panel_nut.add(btn_lam_moi);
        panel_nut.add(btn_quay_lai);
        add(panel_nut, BorderLayout.SOUTH);

        bang_du_lieu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = bang_du_lieu.getSelectedRow();
                if (r >= 0) {
                    txt_ma_khoa.setText(mo_hinh_bang.getValueAt(r, 0).toString());
                    txt_ten_khoa.setText(mo_hinh_bang.getValueAt(r, 1).toString());
                    txt_sdt.setText(mo_hinh_bang.getValueAt(r, 2) != null ? mo_hinh_bang.getValueAt(r, 2).toString() : "");
                    txt_email.setText(mo_hinh_bang.getValueAt(r, 3) != null ? mo_hinh_bang.getValueAt(r, 3).toString() : "");
                    txt_ma_khoa.setEditable(false);
                }
            }
        });

        btn_them.addActionListener(e -> xu_ly_sql("INSERT INTO Khoa (TenKhoa, SoDienThoai, Email, MaKhoa) VALUES (?, ?, ?, ?)", "Them khoa thanh cong!"));
        btn_sua.addActionListener(e -> xu_ly_sql("UPDATE Khoa SET TenKhoa=?, SoDienThoai=?, Email=? WHERE MaKhoa=?", "Cap nhat khoa thanh cong!"));
        btn_xoa.addActionListener(e -> xoa_khoa());
        btn_lam_moi.addActionListener(e -> lam_moi());
        btn_tim_kiem.addActionListener(e -> tai_du_lieu_bang(txt_tim_kiem.getText().trim()));
        btn_quay_lai.addActionListener(e -> {
            new GiaoDienChinh(ten_dang_nhap, vai_tro).setVisible(true);
            this.dispose();
        });
    }

    private void ap_dung_phan_quyen() {
        if (!"Admin".equalsIgnoreCase(vai_tro)) {
            btn_them.setEnabled(false);
            btn_sua.setEnabled(false);
            btn_xoa.setEnabled(false);
            txt_ma_khoa.setEditable(false);
            txt_ten_khoa.setEditable(false);
            txt_sdt.setEditable(false);
            txt_email.setEditable(false);
        }
    }

    private void tai_du_lieu_bang(String tu_khoa) {
        mo_hinh_bang.setRowCount(0);
        String sql = tu_khoa.isEmpty() ? "SELECT * FROM Khoa" : "SELECT * FROM Khoa WHERE MaKhoa LIKE ? OR TenKhoa LIKE ?";
        try (Connection cn = JavaApplication1.lay_ket_noi(); PreparedStatement ps = cn.prepareStatement(sql)) {
            if (!tu_khoa.isEmpty()) {
                ps.setString(1, "%" + tu_khoa + "%");
                ps.setString(2, "%" + tu_khoa + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mo_hinh_bang.addRow(new Object[]{rs.getString("MaKhoa"), rs.getString("TenKhoa"), rs.getString("SoDienThoai"), rs.getString("Email")});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi tai du lieu: " + ex.getMessage());
        }
    }

    private void xu_ly_sql(String sql, String thong_bao) {
        try (Connection cn = JavaApplication1.lay_ket_noi(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, txt_ten_khoa.getText().trim());
            ps.setString(2, txt_sdt.getText().trim());
            ps.setString(3, txt_email.getText().trim());
            ps.setString(4, txt_ma_khoa.getText().trim());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, thong_bao);
            lam_moi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi thao tac: " + ex.getMessage());
        }
    }

    private void xoa_khoa() {
        String ma = txt_ma_khoa.getText().trim();
        if (ma.isEmpty()) return;
        int xac_nhan = JOptionPane.showConfirmDialog(this, "Xoa khoa [" + ma + "]?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (xac_nhan == JOptionPane.YES_OPTION) {
            try (Connection cn = JavaApplication1.lay_ket_noi(); PreparedStatement ps = cn.prepareStatement("DELETE FROM Khoa WHERE MaKhoa=?")) {
                ps.setString(1, ma);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xoa khoa thanh cong!");
                lam_moi();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Khong the xoa (dang co lop thuoc khoa nay): " + ex.getMessage());
            }
        }
    }

    private void lam_moi() {
        txt_ma_khoa.setText("");
        txt_ten_khoa.setText("");
        txt_sdt.setText("");
        txt_email.setText("");
        txt_tim_kiem.setText("");
        if ("Admin".equalsIgnoreCase(vai_tro)) {
            txt_ma_khoa.setEditable(true);
        }
        bang_du_lieu.clearSelection();
        tai_du_lieu_bang("");
    }
}
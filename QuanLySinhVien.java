package javaapplication1;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuanLySinhVien extends JFrame {

    private JTextField txt_ma_sv, txt_ho_ten, txt_ngay_sinh, txt_dia_chi, txt_sdt, txt_email, txt_ten_khoa, txt_tim_kiem;
    private JComboBox<String> cbo_gioi_tinh, cbo_ma_lop;
    private JButton btn_them, btn_sua, btn_xoa, btn_lam_moi, btn_tim_kiem, btn_quay_lai;
    private JTable bang_sv;
    private DefaultTableModel mo_hinh_bang;
    private String ten_dang_nhap, vai_tro;

    public QuanLySinhVien(String ten_dang_nhap, String vai_tro) {
        this.ten_dang_nhap = ten_dang_nhap;
        this.vai_tro = vai_tro;
        khoi_tao_giao_dien();
        nap_danh_sach_lop();
        ap_dung_phan_quyen();
        tai_du_lieu_sv("");
    }

    private void khoi_tao_giao_dien() {
        setTitle("Quan Ly Sinh Vien - Nguoi dung: " + ten_dang_nhap + " [" + vai_tro + "]");
        setSize(1020, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel_tren = new JPanel(new BorderLayout(5, 5));
        panel_tren.setBorder(BorderFactory.createTitledBorder("Thong tin Sinh Vien"));

        JPanel panel_nhap = new JPanel(new GridLayout(5, 4, 10, 8));
        panel_nhap.add(new JLabel("Ma SV:"));
        txt_ma_sv = new JTextField();
        panel_nhap.add(txt_ma_sv);

        panel_nhap.add(new JLabel("Ho Ten:"));
        txt_ho_ten = new JTextField();
        panel_nhap.add(txt_ho_ten);

        panel_nhap.add(new JLabel("Ngay Sinh (YYYY-MM-DD):"));
        txt_ngay_sinh = new JTextField("2006-01-01");
        panel_nhap.add(txt_ngay_sinh);

        panel_nhap.add(new JLabel("Gioi Tinh:"));
        cbo_gioi_tinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        panel_nhap.add(cbo_gioi_tinh);

        panel_nhap.add(new JLabel("Dia Chi:"));
        txt_dia_chi = new JTextField();
        panel_nhap.add(txt_dia_chi);

        panel_nhap.add(new JLabel("So Dien Thoai:"));
        txt_sdt = new JTextField();
        panel_nhap.add(txt_sdt);

        panel_nhap.add(new JLabel("Email:"));
        txt_email = new JTextField();
        panel_nhap.add(txt_email);

        panel_nhap.add(new JLabel("Ma Lop:"));
        cbo_ma_lop = new JComboBox<>();
        panel_nhap.add(cbo_ma_lop);

        panel_nhap.add(new JLabel("Thuoc Khoa:"));
        txt_ten_khoa = new JTextField();
        txt_ten_khoa.setEditable(false); // Tu dong lay theo Ma Lop
        panel_nhap.add(txt_ten_khoa);

        panel_nhap.add(new JLabel(""));
        panel_nhap.add(new JLabel(""));

        JPanel panel_tim = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel_tim.add(new JLabel("Tim (Ma/Ten/Lop/Khoa):"));
        txt_tim_kiem = new JTextField(20);
        panel_tim.add(txt_tim_kiem);
        btn_tim_kiem = new JButton("Tim");
        panel_tim.add(btn_tim_kiem);

        panel_tren.add(panel_nhap, BorderLayout.CENTER);
        panel_tren.add(panel_tim, BorderLayout.SOUTH);
        add(panel_tren, BorderLayout.NORTH);

        String[] cot = {"Ma SV", "Ho Ten", "Ngay Sinh", "Gioi Tinh", "Dia Chi", "SDT", "Email", "Ma Lop", "Ten Khoa"};
        mo_hinh_bang = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bang_sv = new JTable(mo_hinh_bang);
        bang_sv.setRowHeight(24);
        add(new JScrollPane(bang_sv), BorderLayout.CENTER);

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

        // Tu dong cap nhat Ten Khoa khi chon Ma Lop khac
        cbo_ma_lop.addActionListener(e -> cap_nhat_ten_khoa());

        bang_sv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = bang_sv.getSelectedRow();
                if (r >= 0) {
                    txt_ma_sv.setText(mo_hinh_bang.getValueAt(r, 0).toString());
                    txt_ho_ten.setText(mo_hinh_bang.getValueAt(r, 1).toString());
                    txt_ngay_sinh.setText(mo_hinh_bang.getValueAt(r, 2).toString());
                    cbo_gioi_tinh.setSelectedItem(mo_hinh_bang.getValueAt(r, 3).toString());
                    txt_dia_chi.setText(mo_hinh_bang.getValueAt(r, 4) != null ? mo_hinh_bang.getValueAt(r, 4).toString() : "");
                    txt_sdt.setText(mo_hinh_bang.getValueAt(r, 5) != null ? mo_hinh_bang.getValueAt(r, 5).toString() : "");
                    txt_email.setText(mo_hinh_bang.getValueAt(r, 6) != null ? mo_hinh_bang.getValueAt(r, 6).toString() : "");
                    cbo_ma_lop.setSelectedItem(mo_hinh_bang.getValueAt(r, 7).toString());
                    txt_ten_khoa.setText(mo_hinh_bang.getValueAt(r, 8) != null ? mo_hinh_bang.getValueAt(r, 8).toString() : "");
                    txt_ma_sv.setEditable(false);
                }
            }
        });

        btn_them.addActionListener(e -> xu_ly_sv("INSERT INTO SinhVien (HoTen, NgaySinh, GioiTinh, DiaChi, SoDienThoai, Email, MaLop, MaSV) VALUES (?,?,?,?,?,?,?,?)", "Them SV thanh cong!"));
        btn_sua.addActionListener(e -> xu_ly_sv("UPDATE SinhVien SET HoTen=?, NgaySinh=?, GioiTinh=?, DiaChi=?, SoDienThoai=?, Email=?, MaLop=? WHERE MaSV=?", "Cap nhat SV thanh cong!"));
        btn_xoa.addActionListener(e -> xoa_sv());
        btn_lam_moi.addActionListener(e -> lam_moi());
        btn_tim_kiem.addActionListener(e -> tai_du_lieu_sv(txt_tim_kiem.getText().trim()));
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
            txt_ma_sv.setEditable(false);
            txt_ho_ten.setEditable(false);
            txt_ngay_sinh.setEditable(false);
            txt_dia_chi.setEditable(false);
            txt_sdt.setEditable(false);
            txt_email.setEditable(false);
            cbo_gioi_tinh.setEnabled(false);
            cbo_ma_lop.setEnabled(false);
        }
    }

    private void nap_danh_sach_lop() {
        try (Connection cn = JavaApplication1.lay_ket_noi();
             PreparedStatement ps = cn.prepareStatement("SELECT MaLop FROM Lop");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cbo_ma_lop.addItem(rs.getString("MaLop"));
            }
            cbo_ma_lop.setSelectedItem("TH30.03");
            cap_nhat_ten_khoa();
        } catch (Exception ignored) {}
    }

    private void cap_nhat_ten_khoa() {
        if (cbo_ma_lop.getSelectedItem() == null) return;
        String ma_lop = cbo_ma_lop.getSelectedItem().toString();
        String sql = "SELECT k.TenKhoa FROM Lop l JOIN Khoa k ON l.MaKhoa = k.MaKhoa WHERE l.MaLop = ?";
        try (Connection cn = JavaApplication1.lay_ket_noi();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, ma_lop);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    txt_ten_khoa.setText(rs.getString("TenKhoa"));
                }
            }
        } catch (Exception ignored) {}
    }

    private void tai_du_lieu_sv(String tu_khoa) {
        mo_hinh_bang.setRowCount(0);
        String sql = "SELECT sv.MaSV, sv.HoTen, sv.NgaySinh, sv.GioiTinh, sv.DiaChi, sv.SoDienThoai, sv.Email, sv.MaLop, k.TenKhoa "
                   + "FROM SinhVien sv "
                   + "JOIN Lop l ON sv.MaLop = l.MaLop "
                   + "JOIN Khoa k ON l.MaKhoa = k.MaKhoa";
        if (!tu_khoa.isEmpty()) {
            sql += " WHERE sv.MaSV LIKE ? OR sv.HoTen LIKE ? OR sv.MaLop LIKE ? OR k.TenKhoa LIKE ?";
        }

        try (Connection cn = JavaApplication1.lay_ket_noi(); PreparedStatement ps = cn.prepareStatement(sql)) {
            if (!tu_khoa.isEmpty()) {
                ps.setString(1, "%" + tu_khoa + "%");
                ps.setString(2, "%" + tu_khoa + "%");
                ps.setString(3, "%" + tu_khoa + "%");
                ps.setString(4, "%" + tu_khoa + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mo_hinh_bang.addRow(new Object[]{
                    rs.getString("MaSV"), rs.getString("HoTen"), rs.getString("NgaySinh"),
                    rs.getString("GioiTinh"), rs.getString("DiaChi"), rs.getString("SoDienThoai"),
                    rs.getString("Email"), rs.getString("MaLop"), rs.getString("TenKhoa")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi tai SV: " + ex.getMessage());
        }
    }

    private void xu_ly_sv(String sql, String thong_bao) {
        try (Connection cn = JavaApplication1.lay_ket_noi(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, txt_ho_ten.getText().trim());
            ps.setString(2, txt_ngay_sinh.getText().trim());
            ps.setString(3, cbo_gioi_tinh.getSelectedItem().toString());
            ps.setString(4, txt_dia_chi.getText().trim());
            ps.setString(5, txt_sdt.getText().trim());
            ps.setString(6, txt_email.getText().trim());
            ps.setString(7, cbo_ma_lop.getSelectedItem().toString());
            ps.setString(8, txt_ma_sv.getText().trim());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, thong_bao);
            lam_moi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi thao tac: " + ex.getMessage());
        }
    }

    private void xoa_sv() {
        String ma = txt_ma_sv.getText().trim();
        if (ma.isEmpty()) return;
        int xac_nhan = JOptionPane.showConfirmDialog(this, "Xoa sinh vien [" + ma + "]?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (xac_nhan == JOptionPane.YES_OPTION) {
            try (Connection cn = JavaApplication1.lay_ket_noi(); PreparedStatement ps = cn.prepareStatement("DELETE FROM SinhVien WHERE MaSV=?")) {
                ps.setString(1, ma);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xoa SV thanh cong!");
                lam_moi();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi xoa: " + ex.getMessage());
            }
        }
    }

    private void lam_moi() {
        txt_ma_sv.setText("");
        txt_ho_ten.setText("");
        txt_dia_chi.setText("");
        txt_sdt.setText("");
        txt_email.setText("");
        txt_tim_kiem.setText("");
        if ("Admin".equalsIgnoreCase(vai_tro)) {
            txt_ma_sv.setEditable(true);
        }
        cap_nhat_ten_khoa();
        bang_sv.clearSelection();
        tai_du_lieu_sv("");
    }
}
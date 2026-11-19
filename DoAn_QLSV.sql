-- ============================================================================
-- DO AN MON HOC: LAP TRINH JAVA (JAVA SWING + MYSQL)
-- DE TAI: QUAN LY SINH VIEN (DoAn_QLSV)
-- ============================================================================

DROP DATABASE IF EXISTS DoAn_QLSV;
CREATE DATABASE DoAn_QLSV CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE DoAn_QLSV;

-- ============================================================================
-- PHAN 1: TAO CAU TRUC CAC BANG VA RANG BUOC TOAN VEN
-- ============================================================================

-- 1. Bang Khoa
CREATE TABLE Khoa (
    MaKhoa VARCHAR(20) PRIMARY KEY,
    TenKhoa VARCHAR(100) NOT NULL UNIQUE,
    SoDienThoai VARCHAR(15) UNIQUE,
    Email VARCHAR(100) UNIQUE
);

-- 2. Bang Lop
CREATE TABLE Lop (
    MaLop VARCHAR(20) PRIMARY KEY,
    TenLop VARCHAR(100) NOT NULL,
    KhoaHoc VARCHAR(20) NOT NULL,
    MaKhoa VARCHAR(20) NOT NULL,
    CONSTRAINT fk_lop_khoa FOREIGN KEY (MaKhoa) 
        REFERENCES Khoa(MaKhoa) 
        ON UPDATE CASCADE 
        ON DELETE RESTRICT
);

-- 3. Bang SinhVien
CREATE TABLE SinhVien (
    MaSV VARCHAR(20) PRIMARY KEY,
    HoTen VARCHAR(100) NOT NULL,
    NgaySinh DATE NOT NULL,
    GioiTinh ENUM('Nam', 'Nữ') NOT NULL DEFAULT 'Nam',
    DiaChi VARCHAR(255),
    SoDienThoai VARCHAR(15) UNIQUE,
    Email VARCHAR(100) UNIQUE,
    MaLop VARCHAR(20) NOT NULL,
    CONSTRAINT fk_sv_lop FOREIGN KEY (MaLop) 
        REFERENCES Lop(MaLop) 
        ON UPDATE CASCADE 
        ON DELETE RESTRICT
);

-- 4. Bang MonHoc
CREATE TABLE MonHoc (
    MaMH VARCHAR(20) PRIMARY KEY,
    TenMH VARCHAR(100) NOT NULL UNIQUE,
    SoTinChi INT NOT NULL,
    CONSTRAINT chk_tin_chi CHECK (SoTinChi BETWEEN 1 AND 10)
);

-- 5. Bang Diem
CREATE TABLE Diem (
    MaSV VARCHAR(20) NOT NULL,
    MaMH VARCHAR(20) NOT NULL,
    DiemChuyenCan DECIMAL(4,2) DEFAULT 0,
    DiemGiuaKy DECIMAL(4,2) DEFAULT 0,
    DiemCuoiKy DECIMAL(4,2) DEFAULT 0,
    PRIMARY KEY (MaSV, MaMH),
    CONSTRAINT fk_diem_sv FOREIGN KEY (MaSV) 
        REFERENCES SinhVien(MaSV) 
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
    CONSTRAINT fk_diem_mh FOREIGN KEY (MaMH) 
        REFERENCES MonHoc(MaMH) 
        ON UPDATE CASCADE 
        ON DELETE RESTRICT,
    CONSTRAINT chk_diem_cc CHECK (DiemChuyenCan BETWEEN 0 AND 10),
    CONSTRAINT chk_diem_gk CHECK (DiemGiuaKy BETWEEN 0 AND 10),
    CONSTRAINT chk_diem_ck CHECK (DiemCuoiKy BETWEEN 0 AND 10)
);

-- 6. Bang TaiKhoan (Phuc vu dang nhap va phan quyen he thong)
CREATE TABLE TaiKhoan (
    TenDangNhap VARCHAR(50) PRIMARY KEY,
    MatKhau VARCHAR(100) NOT NULL,
    VaiTro ENUM('Admin', 'SinhVien') NOT NULL DEFAULT 'SinhVien'
);

-- ============================================================================
-- PHAN 2: THEM DU LIEU MAU
-- ============================================================================

-- 1. Du lieu Khoa
INSERT INTO Khoa (MaKhoa, TenKhoa, SoDienThoai, Email) VALUES
('CNTT', 'Công nghệ thông tin', '0243123456', 'cntt@university.edu.vn'),
('KTPM', 'Kỹ thuật phần mềm', '0243123457', 'ktpm@university.edu.vn'),
('QTKD', 'Quản trị kinh doanh', '0243123458', 'qtkd@university.edu.vn');

-- 2. Du lieu Lop
INSERT INTO Lop (MaLop, TenLop, KhoaHoc, MaKhoa) VALUES
('TH30.03', 'Tin học ứng dụng 03 - K30', 'K30', 'CNTT'),
('TH30.01', 'Tin học ứng dụng 01 - K30', 'K30', 'CNTT'),
('PM30.01', 'Kỹ thuật phần mềm 01 - K30', 'K30', 'KTPM');

-- 3. Du lieu SinhVien (6 sinh vien lop TH30.03 + 2 sinh vien lop khac)
INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, SoDienThoai, Email, MaLop) VALUES
('SV300301', 'Trần Hoàng Trường', '2006-05-14', 'Nam', 'Hà Nội', '0912345601', 'truong.th@sv.edu.vn', 'TH30.03'),
('SV300302', 'Nguyễn Minh Anh', '2006-08-22', 'Nữ', 'Hải Phòng', '0912345602', 'anh.nm@sv.edu.vn', 'TH30.03'),
('SV300303', 'Lê Đức Huy', '2006-02-10', 'Nam', 'Nam Định', '0912345603', 'huy.ld@sv.edu.vn', 'TH30.03'),
('SV300304', 'Phạm Thu Hà', '2006-11-30', 'Nữ', 'Bắc Ninh', '0912345604', 'ha.pt@sv.edu.vn', 'TH30.03'),
('SV300305', 'Hoàng Gia Bảo', '2006-07-19', 'Nam', 'Thái Bình', '0912345605', 'bao.hg@sv.edu.vn', 'TH30.03'),
('SV300306', 'Vũ Khánh Linh', '2006-09-05', 'Nữ', 'Hưng Yên', '0912345606', 'linh.vk@sv.edu.vn', 'TH30.03'),
('SV300101', 'Đặng Quốc Tuấn', '2006-03-12', 'Nam', 'Hà Nội', '0912345607', 'tuan.dq@sv.edu.vn', 'TH30.01'),
('SV300102', 'Bùi Phương Thảo', '2006-12-01', 'Nữ', 'Hải Dương', '0912345608', 'thao.bp@sv.edu.vn', 'PM30.01');

-- 4. Du lieu MonHoc
INSERT INTO MonHoc (MaMH, TenMH, SoTinChi) VALUES
('JAVA01', 'Lập trình Java nâng cao', 3),
('CSDL01', 'Hệ quản trị cơ sở dữ liệu', 3),
('CTDL01', 'Cấu trúc dữ liệu và giải thuật', 4),
('MMT01', 'Mạng máy tính', 3);

-- 5. Du lieu Diem
INSERT INTO Diem (MaSV, MaMH, DiemChuyenCan, DiemGiuaKy, DiemCuoiKy) VALUES
('SV300301', 'JAVA01', 9.5, 8.5, 9.0),
('SV300301', 'CSDL01', 9.0, 8.0, 8.5),
('SV300302', 'JAVA01', 10.0, 9.0, 8.5),
('SV300303', 'JAVA01', 8.0, 7.5, 7.0),
('SV300304', 'CSDL01', 9.5, 8.5, 9.0),
('SV300305', 'CTDL01', 8.5, 7.0, 7.5),
('SV300306', 'JAVA01', 9.0, 8.0, 8.5);

-- 6. Du lieu TaiKhoan
INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro) VALUES
('admin', '123456', 'Admin'),
('SV300301', '123456', 'SinhVien'),
('SV300302', '123456', 'SinhVien');
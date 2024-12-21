# Hướng dẫn cài đặt dự án "Quản lý bất động sản" từ GitHub

## Các bước thực hiện:

### 1. Tải source code về:
```bash
git clone https://github.com/nhatdizi/estore.git
```

### 2. Thiết lập cơ sở dữ liệu:
- Mở MySQL Workbench.
- Tạo một database mới.
- Import cơ sở dữ liệu từ file `estorejdbc.sql` trong source code.

### 3. Chạy source code:
- Mở dự án bằng IntelliJ IDEA.
- Chạy ứng dụng.

## Tài khoản đăng nhập mẫu:

### Admin:
- **Username:** nhat 
- **Password:** 123456

### Khách hàng:
- **Username:** quang  
- **Password:** 123456

## Yêu cầu hệ thống:
- Máy tính cần cài đặt **JDK 17** trở lên.

## Phạm vi của dự án:

### Đăng nhập:
- Phân quyền: Admin và User.

### Admin:
- Quản lý sản phẩm.
- Quản lý danh mục

### Khách hàng:
- Đăng nhập
- Đăng ký
- Tìm kiếm sản phẩm
- Thêm sản phẩm vào giỏ hàng
- Xem danh sách sản phẩm
- Xem thông tin giỏ hàng
- Cập nhật giỏ hàng
- Thêm sản phẩm vào danh sách yêu thích

const menuItems = document.querySelectorAll('.nav-links li');

// Lấy trạng thái từ localStorage
let activePage = localStorage.getItem('activeMenu');

// Nếu chưa có giá trị, mặc định là "index.html"
if (!activePage) {
    activePage = 'index.html';
    localStorage.setItem('activeMenu', activePage);
}

// Áp dụng lớp "active" dựa trên trạng thái
menuItems.forEach((item) => {
    const link = item.querySelector('a').getAttribute('href');
    if (link === activePage) {
        item.classList.add('active');
    } else {
        item.classList.remove('active');
    }
});

// Thêm sự kiện cho từng mục menu
menuItems.forEach((item) => {
    item.addEventListener('click', function (event) {
        // Ngăn hành động mặc định
        event.preventDefault();

        // Loại bỏ lớp active khỏi tất cả mục
        menuItems.forEach((menuItem) => menuItem.classList.remove('active'));

        // Thêm lớp active vào mục được nhấn
        item.classList.add('active');

        // Lưu trạng thái vào localStorage
        const link = item.querySelector('a').getAttribute('href');
        localStorage.setItem('activeMenu', link);

        // Điều hướng đến trang mới
        location.href = link;
    });
});

const logoutButton = document.querySelector('.logout-mode li a');

logoutButton.addEventListener('click', (event) => {
    // Ngăn hành động mặc định (nếu cần)
    event.preventDefault();

    // Xóa trạng thái activeMenu khỏi localStorage
    localStorage.removeItem('activeMenu');

    // Điều hướng đến trang đăng nhập
    // location.href = 'login.html';
});

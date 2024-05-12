Automated language test registration
====================================
[Requirements](#requirements)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
  - Tải về và cài đặt
- [Maven](https://maven.apache.org/download.cgi)
  - [Hướng dẫn cài đặt](https://stackjava.com/install/maven-phan-1-maven-la-gi-cai-dat-maven.html)

[Hướng dẫn nhập dữ liệu](#data-instruction)
- Dữ liệu được lưu theo từng dòng trong file excel:
  - Cột thứ nhất: username
  - Cột thứ hai: password
  - Cột thứ ba: thông tin ngày thi được chia cách bởi dấu `::`(hai dấu hai chấm) gồm 5 trường:
    - Trường thứ nhất: ngày thi `2024/07/06`
    - Trường thứ hai: Địa điểm thi (copy phần tiếng trung)
    - Trường thứ ba: khung giờ thi (08:10)
    - Trường thứ tư: Band (Band A, Band B, Band C)
    - Trường thứ năm: loại tiếng (Traditional, Simplified)
  - Tất cả những cột sau cột thứ ba là thông tin ngày thi thêm, sắp xếp theo thứ tự ưu tiên giảm dần
- Mỗi dòng là một học sinh
- Nếu dữ liệu bắt đầu bằng `0`, thêm `'` trước trường dữ liệu để tránh bị xóa mất số `0`
- Ví dụ: Username, Password, 2024/06/23::越南_越南城東大學 越南_越南城東大學::( 08:00 - 10:00 )::Band A::Traditional

[Hướng dẫn chạy chương trình](#program-instruction)
- Chọn phần đường dẫn của folder, xóa đi và thêm `cmd` vào rồi chạy
- Chạy câu lệnh `mvn -B clean test -DdataPath="Auto-login.xlsx"` để bắt đầu đăng ký. Thay thế tên file `Auto-login.xlsx` bằng tên file cụ thể
  - Sử dụng thêm `-Ddataproviderthreadcount=number` để đặt số lượng người dùng cụ thể trong 1 lần đăng ký
- Sau khi chương trình chạy hoàn thành, xem thư mục `succeeded` và `failed` bên trong `target` thể kiểm tra kết quả

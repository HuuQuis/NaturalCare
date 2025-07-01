# Hướng dẫn cài đặt và chạy dự án NaturalCare

## 📋 Mục lục
1. [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
2. [Clone dự án](#clone-dự-án)
3. [Cài đặt cơ sở dữ liệu](#cài-đặt-cơ-sở-dữ-liệu)
4. [Cấu hình ứng dụng](#cấu-hình-ứng-dụng)
5. [Import dữ liệu địa phương](#import-dữ-liệu-địa-phương)
6. [Biên dịch và chạy ứng dụng](#biên-dịch-và-chạy-ứng-dụng)
7. [Truy cập ứng dụng](#truy-cập-ứng-dụng)
8. [Khắc phục sự cố](#khắc-phục-sự-cố)

## 🛠️ Yêu cầu hệ thống

### Phần mềm cần thiết:
- **Java Development Kit (JDK) 11** hoặc cao hơn
- **Apache Maven 3.6+**
- **MySQL 8.0+** 
- **IDE** (IntelliJ IDEA, Eclipse, hoặc NetBeans)
- **Git** để clone dự án

### Kiểm tra phiên bản:
```bash
java -version
mvn -version
mysql --version
git --version
```

## 📥 Clone dự án

1. **Clone repository từ Git:**
```bash
git clone <repository-url>
cd NaturalCare
```

2. **Kiểm tra cấu trúc dự án:**
```
NaturalCare/
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   └── webapp/
│   └── test/
├── database/
├── pom.xml
└── README.md
```

## 🗄️ Cài đặt cơ sở dữ liệu

### Bước 1: Tạo database
1. **Khởi động MySQL server**
2. **Đăng nhập MySQL:**
```bash
mysql -u root -p
```

3. **Tạo database và import schema:**
```sql
-- Tạo database
CREATE DATABASE natural_care;

-- Sử dụng database
USE natural_care;

-- Import schema
SOURCE path/to/NaturalCare/database/natural_care_schema.sql;
```

### Bước 2: Import dữ liệu mẫu
```sql
-- Import dữ liệu 
SOURCE path/to/NaturalCare/database/natural_care_data.sql;

```

### Bước 3: Tạo user database (tùy chọn)
```sql
-- Tạo user riêng cho ứng dụng
CREATE USER 'naturalcare_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON natural_care.* TO 'naturalcare_user'@'localhost';
FLUSH PRIVILEGES;
```

## ⚙️ Cấu hình ứng dụng

### Cấu hình database:
Chỉnh sửa file `src/main/resources/config.properties`:

```properties
# Database Configuration
db.user=your_username
db.password=your_password
db.url=jdbc:mysql://localhost:3306/natural_care?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

# Mail Configuration (cho tính năng reset password)
mail.username=your_email@gmail.com
mail.password=your_app_password

# Application Base URL
app.base.url=http://localhost:8080/NaturalCare
```

### ⚠️ Quan trọng:
- **Thay đổi `db.user` và `db.password`** theo cấu hình MySQL của bạn
- **Cập nhật `mail.username` và `mail.password`** nếu muốn sử dụng tính năng gửi email
- **Điều chỉnh `app.base.url`** theo port server của bạn

## 🌏 Import dữ liệu địa phương

Theo hướng dẫn trong README, cần chạy một lần để import dữ liệu địa phương:

### Cách 1: Chạy từ IDE
1. Mở file `src/main/java/utils/ImportAddressWithLatLng.java`
2. Right-click → **Run 'ImportAddressWithLatLng.main()'**

### Cách 2: Chạy từ command line
```bash
# Biên dịch project trước
mvn compile

# Chạy class ImportAddressWithLatLng
mvn exec:java -Dexec.mainClass="utils.ImportAddressWithLatLng" -Dexec.classpathScope="compile"
```

### ⚠️ Lưu ý:
- Quá trình này sẽ mất **khá nhiều thời gian** (có thể 1-2 giờ)
- Cần **kết nối internet** để lấy tọa độ từ OpenStreetMap
- **Chỉ chạy 1 lần duy nhất** sau khi tạo database

## 🚀 Biên dịch và chạy ứng dụng

### Bước 1: Clean và compile project
```bash
mvn clean compile
```

### Bước 2: Package ứng dụng
```bash
mvn package
```

### Bước 3: Deploy và chạy

#### Cách 1: Sử dụng IDE
1. **Import project** vào IDE (IntelliJ IDEA/Eclipse/NetBeans)
2. **Cấu hình Tomcat server** trong IDE
3. **Deploy** project lên Tomcat
4. **Start server**

#### Cách 2: Sử dụng Maven plugin
```bash
# Nếu có maven tomcat plugin
mvn tomcat7:run
# hoặc
mvn jetty:run
```

#### Cách 3: Deploy thủ công lên Tomcat
1. **Copy** file `target/NaturalCare-1.0-SNAPSHOT.war` 
2. **Paste** vào thư mục `webapps` của Tomcat
3. **Start Tomcat server**

## 🌐 Truy cập ứng dụng

Sau khi khởi động thành công:

### URL truy cập:
- **Trang chủ:** `http://localhost:8080/NaturalCare/`
- **Hoặc:** `http://localhost:8080/NaturalCare/home`

### Tài khoản mặc định:
(Kiểm tra trong database hoặc file data SQL để biết tài khoản admin/user mặc định)

## 🔧 Khắc phục sự cố

### Lỗi kết nối database:
```
Caused by: java.sql.SQLException: Access denied for user...
```
**Giải pháp:** Kiểm tra `config.properties`, đảm bảo username/password MySQL chính xác.

### Lỗi thiếu dependency:
```
ClassNotFoundException: com.mysql.cj.jdbc.Driver
```
**Giải pháp:** Chạy `mvn clean install` để download dependencies.

### Lỗi port bị chiếm:
```
Port 8080 already in use
```
**Giải pháp:** 
- Thay đổi port trong server configuration
- Hoặc stop process đang dùng port 8080

### Database không tồn tại:
```
Unknown database 'natural_care'
```
**Giải pháp:** Kiểm tra lại việc tạo database và import schema.

### Lỗi import địa phương:
```
Không thể kết nối CSDL!
```
**Giải pháp:** 
- Kiểm tra MySQL đã start
- Kiểm tra config.properties
- Đảm bảo database đã được tạo

## 📚 Thông tin bổ sung

### Cấu trúc database:
- **natural_care_schema.sql:** Tạo bảng và cấu trúc
- **natural_care_data.sql:** Dữ liệu mẫu

### Công nghệ sử dụng:
- **Backend:** Java Servlet, JSP, JSTL
- **Database:** MySQL 8.0
- **Build Tool:** Maven
- **Server:** Apache Tomcat
- **Email:** Jakarta Mail API

### Cấu trúc thư mục quan trọng:
```
src/main/
├── java/
│   ├── controller/     # Servlet controllers
│   ├── dal/           # Database access layer  
│   ├── model/         # Entity classes
│   ├── filter/        # Request filters
│   └── utils/         # Utility classes
├── resources/
│   └── config.properties  # Configuration file
└── webapp/
    ├── view/          # JSP pages
    ├── css/           # Stylesheets
    ├── js/            # JavaScript files
    └── WEB-INF/       # Web configuration
```

---

**📞 Hỗ trợ:** Nếu gặp vấn đề, vui lòng tạo issue trên repository hoặc liên hệ team phát triển.

**🎉 Chúc bạn setup thành công!**
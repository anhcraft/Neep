# Hưỡng dẫn

Sau đây là các hưỡng dẫn cơ bản giúp bạn làm quen với Neep.

### Neep là gì?
Neep là một format cấu hình đơn giản và tiện lợi.
<br><br>
Ý tưởng của Neep ra đời bắt nguồn từ Yaml - là một loại format lưu trữ dữ liệu dưới dạng có thể đọc được. Mặc dù Yaml được sử dụng rộng rãi nhưng có những nhược điểm như:
- Có quá nhiều quy tắc, không phù hợp với những người mới làm quen
- Dễ gặp lỗi (chẳng hạn, chỉ cần thiếu một khoảng trắng)
- Không thích hợp để lưu trữ các dữ liệu lớn do có thời gian đọc-viết khá chậm
- Các cặp khoá-giá trị cần phải nằm ở các dòng khác nhau, từ đó làm file trở nên dài thêm
<br><br>
Neep sẽ phần nào khắc phục những điều đó bởi các yếu tố như:
- Dễ học: Neep có rất ít các quy tắc, bạn chỉ cần dành vài phút để học thuần thục!
- Đơn giản & tiện lợi: Các quy tắc để viết Neep rất gọn nhẹ, tránh lan mang và dài dòng; và có nhiều kiểu viết khác nhau phù hợp với sở thích mỗi người

**Ví dụ:**
```
day_la_khoa  day_la_gia_tri  # day la ghi chu
so 100
trang_thai true
muc {
    chuoi "day la chuoi"
    hay_tinh_toan "1 * 2 * 3 + 4 * 5 * 6"
}
```

### Kiểu dữ liệu
- Comment (Kiểu ghi chú)
- Expression (Kiểu biều thức toán học)
- Primitive (Các kiểu nguyên thuỷ)
  + Boolean (Kiểu Logic đúng/sai)
  + Integer (Kiểu số nguyên 32-bit)
  + Long (Kiểu số nguyên 64-bit)
  + Double (Kiểu số thực)
  + String (Kiểu chuỗi)
- Container (Các kiểu chứa)
  + List (Kiểu danh sách)
  + Section (Kiểu danh mục)

### Quy tắc căn bản
Điều đầu tiên bạn cần phải biết một cặp khoá-giá trị là gì!<br>
Hãy lấy từ ví dụ trên:
```
day_la_khoa day_la_gia_tri
```
Khoá và giá trị luôn đi với nhau để tạo thành một cặp.
- Khoá: 
    + Dùng để nhận biết và phân biệt với các cặp khoá-giá trị khác. Khoá chỉ bao gồm các kí tự a-z, A-Z, 0-9, dấu gạch ngang (-) và dấu gạch dưới (_). Và một chú ý nữa là có phân biệt hoa-thường, tức là ví dụ khoá _ten_cua_ban_ sẽ khác với _TEN_CUA_BAN_
    + Bạn có thể đặt tên khoá theo ý thích. Tuy nhiên một lưu ý **quan trọng** đó là, nếu bạn chỉnh cấu hình đã có sẵn (ví dụ được cung cấp bởi một phần mềm nào đó), tốt nhất **đừng nên thay đổi khoá đã có sẵn** bởi nó sẽ gây ra lỗi! Chẳng hạn như, một phần mềm được thiết kế để đọc khoá _username_ nhằm lấy tên tài khoản, nếu bạn đổi khoá đó thành _USERNAME_, điều đó có thể gây ra lỗi.
- Giá trị:
    + Giá trị luôn đi cùng với khoá. Do vậy, dù hai cặp khoá-giá trị có cùng một giá trị giống nhau, chúng hoàn toàn không liên quan tới nhau bởi khác nhau về khoá.
    + Đây là chỗ được phép tuỳ chỉnh một cách thoải mái để phù hợp với nhu cầu của bạn
- Khoá và giá trị phải tách rời nhau: tức là phải nằm ở hai dòng khác nhau, hoặc nếu ở chung một dòng thì phải cách nhau bởi ít nhất một khoảng trắng 
```
day_la_khoa 

            day_la_gia_tri
```
Hoàn toàn dễ hiểu phải không :>

### Kiểu ghi chú
Thông thường, nếu chỉ nhìn vào khoá, rất khó để bạn có thể đoán nó mang nghĩa gì, liệu việc chỉnh sửa giá trị tại khoá này sẽ mang lại tác dụng gì?, vvv. Vì thế trong các cấu hình phần mềm chẳng hạn, bạn thấy người ta hay để các ghi chú để mọi người tiện xem và hiểu được ^^<br>
**Ví dụ:**
```
# Tài khoản quản trị viên
username admin
# Mật khẩu của tài khoản
password root # Tránh đặt mật khẩu quá đơn giản nhé!
```
Có hai kiểu ghi chú trong Neep đó là:
- Ghi chú cùng dòng: đằng sau mỗi cặp khoá-giá trị sẽ là một ghi chú
- Ghi chú toàn dòng: dành riêng một dòng ra để ghi chú!
<br>
Dù thuộc kiểu nào thì đều có chung một quy tắc đó là: **thêm dấu thăng (#) vào trước nội dung cần ghi chú**.
<br>

**Quy ước:**

- Mọi kí tự kể từ sau dấu thăng đến cuối dòng đều nằm trong một ghi chú

- Ghi chú không thể gộp. Ví dụ ```# ghi chu 1 # ghi chu 2``` là một ghi chú, có nội dung là ```ghi chu 1 # ghi chu 2```

### Kiểu nguyên thuỷ
Đây là kiểu dữ liệu thông dụng nhất, áp dụng quy tắc khoá-giá trị.<br>
Kiểu nguyên thuỷ bao gồm nhiều kiểu con như chuỗi, số nguyên, số thực, vvv.<br>
Đây là cách mà Neep sẽ phân loại kiểu dữ liệu nguyên thuỷ:
- Nếu nội dung là _true_ hoặc _false_ thì sẽ là kiểu **Boolean**
- Nếu nội dung là số thực sẽ là kiểu **Double**
- Nếu nội dung là số nguyên:
    + Từ 9 kí tự trở xuống không gồm dấu âm/dương: kiểu **Integer**
    + Còn dài hơn nữa sẽ là kiểu **Long**
- Còn lại đều thuộc kiểu **String**
<br>

**Ví dụ:**
```
ho_va_ten   "Nguyen Van A"
tuoi        21
cong_viec   freelancer
doc_than    true
vi_tien     16500000.700
```

**Chú ý đối với giá trị**
- Giá trị nên được đặt trong cặp dấu ngoặc kép ("), bởi lúc này phần giá trị sẽ được tính từ dấu ngoặc kép đầu tiên cho tới dấu ngoặc kép tiếp theo, tức là nội dung được phép nằm trên **nhiều dòng**.
```
gioi_thieu "
              Toi ten la Nguyen Van A, doc than, nam nay 21 tuoi
              Toi la mot freelancer, chuyen ve mang thiet ke
            "
```

- Giả sử nội dung có dấu ngoặc kép thì sao? Lúc này bạn phải thêm dấu gạch chéo ngược (\\) ở phía trước để triệt tiêu tác dụng của dấu ngoặc kép, Neep sẽ hiểu rằng nội dung nên được tiếp tục thay vì dừng ở đây.
```
story "
        Nam 1969, mot nguoi Dong Lao da tung noi:
        \" asdasdasadasadasadadaadasasasadadas \"
        Cho toi nay van chua ai hieu duoc.
      " # mot cau chuyen vui
```

- Bạn cảm thấy dấu ngoặc kép này có vẻ phiền toái? Nếu nội dung của bạn **không cần nằm trên nhiều dòng** thì bạn không bắt buộc phải thêm dấu đó ^^. Chẳng hạn:
```
username admin # tai khoan
password root # mat khau
```

### Kiểu biểu thức toán học
Kiểu này tương tự như kiểu nguyên thuỷ, đó là theo quy tắc khoá-giá trị trong đó giá trị là một biểu thức toán học, có số và các dấu. Biểu thức sẽ được tính toán khi phần mềm lấy giá trị.<br>
Với kiểu này, giá trị của bạn bắt buộc phải đặt trong cặp dấu huyền (`). Nếu bạn không đặt trong cặp dấu này, Neep sẽ tự hiểu giá trị có kiểu nguyên thuỷ.
```
tinh_toan `1 + 2 + 3 + 4 + 5`
```
Kiểu này rất phù hợp cho các việc tính toán liên quan tới thời gian, thời lượng, khoảng cách, vvv. Chẳng hạn:
```
thoi_gian "60 * 60 * 24 * 3" # doi vi: giay
```
Như ví dụ trên, đơn vị của giá trị được quy ước là giây, vì thế bạn có thể dùng biểu thức trên để quy đổi 3 ngày thành giây. Giá sử bạn muốn chỉnh 3 ngày thành 5 ngày, thì chỉ cần sửa số 3 thành 5 là được ^^! Không phải tính toán lại, mà nhìn vào cũng rất dễ hiểu đúng không :>

### Kiểu chứa
Kiểu chứa là kiểu có khả năng chứa nhiều cặp khoá-giá trị! Mục đích là sắp xếp chúng vào cùng một loại, cùng một mục đích, để bạn dễ tìm và chỉnh sửa hơn.<br>
Có hai kiểu chứa:
- Kiểu danh sách:
    + Bao gồm nhiều phần tử là các cặp khoá-giá trị. **Tuy nhiên, bạn chỉ phải viết phần giá trị**, còn khoá sẽ được tạo tự động dựa theo vị trí của phần tử hiện tại (bắt đầu từ 0)
    + Các phần tử phải nằm trong cặp ngoặc vuông ([ ... ]). Hai dấu ngoặc vuông này phải tách biệt nhau, và tách biệt so với khoá của danh sách.
    + Các phần tử không bắt buộc phải cùng một kiểu, chẳng hạn một danh sách vừa gồm chuỗi, vừa gồm số, vừa có kiểu logic, vừa có kiểu chứa được tính là hợp lệ.
    + Ví dụ một vài cách trình bày kiểu này:
```
danh_sach_1 [
    con_1 # Khoá 0
    con_2 # Khoá 1
    con_3 # Khoá 2
]

danh_sach_2
[
    -3 # Khoá 0; Kiểu Integer
    5 # Khoá 1; Kiểu Integer
    8 # Khoá 2; Kiểu Integer
    true # Khoá 3; Kiểu Boolean
    "đây là một chuỗi" # Khoá 4; Kiểu String
]

danh_sach_3 [ 1 2 3 4 5 6 ]
danh_sach_4 [ "a" "b" "c" "d" ]
```
- Kiểu danh mục:
    + Tương tự như kiểu danh sách, tuy nhiên bạn phải ghi cả khoá nữa!
    + Các phần tử phải đặt trong cặp dấu ngoặc nhọn ({ ... })
    + Và cũng như kiểu danh sách, các phần tử không cần thuộc về một kiểu
    + Ví dụ:
```
thong_tin_co_ban {
    ten "Nguyen Van B"
    tuoi 10
}

thanh_tuu [
    {
        thoi_gian "27/10/2068"
        noi_dung "Ra doi!"
    }
    {
        thoi_gian "?/?/2074"
        noi_dung "Bat dau di hoc, huhuhu"
    }
    {
        thoi_gian "28/10/2076"
        noi_dung "Bi duoi hoc"
    }
    {
        thoi_gian "31/12/2077"
        noi_dung "Tu nan. Ly do: ???"
    }
]

lien_he {
    website "https://nguyenvanb.me/"
    facebook "Nguyen Van B"
    twitter "nguyenvanb"
    linkedin "nvb2077"
}
```

Đó là toàn bộ kiến thức mà bạn cần để làm quen với Neep!

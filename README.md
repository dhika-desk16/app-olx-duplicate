BEELI - ANDROID APP 
=

API
-
## A. Persiapan
1. Buat file `.env`
2. Copy isi di file `.env.example` dan paste ke `.env` yang baru saja dibuat
3. Pastikan perangkat tersambung di router
4. **Jalankan API**
    - Menjalankan api secara otomatis: jalankan/buka file `local-start.bat` di `RESTful-API\local-start.bat`
    - Menjalankan api secara manual:
      ```
      php artisan serve --host= --port=80
      ```


Android APP
-
## A. Persiapan

1. **Pastikan API sudah dijalankan.**

2. **Ubah Base URL di `ApiService.java`:**
   - Buka file `app\src\main\java\com\example\beeli\retrofit\ApiService.java`.
   - Ubah nilai variabel `private String Base_URL` sesuai URL API, misalnya:
     ```java
     private String Base_URL = "http://192.168.1.20/api/";
     ```

3. **Ubah Domain di `domain.xml`:**
   - Buka file `app\src\main\res\xml\domain.xml`.
   - Ubah nilai domain sesuai dengan API, misalnya:
     ```xml
     <domain includeSubdomains="true">192.168.1.20</domain>
     ```

4. **Jalankan Aplikasi.**

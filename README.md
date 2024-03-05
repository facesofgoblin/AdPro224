Nama    : Rana Koesumastuti

NPM     : 2206083496

Kelas   : AdPro C

# Tutorial 1
## Refleksi 1:
Setelah mengimplementasi 2 fitur baru berupa edit dan delete, saya telah belajar standar koding dari modul ini dan mempraktikkannya dalam kedua fitur ini.
Adapun clean code principles dan secure coding practices yang telah diimplementasikan adalah:

### Prinsip Clean Code:

1. Penamaan yang Deskriptif: variabel, metode, dan kelas diberikan nama yang deskriptif seperti service, product, createProductPage, dan sebagainya.
2. Modularitas dan SRP: Kode terbagi menjadi kelas-kelas yang memiliki tanggung jawab yang jelas, seperti ProductController, ProductService, dan ProductRepository.
3. Penggunaan Anotasi: Anotasi Spring digunakan secara konsisten dan tepat untuk mendefinisikan komponen dan menangani permintaan HTTP.
4. Format Kode dan Gaya: Format kode dalam kode yang disediakan terlihat konsisten dan rapi, mengikuti konvensi penulisan Java standar.

Akan tetapi, saya merasa belum mengimplementasikan prinsip clean code secara menyeluruh terlebih secure coding practices. Misalnya, saya belum melakukan validasi input, penanganan kesalahan, otentikasi dan otorisasi. 


## Refleksi 2:

1. Menulis unit test penting untuk memastikan bahwa kode berfungsi dengan baik. Code coverage bisa digunakan untuk memastikan sebagian besar kode telah diuji, tetapi memiliki 100% coverage tidak menjamin bebas dari bug. Selain itu, test harus efektif dalam memverifikasi fungsionalitas yang diharapkan dan menangani kasus-kasus khusus. Oleh karena itu, kita diminta untuk membuat test untuk kasus positif dan negatif untuk mengantisipasi.

2. Dalam pembuatan test fungsional baru, penting untuk memperhatikan kebersihan kode. Potensi masalah meliputi:
Duplikasi Kode: Risiko duplikasi kode tinggi jika setup dan variabel sama dengan test sebelumnya.

### Saran perbaikan meliputi:

Refactoring untuk Mengurangi Duplikasi: Gunakan abstraksi untuk setup serupa atau pindahkan kode ke metode helper.
Parameterized Tests: Gunakan parameterized tests untuk mengurangi duplikasi dan membuat test lebih efisien.

# Tutorial 2
1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
   Adapun masalah-masalah kode yang saya perbaiki adalah kode unit dan functional tests saya yang masih belum benar. Selain itu, saya juga menambahkan beberapa unit test dan functional test untuk meng-_cover_ kondisi positif dan negatif.
    Masalah yang saya hadapi juga meliputi kebingungan saya untuk memikirkan kasus-kasus khusus yang perlu dicover oleh test.
2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
   Saya merasa bahwa CI/CD workflows saya sudah memenuhi definisi dari CI/CD itu sendiri sebab alur kerja secara konsisten mengintegrasikan perubahan kode, menjalankan pengujian otomatis, dan secara otomatis menerapkan perubahan ke lingkungan _development_

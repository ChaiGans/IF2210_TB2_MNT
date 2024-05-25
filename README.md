# IF2210_TB2_MNT

<h1 align="center"> Tugas Besar 2 IF2210 Pemrograman Berorientasi Objek</h1>

## Identitas Pengembang Program

### **Kelompok MNT**

|   NIM    |            Nama            |
| :------: | :------------------------: |
| 13522019 |        Wilson Yusda        |
| 13522021 |          Filbert           |
| 13522045 |        Elbert Chailes      |
| 13522085 |      Zahira Dina Amalia    |
| 13522111 |     Ivan Hendrawan Tan     |

## Deskripsi Program

Dalam proyek tugas besar ini, dikembangkan sebuah aplikasi dengan antarmuka grafis yang mensimulasikan pengelolaan ladang secara interaktif. Ladang dalam aplikasi ini disusun dalam bentuk matriks dengan beberapa petak yang bisa ditanami berbagai jenis tanaman atau diisi dengan hewan. Sistem ini menyerupai permainan kartu di mana setiap kartu mewakili tanaman, hewan atau produk tertentu. Pemain dapat menempatkan kartu ini di ladang mereka kecuali produk dan dapat menjualnya produk di toko ketika berada dalam tumpukan kartu aktif.

Program ini juga dilengkapi dengan kemungkinan adanya serangan beruang yang menghancurkan objek di ladang secara acak, menambah tantangan dalam permainan. Terdapat fitur untuk menyimpan dan memuat kembali kondisi permainan melalui file teks, serta kemampuan untuk mengintegrasikan format file lain melalui sistem plugin yang meningkatkan fleksibilitas dalam penyimpanan data. Permainan ini diikuti oleh dua pemain yang memiliki ladang dan kartu masing-masing. Pemenang ditentukan berdasarkan jumlah uang yang dikumpulkan setelah 20 giliran permainan. Aplikasi dirancang untuk menghadirkan pengalaman yang menantang dan dinamis, dengan kemampuan untuk memperluas fungsi penyimpanan dan pemuatan melalui penggunaan plugin.

## Requirements Program

- JDK 17 (https://www.oracle.com/id/java/technologies/downloads/)
- Apache Maven (https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip)
- JavaFX 21.0.3 (https://download2.gluonhq.com/openjfx/21.0.3/openjfx-21.0.3_windows-x64_bin-sdk.zip)

## Cara Menjalankan Program dengan Clone Github

1. Clone github repository berikut https://github.com/ChaiGans/IF2210_TB2_MNT.git bisa dengan menjalankan perintah berikut di terminal git clone https://github.com/ChaiGans/IF2210_TB2_MNT.git.
2. Dari root folder hasil repository, yang sekarang diprediksikan merupakan folder "IF2210_TB2_MNT" sebagai current directory. Maka, terdapat beberapa langkah yang harus dilakukan untuk menghasilkan 3 buah jar, yaitu jar program utama, jar untuk Plugin-JSON-Loader, dan jar untuk Plugin-XML-Loader.
    -  Untuk membuat jar src, cd src terlebih dahulu kemudian lakukan mvn clean install. Jika menggunakan intellij, pada toolbox maven, lakukan clean kemudian lakukan install, maka akan tergenerate sebuah folder target yang berisi jile far src-1.0-SNAPSHOT-shaded.jar. Jar file tersebut merupakan file executable yang dapat dijalankan dengan java -jar  src-1.0-SNAPSHOT-shaded.jar pada folder tersebut, atau dengan melakukan double-click pada file tersebut.

    - Untuk membuat jar plugin loader untuk file json, dari root folder lakukan cd Plugin-JSON-Loader, kemudian lakukan hal yang sama, yaitu mvn clear install. Maka, akan muncul sebuah folder target yang memiliki sebuah jar file bernama Plugin-JSON-Loader-1.0-SNAPSHOT.jar

    - Untuk membuat jar plugin loader untuk file berekstensi XML, dari roto folder lakukan cd Plugin-XML-Loader, kemudian lakukan hal yang sama, yaitu mvn clear install. Maka, akan muncul sebuah folder target yang memiliki sebuah jar file bernama Plugin-XML-Loader-1.0-SNAPSHOT.jar
3. Jalankan dengan masuk ke dalam folder bin dengan cara mengetik java -jar "src-1.0-SNAPSHOT-shaded.jar" atau juga bisa langsung dengan double-click pada file jar nya.

## Cara Menjalankan Program melalui drive pengumpulan

1. Ekstrak zip pengumpulan, akan didapatkan 3 file jar pada bin yaitu src-1.0-SNAPSHOT-shaded.jar (program utama), Plugin-JSON-Loader-1.0-SNAPSHOT.jar (plugin), dan Plugin-XML-Loader-1.0-SNAPSHOT.jar (plugin).
2. Masuk ke dalam folder hasil ekstraksi zip pengumpulan dan jalankan perintah java -jar "src-1.0-SNAPSHOT-shaded.jar" dan ini perintah tersebut bisa disesuaikan tergantung dari lokasi jar file java -jar "src-1.0-SNAPSHOT-shaded.jar"
3. Bisa juga dijalankan dengan cara yang lebih gampang yaitu dengan mendouble-click pada file jar nya.
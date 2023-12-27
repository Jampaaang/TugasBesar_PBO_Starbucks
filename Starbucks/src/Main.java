import java.util.ArrayList;
import java.util.Scanner;

// Interface untuk item-item di Starbucks
interface StarbucksItem {
    String getNama();
    String getKategori();
    int getHarga();
}

// Kelas abstrak sebagai superclass untuk mewakili item di Starbucks
abstract class Item implements StarbucksItem {
    String nama;
    String kategori;
    int harga;

    public Item(String nama, String kategori, int harga) {
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public String getKategori() {
        return kategori;
    }

    public int getHarga() {
        return harga;
    }
}

// Kelas untuk mewakili makanan di Starbucks
class Makanan extends Item {
    public Makanan(String nama, int harga) {
        super(nama, "Makanan", harga);
    }
}

// Kelas untuk mewakili minuman di Starbucks
class Minuman extends Item {
    public Minuman(String nama, int harga) {
        super(nama, "Minuman", harga);
    }
}

// Kelas untuk mewakili merchandise di Starbucks
class Merchandise extends Item {
    public Merchandise(String nama, int harga) {
        super(nama, "Merchandise", harga);
    }
}

// Kelas untuk struk pembelian
class StrukPembelian {
    ArrayList<StarbucksItem> belanjaan = new ArrayList<>();
    int totalPembelian = 0;

    public void tambahItem(StarbucksItem item) {
        belanjaan.add(item);
        totalPembelian += item.getHarga();
    }

    public void cetakStruk() {
        System.out.println("=========================================");
        System.out.println("              Struk Pembelian             ");
        System.out.println("=========================================");
        for (StarbucksItem item : belanjaan) {
            System.out.printf("%-20s | %-10s | Rp%-9d\n", item.getNama(), item.getKategori(), item.getHarga());
        }
        System.out.println("-----------------------------------------");
        System.out.printf("%-20s | %-10s | Rp%-9d\n", "Total Pembelian", "", totalPembelian);
        System.out.println("=========================================");
    }
}

// Kelas aplikasi Starbucks
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Masukkan saldo awal (Rp): ");
            int saldo = scanner.nextInt();

            // Membuat objek StrukPembelian di dalam loop agar diinisialisasi ulang setiap kali pengguna memulai transaksi baru
            StrukPembelian strukPembelian = new StrukPembelian();

            while (true) {
                System.out.println("\n===== Menu Starbucks =====");
                System.out.println("1. Makanan");
                System.out.println("2. Minuman");
                System.out.println("3. Merchandise");
                System.out.println("4. Struk Pembelian");
                System.out.println("5. Selesai");

                System.out.print("Pilih opsi (1-5): ");
                int opsi = scanner.nextInt();

                if (opsi == 5) {
                    System.out.println("Terima kasih! Program selesai.");
                    break;
                }

                switch (opsi) {
                    case 1:
                        saldo = beliItem(new Makanan("Dummy", 0), saldo, strukPembelian);
                        break;
                    case 2:
                        saldo = beliItem(new Minuman("Dummy", 0), saldo, strukPembelian);
                        break;
                    case 3:
                        saldo = beliItem(new Merchandise("Dummy", 0), saldo, strukPembelian);
                        break;
                    case 4:
                        strukPembelian.cetakStruk();
                        break;
                    default:
                        System.out.println("Opsi tidak valid. Silakan pilih lagi.");
                }
            }
        }
    }

    private static int beliItem(StarbucksItem item, int saldo, StrukPembelian strukPembelian) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== Daftar " + item.getKategori() + " =====");
        if (item instanceof Makanan) {
            tampilkanMenu(daftarMakanan);
        } else if (item instanceof Minuman) {
            tampilkanMenu(daftarMinuman);
        } else if (item instanceof Merchandise) {
            tampilkanMenu(daftarMerchandise);
        }

        System.out.print("Pilih nomor " + item.getKategori() + ": ");
        int nomorItem = scanner.nextInt();

        // Menggunakan daftar item yang telah disiapkan di atas
        StarbucksItem itemTerpilih = null;
        if (item instanceof Makanan && nomorItem >= 1 && nomorItem <= 4) {
            itemTerpilih = daftarMakanan[nomorItem - 1];
        } else if (item instanceof Minuman && nomorItem >= 1 && nomorItem <= 4) {
            itemTerpilih = daftarMinuman[nomorItem - 1];
        } else if (item instanceof Merchandise && nomorItem >= 1 && nomorItem <= 4) {
            itemTerpilih = daftarMerchandise[nomorItem - 1];
        }

        if (itemTerpilih != null && saldo >= itemTerpilih.getHarga()) {
            strukPembelian.tambahItem(itemTerpilih);
            saldo -= itemTerpilih.getHarga();
            System.out.println("\nPembelian " + itemTerpilih.getNama() + " berhasil!");
            System.out.println("Sisa saldo: Rp" + saldo);
        } else {
            System.out.println("\nSaldo tidak mencukupi atau nomor item tidak valid. Pembelian dibatalkan.");
        }

        return saldo;
    }

    private static void tampilkanMenu(StarbucksItem[] daftarItem) {
        for (int i = 0; i < daftarItem.length; i++) {
            System.out.printf("%-4d. %-20s | %-10s | Rp%-9d\n", i + 1, daftarItem[i].getNama(), daftarItem[i].getKategori(), daftarItem[i].getHarga());
        }
    }

    // Daftar item Makanan, Minuman, dan Merchandise
    private static StarbucksItem[] daftarMakanan = {
            new Makanan("Chicken Pesto Panini", 70000),
            new Makanan("Spinach and Feta Wrap", 60000),
            new Makanan("Turkey and Swiss Sandwich", 75000),
            new Makanan("Chocolate Marble Loaf Cake", 40000)
    };

    private static StarbucksItem[] daftarMinuman = {
            new Minuman("Caramel Macchiato", 55000),
            new Minuman("Green Tea Frappuccino", 60000),
            new Minuman("Flat White", 50000),
            new Minuman("Iced Caramel Cloud Macchiato", 65000)
    };

    private static StarbucksItem[] daftarMerchandise = {
            new Merchandise("Starbucks Tumbler", 200000),
            new Merchandise("Coffee Lover's Mug", 100000),
            new Merchandise("Starbucks Cold Cup", 130000),
            new Merchandise("Starbucks Logo T-shirt", 250000)
    };
}

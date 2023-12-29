/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fsm.alienes_gurel_2121221032_bp2_p2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alien
 */
public class DB_Ders_Icerik_Takibi {

    private static final String connectionString = "jdbc:mysql://127.0.0.1:3306/db_derstakip?user=root&password=fsmblm";
    public static Connection conn;

    public static Kullanici Login(String email, String parola) {//giris islemi
        Kullanici kullanici = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query = "SELECT * FROM db_derstakip.tbl_kullanici WHERE email='" + email + "' AND parola='" + parola + "';";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                kullanici = new Kullanici();
                kullanici.id = rs.getInt("id");
                kullanici.ad_soyad = rs.getString("ad_soyad");
                kullanici.email = rs.getString("email");
                kullanici.parola = rs.getString("parola");
                kullanici.kullanici_turu = rs.getString("kullanici_turu");
                kullanici.ogrenci_numarasi = rs.getString("numarasi");
                kullanici.kullanici_akts = rs.getInt("akts");
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return kullanici;
    }

    public static boolean AddPerson(Kullanici kullanici) {//ogrenci kayit olma
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "INSERT INTO db_derstakip.tbl_kullanici "
                    + "(ad_soyad, email, parola, kullanici_turu, numarasi, akts)"
                    + "VALUES('" + kullanici.ad_soyad + "','" + kullanici.email + "','"
                    + kullanici.parola + "','" + kullanici.kullanici_turu + "','" + kullanici.ogrenci_numarasi + "'," + "40" + ");";
            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean AddPersonAkademisyen(Kullanici kullanici) {//akademisyen kayit olma
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "INSERT INTO db_derstakip.tbl_kullanici "
                    + "(ad_soyad, email, parola, kullanici_turu, numarasi, akts)"
                    + "VALUES('" + kullanici.ad_soyad + "','" + kullanici.email + "','"
                    + kullanici.parola + "','" + kullanici.kullanici_turu + "','" + kullanici.ogrenci_numarasi + "'," + "0" + ");";
            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean AddDers(Ders d) { //ders ekleme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "INSERT INTO db_derstakip.tbl_ders "
                    + "(ders_adi, ders_icerigi, ders_akts)"
                    + "VALUES('" + d.ders_adi + "','" + d.icerik + "','" + d.ders_akts + "');";
            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static ArrayList<Ders> DersleriGetir() {//tabloya dersleri yazdirmak icin
        ArrayList<Ders> courses = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query = "SELECT * FROM db_derstakip.tbl_ders;";
            ResultSet rs = st.executeQuery(query);
            courses = new ArrayList<>();
            while (rs.next()) {
                Ders ders = new Ders();
                ders.id = rs.getInt("id");
                ders.ders_adi = rs.getString("ders_adi");
                ders.ders_akts = rs.getInt("ders_akts");
                courses.add(ders);
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return courses;
    }

    public static Ders DersiBul(String ders_adi) {//Ders aramasi yapmak icin
        Ders ders = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query = "SELECT * FROM db_derstakip.tbl_ders WHERE ders_adi='" + ders_adi + "';";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                ders = new Ders();
                ders.id = rs.getInt("id");
                ders.ders_adi = rs.getString("ders_adi");
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ders;
    }

    public static Icerik IcerikBul(int ders_id) {//Icerik aramasi yapmak icin
        Icerik icerik = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query = "SELECT * FROM db_derstakip.tbl_icerik WHERE icerik_ders_id=" + ders_id + ";";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                icerik = new Icerik();
                icerik.haftasi = rs.getInt("icerik_hafta");
                icerik.eklenen = rs.getString("icerik_eklenen");
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return icerik;
    }

    public static boolean IcerikVarMi(int ders_id, int hafta) {//Kayitli herhangi icerik kontrolu saglar
        Icerik icerik = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query = "SELECT * FROM db_derstakip.tbl_icerik WHERE icerik_ders_id=" + ders_id + " AND icerik_hafta=" + hafta + ";";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                icerik = new Icerik();
                icerik.ders_id = rs.getInt("icerik_ders_id");
                icerik.haftasi = rs.getInt("icerik_hafta");
                icerik.eklenen = rs.getString("icerik_eklenen");
                return true;
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return false;
    }

    public static ArrayList<Kullanici> OgrencileriGetir() {//Tabloya ogrencileri yazdirmak icin
        ArrayList<Kullanici> kullanicilar = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query = "SELECT * FROM db_derstakip.tbl_kullanici;";
            ResultSet rs = st.executeQuery(query);
            kullanicilar = new ArrayList<>();
            while (rs.next()) {
                Kullanici k = new Kullanici();
                k.id = rs.getInt("id");
                k.ad_soyad = rs.getString("ad_soyad");
                k.kullanici_turu = rs.getString("kullanici_turu");
                k.kullanici_akts = rs.getInt("akts");
                kullanicilar.add(k);
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return kullanicilar;
    }

    public static boolean AddCourseToStudentById(int id_ogrenci, int id_ders, String ders_adi, int ders_akts) {//Ogrenciye ders ekleme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "INSERT INTO db_derstakip.tbl_ders_ogrenci (id_ogrenci, id_ders, ders_adi, ders_akts) "
                    + "VALUES (" + id_ogrenci + ", " + id_ders + ", '" + ders_adi + "', " + ders_akts + ");";

            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean AddIcerikToStudentById(int ders_id, int haftasi, String eklenen) {//Ogrenciye ders icerigi ekleme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "INSERT INTO db_derstakip.tbl_icerik (icerik_ders_id, icerik_hafta, icerik_eklenen) "
                    + "VALUES (" + ders_id + ", " + haftasi + ", '" + eklenen + "');";
            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean DeleteCourseToStudentById(int id_ogrenci, int id_ders) {//dersten cikma
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "DELETE FROM db_derstakip.tbl_ders_ogrenci WHERE id_ogrenci=" + id_ogrenci + " AND id_ders=" + id_ders + ";";
            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean DerstenCikar(int id_ogrenci, int id_ders) {//akademisyen ogrenciyi dersten cikarir
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "DELETE FROM db_derstakip.tbl_ders_ogrenci WHERE id_ogrenci=" + id_ogrenci + " AND id_ders=" + id_ders + ";";
            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean DeleteDers(String ders_adi, String ders_icerigi) {//ders silme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "DELETE FROM db_derstakip.tbl_ders WHERE ders_adi='" + ders_adi + "' AND ders_icerigi='" + ders_icerigi + "';";
            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean DeleteOgrenciByIdDB(int id_ogrenci) {//ogrenci kayit silme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "DELETE FROM db_derstakip.tbl_kullanici WHERE id=" + id_ogrenci + ";";
            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean DeleteOgrenciById(int id_ogrenci) {//ogrenciyi dersten cikarma
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "DELETE FROM db_derstakip.tbl_ders_ogrenci WHERE id_ogrenci=" + id_ogrenci + ";";
            stmt.execute(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static ArrayList<Ders> GetStudentSelectedCoursesByIdJoin(int id) {//join islemi ile ders arma
        ArrayList<Ders> dersler = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query = "SELECT * FROM db_derstakip.tbl_ders_ogrenci AS cp "
                    + "INNER JOIN db_derstakip.tbl_ders AS c"
                    + " ON cp.id_ders=c.id WHERE cp.id_ogrenci=" + id + ";";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Ders ders = new Ders();
                ders.id = rs.getInt("id_ders");
                ders.ders_adi = rs.getString("ders_adi");
                ders.ders_akts = rs.getInt("ders_akts");
                dersler.add(ders);
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return dersler;
    }

    public static ArrayList<Ders> aramaTumDers(String search) {//tum derslerden arama tbl_ders
        ArrayList<Ders> dersler = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query1 = "SELECT * FROM db_derstakip.tbl_ders WHERE ders_adi LIKE '%" + search + "%'";
            ResultSet rs = st.executeQuery(query1);
            while (rs.next()) {
                Ders d = new Ders();
                d.id = rs.getInt("id");
                d.ders_adi = rs.getString("ders_adi");
                d.ders_akts = rs.getInt("ders_akts");
                dersler.add(d);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(OgrenciDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dersler;
    }

    public static ArrayList<Kullanici> aramaTumOgrenci(String search) {//ogrenci arama tbl_kullanici
        ArrayList<Kullanici> kullanicilar = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query1 = "SELECT * FROM db_derstakip.tbl_kullanici WHERE ad_soyad LIKE '%" + search + "%'";
            ResultSet rs = st.executeQuery(query1);
            while (rs.next()) {
                Kullanici k = new Kullanici();
                k.id = rs.getInt("id");
                k.ad_soyad = rs.getString("ad_soyad");
                k.kullanici_turu = rs.getString("kullanici_turu");
                k.kullanici_akts = rs.getInt("akts");
                kullanicilar.add(k);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AkademisyenDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kullanicilar;
    }

    public static Ders searchDersById(int id) {//ders arama tbl_ders
        Ders d = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query1 = "SELECT * FROM db_derstakip.tbl_ders WHERE id=" + id + ";";
            ResultSet rs = st.executeQuery(query1);
            if (rs.next()) {
                d = new Ders();
                d.id = rs.getInt("id");
                d.ders_adi = rs.getString("ders_adi");
                d.icerik = rs.getString("ders_icerigi");
                d.ders_akts = rs.getInt("ders_akts");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(OgrenciDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    public static Kullanici searchOgrenciById(int id) {//ogrenci arama tbl_kullanici
        Kullanici k = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query1 = "SELECT * FROM db_derstakip.tbl_kullanici WHERE id=" + id + ";";
            ResultSet rs = st.executeQuery(query1);
            if (rs.next()) {
                k = new Kullanici();
                k.id = rs.getInt("id");
                k.ad_soyad = rs.getString("ad_soyad");
                k.email = rs.getString("email");
                k.parola = rs.getString("parola");
                k.kullanici_turu = rs.getString("kullanici_turu");
                k.ogrenci_numarasi = rs.getString("numarasi");
                k.kullanici_akts = rs.getInt("akts");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(OgrenciDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return k;
    }

    public static ArrayList<Ders> aramaOgrenciDers(int search) {//tum ogrencilerden arama yapma tbl_ders_ogrenci
        ArrayList<Ders> dersler = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query1 = "SELECT * FROM db_derstakip.tbl_ders_ogrenci WHERE id_ogrenci LIKE '%" + search + "%'";
            ResultSet rs = st.executeQuery(query1);
            while (rs.next()) {
                Ders d = new Ders();
                d.id = rs.getInt("id_ders");
                d.ders_adi = rs.getString("ders_adi");
                d.ders_akts = rs.getInt("ders_akts");
                dersler.add(d);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(OgrenciDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dersler;
    }

    public static ArrayList<Kullanici> aramaDersAlanlarById(int search) {//id ye gore deri alanlari arama
        ArrayList<Kullanici> ogrencilerId = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement st = conn.createStatement();
            String query1 = "SELECT * FROM db_derstakip.tbl_ders_ogrenci AS a "
                    + "INNER JOIN db_derstakip.tbl_kullanici AS d"
                    + " ON a.id_ogrenci=d.id WHERE a.id_ders=" + search + ";";
            ResultSet rs = st.executeQuery(query1);
            while (rs.next()) {
                Kullanici k = new Kullanici();
                k.id = rs.getInt("id_ogrenci");
                k.ad_soyad = rs.getString("ad_soyad");
                ogrencilerId.add(k);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(OgrenciDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ogrencilerId;
    }

    public static boolean UpdateKullanici(Kullanici k1) {//ogrenci bilgi guncelleme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "UPDATE tbl_kullanici SET "
                    + "ad_soyad='" + k1.ad_soyad
                    + "', email='" + k1.email
                    + "', parola='" + k1.parola
                    + "', numarasi='" + k1.ogrenci_numarasi
                    + "', akts=" + k1.kullanici_akts
                    + " WHERE id=" + k1.id + ";";
            stmt.executeUpdate(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OgrenciDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean UpdateDers(Ders d1) {//tbl_ders tablosundan ders guncelleme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "UPDATE tbl_ders SET "
                    + "ders_adi='" + d1.ders_adi
                    + "', ders_icerigi='" + d1.icerik
                    + "', ders_akts=" + d1.ders_akts
                    + " WHERE id=" + d1.id + ";";
            stmt.executeUpdate(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AkademisyenDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean UpdateDersDB(Ders d1) {//tbl_ders_ogrenci tablosundan ders guncelleme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "UPDATE tbl_ders_ogrenci SET "
                    + " ders_akts=" + d1.ders_akts
                    + " WHERE id_ders=" + d1.id + ";";
            stmt.executeUpdate(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AkademisyenDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean UpdateAkts(Kullanici k) {//ogrenci akts guncelleme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "UPDATE tbl_kullanici SET "
                    + " kalan_akts=" + k.kalan_akts
                    + " WHERE id=" + k.id + ";";
            stmt.executeUpdate(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AkademisyenDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean UpdateIcerik(Icerik i) {//icerik guncelleme
        try {
            conn = DriverManager.getConnection(connectionString);
            java.sql.Statement stmt = conn.createStatement();
            String query = "UPDATE tbl_icerik SET "
                    + " icerik_eklenen='" + i.eklenen
                    + "' WHERE icerik_ders_id=" + i.ders_id + " AND icerik_hafta=" + i.haftasi + ";";
            stmt.executeUpdate(query);
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AkademisyenDersIcerik.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

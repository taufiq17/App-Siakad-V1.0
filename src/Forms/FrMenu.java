/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Tools.KoneksiDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



/**
 *
 * @author klidang lor
 */
public class FrMenu extends javax.swing.JFrame {
    KoneksiDB getCnn = new KoneksiDB();//mendeklarasikan class koneksi database yang bernama KoneksiDB
    Connection _Cnn;
    
    String vid_user, vnama_user, vpass, vlev_user;
    String sqlselect;
   

    /**
     * Creates new form FrMenu
     */
    public FrMenu() {
        initComponents();
        disableMenu();
        this.setExtendedState(this.getExtendedState () | FrMenu.MAXIMIZED_BOTH);
        txtIDUser.requestFocus(true);
    }
    
    private void disableMenu(){
        mnSistem.setEnabled(false);
        mnMaster.setEnabled(false);
        mnMahasiswa.setEnabled(false);
    }
    
    private void enableMenu(){
        mnSistem.setEnabled(true);
        mnMaster.setEnabled(true);
        mnMahasiswa.setEnabled(true);
    }
    
    void userAdministrator(){
        mnSistem.setVisible(true);
        mnMaster.setVisible(true);
        mnMahasiswa.setVisible(true);
    }
    
    void userKemahasiswaan(){
        mnSistem.setVisible(true);
        mnUser.setText("Ubah Password");
        mnMaster.setVisible(false);
        mnMahasiswa.setVisible(true);
    }
    
    private void aksilogin(){//method yg berisi validasi login
        if(txtIDUser.getText().equals("")|| txtPassword.getText().equals("")){
            JOptionPane.showMessageDialog(this, "ID User dan Password belum diisi");
        }else{
            vid_user = txtIDUser.getText();//memberikan nilai pd variabel vid_user
            vpass = txtPassword.getText();
            try{
                _Cnn = null;//mengkosongkan koneksi
                _Cnn = getCnn.getConnection();//membuka koneksi dg getCnn(object koneksiDB)
                sqlselect = "select * from tbuser where id_user='"+vid_user+"' "
                        + " and pass=md5('"+vpass+"') ";//query
                Statement stat = _Cnn.createStatement();//membuat statement query
                ResultSet res = stat.executeQuery(sqlselect);//menjalankan query, ResultSet hanya jika querynya select
                if(res.first()){//jika hasilnya(query select) ditemukan
                    vlev_user = res.getString("lev_user");//memberikan nilai pd variabel vlev_user dr hasil query select kolom lev_user
                    vnama_user = res.getString("nama_user");//variabel nama_user diberi nilai pd tabel
                    lblKeterangan.setText("ID. User : "+vid_user+" - "+vnama_user+
                            "   | Lev. User : "+vlev_user);//menampilkan id user dan lev user yg sedang login di label keterangan
                    panelLogin.setVisible(false);
                    enableMenu();
                    if(vlev_user.equals("Administrator")){
                        userAdministrator();
                    }else if(vlev_user.equals("Staff Kemahasiswaan")){
                        userKemahasiswaan();
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "ID. User dan Password salah ");
                }
            
            }catch(SQLException se){
                JOptionPane.showMessageDialog(this, "Error method aksilogin() : " +se);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblKeterangan = new javax.swing.JLabel();
        panelMenu = new javax.swing.JDesktopPane();
        panelLogin = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        txtIDUser = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnSistem = new javax.swing.JMenu();
        mnUser = new javax.swing.JMenuItem();
        mnKeluar = new javax.swing.JMenuItem();
        mnMaster = new javax.swing.JMenu();
        mnTahunAngkatan = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnJurusan = new javax.swing.JMenuItem();
        mnProdi = new javax.swing.JMenuItem();
        mnMahasiswa = new javax.swing.JMenu();
        mnDataMhs = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnLaporanDataMhs = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("App Siakad v.1.0.1");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblKeterangan.setText("ID. User : ..... | Lev. User : .....");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKeterangan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtIDUser.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "ID. User"));
        txtIDUser.setOpaque(false);

        txtPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Password :"));
        txtPassword.setOpaque(false);
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/login-blue.png"))); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/0 - login batal.png"))); // NOI18N
        btnBatal.setText("Batal");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login SIAKAD V.1.0.1");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("-{Sistem Informasi Oemah Codinger}-");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword)
                            .addComponent(txtIDUser, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(42, 42, 42))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIDUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelLogin.addTab("Login", new javax.swing.ImageIcon(getClass().getResource("/Icons/Admin-Schoolar-Icon.png")), jPanel2); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icon-login.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 51));
        jLabel5.setText("SISTEM INFORMASI AKADEMIK");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 51));
        jLabel6.setText("Sekolah Tinggi Oemah Codinger [STOC]");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 51));
        jLabel7.setText("Oemahcodinger.com");

        panelMenu.setLayer(panelLogin, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelMenu.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelMenu.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelMenu.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelMenu.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelMenu.setLayer(jSeparator3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap(246, Short.MAX_VALUE)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                        .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addContainerGap())))
        );

        mnSistem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1 - Sistem.png"))); // NOI18N
        mnSistem.setText("Sistem");

        mnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1 - user.png"))); // NOI18N
        mnUser.setText("User");
        mnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUserActionPerformed(evt);
            }
        });
        mnSistem.add(mnUser);

        mnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/small_logout.png"))); // NOI18N
        mnKeluar.setText("Keluar");
        mnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnKeluarActionPerformed(evt);
            }
        });
        mnSistem.add(mnKeluar);

        jMenuBar1.add(mnSistem);

        mnMaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/2 - Master.png"))); // NOI18N
        mnMaster.setText("Master");

        mnTahunAngkatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/2 - master small.png"))); // NOI18N
        mnTahunAngkatan.setText("Tahun Angkatan");
        mnTahunAngkatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTahunAngkatanActionPerformed(evt);
            }
        });
        mnMaster.add(mnTahunAngkatan);
        mnMaster.add(jSeparator1);

        mnJurusan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/2 - master small.png"))); // NOI18N
        mnJurusan.setText("Jurusan");
        mnJurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnJurusanActionPerformed(evt);
            }
        });
        mnMaster.add(mnJurusan);

        mnProdi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/2 - master small.png"))); // NOI18N
        mnProdi.setText("Program Studi");
        mnProdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnProdiActionPerformed(evt);
            }
        });
        mnMaster.add(mnProdi);

        jMenuBar1.add(mnMaster);

        mnMahasiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/3 - Mahasiswa.png"))); // NOI18N
        mnMahasiswa.setText("Mahasiswa");

        mnDataMhs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/3 - mahasiswa small.png"))); // NOI18N
        mnDataMhs.setText("Data Mahasiswa");
        mnDataMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDataMhsActionPerformed(evt);
            }
        });
        mnMahasiswa.add(mnDataMhs);
        mnMahasiswa.add(jSeparator2);

        mnLaporanDataMhs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/report-small2.png"))); // NOI18N
        mnLaporanDataMhs.setText("Laporan Data Mahasiswa");
        mnLaporanDataMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLaporanDataMhsActionPerformed(evt);
            }
        });
        mnMahasiswa.add(mnLaporanDataMhs);

        jMenuBar1.add(mnMahasiswa);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMenu)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnLaporanDataMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLaporanDataMhsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnLaporanDataMhsActionPerformed

    private void mnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUserActionPerformed
        IfrUsers fr = new IfrUsers();
        panelMenu.add(fr);
        fr.setVisible(true);
        
    }//GEN-LAST:event_mnUserActionPerformed

    private void mnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnKeluarActionPerformed
         int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda akan keluar dari sistem?","Informasi",
                JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_mnKeluarActionPerformed

    private void mnTahunAngkatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTahunAngkatanActionPerformed
        IfrTahunAngkatan fr = new IfrTahunAngkatan();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnTahunAngkatanActionPerformed

    private void mnJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnJurusanActionPerformed
        IfrJurusan fr = new IfrJurusan();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnJurusanActionPerformed

    private void mnProdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnProdiActionPerformed
        IfrProdi fr = new IfrProdi();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnProdiActionPerformed

    private void mnDataMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDataMhsActionPerformed
        IfrMahasiswa fr = new IfrMahasiswa();
        panelMenu.add(fr);
        fr.setVisible(true);
        fr.getDesktopPane().getDesktopManager().maximizeFrame(fr);
    }//GEN-LAST:event_mnDataMhsActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        aksilogin();
        
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
       aksilogin();
    }//GEN-LAST:event_txtPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws UnsupportedLookAndFeelException,
           IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
       try{
           com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default", "Java Swing", "");
           UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
           SwingUtilities.updateComponentTreeUI(new FrMenu());
       } finally {
           new FrMenu().setVisible(true);
       }
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblKeterangan;
    private javax.swing.JMenuItem mnDataMhs;
    private javax.swing.JMenuItem mnJurusan;
    private javax.swing.JMenuItem mnKeluar;
    private javax.swing.JMenuItem mnLaporanDataMhs;
    private javax.swing.JMenu mnMahasiswa;
    private javax.swing.JMenu mnMaster;
    private javax.swing.JMenuItem mnProdi;
    private javax.swing.JMenu mnSistem;
    private javax.swing.JMenuItem mnTahunAngkatan;
    private javax.swing.JMenuItem mnUser;
    private javax.swing.JTabbedPane panelLogin;
    private javax.swing.JDesktopPane panelMenu;
    private javax.swing.JTextField txtIDUser;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
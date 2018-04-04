/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;
import Tools.KoneksiDB;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author klidang lor
 */
public class IfrMahasiswa extends javax.swing.JInternalFrame {
    
    KoneksiDB getCnn = new KoneksiDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete;
    String vnim, vid_ta, vkd_prodi, vnama_mhs, vjk, vtmp_Lahir, vtgl_Lahir,
            vagama, vnama_ayah, vnama_ibu, valamat, vno_telepon, vfoto;
    File imageFile = null;
    
    private DefaultTableModel tblmahasiswa;
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Creates new form IfrMahasiswa
     */
    public IfrMahasiswa() {
        initComponents();
        listTA();
        listProdi();
        clearInput();
        setTabelMahasiswa();
        showDataMahasiswa();
        jdInputMahasiswa.setSize(580, 600);
        jdInputMahasiswa.setLocationRelativeTo(this);
    }
    
    public void clearInput(){
            txtNIM.setText("");
            cmbTA.setSelectedIndex(0);
            cmbProdi.setSelectedIndex(0);
            cmbJnsKel.setSelectedIndex(0);
            cmbAgama.setSelectedIndex(0);
            txtNamaMhs.setText("");
            txtTempat.setText("");
            dtTglLahir.setDate(new Date());
            txtNamaAyah.setText("");
            txtNamaIbu.setText("");
            txtAlamat.setText("");
            txtNoTelepon.setText("");
            lbFoto.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/no-picture.jpg")));
            imageFile = null;
            btnSimpan.setText("Simpan");
            vnim="";
    }
    
    public void setTabelMahasiswa(){
        String[] kolom1 = {"NIM", "Nama Mahasiswa", "L/P", "Tempat", "Tgl. Lahir",
                                "Program Studi", "Alamat", "No. Telepon"};
            tblmahasiswa = new DefaultTableModel(null, kolom1){
                Class[] types = new Class[]{
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class
                };
                public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            
            //agar tabel tidak bisa diedit
            public boolean isCellEditable(int row, int col){
                int cola = tblmahasiswa.getColumnCount();
                return (col < cola) ? false :true;
            }
          };
            tbDataMahasiswa.setModel(tblmahasiswa);
            tbDataMahasiswa.getColumnModel().getColumn(0).setPreferredWidth(75);
            tbDataMahasiswa.getColumnModel().getColumn(1).setPreferredWidth(250);
            tbDataMahasiswa.getColumnModel().getColumn(2).setPreferredWidth(25);
            tbDataMahasiswa.getColumnModel().getColumn(3).setPreferredWidth(75);
            tbDataMahasiswa.getColumnModel().getColumn(4).setPreferredWidth(75);
            tbDataMahasiswa.getColumnModel().getColumn(5).setPreferredWidth(150);
            tbDataMahasiswa.getColumnModel().getColumn(6).setPreferredWidth(300);
            tbDataMahasiswa.getColumnModel().getColumn(7).setPreferredWidth(150);
    }
    
    public void clearTabelMahasiswa(){
         int row = tblmahasiswa.getRowCount();
            for(int i = 0; i < row; i++){
                tblmahasiswa.removeRow(0);
            }
    }
    
    public void showDataMahasiswa(){
        try{
             _Cnn = null;
             _Cnn = getCnn.getConnection();
             clearTabelMahasiswa();
             sqlselect = "select * from tbmahasiswa a, tbprodi b "
                     +" where a.kd_prodi=b.kd_prodi order by a.kd_prodi, a.nama_mhs asc ";
             Statement stat = _Cnn.createStatement();
             ResultSet res = stat.executeQuery(sqlselect);
             while(res.next()){
                 vnim = res.getString("nim");
                 vnama_mhs = res.getString("nama_mhs");
                 vjk = res.getString("jk");
                 vtmp_Lahir = res.getString("tmp_lahir");
                 vtgl_Lahir = tglview.format(res.getDate("tgl_lahir"));
                 vkd_prodi = res.getString("prodi");
                 valamat = res.getString("alamat");
                 vno_telepon = res.getString("no_telepon");
                 Object[]data = {vnim, vnama_mhs, vjk, vtmp_Lahir,
                     vtgl_Lahir, vkd_prodi, valamat, vno_telepon};
                 tblmahasiswa.addRow(data);
                 }
                 lbRecord.setText("Record : "+tbDataMahasiswa.getRowCount());
           } catch(SQLException ex){
               JOptionPane.showMessageDialog(this, "Error method showDataMahasiswa() : "+ex);
           }
    }
    
    public void cariNamaMahasiswa(){
        try{
             _Cnn = null;
             _Cnn = getCnn.getConnection();
             clearTabelMahasiswa();
             sqlselect = "select * from tbmahasiswa a, tbprodi b "
                     +"where a.kd_prodi=b.kd_prodi and a.nama_mhs like '%"+txtCari.getText()+"%'"
                     +"order by a.kd_prodi, a.nama_mhs asc ";
             Statement stat = _Cnn.createStatement();
             ResultSet res = stat.executeQuery(sqlselect);
             while(res.next()){
                 vnim = res.getString("nim");
                 vnama_mhs = res.getString("nama_mhs");
                 vjk = res.getString("jk");
                 vtmp_Lahir = res.getString("tmp_lahir");
                 vtgl_Lahir = tglview.format(res.getDate("tgl_lahir"));
                 vkd_prodi = res.getString("prodi");
                 valamat = res.getString("alamat");
                 vno_telepon = res.getString("no_telepon");
                 Object[]data = {vnim, vnama_mhs, vjk, vtmp_Lahir,
                     vtgl_Lahir, vkd_prodi, valamat, vno_telepon};
                 tblmahasiswa.addRow(data);
                 }
                 lbRecord.setText("Record : "+tbDataMahasiswa.getRowCount());
           } catch(SQLException ex){
               JOptionPane.showMessageDialog(this, "Error method cariNamaMahasiswa() : "+ex);
           }
    }
    
    public void getDataMahasiswa(){
        try{
               _Cnn = null;
               _Cnn = getCnn.getConnection();
               sqlselect = "select * from tbmahasiswa a, tbprodi b, tbthangkatan c"
                       + " where a.kd_prodi=b.kd_prodi and a.id_ta=c.id_ta and a.nim='"+vnim+"' ";
               Statement stat = _Cnn.createStatement();
               ResultSet res = stat.executeQuery(sqlselect);
               if(res.first()){
                   cmbTA.setSelectedItem(res.getString("tahun_angkatan"));
                   cmbProdi.setSelectedItem(res.getString("prodi"));
                   txtNamaMhs.setText(res.getString("nama_mhs"));
                   vjk = res.getString("jk");
                   if(vjk.equals("L")){
                       cmbJnsKel.setSelectedItem("Laki-Laki");
                   }else{
                       cmbJnsKel.setSelectedItem("Perempuan");
                   }
                   cmbAgama.setSelectedItem(res.getString("agama"));
                   txtTempat.setText(res.getString("tmp_lahir"));
                   dtTglLahir.setDate(res.getDate("tgl_lahir"));
                   txtNamaAyah.setText(res.getString("nama_ayah"));
                   txtNamaIbu.setText(res.getString("nama_ibu"));
                   txtAlamat.setText(res.getString("alamat"));
                   txtNoTelepon.setText(res.getString("no_telepon"));
                   
                   if(res.getBlob("foto").getBinaryStream() == null){
                       lbFoto.setIcon(new javax.swing.ImageIcon(getClass().
                               getResource("/Icons/no-picture.jpg")));
                   }else{
                       InputStream is = res.getBlob("foto").getBinaryStream();
                       ImageIcon icon = new ImageIcon(resize(ImageIO.read(is),
                               lbFoto.getWidth(), lbFoto.getHeight()));
                       lbFoto.setIcon(icon);
                   }
               }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method getDataMahasiswa() : "+ex,
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }catch(IOException ex){
                Logger.getLogger(IfrMahasiswa.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void aksiSimpan(){
           vnim = txtNIM.getText();
           vid_ta = KeyTA[cmbTA.getSelectedIndex()];
           vkd_prodi = KeyProdi[cmbProdi.getSelectedIndex()];
           vnama_mhs = txtNamaMhs.getText();
           vagama = cmbAgama.getSelectedItem().toString();
           vtmp_Lahir = txtTempat.getText();
           vtgl_Lahir = tglinput.format(dtTglLahir.getDate());
           vnama_ayah = txtNamaAyah.getText();
           vnama_ibu = txtNamaIbu.getText();
           valamat = txtAlamat.getText();
           vno_telepon = txtNoTelepon.getText();
           if(btnSimpan.getText().equals("Simpan")){
               
               if (imageFile == null){
                   try{
                       _Cnn = null;
                       _Cnn = getCnn.getConnection();
                       sqlinsert = "insert into tbmahasiswa values(?,?,?,?,?,?,?,?,?,?,?,?)";
                       PreparedStatement stat = _Cnn.prepareStatement(sqlinsert);
                       stat.setString(1, vnim);
                       stat.setString(2, vid_ta);
                       stat.setString(3, vkd_prodi);
                       stat.setString(4, vnama_mhs);
                       stat.setString(5, vjk);
                       stat.setString(6, vtmp_Lahir);
                       stat.setString(7, vtgl_Lahir);
                       stat.setString(8, vagama);
                       stat.setString(9, vnama_ayah);
                       stat.setString(10, vnama_ibu);
                       stat.setString(11, valamat);
                       stat.setString(12, vno_telepon);
                   }catch(SQLException ex){
                       JOptionPane.showMessageDialog(this, "Error method aksiSimpan() 1: "+ex,
                               "Informasi", JOptionPane.INFORMATION_MESSAGE);
                   }
               }else{
                   try{
                       InputStream is = new FileInputStream(imageFile);
                       _Cnn = null;
                       _Cnn = getCnn.getConnection();
                       sqlinsert = "insert into tbmahasiswa values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                       PreparedStatement stat = _Cnn.prepareStatement(sqlinsert);
                       stat.setString(1, vnim);
                       stat.setString(2, vid_ta);
                       stat.setString(3, vkd_prodi);
                       stat.setString(4, vnama_mhs);
                       stat.setString(5, vjk);
                       stat.setString(6, vtmp_Lahir);
                       stat.setString(7, vtgl_Lahir);
                       stat.setString(8, vagama);
                       stat.setString(9, vnama_ayah);
                       stat.setString(10, vnama_ibu);
                       stat.setString(11, valamat);
                       stat.setString(12, vno_telepon);
                       stat.setBlob(13, is);
                       stat.executeUpdate();
                       clearInput(); showDataMahasiswa(); jdInputMahasiswa.setVisible(false);
                       JOptionPane.showMessageDialog(this, "Data berhasil disimpan",
                               "Informasi", JOptionPane.INFORMATION_MESSAGE);
                       
                   }catch(SQLException ex){
                       JOptionPane.showMessageDialog(this, "Error method aksiSimpan()2 : "+ex,
                               "Informasi", JOptionPane.INFORMATION_MESSAGE);
                   } catch(FileNotFoundException ex){
                       Logger.getLogger(IfrMahasiswa.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
           }else{
               
               if (imageFile == null){
                  try{
                      _Cnn = null;
                      _Cnn = getCnn.getConnection();
                      sqlinsert = "update tbmahasiswa set kd_prodi=?, "
                      + " nama_mhs=?, jk=?, "
                      + " tmp_lahir=?, tgl_lahir=?,"
                      + " agama=?, nama_ayah=?, "
                      + " nama_ibu=?, alamat=?, "
                      + " no_telepon=? where nim='"+vnim+"' ";
                      PreparedStatement stat = _Cnn.prepareStatement(sqlinsert);
                      stat.setString(1, vkd_prodi);
                      stat.setString(2, vnama_mhs);
                      stat.setString(3, vjk);
                      stat.setString(4, vtmp_Lahir);
                      stat.setString(5, vtgl_Lahir);
                      stat.setString(6, vagama);
                      stat.setString(7, vnama_ayah);
                      stat.setString(8, vnama_ibu);
                      stat.setString(9, valamat);
                      stat.setString(10, vno_telepon);
                      stat.executeUpdate();
                      clearInput(); showDataMahasiswa(); jdInputMahasiswa.setVisible(false);
                      JOptionPane.showMessageDialog(this, "Data berhasil diubah",
                              "Informasi", JOptionPane.INFORMATION_MESSAGE);
                  } catch(SQLException ex){
                      JOptionPane.showMessageDialog(this, "Error method aksiSimpan() 3: "+ex,
                              "Informasi", JOptionPane.INFORMATION_MESSAGE);
                  }
               }else{
                   try{
                       InputStream is = new FileInputStream(imageFile);
                      _Cnn = null;
                      _Cnn = getCnn.getConnection();
                      sqlinsert = "update tbmahasiswa set kd_prodi=?, "
                      + " nama_mhs=?, jk=?, "
                      + " tmp_lahir=?, tgl_lahir=?,"
                      + " agama=?, nama_ayah=?, "
                      + " nama_ibu=?, alamat=?, "
                      + " no_telepon=?, foto=? where nim='"+vnim+"'";
                      PreparedStatement stat = _Cnn.prepareStatement(sqlinsert);
                      stat.setString(1, vkd_prodi);
                      stat.setString(2, vnama_mhs);
                      stat.setString(3, vjk);
                      stat.setString(4, vtmp_Lahir);
                      stat.setString(5, vtgl_Lahir);
                      stat.setString(6, vagama);
                      stat.setString(7, vnama_ayah);
                      stat.setString(8, vnama_ibu);
                      stat.setString(9, valamat);
                      stat.setString(10, vno_telepon);
                      stat.setBlob(11, is);
                      stat.executeUpdate();
                      clearInput(); showDataMahasiswa(); jdInputMahasiswa.setVisible(false);
                      JOptionPane.showMessageDialog(this, "Data berhasil diubah",
                              "Informasi", JOptionPane.INFORMATION_MESSAGE);
                  } catch(SQLException ex){
                      JOptionPane.showMessageDialog(this, "Error method aksiSimpan() 4: "+ex,
                              "Informasi", JOptionPane.INFORMATION_MESSAGE);
                  }catch (FileNotFoundException ex){
                      Logger.getLogger(IfrMahasiswa.class.getName()).log(Level.SEVERE, null, ex);
                  }
               }
           }
    }
    
    public void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this,
                    "Apakah anda yakin akan menghapus data ini? NIM : "+vnim,
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if(jawab==JOptionPane.YES_OPTION){
                try{
                    _Cnn = null;
                    _Cnn = getCnn.getConnection();
                    sqldelete = "delete from tbmahasiswa "
                                + " where nim='"+vnim+"' ";
                    Statement stat = _Cnn.createStatement();
                    stat.executeUpdate(sqldelete);
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus",
                            "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    clearInput(); showDataMahasiswa();
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "Error method aksiHapus() : "+ex,
                            "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }
            }
    }
    
    public static BufferedImage loadImage(String ref){
            BufferedImage bimg = null;
            try{
                bimg = ImageIO.read(new File(ref));
            } catch (Exception ex){   
            }return bimg;
        } 
        
        public static BufferedImage resize(BufferedImage img, int newW, int newH){
            int w = img.getWidth();
            int h = img.getWidth();
            BufferedImage dimg = dimg = new BufferedImage(newW, newH,img.getType());
            Graphics2D g = dimg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
            g.dispose();
            return dimg;
        }
    
    String[] KeyTA;
    public void listTA(){
        try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select * from tbthangkatan order by id_ta asc";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                cmbTA.removeAllItems();
                cmbTA.repaint();
                cmbTA.addItem("-- Pilih --");
                int i = 1;
                while(res.next()){
                    cmbTA.addItem(res.getString(2));
                    i++;
                }
                res.first();
                KeyTA = new String[i+1];
                for(Integer x = 1;x < i;x++){
                    KeyTA[x] = res.getString(1);
                    res.next();
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method listTA() : "
                + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
    }
    
    String[] KeyProdi;
    public void listProdi(){
        try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select kd_prodi, prodi"
                        + " from tbprodi order by kd_jur asc";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                cmbProdi.removeAllItems();
                cmbProdi.repaint();
                cmbProdi.addItem("-- Pilih --");
                int i = 1;
                while(res.next()){
                    cmbProdi.addItem(res.getString(2));
                    i++;
                }
                res.first();
                KeyProdi = new String[i+1];
                for(Integer x = 1;x < i;x++){
                    KeyProdi[x] = res.getString(1);
                    res.next();
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method listProdi() : "
                + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
    }
    
    public void ambilGambar(){
        javax.swing.JFileChooser jfc = new JFileChooser();
            FileFilter jpgFilter, gifFilter, bothFilter;
            jpgFilter = new FileNameExtensionFilter("Gambar JPEG", "jpg");
            gifFilter = new FileNameExtensionFilter("Gambar GIF", "gif");
            bothFilter = new FileNameExtensionFilter("Gambar JPEG dan GIF", "jpg", "gif");
            jfc.setAcceptAllFileFilterUsed(false);
            jfc.addChoosableFileFilter(jpgFilter);
            jfc.addChoosableFileFilter(gifFilter);
            jfc.addChoosableFileFilter(bothFilter);
            if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                vfoto = jfc.getSelectedFile().toString();
                imageFile = jfc.getSelectedFile();
                BufferedImage loadImg = loadImage(vfoto);
                ImageIcon imageIcon = new ImageIcon(resize(loadImg,lbFoto.getWidth(), lbFoto.getHeight()));
                lbFoto.setIcon(imageIcon);
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

        jdInputMahasiswa = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lbFoto = new javax.swing.JLabel();
        cmbTA = new javax.swing.JComboBox<>();
        cmbProdi = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        txtNIM = new javax.swing.JTextField();
        txtNamaMhs = new javax.swing.JTextField();
        cmbJnsKel = new javax.swing.JComboBox<>();
        cmbAgama = new javax.swing.JComboBox<>();
        txtTempat = new javax.swing.JTextField();
        dtTglLahir = new com.toedter.calendar.JDateChooser();
        txtNoTelepon = new javax.swing.JTextField();
        txtNamaIbu = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JEditorPane();
        txtNamaAyah = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataMahasiswa = new javax.swing.JTable();
        lbRecord = new javax.swing.JLabel();

        jdInputMahasiswa.setTitle(".: Form Entry Mahasiswa");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Form Entry Mahasiswa");

        jLabel5.setText("Form ini digunakan untuk melakukan input data mahasiswa");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel2.setOpaque(false);

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/btn_delete.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(213, 213, 213)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Foto"));
        jPanel3.setOpaque(false);

        lbFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/no-picture.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbFoto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmbTA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbTA.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tahun Angkatan"));
        cmbTA.setOpaque(false);

        cmbProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbProdi.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Program Studi"));
        cmbProdi.setOpaque(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Input Data Mahasiswa"));
        jPanel4.setOpaque(false);

        txtNIM.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "NIM "));
        txtNIM.setOpaque(false);

        txtNamaMhs.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nama Mahasiswa"));
        txtNamaMhs.setOpaque(false);

        cmbJnsKel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbJnsKel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Jenis Kelamin"));
        cmbJnsKel.setOpaque(false);
        cmbJnsKel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJnsKelItemStateChanged(evt);
            }
        });

        cmbAgama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbAgama.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Agama"));
        cmbAgama.setOpaque(false);

        txtTempat.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tempat"));
        txtTempat.setOpaque(false);

        dtTglLahir.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tanggal Lahir"));
        dtTglLahir.setOpaque(false);

        txtNoTelepon.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "No. Telepon/HP"));
        txtNoTelepon.setOpaque(false);

        txtNamaIbu.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nama Ibu"));
        txtNamaIbu.setOpaque(false);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Alamat :"));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setOpaque(false);

        txtAlamat.setOpaque(false);
        jScrollPane3.setViewportView(txtAlamat);

        txtNamaAyah.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nama Ayah"));
        txtNamaAyah.setOpaque(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtNIM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNamaMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cmbJnsKel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtTempat)
                                .addGap(3, 3, 3))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtNamaAyah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNamaIbu)
                            .addComponent(dtTglLahir, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(cmbAgama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtNoTelepon)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNIM, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbJnsKel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAgama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTempat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNamaIbu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaAyah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdInputMahasiswaLayout = new javax.swing.GroupLayout(jdInputMahasiswa.getContentPane());
        jdInputMahasiswa.getContentPane().setLayout(jdInputMahasiswaLayout);
        jdInputMahasiswaLayout.setHorizontalGroup(
            jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                                .addComponent(cmbTA, 0, 191, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jdInputMahasiswaLayout.setVerticalGroup(
            jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)))
                .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jdInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTA, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jdInputMahasiswaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle(".: Form Mahasiswa");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Admin-Schoolar-Icon.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel1.setOpaque(false);

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-add.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        jLabel2.setText("Cari Nama");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(5, 5, 5))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tabel Data Mahasiswa : Klik 2x untuk mengubah/menghapus data"));
        jScrollPane1.setOpaque(false);

        tbDataMahasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "NIM", "Nama Mahasiswa", "L/P", "Tempat Lahir", "Tgl. Lahir", "Alamat", "No. Telepon"
            }
        ));
        tbDataMahasiswa.setRowHeight(25);
        tbDataMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataMahasiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataMahasiswa);

        lbRecord.setText("Record : 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbRecord)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbRecord)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        cariNamaMahasiswa();
    }//GEN-LAST:event_txtCariActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtNIM.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Data NIM harus diisi", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNamaMhs.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Data nama mahasiswa harus diisi", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
            jdInputMahasiswa.setVisible(false);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        clearInput();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        clearInput();
        jdInputMahasiswa.setVisible(true);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(vnim.equals("")){
            if(vnim.equals("")){
                JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataMahasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMahasiswaMouseClicked
        if(evt.getClickCount()==1){
           btnHapus.setEnabled(true);
           vnim = tbDataMahasiswa.getValueAt(tbDataMahasiswa.getSelectedRow(), 0).toString();
           txtNIM.setText(vnim);
       }else if(evt.getClickCount()==2){
           btnSimpan.setText("Ubah");
           jdInputMahasiswa.setVisible(true); jdInputMahasiswa.setLocationRelativeTo(this);
           getDataMahasiswa();
        }
    }//GEN-LAST:event_tbDataMahasiswaMouseClicked

    private void cmbJnsKelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJnsKelItemStateChanged
        if(cmbJnsKel.getSelectedItem().equals("Laki-laki")){
            vjk = "L";
        }else{
            vjk = "P";
        }
    }//GEN-LAST:event_cmbJnsKelItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbAgama;
    private javax.swing.JComboBox<String> cmbJnsKel;
    private javax.swing.JComboBox<String> cmbProdi;
    private javax.swing.JComboBox<String> cmbTA;
    private com.toedter.calendar.JDateChooser dtTglLahir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JDialog jdInputMahasiswa;
    private javax.swing.JLabel lbFoto;
    private javax.swing.JLabel lbRecord;
    private javax.swing.JTable tbDataMahasiswa;
    private javax.swing.JEditorPane txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtNIM;
    private javax.swing.JTextField txtNamaAyah;
    private javax.swing.JTextField txtNamaIbu;
    private javax.swing.JTextField txtNamaMhs;
    private javax.swing.JTextField txtNoTelepon;
    private javax.swing.JTextField txtTempat;
    // End of variables declaration//GEN-END:variables

    
}

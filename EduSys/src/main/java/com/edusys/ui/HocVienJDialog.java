/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.ChuyenDeDAO;
import com.edusys.dao.HocVienDAO;
import com.edusys.dao.KhoaHocDAO;
import com.edusys.dao.NguoiHocDAO;
import com.edusys.entity.ChuyenDe;
import com.edusys.entity.HocVien;
import com.edusys.entity.KhoaHoc;
import com.edusys.entity.NguoiHoc;
import com.edusys.utils.Auth;
import com.edusys.utils.MsgBox;
import com.edusys.utils.XImage;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dzboy2k
 */
public class HocVienJDialog extends javax.swing.JDialog {
    ChuyenDeDAO cdDAO = new ChuyenDeDAO();
    KhoaHocDAO khDAO = new KhoaHocDAO();
    NguoiHocDAO nhDAO = new NguoiHocDAO();
    HocVienDAO hvDAO = new HocVienDAO();
    /**
     * Creates new form HocVienJDialog
     */
    public HocVienJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    void init() {
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("EDUSYS QUẢN LÝ HỌC VIÊN");
        fillComboboxChuyenDe();
    }
    void fillComboboxChuyenDe(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
        model.removeAllElements();
        try {
            List<ChuyenDe> list = cdDAO.selectAll();
            for (ChuyenDe cd : list) {
                model.addElement(cd);
            }
            fillComboboxKhoaHoc();
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    void fillComboboxKhoaHoc(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
        model.removeAllElements();
        try {
            ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
            if (chuyenDe != null) {
                List<KhoaHoc> list = khDAO.selectKhoaHocByChuyenDe(chuyenDe.getMaCD());
                for (KhoaHoc khoaHoc : list) {
                    model.addElement(khoaHoc);
                }
            }
            fillTableHocVien();
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    public void fillTableNguoiHoc(){
        DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if (kh != null) {
                System.out.println("MaKH:" + kh.getMaKH());
                String keyword = txtFind.getText();
                List<NguoiHoc> list = nhDAO.selectNotInCourse(kh.getMaKH(), keyword);
                for (NguoiHoc nh : list) {
                    Object[] row = {
                        nh.getMaNH(), nh.getHoTen(), nh.getNgaySinh(), nh.isGioiTinh() ? "Nam" : "Nữ",
                        nh.getDienThoai(), nh.getEmail()
                    };
                    model.addRow(row);
                }
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu! fillTableNguoiHoc");
        }
    }
    public void fillTableHocVien(){
        DefaultTableModel model = (DefaultTableModel) tblHocVien.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if (kh != null) {
                System.out.println("fillTableHocVien MaKH:" + kh.getMaKH());
                List<HocVien> list = hvDAO.selectByKhoaHoc(kh.getMaKH());
                System.out.println("list:" + list.size());
                for (int i = 0; i < list.size(); i++) {
                    HocVien hv = list.get(i);
                    String hoTen = nhDAO.selectById(hv.getMaNH()).getHoTen();
                    Object[] row = {
                        i + 1, hv.getMaHV(), hv.getMaNH(), hoTen, hv.getDiem()
                    };
                    model.addRow(row);
                }
            }
            fillTableNguoiHoc();
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    void addHocVien(){
        KhoaHoc khoaHoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
        for (int row : tblNguoiHoc.getSelectedRows()) {
            HocVien hv = new HocVien();
            hv.setMaKH(khoaHoc.getMaKH());
            hv.setDiem(0);
            hv.setMaNH((String) tblNguoiHoc.getValueAt(row, 0));
            System.out.println("=>"+hv.getMaKH()+"-"+hv.getMaHV()+"-"+hv.getDiem());
            hvDAO.insert(hv);
        }
        fillTableHocVien();
        tabs.setSelectedIndex(0);
    }
    void removeHocVien(){
//        if (!Auth.isManager()) {
//            MsgBox.alert(this, "Bạn không đủ quyền để xóa học viên");
//        }else{
            if (MsgBox.confirm(this, "Bạn muốn xóa các học viên được chọn")) {
                for (int row : tblHocVien.getSelectedRows()) {
                    int maHV = (int) tblHocVien.getValueAt(row, 1);
                    hvDAO.delete(maHV);
                }
                fillTableHocVien();
            }
//        }
    }
    void updateDiem(){
        for (int i = 0; i < tblHocVien.getRowCount(); i++) {
            int maHV = (int) tblHocVien.getValueAt(i, 1);
            HocVien hocVien = hvDAO.selectById(maHV);
//            hocVien.setDiem((Double) tblHocVien.getValueAt(i, 4));
            hocVien.setDiem(Double.parseDouble(tblHocVien.getValueAt(i, 4).toString()));
            hvDAO.update(hocVien);
        }
        MsgBox.alert(this, "Cập nhật điểm thành công");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cboChuyenDe = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        btnRemoveHocVien = new javax.swing.JButton();
        btnUpdateDiem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtFind = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        btnAddHocVien = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Chuyên đề:");

        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        jLabel2.setText("Khóa học:");

        cboKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhoaHocActionPerformed(evt);
            }
        });

        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HV", "Mã NH", "Họ và Tên", "ĐIểm"
            }
        ));
        jScrollPane2.setViewportView(tblHocVien);
        if (tblHocVien.getColumnModel().getColumnCount() > 0) {
            tblHocVien.getColumnModel().getColumn(0).setResizable(false);
            tblHocVien.getColumnModel().getColumn(0).setPreferredWidth(15);
            tblHocVien.getColumnModel().getColumn(1).setResizable(false);
            tblHocVien.getColumnModel().getColumn(1).setPreferredWidth(15);
            tblHocVien.getColumnModel().getColumn(2).setResizable(false);
            tblHocVien.getColumnModel().getColumn(2).setPreferredWidth(15);
            tblHocVien.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblHocVien.getColumnModel().getColumn(4).setResizable(false);
            tblHocVien.getColumnModel().getColumn(4).setPreferredWidth(15);
        }

        btnRemoveHocVien.setText("Xóa học viên");
        btnRemoveHocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveHocVienActionPerformed(evt);
            }
        });

        btnUpdateDiem.setText("Cập nhật điểm");
        btnUpdateDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRemoveHocVien, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdateDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoveHocVien)
                    .addComponent(btnUpdateDiem))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        tabs.addTab("HỌC VIÊN", jPanel1);

        jLabel3.setText("Tìm kiếm:");

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NH", "Họ và Tên", "Ngày Sinh", "Giới Tính", "Điện Thoại", "Email"
            }
        ));
        jScrollPane1.setViewportView(tblNguoiHoc);

        btnAddHocVien.setText("Thêm học viên vào khóa học");
        btnAddHocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddHocVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFind)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddHocVien, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddHocVien)
                .addContainerGap())
        );

        tabs.addTab("NGƯỜI HỌC", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        // TODO add your handling code here:
        fillComboboxKhoaHoc();
    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHocActionPerformed
        // TODO add your handling code here:
        fillTableHocVien();
    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void btnAddHocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddHocVienActionPerformed
        // TODO add your handling code here:
        addHocVien();
    }//GEN-LAST:event_btnAddHocVienActionPerformed

    private void btnRemoveHocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveHocVienActionPerformed
        // TODO add your handling code here:
        removeHocVien();
    }//GEN-LAST:event_btnRemoveHocVienActionPerformed

    private void btnUpdateDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDiemActionPerformed
        // TODO add your handling code here:
        updateDiem();
    }//GEN-LAST:event_btnUpdateDiemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HocVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HocVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HocVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HocVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HocVienJDialog dialog = new HocVienJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddHocVien;
    private javax.swing.JButton btnRemoveHocVien;
    private javax.swing.JButton btnUpdateDiem;
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtFind;
    // End of variables declaration//GEN-END:variables
}

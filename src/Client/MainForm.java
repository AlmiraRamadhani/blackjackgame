
package Client;

import Server.Kartu;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MainForm extends javax.swing.JFrame {
    
    String username;
    String host;
    int port;
    Socket socket;
    DataOutputStream dos;
    public boolean attachmentOpen = false;
    private boolean isConnected = false;
    private String mydownloadfolder = "D:\\";
    Kartu card;
    
    ArrayList<String> listKartu;
    Random rand;
    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        listKartu = new ArrayList<String>();
        rand = new Random();
    }

    
    public void addCard(String kartu){
        listKartu.add(kartu);
    }
  
    public String dealCard(){
        int index = rand.nextInt(listKartu.size());
        String sr = listKartu.get(index);
       return sr;
    }
    
    
    public void RefreshTotal(){
        int total = 0;
       for (int i = 0; i < listKartu.size(); i++) {
           String[] sek = listKartu.get(i).split(";;");
           String[] kk = sek[0].split("~");
           total += Integer.valueOf(kk[2]);
        }
       
        if (total > 21) {
            total = -1*(total - 21);
        }
       jLabelTotal.setText(String.valueOf(total));
    }
    public void RefreshList(){
        jTextAreaListCard.setText("");
        for (int i = 0; i < listKartu.size(); i++) {
            jTextAreaListCard.append(listKartu.get(i)+"\n");
        }
    }
    public void initFrame(String username, String host, int port){
        this.username = username;
        this.host = host;
        this.port = port;
        setTitle("Masuk sebagai : " + username);
        /** Connect **/
        connect();
    }
    
    public void connect(){
        appendMessage(" Connecting...", "Status", Color.BLACK, Color.BLACK);
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            /** Send our username **/
            dos.writeUTF("CMD_JOIN "+ username);
            appendMessage(" Connected", "Status", Color.BLACK, Color.BLACK);
            appendMessage(" Ketikan pesan ...", "Status", Color.BLACK, Color.BLACK);
            
            /** Start Client Thread **/
            new Thread(new ClientThread(socket, this)).start();
            jButton1.setEnabled(true);
            // were now connected
            isConnected = true;
            
        }
        catch(IOException e) {
            isConnected = false;
            JOptionPane.showMessageDialog(this, "Gagal konek ke server, silahkan coba beberapa saat lagi.!","Koneksi putus",JOptionPane.ERROR_MESSAGE);
            appendMessage("[IOException]: "+ e.getMessage(), "Error", Color.RED, Color.RED);
        }
    }
    
    /*
        is Connected
    */
    public boolean isConnected(){
        return this.isConnected;
    }
    
    /*
        System Message
    */
    public void appendMessage(String msg, String header, Color headerColor, Color contentColor){
        jTextPane1.setEditable(true);
        getMsgHeader(header, headerColor);
        getMsgContent(msg, contentColor);
        jTextPane1.setEditable(false);
    }
    
    /*
        My Message
    */
    public void appendMyMessage(String msg, String header){
        jTextPane1.setEditable(true);
        getMsgHeader(header, Color.BLUE);
        getMsgContent(msg, Color.BLACK);
        jTextPane1.setEditable(false);
    }
    
    /*
        Message Header
    */
    public void getMsgHeader(String header, Color color){
        int len = jTextPane1.getDocument().getLength();
        jTextPane1.setCaretPosition(len);
        jTextPane1.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Impact", 13), false);
        jTextPane1.replaceSelection(header+":");
    }
    /*
        Message Content
    */
    public void getMsgContent(String msg, Color color){
        int len = jTextPane1.getDocument().getLength();
        jTextPane1.setCaretPosition(len);
        jTextPane1.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Arial", 12), false);
        jTextPane1.replaceSelection(msg +"\n\n");
    }
    
    public void appendOnlineList(Vector list){
        //  showOnLineList(list);  -  Original Method()
        sampleOnlineList(list);  // - Sample Method()
    }
    
    /*
        Append online list
    */
    public void showOnLineList(Vector list){
        try {
            txtpane2.setEditable(true);
            txtpane2.setContentType("text/html");
            StringBuilder sb = new StringBuilder();
            Iterator it = list.iterator();
            sb.append("<html><table>");
            while(it.hasNext()){
                Object e = it.next();
                URL url = getImageFile();
                //sb.append("<tr><td><img src='").append(url).append("'></td><td>").append(e).append("</td></tr>");
                sb.append("<tr><td><b>></b></td><td>").append(e).append("</td></tr>");
                System.out.println("Online: "+ e);
            }
            sb.append("</table></body></html>");
            txtpane2.removeAll();
            txtpane2.setText(sb.toString());
            txtpane2.setEditable(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*
      ************************************  Show Online Sample  *********************************************
    */
    private void sampleOnlineList(Vector list){
        txtpane2.setEditable(true);
        txtpane2.removeAll();
        txtpane2.setText("");
        Iterator i = list.iterator();
        while(i.hasNext()){
            Object e = i.next();
            /*  Show Online Username   */
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.white);
            
            JLabel label = new JLabel();
            label.setText(" "+ e);
            panel.add(label);
            int len = txtpane2.getDocument().getLength();
            txtpane2.setCaretPosition(len);
            txtpane2.insertComponent(panel);
            /*  Append Next Line   */
            sampleAppend();
        }
        txtpane2.setEditable(false);
    }
    private void sampleAppend(){
        int len = txtpane2.getDocument().getLength();
        txtpane2.setCaretPosition(len);
        txtpane2.replaceSelection("\n");
    }
    /*
      ************************************  Show Online Sample  *********************************************
    */
    
    
    
    
    /*
        Get image file path
    */
    public URL getImageFile(){
        URL url = this.getClass().getResource("/images/online.png");
        return url;
    }
    
    
    /*
        Set myTitle
    */
    public void setMyTitle(String s){
        setTitle(s);
    }
    
    /*
        Get My Download Folder
    */
    public String getMyDownloadFolder(){
        return this.mydownloadfolder;
    }
    
    /*
        Get Host
    */
    public String getMyHost(){
        return this.host;
    }
    
    /*
        Get Port
    */
    public int getMyPort(){
        return this.port;
    }
    
    /*
        Get My Username
    */
    public String getMyUsername(){
        return this.username;
    }
    
    /*
        Update Attachment 
    */
    public void updateAttachment(boolean b){
        this.attachmentOpen = b;
    }
    
    public void getCard(){
        try {
            String content = username;
            dos.writeUTF("CMD_GETCARD "+ content);
            //appendMyMessage(" "+jTextField1.getText(), username);
            //jTextField1.setText("");
        } catch (IOException e) {
            appendMessage("Gagal mengirim pesan, server sedang bermasalah atau coba restart aplikasi.!", "Error", Color.RED, Color.RED);
        }
    }
    
    /*
        This will open a file chooser
    */
    public void openFolder(){
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int open = chooser.showDialog(this, "Browse Folder");
        if(open == chooser.APPROVE_OPTION){
            mydownloadfolder = chooser.getSelectedFile().toString()+"\\";
        } else {
            mydownloadfolder = "D:\\";
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

        chooser = new javax.swing.JFileChooser();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtpane2 = new javax.swing.JTextPane();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaListCard = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        LogoutMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Aplikasi Chat IP");
        setBackground(new java.awt.Color(145, 53, 53));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 153, 204));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Kirim");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jTextPane1);

        txtpane2.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        txtpane2.setForeground(new java.awt.Color(120, 14, 3));
        txtpane2.setAutoscrolls(false);
        txtpane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(txtpane2);

        jButton2.setText("Draw");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextAreaListCard.setColumns(20);
        jTextAreaListCard.setRows(5);
        jScrollPane2.setViewportView(jTextAreaListCard);

        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabelTotal.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabelTotal.setText("00");

        jMenu2.setText("My Account");

        LogoutMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, java.awt.event.InputEvent.CTRL_MASK));
        LogoutMenu.setText("Logout");
        LogoutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutMenuActionPerformed(evt);
            }
        });
        jMenu2.add(LogoutMenu);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelTotal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jButton3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            String content = username+" "+ jTextField1.getText();
            dos.writeUTF("CMD_CHATALL "+ content);
            appendMyMessage(" "+jTextField1.getText(), username);
            jTextField1.setText("");
        } catch (IOException e) {
            appendMessage("Gagal mengirim pesan, server sedang bermasalah atau coba restart aplikasi.!", "Error", Color.RED, Color.RED);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, "Tutup aplikasi ini.?");
        if(confirm == 0){
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            this.dispose();
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        try {
            String content = username+" "+ evt.getActionCommand();
            dos.writeUTF("CMD_CHATALL "+ content);
            appendMyMessage(" "+evt.getActionCommand(), username);
            jTextField1.setText("");
        } catch (IOException e) {
            appendMessage("Gagal mengirim pesan, server sedang bermasalah atau coba restart aplikasi.!", "Error", Color.RED, Color.RED);
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void LogoutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutMenuActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, "Akan Logout.?");
        if(confirm == 0){
            try {
                socket.close();
                setVisible(false);
                /** Login Form **/
                new LoginForm().setVisible(true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_LogoutMenuActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        getCard();
                try{
                String content = username+" Draw Card";
            dos.writeUTF("CMD_CHATALL "+ content);
            //appendMyMessage(" "+jTextField1.getText(), username);
            jTextField1.setText("");
                }catch (IOException e) {
                System.out.println(e.getMessage());
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        RefreshTotal();
        try{
                String content = username+" "+jLabelTotal.getText();
            dos.writeUTF("CMD_CLOSECARD "+ content);
            
            //appendMyMessage(" "+jTextField1.getText(), username);
            jTextField1.setText("");
                }catch (IOException e) {
                System.out.println(e.getMessage());
            }
        jButton2.setEnabled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem LogoutMenu;
    private javax.swing.JFileChooser chooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaListCard;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane txtpane2;
    // End of variables declaration//GEN-END:variables
}

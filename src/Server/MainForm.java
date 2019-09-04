/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Color;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static java.nio.file.Files.size;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import javax.swing.JOptionPane;

public class MainForm extends javax.swing.JFrame {

    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
    Thread t;
    ServerThread serverThread;
    private int size;
    Client.MainForm main;
    /**
     * Chat List  *
     */
    public Vector socketList = new Vector();
    public Vector clientList = new Vector();
    /**
     * File Sharing List *
     */
    public Vector clientFileSharingUsername = new Vector();
    public Vector clientFileSharingSocket = new Vector();
    /**
     * Server *
     */
    ServerSocket server;

    ArrayList<Kartu> listKartu;
    ArrayList<Integer> listTotal;
    /**
     * Creates new form MainForm
     */

    int idxCard = 0;
    Random rn = new Random();

    public MainForm() {
        initComponents();
        listKartu = new ArrayList<Kartu>();
        listTotal = new ArrayList<Integer>();
        
        listKartu.add(new Kartu("Diamond", "Ace", 11));
        listKartu.add(new Kartu("Diamond", "Two", 2));
        listKartu.add(new Kartu("Diamond", "Three", 3));
        listKartu.add(new Kartu("Diamond", "Four", 4));
        listKartu.add(new Kartu("Diamond", "Five", 5));

        listKartu.add(new Kartu("Diamond", "Six", 6));
        listKartu.add(new Kartu("Diamond", "Seven", 7));
        listKartu.add(new Kartu("Diamond", "Eight", 8));
        listKartu.add(new Kartu("Diamond", "Nine", 9));
        listKartu.add(new Kartu("Diamond", "Ten", 10));

        listKartu.add(new Kartu("Diamond", "Jack", 10));
        listKartu.add(new Kartu("Diamond", "Queen", 10));
        listKartu.add(new Kartu("Diamond", "King", 10));

        listKartu.add(new Kartu("Clubs", "Ace", 11));
        listKartu.add(new Kartu("Clubs", "Two", 2));
        listKartu.add(new Kartu("Clubs", "Three", 3));
        listKartu.add(new Kartu("Clubs", "Four", 4));
        listKartu.add(new Kartu("Clubs", "Five", 5));

        listKartu.add(new Kartu("Clubs", "Six", 6));
        listKartu.add(new Kartu("Clubs", "Seven", 7));
        listKartu.add(new Kartu("Clubs", "Eight", 8));
        listKartu.add(new Kartu("Clubs", "Nine", 9));
        listKartu.add(new Kartu("Clubs", "Ten", 10));

        listKartu.add(new Kartu("Clubs", "Jack", 10));
        listKartu.add(new Kartu("Clubs", "Queen", 10));
        listKartu.add(new Kartu("Clubs", "King", 10));

        listKartu.add(new Kartu("Hearts", "Ace", 11));
        listKartu.add(new Kartu("Hearts", "Two", 2));
        listKartu.add(new Kartu("Hearts", "Three", 3));
        listKartu.add(new Kartu("Hearts", "Four", 4));
        listKartu.add(new Kartu("Hearts", "Five", 5));

        listKartu.add(new Kartu("Hearts", "Six", 6));
        listKartu.add(new Kartu("Hearts", "Seven", 7));
        listKartu.add(new Kartu("Hearts", "Eight", 8));
        listKartu.add(new Kartu("Hearts", "Nine", 9));
        listKartu.add(new Kartu("Hearts", "Ten", 10));

        listKartu.add(new Kartu("Hearts", "Jack", 10));
        listKartu.add(new Kartu("Hearts", "Queen", 10));
        listKartu.add(new Kartu("Hearts", "King", 10));

        listKartu.add(new Kartu("Spades", "Ace", 11));
        listKartu.add(new Kartu("Spades", "Two", 2));
        listKartu.add(new Kartu("Spades", "Three", 3));
        listKartu.add(new Kartu("Spades", "Four", 4));
        listKartu.add(new Kartu("Spades", "Five", 5));

        listKartu.add(new Kartu("Spades", "Six", 6));
        listKartu.add(new Kartu("Spades", "Seven", 7));
        listKartu.add(new Kartu("Spades", "Eight", 8));
        listKartu.add(new Kartu("Spades", "Nine", 9));
        listKartu.add(new Kartu("Spades", "Ten", 10));

        listKartu.add(new Kartu("Spades", "Jack", 10));
        listKartu.add(new Kartu("Spades", "Queen", 10));
        listKartu.add(new Kartu("Spades", "King", 10));
    }
    
    public Kartu deadlcard(){
//        int index = rn.nextInt(listKartu.size());
//        Kartu cards = listKartu.get(index);
        idxCard = rn.nextInt(listKartu.size());
        Kartu cards = listKartu.get(idxCard);
        return cards;
    }

    public void appendMessage(String msg) {
        Date date = new Date();
        jTextArea1.append(sdf.format(date) + ": " + msg + "\n");
        jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
    }

    public void nextCard() {
        if (idxCard < listKartu.size() + 1) {
            idxCard++;
        } else {
            idxCard = 0;
        }
    }

    /**
     * Setters *
     */
    public void setSocketList(Socket socket) {
        try {
            socketList.add(socket);
            appendMessage("[setSocketList]: Added");
        } catch (Exception e) {
            appendMessage("[setSocketList]: " + e.getMessage());
        }
    }

    public void setClientList(String client) {
        try {
            clientList.add(client);
            appendMessage("[setClientList]: Added");
        } catch (Exception e) {
            appendMessage("[setClientList]: " + e.getMessage());
        }
    }

    public void setClientFileSharingUsername(String user) {
        try {
            clientFileSharingUsername.add(user);
        } catch (Exception e) {
        }
    }

    public void setClientFileSharingSocket(Socket soc) {
        try {
            clientFileSharingSocket.add(soc);
        } catch (Exception e) {
        }
    }

    /**
     * Getters
     *
     * @param client
     * @return  *
     */
    public Socket getClientList(String client) {
        Socket tsoc = null;
        for (int x = 0; x < clientList.size(); x++) {
            if (clientList.get(x).equals(client)) {
                tsoc = (Socket) socketList.get(x);
                break;
            }
        }
        return tsoc;
    }

    public void removeFromTheList(String client) {
        try {
            for (int x = 0; x < clientList.size(); x++) {
                if (clientList.elementAt(x).equals(client)) {
                    clientList.removeElementAt(x);
                    socketList.removeElementAt(x);
                    appendMessage("[Removed]: " + client);
                    break;
                }
            }
        } catch (Exception e) {
            appendMessage("[RemovedException]: " + e.getMessage());
        }
    }

    public Socket getClientFileSharingSocket(String username) {
        Socket tsoc = null;
        for (int x = 0; x < clientFileSharingUsername.size(); x++) {
            if (clientFileSharingUsername.elementAt(x).equals(username)) {
                tsoc = (Socket) clientFileSharingSocket.elementAt(x);
                break;
            }
        }
        return tsoc;
    }

    /*
    Remove Client File Sharing List
     */
    public void removeClientFileSharing(String username) {
        for (int x = 0; x < clientFileSharingUsername.size(); x++) {
            if (clientFileSharingUsername.elementAt(x).equals(username)) {
                try {
                    Socket rSock = getClientFileSharingSocket(username);
                    if (rSock != null) {
                        rSock.close();
                    }
                    clientFileSharingUsername.removeElementAt(x);
                    clientFileSharingSocket.removeElementAt(x);
                    appendMessage("[FileSharing]: Removed " + username);
                } catch (IOException e) {
                    appendMessage("[FileSharing]: " + e.getMessage());
                    appendMessage("[FileSharing]: Unable to Remove " + username);
                }
                break;
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

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server Aplikasi Chat IP");
        setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setText("Port:");

        jTextField1.setText("8080");

        jButton1.setText("Start Server");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Stop Server");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(0, 204, 51));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton3.setText("Start");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Reset");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("asd");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton5))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int port = Integer.parseInt(jTextField1.getText());
        serverThread = new ServerThread(port, this);
        t = new Thread(serverThread);
        t.start();

        new Thread(new OnlineListThread(this)).start();

        jButton1.setEnabled(false);
        jButton2.setEnabled(true);
//        jButton3.setEnabled(false);

//        if(clientList.size() > 1 ){
//            jButton3.setEnabled(true);
//        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, "Tutup Server.?");
        if (confirm == 0) {
            serverThread.stop();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        if (clientList.size() > 0) {
            for (int x = 0; x < clientList.size(); x++) {

                try {
                    Kartu k = listKartu.get(idxCard);
                    this.deadlcard();
                    //Kartu k = listKartu.get(size);
                    //this.Shuffle();
//                    Deck k = new Deck();
//                    String kartu = k.toString();
                    String kartu = k.getJenis() + "~" + k.getNama() + "~" + k.getValue() + ";;";
                    Kartu k2 = listKartu.get(idxCard);
                    this.deadlcard();
//                    Kartu k2 = listKartu.get(size);
//                    this.Shuffle();
//                    Deck k2 = new Deck();
//                    String kartu2 = k2.toString();
                    String kartu2 = k2.getJenis() + "~" + k2.getNama() + "~" + k2.getValue() + ";;";

                    Socket tsoc2 = (Socket) socketList.elementAt(x);
                    DataOutputStream dos2 = new DataOutputStream(tsoc2.getOutputStream());
                    dos2.writeUTF("GET_CARD " + kartu);
                    dos2.writeUTF("GET_CARD " + kartu2);
                    for (int i = 0; i < socketList.size(); i++) {
                        if (!socketList.elementAt(i).equals(x)) {

                            dos2.writeUTF("CMD_MESSAGE " + clientList.elementAt(i).toString() + " " + kartu + " ;; " + " HIDE");

                        }
                    }
                } catch (IOException e) {
                    appendMessage("[CMD_CHATALL]: " + e.getMessage());
                }

            }
            jButton3.setEnabled(false);
            jButton4.setEnabled(true);
            
        } else {

            JOptionPane.showMessageDialog(this, "Minimal ada 3 player atau lebih");

        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jButton3.setEnabled(true);
        jButton4.setEnabled(false);
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton5ActionPerformed

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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    private void Shuffle() {
       Kartu temp;
       for(int x = 1 ; x<=1000; x++){
           int ran1 = (int)(Math.random()* size);
           int ran2 = (int)(Math.random()* size);
           temp = listKartu.get(ran1);
           listKartu.set(ran1, listKartu.get(ran2));
           listKartu.set(ran1, temp);
       }
    }

}

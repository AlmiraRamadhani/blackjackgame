package Client;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;


public class ClientThread implements Runnable{
    
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    MainForm main;
    StringTokenizer st;
    protected DecimalFormat df = new DecimalFormat("##,#00");
    
    public ClientThread(Socket socket, MainForm main){
        this.main = main;
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            main.appendMessage("[IOException]: "+ e.getMessage(), "Error", Color.RED, Color.RED);
        }
    }

    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()){
                String data = dis.readUTF();
                st = new StringTokenizer(data);
                /** Get Message CMD **/
                String CMD = st.nextToken();
                switch(CMD){
                    case "CMD_MESSAGE":
                        String msg = "";
                        String frm = st.nextToken();
                        while(st.hasMoreTokens()){
                            msg = msg +" "+ st.nextToken();
                        }
                        main.appendMessage(msg, frm, Color.BLACK, Color.RED);
                        break;
                        
                    case "GET_CARD":
                        String kartuData = st.nextToken();
                        main.addCard(kartuData);
                        main.RefreshList();
                        break;
                        
                    case "CMD_ONLINE":
                        Vector online = new Vector();
                        while(st.hasMoreTokens()){
                            String list = st.nextToken();
                            if(!list.equalsIgnoreCase(main.username)){
                                online.add(list);
                            }
                        }
                        main.appendOnlineList(online);
                        break;
//                    case "CMD_SCORE":
//                            String total = st.nextToken();
//                            main.
//                            main.RefreshTotal();
//                            break;
                    
                        
                    //  This will inform the client that there's a file receive, Accept or Reject the file  
                    case "CMD_FILE_XD":  // Format:  CMD_FILE_XD [sender] [receiver] [filename]
                        String sender = st.nextToken();
                        String receiver = st.nextToken();
                        String fname = st.nextToken();
                        int confirm = JOptionPane.showConfirmDialog(main, "From: "+sender+"\nFilename: "+fname+"\nApakah file akan anda terima ?");
                        //SoundEffect.FileSharing.play(); //   Play Audio
                        if(confirm == 0){ // client accepted the request, then inform the sender to send the file now
                            /* Select were to save this file   */
                            main.openFolder();
                            try {
                                dos = new DataOutputStream(socket.getOutputStream());
                                // Format:  CMD_SEND_FILE_ACCEPT [ToSender] [Message]
                                String format = "CMD_SEND_FILE_ACCEPT "+sender+" menerima";
                                dos.writeUTF(format);
                                
                                /*  this will create a filesharing socket to handle incoming file and this socket will automatically closed when it's done.  */
                                Socket fSoc = new Socket(main.getMyHost(), main.getMyPort());
                                DataOutputStream fdos = new DataOutputStream(fSoc.getOutputStream());
                                fdos.writeUTF("CMD_SHARINGSOCKET "+ main.getMyUsername());
                                /*  Run Thread for this   */
                                new Thread(new ReceivingFileThread(fSoc, main)).start();
                            } catch (IOException e) {
                                System.out.println("[CMD_FILE_XD]: "+e.getMessage());
                            }
                        } else { // client rejected the request, then send back result to sender
                            try {
                                dos = new DataOutputStream(socket.getOutputStream());
                                // Format:  CMD_SEND_FILE_ERROR [ToSender] [Message]
                                String format = "CMD_SEND_FILE_ERROR "+sender+" Client menolak requestmu atau koneksi sedang bermasalah !.";
                                dos.writeUTF(format);
                            } catch (IOException e) {
                                System.out.println("[CMD_FILE_XD]: "+e.getMessage());
                            }
                        }                       
                        break;   
                        
                    default: 
                        main.appendMessage("[CMDException]: Unknown Command "+ CMD, "CMDException", Color.RED, Color.RED);
                    break;
                }
            }
        } catch(IOException e){
            main.appendMessage(" Koneksi server sedang bermasalah, mohon tunggu beberapa saat !.", "Error", Color.RED, Color.RED);
        }
    }
}
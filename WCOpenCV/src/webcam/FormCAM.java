package webcam;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class FormCAM extends javax.swing.JFrame {
    //Variaveis
    private DaemonThread myThread = null;
    VideoCapture video = null;
    Mat frame = new Mat();
    MatOfByte frameB = new MatOfByte();
    
    class DaemonThread implements Runnable {
        
        volatile boolean runnable = false;
        
        @Override
        public void run(){
            synchronized(this) {
                while(runnable){
                    if(video.grab()){
                        try{
                            video.retrieve(frame);
                            Graphics g = painel1.getGraphics();
                            
                            Imgcodecs.imencode(".bmp", frame, frameB);
                            
                            Image im = ImageIO.read(new ByteArrayInputStream(frameB.toArray()));
                            
                            BufferedImage buff = (BufferedImage) im;
                            
                            if(g.drawImage(buff, 0, 0, getWidth(), getHeight(), 0, 0, buff.getWidth(), buff.getHeight(), null)){
                                if(runnable == false){
                                    System.out.println("Pausando...");
                                    this.wait();
                                }
                            }
                        } catch(IOException ex){
                            System.out.println("Erro!");
                        } catch(InterruptedException ex) {
                            Logger.getLogger(FormCAM.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }
    public FormCAM() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel2 = new javax.swing.JPanel();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        painel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("webcam");
        setResizable(false);

        painel2.setBackground(new java.awt.Color(255, 0, 0));

        btn1.setText("iniciar");
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btn2.setText("parar");
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painel2Layout = new javax.swing.GroupLayout(painel2);
        painel2.setLayout(painel2Layout);
        painel2Layout.setHorizontalGroup(
            painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(418, Short.MAX_VALUE))
        );
        painel2Layout.setVerticalGroup(
            painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(painel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout painel1Layout = new javax.swing.GroupLayout(painel1);
        painel1.setLayout(painel1Layout);
        painel1Layout.setHorizontalGroup(
            painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        painel1Layout.setVerticalGroup(
            painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(painel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(painel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        video = new VideoCapture(0);
        myThread = new DaemonThread();
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();
        btn1.setEnabled(false);
        btn2.setEnabled(true);
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        myThread.runnable = false;
        btn1.setEnabled(true);
        btn2.setEnabled(false);
        
        video.release();
    }//GEN-LAST:event_btn2ActionPerformed

    public static void main(String args[]) throws Exception {
        String model = System.getProperty("sun.arch.data.model");
        String libraryPath = "C:/dist/lib/x86";
        if(model.equals("64")){
            libraryPath = "C:/dist/lib/x64";
        }
        System.setProperty("java.library.path", libraryPath);
        Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
        sysPath.setAccessible(true);
        sysPath.set(null, null);
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
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
            java.util.logging.Logger.getLogger(FormCAM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCAM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCAM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCAM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FormCAM().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btn1;
    protected javax.swing.JButton btn2;
    protected javax.swing.JPanel painel1;
    protected javax.swing.JPanel painel2;
    // End of variables declaration//GEN-END:variables
}

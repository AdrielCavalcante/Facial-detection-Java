package webcam;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import java.awt.Toolkit;
import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FormCAM extends javax.swing.JFrame {
    
    private DaemonThread myThread = null;
    VideoCapture video = null;
    Mat frame = new Mat();
    MatOfByte frameB = new MatOfByte();
    MatOfRect faceDetections = new MatOfRect();
    CascadeClassifier faceDetector = new CascadeClassifier(FormCAM.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
    
    class DaemonThread implements Runnable {
        
    volatile boolean runnable = false;

        @Override
        public void run() {
                synchronized (this) {
                    while (runnable) {
                        if (video.grab()) {
                            try {
                                video.retrieve(frame);
                                Graphics g = painel1.getGraphics();
                                faceDetector.detectMultiScale(frame, faceDetections);
                                for (Rect rect : faceDetections.toArray()) {
                                    Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255,0),3);
                                }
                                Imgcodecs.imencode(".bmp", frame, frameB);
                                Image im = ImageIO.read(new ByteArrayInputStream(frameB.toArray()));
                                BufferedImage buff = (BufferedImage) im;
                                if (g.drawImage(buff, 0, 0, getWidth(), getHeight(), 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                    if (runnable == false) {
                                        System.out.println("Pausado...");
                                        this.wait();
                                    }
                                }
                            } catch (IOException ex) {
                                System.out.println("Erro!!!");
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FormCAM.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
    
    public FormCAM() {
        initComponents();
        System.out.println(FormCAM.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
        System.out.println(FormCAM.class.getResource("lbpcascade_frontalface.xml").getPath().substring(1));
        setIcon();
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel1 = new javax.swing.JPanel();
        painel2 = new javax.swing.JPanel();
        Btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WebCamJava");
        setResizable(false);

        javax.swing.GroupLayout painel1Layout = new javax.swing.GroupLayout(painel1);
        painel1.setLayout(painel1Layout);
        painel1Layout.setHorizontalGroup(
            painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        painel1Layout.setVerticalGroup(
            painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );

        painel2.setBackground(new java.awt.Color(153, 153, 153));

        Btn1.setText("Iniciar");
        Btn1.setFocusPainted(false);
        Btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn1ActionPerformed(evt);
            }
        });

        btn2.setText("Parar");
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        btn3.setText("Imagem");
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painel2Layout = new javax.swing.GroupLayout(painel2);
        painel2.setLayout(painel2Layout);
        painel2Layout.setHorizontalGroup(
            painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel2Layout.createSequentialGroup()
                .addComponent(Btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn3)
                .addGap(0, 365, Short.MAX_VALUE))
        );
        painel2Layout.setVerticalGroup(
            painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(Btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(painel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(painel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn1ActionPerformed
        video = new VideoCapture(0); // video capture from default cam
        myThread = new DaemonThread(); //create object of threat class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();                 //start thrad
        Btn1.setEnabled(false);  // deactivate start button
        btn2.setEnabled(true);  //  activate stop button

    }//GEN-LAST:event_Btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        myThread.runnable = false;            // stop thread
        Btn1.setEnabled(true);  // deactivate start button
        btn2.setEnabled(false);    // deactivate stop button

        video.release();  // stop caturing fron cam

    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        JFileChooser arquivo = new JFileChooser();
        arquivo.setDialogTitle("Selecionar imagem para detecção");
        arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagem","jpg","png");
        
        arquivo.setFileFilter(filtro);
        int retorno = arquivo.showOpenDialog(this);
        
        if (retorno == JFileChooser.APPROVE_OPTION) {
            File file = arquivo.getSelectedFile();
            String imgArquivo = file.getPath();
            
            Mat src = Imgcodecs.imread(imgArquivo);
                
            CascadeClassifier cc = new CascadeClassifier(FormCAM.class.getResource("lbpcascade_frontalface.xml").getPath().substring(1));
            
            MatOfRect faceDetection = new MatOfRect();
            cc.detectMultiScale(src, faceDetection);
            Detectado detectado = new Detectado();
            detectado.numeroDec.setText(String.format("Faces detectadas: %d", faceDetection.toArray().length));
            detectado.setVisible(true);
            
            for (Rect rect : faceDetection.toArray()) {
                Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255,0),3);
            }
            if (!imgArquivo.equals(imgArquivo.replaceAll(".jpg", ".png"))) {
                Imgcodecs.imwrite(imgArquivo.replaceAll(".jpg", "output")+".jpg", src);
            }  
            if (!imgArquivo.equals(imgArquivo.replaceAll(".png", ".jpg"))) {
                Imgcodecs.imwrite(imgArquivo.replaceAll(".png", "output")+".png", src);
            }
            detectado.local.setText(String.format("Localizado em: "+imgArquivo));
       }
        
    }//GEN-LAST:event_btn3ActionPerformed
    public static void main(String args[]) throws Exception {
        // get the model
        String model = System.getProperty("sun.arch.data.model");
        // the path the .dll lib location
        String libraryPath = "C:/WCOpenCV/dist/lib/x86/";
        // check for if system is 64 or 32
        if(model.equals("64")) {
            libraryPath = "C:/WCOpenCV/dist/lib/x64/";
        }
        // set the path
        System.setProperty("java.library.path", libraryPath);
        Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
        sysPath.setAccessible(true);
        sysPath.set(null, null);
        // load the lib
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
    protected javax.swing.JButton Btn1;
    protected javax.swing.JButton btn2;
    protected javax.swing.JButton btn3;
    protected javax.swing.JPanel painel1;
    protected javax.swing.JPanel painel2;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("favicon.ico")));
        }
}


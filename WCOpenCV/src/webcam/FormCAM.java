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
import org.opencv.core.Size;

public class FormCAM extends javax.swing.JFrame {
    // Variavéis
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
                                faceDetector.load("C:/dist/lib/haarcascade_frontalface_alt.xml");
                                video.retrieve(frame);
                                Graphics g = painel1.getGraphics();
                                faceDetector.detectMultiScale(frame, faceDetections, 1.3, 3, 0, new Size(30,30));
                                for (Rect rect : faceDetections.toArray()) { // Repetição de de quadrados
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
        btn4 = new javax.swing.JButton();

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

        btn4.setText("Sobre");
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 282, Short.MAX_VALUE))
        );
        painel2Layout.setVerticalGroup(
            painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel2Layout.createSequentialGroup()
                .addComponent(Btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
        video = new VideoCapture(0); // 0 captura a câmera padrão do dispositivo
        myThread = new DaemonThread(); // Instancia o objeto da classe Thread
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();                 // Inicia a Thread
        Btn1.setEnabled(false);  // Desativa o botão de ligar
        btn2.setEnabled(true);  //  Ativa o botão de parar

    }//GEN-LAST:event_Btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        myThread.runnable = false;  // Para a Thread
        Btn1.setEnabled(true);  // Ativa o botão de ligar
        btn2.setEnabled(false);    // Desativa o botão de para
        
        video.release();  // Para a captura da câmera

    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        JFileChooser arquivo = new JFileChooser();  // Seletor de arquivos
        arquivo.setDialogTitle("Selecionar imagem para detecção");
        arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY); 
        // Definindo apenas imagens jpg e png para estarem disponíveis
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("image","jpg","png"); 
        
        arquivo.setFileFilter(filtro);
        int retorno = arquivo.showOpenDialog(this);
        // Se o usuário pressionar em selecionar
        if (retorno == JFileChooser.APPROVE_OPTION) {
            File file = arquivo.getSelectedFile();
            String imgArquivo = file.getPath();
            
            Mat src = Imgcodecs.imread(imgArquivo);
            // Carregando o xml da biblioteca no projeto    
            CascadeClassifier cc = new CascadeClassifier(FormCAM.class.getResource("lbpcascade_frontalface.xml").getPath().substring(1));
            cc.load("C:/dist/lib/lbpcascade_frontalface.xml");
            MatOfRect faceDetection = new MatOfRect();
            cc.detectMultiScale(src, faceDetection, 1.1, 3, 0, new Size(30,30));
            // Classe jFrame para mostrar quantas pessoas foram detectadas e o caminho para a imagem
            Detectado detectado = new Detectado();
            detectado.numeroDec.setText(String.format("Faces detectadas: %d", faceDetection.toArray().length));
            detectado.setVisible(true);
            
            for (Rect rect : faceDetection.toArray()) {
                Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255,0),3);
            }
            // Se o caminho da imagem for diferente, repondo .jpg por .png, substitua .jpg por output.jpg
            if (!imgArquivo.equals(imgArquivo.replaceAll(".jpg", ".png"))) {
                Imgcodecs.imwrite(imgArquivo.replaceAll(".jpg", "output")+".jpg", src);
            }
            // Se o caminho da imagem for diferente, repondo .png por .jpg, substitua .png por output.png
            if (!imgArquivo.equals(imgArquivo.replaceAll(".png", ".jpg"))) {
                Imgcodecs.imwrite(imgArquivo.replaceAll(".png", "output")+".png", src);
            }
            detectado.local.setText(String.format("Localizado em: "+imgArquivo));
       }
        
    }//GEN-LAST:event_btn3ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        Sobre sobre = new Sobre();
        sobre.setVisible(true);
    }//GEN-LAST:event_btn4ActionPerformed
    public static void main(String args[]) throws Exception {
        // Pega o modelo
        String model = System.getProperty("sun.arch.data.model");
        // Caminho da DLL da biblioteca
        String libraryPath = "C:/dist/lib/x86";
        // Verifica se o sistema é 32 ou 64 bits
        if(model.equals("64")) {
            libraryPath = "C:/dist/lib/x64";
        }
        // Define o caminho
        System.setProperty("java.library.path", libraryPath);
        Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
        sysPath.setAccessible(true);
        sysPath.set(null, null);
        // Carrega a biblioteca
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
        java.awt.EventQueue.invokeLater(() -> {
            new FormCAM().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton Btn1;
    protected javax.swing.JButton btn2;
    protected javax.swing.JButton btn3;
    protected javax.swing.JButton btn4;
    protected javax.swing.JPanel painel1;
    protected javax.swing.JPanel painel2;
    // End of variables declaration//GEN-END:variables
    
    // Icone do projeto
    private void setIcon() {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("favicon.ico")));
        }
}


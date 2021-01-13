package webcam;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class PainelCAM extends javax.swing.JPanel implements Runnable{

    private VideoCapture video;
    private Mat frame;
    private BufferedImage buff;
    
    public PainelCAM() {
        initComponents();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        new Thread(this).start();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (buff == null) {
            return;
        }
        g.drawImage(buff, 0, 0, buff.getWidth(), buff.getHeight(), null);
    }
    
    @Override
    public void run() {
        this.video = new VideoCapture(0);
        this.frame = new Mat();
        if(video.isOpened()){
            while (true){
            video.read(frame);
            if (!frame.empty()) {
                MatToBufferedImage(frame);
                this.repaint();
            }
          }
        }
    }

    private void MatToBufferedImage(Mat mat) {
        int largura = mat.width();
        int altura = mat.height();
        int canais = mat.channels(); //RGB
        
        byte source[] = new byte[altura * largura * canais];
        mat.get(0,0,source);
        buff = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);
        final byte saida[] = ((DataBufferByte) buff.getRaster().getDataBuffer()).getData();
        System.arraycopy(source, 0, saida, 0, source.length);
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

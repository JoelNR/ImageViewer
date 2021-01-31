package app.swing;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Image;
import view.ImageDisplay;


public class ImagePanel extends JPanel implements ImageDisplay{

    private BufferedImage data;
    private BufferedImage dataNext;
    private Image image;
    private Shift shift;
    private int offset;

    
    public ImagePanel() {
        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }
    
    @Override
    public void paint(Graphics g) {
        Box box = new Box(data.getWidth(),data.getHeight(), this.getWidth(),this.getHeight());  
        
        if(data != null){
            g.drawImage(data,box.x,box.y, box.width, box.height,null);
        }
        if(dataNext != null && offset != 0) {
            g.drawImage(dataNext,offset < 0 ? data.getWidth() + offset : offset - dataNext.getWidth(),box.y, box.width, box.height,null);
        }
    }

    @Override
    public void display(Image image) {
        this.image = image;
        this.data = load(image);
        repaint();
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    private BufferedImage load(Image image) {
        try {
            return ImageIO.read(new File(image.getName()));
        } catch (IOException ex) {
            return null; 
        }
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift;
    }
    
    private static class Box {
        final int x;
        final int y;
        final int width;
        final int height;
        
        private Box(double imageWidth, double imageHeight, double panelWidth, double panelHeight) {
            double imageRatio = imageWidth / imageHeight;
            double panelRatio = panelWidth / imageHeight;
            this.width =(int) (imageRatio >= panelRatio ? panelWidth : panelWidth * panelHeight/imageHeight);
            this.height =(int) (imageRatio <= panelRatio ? panelHeight : panelHeight * panelWidth/imageWidth);
            this.x = (int)(panelWidth -this.width) /2;
            this.y = (int)(panelHeight - this.height) /2; 
        }
        
    }
    private Image imageAt(int shift) {
        if (shift > 0) return this.shift.left();
        if (shift < 0) return this.shift.right();
        return null;
    }
    
    private class MouseHandler implements MouseListener,MouseMotionListener{
        private int initial; 
        
        @Override
        public void mouseClicked(MouseEvent event) {

        }

        @Override
        public void mousePressed(MouseEvent event) {
            this.initial = event.getX();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            int shift = shift(event.getX());
            if(Math.abs(shift) > getWidth() /2) {
                image = imageAt(shift);
                data = load(image);
            }
            offset = 0;
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            int shift =shift(event.getX());
            if(shift == 0) dataNext = null; 
            if (offset/shift <= 0) dataNext = load(imageAt(shift));               
            offset = shift;
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent event) {
        }

        private int shift(int x) {
            return x - initial;
        }


        
    }
   
}

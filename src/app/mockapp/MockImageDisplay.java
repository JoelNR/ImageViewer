package app.mockapp;

import model.Image;
import view.ImageDisplay;


public class MockImageDisplay implements ImageDisplay {
    private Image image;

    @Override
    public void display(Image image) {
        this.image = image;
        System.out.println(image.getName());
    }

    @Override
    public Image getImage() {
        return null; 
    }

    @Override
    public void on(Shift shift) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

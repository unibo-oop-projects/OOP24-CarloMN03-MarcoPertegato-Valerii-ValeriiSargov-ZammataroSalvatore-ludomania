package ludomania.core.impl;

import java.util.Map;

import javafx.scene.image.Image;
import ludomania.core.api.ImageManager;

public class ImageManagerImpl implements ImageManager {
    public final static String DEFAULT_LOCATION = "images/";
    Map<String, Image> images;
    private final String pathToImages;
 
    public ImageManagerImpl(Map<String, Image> images) {
        this.images = images;
        this.pathToImages = ImageManagerImpl.DEFAULT_LOCATION;
    }

    public ImageManagerImpl(Map<String, Image> images, String pathToImages) {
        this.images = images;
        this.pathToImages = pathToImages;
    }
    @Override
    public void init() {
        uploadImage("default.png", "default");
        uploadImage("game1.png","game1");
        uploadImage("game2.png","game2");
        uploadImage("game3.png","game3");
        uploadImage("shopping-cart.png","shoppingCart");
    }

    void uploadImage(String imageName, String id) {
        Image image = new Image(pathToImages + imageName);
        images.put(id, image);
    }
    @Override
    public Image getImage(String id) {
        if(images.containsKey(id)){
            return images.get(id);
        }
        return images.get("default");
    }
}
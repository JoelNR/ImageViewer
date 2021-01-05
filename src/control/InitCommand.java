package control;

import model.Image;
import view.ImageDisplay;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.ImageLoader;

public class InitCommand implements Command {
    private final ImageDisplay imageDisplay;
    private final List <Image> images;
    private final Map<String, Command> commands = new HashMap();
    private final ImageLoader imageLoader;

    public InitCommand(ImageLoader imageLoader, List <Image> images, ImageDisplay imageDisplay) {
        this.images = images;
        this.imageDisplay = imageDisplay;
        this.imageLoader = imageLoader; 
    }

    public Map <String, Command> getCommands() {
        return commands;
    }

    @Override
    public void execute() {
        images.clear();
        images.addAll(imageLoader.load());
        imageDisplay.display(images.get(0));
        commands.put("N", new NextImageCommand(images, imageDisplay));
        commands.put("P", new PrevImageCommand(images, imageDisplay));
        commands.put("Q", new ExitCommand());
    }
}

package control;

public interface Command {
    public void execute();
    
        public class Null implements Command {
            public static final Command Instance = new Null();
        @Override
        public void execute() {
        }
    }
}

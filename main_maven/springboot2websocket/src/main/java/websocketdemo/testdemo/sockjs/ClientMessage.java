package websocketdemo.testdemo.sockjs;

public class ClientMessage {

    private String name;

    public ClientMessage() {
    }

    public ClientMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

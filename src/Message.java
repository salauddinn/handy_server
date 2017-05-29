import java.io.Serializable;
final class Message<T extends Serializable> implements Serializable {
    private T payload;
    private String tag;
    public Message() {
            super();
    }
    public String getTag(){
        return this.tag;
    }
    public Message(T data,String name) {
            super();
            setPayload(data);
            this.tag=name;
    }
    public Message(T msg){
        super();
        setPayload(payload);
    }
    public T getPayload() {
            return payload;
    }

    public void setPayload(T aPayload) {
            payload = aPayload;
    }
}
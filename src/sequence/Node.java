package sequence;

public class Node {
    Node prev;
    Node next;
    int content;

    public Node(int content) {
        this.prev = null;
        this.next = null;
        this.content = content;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public int getContent() {
        return content;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

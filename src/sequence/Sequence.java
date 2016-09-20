//Travis Wulff
//CS41 Data Structures
//Project 1
//Due Date: 2016.09.20
//Total Points: 100
/*Description:
 *A sequence class (ADT) with program to test implementation
 */

package sequence;

public class Sequence {
    //The head is the first Node in the sequence. All other nodes can be discovered by following the "next" Node in the sequence
    private	Node head;
    //The current Node is where the pointer is currently located
    private Node current;
    //The playing Node is where a second pointer is located to simulate the idea of playing a playlist of songs
    private Node playing;

    //The default constructor which takes no arguments creates an empty sequence
    public Sequence() {
    }

    //This constructor creates a Sequence with a single Node which is the head. Both pointers will point to this Node at first.
    public Sequence(Node head) {
        this.head = head;
        this.current = head;
        this.playing = head;
    }

    public Node getCurrent() {
        return current;
    }

    //This will move the "current pointer" to the next non-null Node available
    public void advance() {
        if(current.getNext() != null) {
            current = current.getNext();
        }
    }

    public void moveBack() {
        if(current.getPrev() != null) {
            current = current.getPrev();
        }
    }

    //This will start the playlist. It points the "playing pointer" to the head of the Sequence
    public void start() {
        playing = head;
    }

    //This will move the "playing pointer" to the next non-null Node available
    public void playNext() {
        if(playing.getNext() != null) {
            playing = playing.getNext();
        }
    }

    public void playPrev() {
        if(playing.getPrev() != null) {
            playing = playing.getPrev();
        }
    }

    public Node getPlaying() {
        return this.playing;
    }

    /**
     * Adds a node before the current node
     * <p>
     * There are three (3) possibilities for inserting a node
     * 1) The current sequence is empty
     * 2) The sequence is non-empty and the current node is at the beginning of the sequence
     * 3) The sequence is non-empty and the current node is not at the beginning of the sequence
     */
    public void insert(int ID) {
        //if the "current pointer" is non-empty
        if(current != null) {
            //if the "current pointer" is not the head
            if(current != head) {
                Node tempPrev = current.prev;
                Node node = new Node(ID);
                node.setPrev(tempPrev);
                node.setNext(current);
                tempPrev.setNext(node);
                current.setPrev(node);
                current = node;
                //if the "current pointer" is the head
            } else {
                Node node = new Node(ID);
                node.next = current;
                current.prev = node;
                current = node;
                head = node;
            }
        } else {
            Node node = new Node(ID);
            head = node;
            current = node;
            playing = node;
        }
    }

    /**
     * Adds a node after the current node
     * <p>
     * There are three (3) possibilities for inserting a node
     * 1) The current sequence is empty
     * 2) The sequence is non-empty and the current node is at the end of the sequence
     * 3) The sequence is non-empty and the current node is not at the end of the sequence
     */
    public void attach(int ID) {
        //if the current sequence is non-empty
        if(current != null) {
            //if the "current pointer" is not the tail
            if(current.next != null) {
                Node tempNext = current.next;
                Node node = new Node(ID);
                current.setNext(node);
                node.setPrev(current);
                node.setNext(tempNext);
                tempNext.setPrev(node);
                //if the "current pointer" is the tail
            } else {
                Node node = new Node(ID);
                node.prev = current;
                current.setNext(node);
            }
            //else if the sequence is empty
        } else {
            Node node = new Node(ID);
            head = node;
            current = node;
            playing = node;
        }
    }

    /**
     * Adds a node to the end of the sequence
     * <p>
     * If the head is null (the sequence is empty) then add the new node as the head
     * else use a pointer to traverse the sequence until a node without a next value appears
     * this is the end of the current sequence so append will assign this node's next value to the inserted node
     * the inserted node's prev value becomes the afformentioned node
     */
    public void append(int ID) {
        if(head == null) {
            head = new Node(ID);
            current = head;
        } else {
            Node originalSpot = current;
            while(current.getNext() != null) {
                advance();
            }
            current.setNext(new Node(ID));
            current = originalSpot;
        }
    }

    /**
     * Deletes the Node that is pointed to by the current pointer
     * <p>
     * There are five (5) options as to what the current Node could be
     * 1) A null value
     * 2) A singularity
     * 3) The head node
     * 4) The tail node
     * 5) Some node in the middle of the sequence
     */
    public void deleteCurrent() {
        if (this.getSize() == 1) {
            current = null;
            head = null;
            playing = null;
            //else if the "current pointer" points to the head
        } else if(current.prev  == null) {
            current = current.next;
            current.prev = null;
            head = current;
            //else if the "current pointer" points to the tail
        } else if (current.next == null) {
            current = current.prev;
            current.next = null;
            //else the "current pointer" must point to some element in the middle
        } else {
            Node tempNext = current.next;
            Node tempPrev = current.prev;
            tempPrev.next = tempNext;
            tempNext.prev = tempPrev;
            current = current.prev;
        }
    }

    /**
     * Returns the number of elements contained within the sequence
     * <p>
     * Determines the size by using starting from the head node and incrementing the
     * int variable, size, until it reaches a null value, then returns the size
     *
     * @return the size of the sequence object
     */
    public int getSize() {
        int size = 0;
        Node pointer = head;
        while(pointer != null) {
            size++;
            pointer = pointer.next;
        }
        return size;
    }

    public Node getHead() {
        return head;
    }

    /**
     * Returns a boolean for whether the current item is a valid node
     * <p>
     * As long as current points to a non-null Node returns true, else returns false
     *
     * @return whether or not the value of current is non-null
     */
    public boolean isItem() {
        return (current != null);
    }

    /**
     * Combines two sequences and returns the combination as a new sequence
     * <p>
     * Takes the end node in sequence 1 and sets the next pointer to the head node of sequence 2
     * Takes the head node of sequence 1 and sets the tail pointer to the end node of sequence 1
     * Returns the new sequence
     * @param s1 sequence which will be at the front of the new sequence
     * @param s2 sequence which will be at the end of the new sequence
     * @return a sequence that is the combination of s2 appended to the end of s1
     */
    public static Sequence combineSequence(Sequence s1, Sequence s2) {
        //Copy contents of the first sequence
        Node pointer = s1.getHead();
        Node copyPointer = new Node(pointer.content);
        Sequence sequence = new Sequence(copyPointer);
        while(pointer.getNext() != null) {
            Node tempNode = new Node(pointer.getNext().content);
            copyPointer.setNext(tempNode);
            tempNode.setPrev(copyPointer);
            copyPointer = copyPointer.getNext();
            pointer = pointer.getNext();
        }
        //Link the head of s2 with the tail of s1
        pointer = s2.getHead();
        Node connectorNode = new Node(pointer.content);
        copyPointer.setNext(connectorNode);
        connectorNode.setPrev(copyPointer);
        copyPointer = connectorNode;
        //Copy contents of the second sequence
        while(pointer.getNext() != null) {
            Node tempNode = new Node(pointer.getNext().content);
            copyPointer.setNext(tempNode);
            tempNode.setPrev(copyPointer);
            copyPointer = copyPointer.getNext();
            pointer = pointer.getNext();
        }
        return sequence;
    }

    /**
     * Appends s1 to the end of this sequence
     * <p>
     * Takes the end node of this sequence and sets the next pointer to the head of s1
     * Takes the head node of s1 and sets the prev pointer to the end of this sequence
     * @param s1 sequence which will be appended to this sequence
     */
    public void appendSequence(Sequence s1) {
        Node pointer = this.getHead();
        while(pointer.getNext() != null) {
            pointer = pointer.getNext();
        }
        Node tempNode = s1.getHead();
        Node copyPointer = new Node(tempNode.content);
        pointer.setNext(copyPointer);
        copyPointer.setPrev(pointer);
        while(tempNode.getNext() != null) {
            Node next = new Node(tempNode.getNext().content);
            pointer.setNext(next);
            next.setPrev(pointer);
            pointer = pointer.getNext();
            tempNode = tempNode.getNext();
        }
    }

    public void printAllContents() {
        Node pointer = this.head;
        while(pointer != null) {
            if(pointer.getNext() != null) {
                System.out.print(pointer.content + ", ");
            } else {
                System.out.println(pointer.content);
            }
            pointer = pointer.getNext();
        }
    }
}

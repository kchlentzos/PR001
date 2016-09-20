//Travis Wulff, Andrew Sarmiento, Cathy Vy, Matthew McCauley, Kristin Chlentzos
//CS41 Data Structures
//Project 1
//Due Date: 2016.09.20
//Total Points: 100
/*Description:
 *A sequence class (ADT) with class to test implementation
 */

package sequence;

public class Sequence {

    /**
     * This is the first node in the ordered sequence. All references to other nodes will come from traversing the
     * sequence starting at the head
     */
    private	Node head;

    /**
     * This is the node which will be used for operations such as insertion and deletion
     */
    private Node current;

    /**
     * This is the node which will be used to play through a sequence
     */
    private Node playing;

    /**
     * Creates an empty sequence
     */
    public Sequence() {
    }

    /**
     * Since the sequence is being created with only one node; the head, playing, and current nodes are initialized as this
     * @param head used to initialize head, playing, and current nodes
     */
    public Sequence(Node head) {
        this.head = head;
        this.current = head;
        this.playing = head;
    }

    /**
     * Returns the current node
     * @return the current node
     */
    public Node getCurrent() {
        return current;
    }

    /**
     * Moves the current node to the next node
     */
    public void advance() {
        if(current.getNext() != null) {
            current = current.getNext();
        }
    }

    /**
     * Moves the current node to the previous node
     */
    public void moveBack() {
        if(current.getPrev() != null) {
            current = current.getPrev();
        }
    }

    /**
     * Moves the playing node to the head node
     */
    public void start() {
        playing = head;
    }

    /**
     * Moves the playing node to the next node in the sequence
     */
    public void playNext() {
        if(playing.getNext() != null) {
            playing = playing.getNext();
        }
    }

    /**
     * Moves the playing node to the previous node in the sequence
     */
    public void playPrev() {
        if(playing.getPrev() != null) {
            playing = playing.getPrev();
        }
    }

    /**
     * Returns the playing node
     * @return the playing node
     */
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
     * @param ID The integer that will be used as content for the inserted node
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
     * @param ID The integer which will be the content for the inserted node
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
     * the inserted node's prev value becomes the aforementioned node
     *
     * @param ID The integer which will be used as the content for the inserted node
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

    /**
     * Returns the head node of the sequence
     * @return the head node of the sequence
     */
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

    /**
     * Prints a comma separated list of all the IDs in the Sequence
     */
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

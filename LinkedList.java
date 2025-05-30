public class LinkedList< T > {

    // Node for Doubly linked-list
    public class Node <T> {
        T data;
        Node next;

        Node prev;
        int index; // Stores the index of the node in a list

        public Node(){
            next=null;
        }
        public Node(T d){
            data=d;
            next=null;
        }
    }

    public Node head;
    public int size;
    public int index=0;

    public LinkedList(){
        head=null;
    }

    // Inserting the nodes in a Doubly LinkedList
    public void add(T d){
        size++;
        Node node= new Node(d);
        Node temp = new Node();
        if(head==null) {
            head = node;
            head.index=index;
            index++;
        }
        else{
            temp=head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
            node.next=null;
            node.prev=temp;
            node.index=index;
            index++;
        }
    }

    // Returns true when the data to be found is present in the linkedList
    // Else returns false
    public Boolean find(T d){
        Node temp= new Node();
        temp=head;
        if(head.data==d) return true;
        while(temp!=null && temp.data!=d){
            temp= temp.next;
        }
        return temp != null;
    }

    // Finds the node of the given data
    public Node findNode(T d){
        Node temp= new Node();
        temp=head;
        if(head.data==d) return head;
        while(temp!=null && temp.data!=d){
            temp= temp.next;
        }
        return temp;
    }

    // Checks whether the index exists in the list
    public boolean findIndex(int index){
        Node temp=head;
        while(temp!=null && temp.index!=index){
            temp=temp.next;
        }
        return temp != null;
    }



    // Gets the Node stored at the index in the list
    public Node getNode(int index){
        if (!findIndex(index)){
            System.out.println("Index does not exist");
            return null;
        }
        if(head.index==index) return head;
        else{
            Node temp=head;
            while(temp!=null && temp.index!=index){
                temp=temp.next;
            }
            if(temp.index==index) return temp;
            else return null;
        }
    }

    // Clears the list
    public void clear(){
        size=0;
        head=null;
    }

    // Deletes the node of the given data in the list
    public void delete(T d){
        if(!find(d)){
            System.out.println("This node does not exist in the list");
            return;
        }
        else {
            int index=findNode(d).index;
            for(int i=(index+1); i<size; i++){
                this.getNode(i).index--;
            }
            size--;
            Node temp;
            temp = head;
            if (head == null) {
                return;
            }
            if (head.data == d) {
                head = temp.next;
                return;
            }

            while (temp.next.data != d) {
                temp = temp.next;
            }

            temp.next = temp.next.next;
        }
    }

    // Returns the data of the node stored at index i in the list
    public T get(int index){
        if(!findIndex(index)){
            System.out.println("This index does not exist");
            return null;
        }
        if(head.index==index) return (T) head.data;
        else{
            Node temp=head;
            while(temp!=null && temp.index!=index){
                temp=temp.next;
            }
            if(temp.index==index) return (T) temp.data;
            else return null;
        }
    }

    // Returns the size of the list
    public int size(){
        return this.size;
    }

    // Printing out the list
    public String toString(){
        Node temp;
        String s="";
        temp=head;
        while(temp!=null){
            s +=temp.data+", ";
            temp=temp.next;
        }
        return s;
    }

    // Inserting before a give node
    public void insertBefore(Node n, T d){
        Node temp= new Node();
        temp=head;
        if(head==null){
            add(d);
        }
        while(temp.next!=null && temp.next!=n){
            temp=temp.next;
        }
        if(temp.next==null){
            return;
        }
        Node localNode = new Node(d);
        localNode.next=temp.next;
        temp.next= localNode;


    }

}

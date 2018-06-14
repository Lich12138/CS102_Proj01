package Homework1;

public class DoublyLinkedList<E> {
	
	private static class Node<E> {
		//node is static so that it cannot matter with the variables in DDL
		//private and static makes Node class independent 

	    private E element;               // reference to the element stored at this node

	    private Node<E> prev;            // reference to the previous node in the list

	    private Node<E> next;            // reference to the subsequent node in the list

	    public Node(E e, Node<E> p, Node<E> n) {
	      element = e;
	      prev = p;
	      next = n;
	    }

	    public E getElement() { return element; }

	    public Node<E> getPrev() { return prev; }

	    public Node<E> getNext() { return next; }

	    public void setPrev(Node<E> p) { prev = p; }

	    public void setNext(Node<E> n) { next = n; }
	  } 
	//----------- end of nested Node class -----------

	  
	  private Node<E> header;                    // header sentinel
	  private Node<E> trailer;                   // trailer sentinel
	  private int size = 0;                      // number of elements in the list
	
	  public DoublyLinkedList() {
		//头放在第一个，尾放在第二个，内容插在中间，最后吧头和尾连上
	    header = new Node<>(null, null, null);      // create header
	    trailer = new Node<>(null, header, null);   // trailer is preceded by header
	    header.setNext(trailer);                    // header is followed by trailer
	  }

	  public int size() { return size; }

	  public boolean isEmpty() { return size == 0; }

	  public E first() {
	    if (isEmpty()) return null;
	    return header.getNext().getElement();   // first element is beyond header
	  }

	  public E last() {
	    if (isEmpty()) return null;
	    return trailer.getPrev().getElement();    // last element is before trailer
	  }

	  public void addFirst(E e) {
	    addBetween(e, header, header.getNext());    // place just after the header
	  }

	  public void add(E e) {
	    addBetween(e, trailer.getPrev(), trailer);  // place just before the trailer
	  }

	  public E removeFirst() {
	    if (isEmpty()) return null;                  // nothing to remove
	    return remove(header.getNext());             // first element is beyond header
	  }

	  public E removeLast() {
	    if (isEmpty()) return null;                  // nothing to remove
	    return remove(trailer.getPrev());            // last element is before trailer
	  }

	  private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
	    // create and link a new node
	    Node<E> newest = new Node<>(e, predecessor, successor);
	    predecessor.setNext(newest);
	    successor.setPrev(newest);
	    size++;
	  }
	  //Will this cause a problem if the predecessor is not actually the previous one of the successor?
	  
	  private E remove(Node<E> node) {
	    Node<E> predecessor = node.getPrev();
	    Node<E> successor = node.getNext();
	    predecessor.setNext(successor);
	    successor.setPrev(predecessor);
	    size--;
	    return node.getElement();
	  }

	  public String toString() {
	    StringBuilder sb = new StringBuilder("(");
	    Node<E> walk = header.getNext();
	    while (walk != trailer) {
	      sb.append(walk.getElement());
	      walk = walk.getNext();
	      if (walk != trailer)
	        sb.append(", ");
	    }
	    sb.append(")");
	    return sb.toString();
	  }
	  
	  public String toString(int i, int j) {
		    StringBuilder sb = new StringBuilder("");
		    Node<E> walk = loc(i).getNext();
		    while (walk != loc(j)) {
		      sb.append(walk.getElement());
		      walk = walk.getNext();
		      if (walk != trailer)
		        sb.append(" - ");
		    }
		    return sb.toString();
		  }
	  
	  
	  public String ReverseToString() {
			StringBuilder sb = new StringBuilder("(");
			Node<E> walk = trailer.getPrev();
			while(walk != header) {
				sb.append(walk.getElement());
				walk = walk.getPrev();
				if(walk != header) {
					sb.append(", ");
				}
			}
			sb.append(")");
			return sb.toString();
		}
	  
	  public E nextSpace(E currspot, int jump) {
		  Node<E> walk = header.getNext();
		  while(walk.getElement() != currspot) {
			  if(walk == trailer) {
				  return currspot;
			  }
			  walk = walk.getNext();
			  jump--;
			  if(walk == trailer) {
				  return walk.getPrev().getElement();
			  }
		  }
		  return walk.getElement();
	  }
	  
	  public void swap(Node<E> p, Node<E> n) {
		 addBetween(n.getElement(),p.getPrev(),p);
		 addBetween(p.getElement(),n.getPrev(),n);
		 remove(n);
		 remove(p);
	  }
	  
	  public Node<E> loc(int a){
		  Node<E> walk = header.getNext();
		  for(int i = 0; i < a; i++) {
			  walk = walk.getNext();
		  }
		  return walk;
	  }
	  
	  public void swap(int a, int b) {
		  swap(loc(a), loc(b));
	  }
	  
}



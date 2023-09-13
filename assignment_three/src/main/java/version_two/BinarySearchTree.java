/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package version_two;

/**
 *
 * @author max
 */
public class BinarySearchTree<E extends Comparable<E>> {
    //Fields
    protected Node<E> nil;
    protected Node<E> root;
    
    
    //Constructor
    public BinarySearchTree() {
        setNil(new Node<>(null));
    }

    
    //Methods
    public Node<E> search(E element) {
        if (this.root.compareTo(nil) == 0) {
            return null;
        } else {
            return searchNode(element, root);
        }
    }

    protected Node<E> searchNode(E element, Node<E> rootNode) {
        int compareValue = rootNode.element.compareTo(element);

        if (compareValue < 0) {
            if (rootNode.leftChild == nil) {
                return null;
            } else {
                return searchNode(element, rootNode.leftChild);
            }
        } else if (0 < compareValue) {
            if (rootNode.rightChild == nil) {
                return null;
            } else {
                return searchNode(element, rootNode.rightChild);
            }
        }
        return rootNode ; //Found
    }
    
    public void add(E element) {
        if(this.root.compareTo(nil) != 0) {
            Node<E> nodeToAdd = new Node<>(element);
            if(addNode(nodeToAdd, this.root)) {
                //Add success
            } else {
                //Remove success
            }
        }
    }
    
    protected boolean addNode(Node<E> nodeToAdd, Node<E> nodePtr) {
        
        
        return false;
    }
    
    
    

    protected void setNil(Node<E> node) {
            this.nil = node;
            nil.leftChild = nil;
            nil.rightChild = nil;

    } 
    
    
    /** HOOK METHODS */
    
    
    
    
    //Protected inner class
    protected class Node<E extends Comparable<E>> implements Comparable<Node<E>> {
        //Fields for Node
        protected E element;
        protected Node<E> leftChild;
        protected Node<E> rightChild;
        
        //Constructors for Node
        public Node(E element) {
            this.element = element;
            this.leftChild = (Node<E>)nil;
            this.rightChild = (Node<E>)nil;
        }
        
        
        //Methods for Node
        @Override
        public int compareTo(Node<E> t) {
            return this.element.compareTo(t.element);
        }

        @Override
        public String toString() {
            if(this.element == null) {
                return "nil";
            } else {
                return this.element.toString();
            }
        }
    }
    
    //Main Method
}

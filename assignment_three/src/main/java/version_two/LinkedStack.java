/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package version_two;

/**
 *
 * @author max
 */
public class LinkedStack<E> {
    //Fields
    private Node<E> top;
    private int count;
    
    
    //Constructor
    public LinkedStack() {
        this.count = 0;
        this.top = null;
    }
    
    
    //Methods
    public int size() {
        return this.count;
    }
    
    public E pop() {
        if(this.top != null) {
            Node<E> topNode = this.top;
            if(this.top.next == null) {
                this.count--;
                this.top = null;
                return topNode.element;
            } else {
                this.top = topNode.next;
                this.count--;
                return topNode.element;
            }
        } else {
            return null;
        }
    }
    
    public void push(E element) {
        Node<E> newNode = new Node<>(element);
        
        if(this.top == null) {
            this.top = newNode;
            this.count++;
        } else {
            newNode.next = this.top;
            this.top = newNode;
            this.count++;
        }
    }
    
    public E peek() {
        if(this.top != null) {
            return this.top.element;
        } else {
            return null;
        }
    }
    
    public void clear() {
        this.top = null;
        this.count = 0;
    }

    @Override
    public String toString() {
        String tempString = "-\n";
        
        if(this.top != null) {
            Node<E> nodePtr = this.top;
            while(nodePtr != null) {
                tempString += nodePtr.toString() + "\n";
                nodePtr = nodePtr.next;
            }
        }
        return tempString += "-";
    }
    
    
    
    
    
    //Private inner class
    private class Node<E> {
        //fields
        public E element;
        public Node<E> next;
        
        //constructor
        public Node(E element) {
            this.element = element;
        }
                
        @Override
        public String toString() {
            return this.element.toString();
        }
    }
    
    //Testing method
    //Main Method
//    public static void main(String[] args) {
//        LinkedStack<Integer> stack = new LinkedStack<>();
//        
//        stack.push(1);
//        System.out.println(stack);
//        stack.push(2);
//        stack.pop();
//        System.out.println(stack);
//    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment_three;

/**
 *
 * @author max
 */
public class BinarySearchTree<E extends Comparable> {
    //Fields
    protected Node<E> rootNode;
    protected Node<E> nil;
    protected TraversalStack<Node<E>> stack;
    
    //Constructors
    public BinarySearchTree() {
        this.setNil(new Node<>(null));
        this.rootNode = nil;
        this.stack = null;
    }

    //Methods
    protected void setNil(Node<E> node) {
        this.nil = node;
        nil.leftChild = nil;
        nil.rightChild = nil;
    }
    
    protected Node<E> search(E elment) {
        Node<E> tempNode = new Node<>(elment);
        Node<E> nodePtr = this.rootNode;

        while (nodePtr != nil) {

            int compare = tempNode.compareTo(nodePtr);

            if (compare == 0) {
                return nodePtr;
            } else {
                this.nodeVisited(nodePtr);
                if (compare < 0) {
                    nodePtr = nodePtr.leftChild;
                } else {
                    nodePtr = nodePtr.rightChild;

                }
            }
        }
        return nil;
    }
    
    protected void add(E element) {
        Node<E> newNode = new Node<>(element);
        this.initilizeTraversal();
        
        boolean success = this.addToTree(newNode);
        
        if(success) {
            this.buildTree(); 
        }
        
        this.finishedTraversal();
    }
    
    protected boolean addToTree(Node<E> addNode) {
        Node<E> traversalParent = nil;
        Node<E> nodePtr = this.rootNode;
        
        while (nodePtr != nil) {
            traversalParent = nodePtr;
            this.nodeVisited(traversalParent);
            int compare = addNode.compareTo(nodePtr);
            
            if(compare == 0) {
                return false; //Exists
            } else if(compare < 0) {
                nodePtr = nodePtr.leftChild;
            } else {
                nodePtr = nodePtr.rightChild;
            }
        }
        
        if(traversalParent == nil) {
            this.rootNode = addNode;
        } else {
            if(addNode.compareTo(traversalParent) < 0) {
                traversalParent.leftChild = addNode;
            } else {
                traversalParent.rightChild = addNode;
            }
        }
        this.addBalance(addNode);
        return true;
    }
    
    protected void addBalance(Node<E> node) {
        
    }
    
    protected Node<E> remove(E element) {
        this.initilizeTraversal();
        Node<E> nodeToRemove = this.search(element);
        
        boolean success = this.removeNode(nodeToRemove);
        
        this.finishedTraversal();
        return null;
    }
    
    protected boolean removeNode(Node<E> nodeToRemove) {
        Node<E> yParent = this.stack.peek();
        Node<E> zParent = this.stack.peek();                
        Node<E> z = nodeToRemove; //Node to remove
        Node<E> y = z; // Replacement
        Node<E> x = (Node<E>) nil; // Replacement
        
        if(z == nil) {
            return false;
        }
        
        if(z.leftChild != nil && z.rightChild != nil) {
            //Getting the predecessor
            y = this.predecessor(z);
            yParent = this.stack.peek();
        }
        
        if(z.leftChild != nil) {
            x = y.leftChild;
        } else {
            x = y.rightChild;
        }
        
        
        
        
        if(yParent == nil) {
            this.rootNode = x;
        } else {
            if (y == yParent.leftChild) {
                yParent.leftChild = x;
            } else {
                yParent.rightChild = x;
            }
        }
        
        if(y != z) {
            y.leftChild = z.leftChild;
            y.rightChild = z.rightChild;
            yParent = zParent;
            
            if(z == this.rootNode) {
                this.rootNode = y;
            } else if(z == zParent.leftChild) {
                zParent.leftChild = y;
            } else {
                zParent.rightChild = y;
            }
            
        } 

        
        //Build must be called before         
        this.removeFix(x, nodeToRemove); //Will have to have the check for colour within the removeFix implementation
        this.buildTree(x, nodeToRemove);
        
        return false;
    }
    
    protected void buildTree() {
        
    }
    protected void buildTree(Node<E> replacement, Node<E> removed) {
        
    }
    
    protected void removeFix(Node<E> checkNode, Node<E> parent) {
        
    }
    
    public Node<E> predecessor(Node<E> node) {
        Node<E> x = node;
        
        
        if(x.leftChild != nil) {
            this.nodeVisited(x);
            return treeMaximum(x.leftChild);
        }
        
        Node<E> y = this.stack.peek();
        while(y != nil && x == y.leftChild) {
            x = y;
            y = this.stack.pop();
        }
        
        return y;
    }
    
    public Node<E> treeMaximum(Node<E> rootNode) {
        while(rootNode.rightChild != nil) {
            this.nodeVisited(rootNode);
            rootNode = rootNode.rightChild;
        }
        
        return rootNode;
    }
    
    
    /** HOOK METHODS. */
    protected Node<E> duplicateNode(Node<E> node) {
        Node<E> temp = new Node<>(node.element);
        temp.leftChild = node.leftChild;
        temp.rightChild = node.rightChild;
        return temp;
    }
    
    protected void initilizeTraversal() {
        this.stack = new TraversalStack<>();
        this.stack.push(nil); //Nill is added to be the parent of the root node
    }
    
    
    protected void nodeVisited(Node<E> node) {
        this.stack.push(node);
    }
    
    /**
     * Could create new version of the tree by popping of stack and balancing on way up.
     */
    protected void finishedTraversal() {
        this.stack = null;
    }
    
    @Override
    public String toString() {
        if (this.rootNode == this.nil) {
            return "()";
        }
        String tempString = nodeToString(this.rootNode);

        return tempString;
    }

    protected String nodeToString(Node<E> currentNode) {
        //Inorder traversal of tree using recursion
        String tempString = "";

        //Left 
        if (currentNode.leftChild != this.nil) {
            String retString = nodeToString(currentNode.leftChild);
            tempString += "(" + retString;
        } else {
            tempString += "(";
        }
        //Me
        tempString += currentNode.toString();

        //Right
        if (currentNode.rightChild != this.nil) {
            String retString = nodeToString(currentNode.rightChild);
            tempString += retString + ")";
        } else {
            tempString += ")";
        }
        return tempString;
    }
    
    //Protected inner class
    protected class Node<E extends Comparable> implements Comparable<Node<E>> {
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
    
    //Protected inner class
    protected class TraversalStack<F extends Node<E>> {
        //Fields for TraversalStack
        protected StackNode<F> topNode;
        protected int numOfNodes;
        
        //Constructors for TraversalStack
        public TraversalStack() {
            this.numOfNodes = 0;
            this.topNode = null;
        }
        
        
        //Methods for TraversalStack
        public int size() {
            return this.numOfNodes;
        }
        
        public F peek() {
            return this.topNode.node;
        }
        
        public void push(F node) {
            StackNode<F> tempNode = new StackNode<>(node);
            
            if(this.numOfNodes == 0) {
                this.topNode = tempNode;
            } else {
                tempNode.nextNode = this.topNode;
                this.topNode = tempNode;
            }
            this.numOfNodes++;
        }
        
        public F pop() {
            StackNode<F> tempNode = this.topNode;
            this.topNode = this.topNode.nextNode;
            
            this.numOfNodes--;
            return tempNode.node;
        }
        
        public F getSecond() {
            return this.topNode.nextNode.node;
        }
        
        public F getNum(int num) {
            StackNode<F> stackPtr = this.topNode;
            for(int i = 0; i < num - 1; i++) {
                stackPtr = stackPtr.nextNode;
            }
            
            return stackPtr.node;
        }

        @Override
        public String toString() {
            String tempString = "TOP --> ";
            
            StackNode<F> nodePtr = this.topNode;
            for(int i = 0; i < this.size(); i++) {
                tempString += ", " + nodePtr.node.toString();
                nodePtr = nodePtr.nextNode;
            }
            
            return tempString + " ]";
        }
        
        //Protected inner class for TraversalStack
        protected class StackNode<F> {
            //Fields for StackNode
            protected F node;
            protected StackNode<F> nextNode;
            
            //Constructor for StackNode
            public StackNode(F node) {
                this.node = node;
                this.nextNode = null;
            }
            
            //Methods for StackNode
        }
        
    }
    
    //Main Method
    public static void main(String[] args) {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        
        System.out.println("ADD TEST");
        tree.add("Ant");
        tree.add("Bee");
        tree.add("Cat");
        tree.add("Dog");
        System.out.println(tree.toString());
    
        System.out.println("REMOVE TEST: Ant");
        tree.remove("Ant");
        System.out.println(tree.toString());
    
        System.out.println("ADD TEST: Bat");
        tree.add("Bat");
        System.out.println(tree.toString());
    }
    
}

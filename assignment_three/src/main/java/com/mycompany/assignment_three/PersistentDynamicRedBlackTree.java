/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment_three;

/**
 *
 * @author max
 */
public class PersistentDynamicRedBlackTree<E extends Comparable> extends PersistentDynamicTree<E>{
    //Fields
    protected TraversalStack<RedBlackNode<E>> balanceStack;
    
    //Constructors
    public PersistentDynamicRedBlackTree() {
        super();
        this.blackNil();
        this.rootNode = nil;
    }
    
    
    //Methods

    @Override
    protected Node<E> remove(E element) {
        this.initilizeTraversal();
        RedBlackNode<E> nodeToRemove = (RedBlackNode<E>)this.search(element);
        
        boolean success = this.removeNode(nodeToRemove);
        
        this.finishedTraversal();
        return null;
    }

    @Override
    protected void add(E element) {
        RedBlackNode<E> newNode = new RedBlackNode<>(element);
        this.initilizeTraversal();
        
        boolean success = this.addToTree(newNode);

        if(success) {
            this.buildTree(); 
        }
        
        this.finishedTraversal();
    }

    @Override
    protected void addBalance(Node<E> node) {
        RedBlackNode<E> y = null;
        //TOP of stack is node parent
        while(this.balanceStack.peek().colour == NodeColour.RED) {
            if(this.balanceStack.peek() == this.balanceStack.getSecond().leftChild) {
                y = (RedBlackNode<E>)this.balanceStack.getSecond().rightChild;
                if(y.colour == NodeColour.RED) {
                    this.balanceStack.peek().colour = NodeColour.BLACK;
                    y = this.duplicateAndInsert(y, (RedBlackNode<E>)this.balanceStack.getSecond());
                    y.colour = NodeColour.BLACK;
                    this.balanceStack.getSecond().colour = NodeColour.RED;
                    this.balanceStack.pop();//Removing parent
                    node = this.balanceStack.pop();//Saving gParent into node
                } else {
                    if(node == this.balanceStack.peek().rightChild) {
                        node = this.balanceStack.pop();
                        this.leftRotate((RedBlackNode<E>)node);
                    }
                    
                    this.balanceStack.peek().colour = NodeColour.BLACK;
                    this.balanceStack.getSecond().colour = NodeColour.RED;
                    RedBlackNode<E> parent = this.balanceStack.pop();
                    node = this.balanceStack.pop();
                    this.rightRotate((RedBlackNode<E>)node);
                }
            } else {
                y = (RedBlackNode<E>) this.balanceStack.getSecond().leftChild;
                if (y.colour == NodeColour.RED) {
                    this.balanceStack.peek().colour = NodeColour.BLACK;
                    y = this.duplicateAndInsert(y, (RedBlackNode<E>)this.balanceStack.getSecond());
                    y.colour = NodeColour.BLACK;
                    this.balanceStack.getSecond().colour = NodeColour.RED;
                    this.balanceStack.pop();//Removing parent
                    node = this.balanceStack.pop();//Saving gParent into node
                } else {
                    if (node == this.balanceStack.peek().leftChild) {
                        node = this.balanceStack.pop();
                        this.rightRotate((RedBlackNode<E>) node);
                    }

                    this.balanceStack.peek().colour = NodeColour.BLACK;//BEE
                    this.balanceStack.getSecond().colour = NodeColour.RED;//
                    RedBlackNode<E> parent = this.balanceStack.pop();
                    node = this.balanceStack.pop();
                    this.leftRotate((RedBlackNode<E>) node);
                }
            }
        }
        ((RedBlackNode<E>)this.rootNode).colour = NodeColour.BLACK;
    }

    @Override
    protected void removeFix(Node<E> checkNode, Node<E> parent) {
        System.out.println(this.balanceStack.toString());
        if(((RedBlackNode<E>)parent).colour == NodeColour.BLACK) {
            //ASSUMING THAT TOP OF STACK IS PARENT OF checkNode
            while(checkNode != this.rootNode && ((RedBlackNode<E>)checkNode).colour == NodeColour.BLACK) {
                if(this.balanceStack.peek().leftChild == checkNode) {
                    RedBlackNode<E> sibling =  (RedBlackNode<E>)balanceStack.peek().rightChild;
                    sibling = this.duplicateAndInsert(sibling, (RedBlackNode<E>)this.balanceStack.peek());
                    //sibling = this.duplicateAndInsert(sibling, (RedBlackNode<E>)this.balanceStack.peek());
                    if(sibling.colour == NodeColour.RED) {
                        sibling.colour = NodeColour.BLACK;
                        this.balanceStack.peek().colour = NodeColour.RED;
                        RedBlackNode<E> parentNode = this.balanceStack.pop();
                        this.leftRotate(parentNode);
                        this.balanceStack.push(parentNode);
                        sibling = (RedBlackNode<E>)this.balanceStack.peek().rightChild;
                        sibling = this.duplicateAndInsert(sibling, (RedBlackNode<E>)this.balanceStack.peek());
                    }
                    
                    if((((RedBlackNode)sibling.leftChild).colour == NodeColour.BLACK)
                            && (((RedBlackNode)sibling.rightChild).colour == NodeColour.BLACK)) {
                        sibling.colour = NodeColour.RED;
                        checkNode = this.balanceStack.pop();
                    } else {
                        if(((RedBlackNode<E>)sibling.rightChild).colour == NodeColour.BLACK) {
                            ((RedBlackNode<E>)sibling.leftChild).colour = NodeColour.BLACK;
                            sibling.colour = NodeColour.RED;
                            this.rightRotate(sibling);
                            sibling = this.balanceStack.peek();
                            sibling = this.duplicateAndInsert(sibling, (RedBlackNode<E>)this.balanceStack.getSecond());
                        }
                        
                        sibling.colour = this.balanceStack.peek().colour;
                        this.balanceStack.peek().colour = NodeColour.BLACK;
                        ((RedBlackNode<E>)sibling.rightChild).colour = NodeColour.BLACK;
                        this.leftRotate(this.balanceStack.pop());
                        checkNode = (RedBlackNode<E>)this.rootNode;
                    }
                } else {
                    RedBlackNode<E> sibling =  (RedBlackNode<E>)balanceStack.peek().leftChild;
                    sibling = this.duplicateAndInsert(sibling, (RedBlackNode<E>)this.balanceStack.peek());
                    //sibling = this.duplicateAndInsert(sibling, (RedBlackNode<E>)this.balanceStack.peek());
                    if(sibling.colour == NodeColour.RED) {
                        sibling.colour = NodeColour.BLACK;
                        this.balanceStack.peek().colour = NodeColour.RED;
                        RedBlackNode<E> parentNode = this.balanceStack.pop();
                        this.rightRotate(parentNode);
                        this.balanceStack.push(parentNode);
                        sibling = (RedBlackNode<E>)this.balanceStack.peek().leftChild;
                        sibling = this.duplicateAndInsert(sibling, (RedBlackNode<E>)this.balanceStack.peek());
                    }
                    
                    if((((RedBlackNode)sibling.leftChild).colour == NodeColour.BLACK)
                            && (((RedBlackNode)sibling.rightChild).colour == NodeColour.BLACK)) {
                        sibling.colour = NodeColour.RED;
                        checkNode = this.balanceStack.pop();
                    } else {
                        if(((RedBlackNode<E>)sibling.leftChild).colour == NodeColour.BLACK) {
                            ((RedBlackNode<E>)sibling.rightChild).colour = NodeColour.BLACK;
                            sibling.colour = NodeColour.RED;
                            this.leftRotate(sibling);
                            sibling = this.balanceStack.peek();
                            sibling = this.duplicateAndInsert(sibling, (RedBlackNode<E>)this.balanceStack.getSecond());

                        }
                        
                        sibling.colour = this.balanceStack.peek().colour;
                        this.balanceStack.peek().colour = NodeColour.BLACK;
                        ((RedBlackNode<E>)sibling.leftChild).colour = NodeColour.BLACK;
                        this.rightRotate(this.balanceStack.pop());
                        checkNode = (RedBlackNode<E>)this.rootNode;
                    }
                }   
            }
        }
        ((RedBlackNode<E>)checkNode).colour = NodeColour.BLACK;
    }
    
    

    @Override
    protected void nodeVisited(Node<E> node) {
        super.nodeVisited(node); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        if(node.getClass() == RedBlackNode.class) {
            this.balanceStack.push((RedBlackNode<E>)node);
        }
    }

    @Override
    protected Node<E> duplicateNode(Node<E> node) {
        if(node.getClass() == RedBlackNode.class) {
            RedBlackNode<E> tempNode = new RedBlackNode<>(node.element);
            tempNode.leftChild = node.leftChild;
            tempNode.rightChild = node.rightChild;
            tempNode.colour = ((RedBlackNode<E>)node).colour;
            return tempNode;
        } 
        return super.duplicateNode(node);
    }
    
    protected RedBlackNode<E> duplicateAndInsert(RedBlackNode<E> node, RedBlackNode<E> parentNode) {
        RedBlackNode<E> duplicateNode = (RedBlackNode<E>)this.duplicateNode(node);
        
        if(parentNode == nil) {
            this.rootNode = duplicateNode;
        } else {
            if(parentNode.leftChild == node) {
                parentNode.leftChild = duplicateNode;
            } else {
                parentNode.rightChild = duplicateNode;
            }
        }
        
        return duplicateNode;
    }

    @Override
    protected void finishedTraversal() {
        super.finishedTraversal(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.balanceStack = null;
    }

    @Override
    protected void initilizeTraversal() {
        super.initilizeTraversal(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.balanceStack = new TraversalStack<>();
        this.balanceStack.push((RedBlackNode<E>)nil);
    }

    protected void blackNil() {
        this.nil = new RedBlackNode<>(null);
        this.nil.leftChild = nil;
        this.nil.rightChild = nil;
        ((RedBlackNode<E>)this.nil).colour = NodeColour.BLACK;
    }
    
    protected void leftRotate(RedBlackNode<E> node) {
        //We assume that the node on the top of the stack is the parent of node
        
        RedBlackNode<E> y = (RedBlackNode<E>)node.rightChild;
        
        node.rightChild = y.leftChild;
        
        if(this.balanceStack.peek() == nil) {
            this.rootNode = y;
        } else {
            if(node  == this.balanceStack.peek().leftChild) {
                this.balanceStack.peek().leftChild = y;
            } else {
                this.balanceStack.peek().rightChild = y;
            }
        }
        
        y.leftChild = node;
        this.balanceStack.push(y);//Pushing the y node onto Stack
        //So now y is the parent of node
    }
    
    protected void rightRotate(RedBlackNode<E> node) {
         //We assume that the node on the top of the stack is the parent of node
        RedBlackNode<E> y = (RedBlackNode<E>)node.leftChild;
        
        node.leftChild = y.rightChild;
        
        if(this.balanceStack.peek() == nil) {
            this.rootNode = y;
        } else {
            if(node  == this.balanceStack.peek().leftChild) {
                this.balanceStack.peek().leftChild = y;
            } else {
                this.balanceStack.peek().rightChild = y;
            }
        }
        
        y.leftChild = node;        
        this.balanceStack.push(y);//Pushing the y node onto Stack
        //So now y is the parent of node
    }
    
            
    //Protected inner class
    protected class RedBlackNode<E extends Comparable> extends Node<E> {
        //Fields for RedBlackNode
        protected NodeColour colour;
        
        //Constructors for RedBlackNode
        public RedBlackNode(E element) {
            super(element);
            this.colour = NodeColour.RED;
        }
        
        //Methods for RedBlackNode
        @Override
        public String toString() {
            if(this.element == null) {
                return "nil, " + this.colour;
            } else {
                return this.element.toString() + ", " + this.colour.toString();
            }
        }
    }

    private enum NodeColour {
        RED,
        BLACK;
    }
    
    //Main Method
    public static void main(String[] args) {
        BinarySearchTree<String> tree = new PersistentDynamicRedBlackTree<>();
        
        tree.add("Ant");
        tree.add("Bee");
        tree.add("Cat");
        tree.add("Cow");
        tree.add("Dog");
        tree.remove("Dog");

        System.out.println("-----");
        System.out.println(tree.toString());
    }
}

package source;

import java.util.LinkedList;
import java.util.Queue;

public class Arbre {
	
	Node root;



	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	private Node addRecursive(Node current, Sim sim) {
		int Id=sim.getId();
	    if (current == null) {
	        return new Node(sim);
	    }

	    if (Id < current.Id) {
	        current.left = addRecursive(current.left, sim);
	    } else if (Id > current.Id) {
	        current.right = addRecursive(current.right, sim);
	    } else {
	        // value already exists
	        return current;
	    }

	    return current;
	}

	public void add(Sim sim) {
	    root = addRecursive(root, sim);
	}  
	
	private Node findSmallestNode(Node root) {
	    return root.left == null ? root : findSmallestNode(root.left);
	}
	
	
	private Node deleteRecursive(Node current, Sim sim) {
		int Id=sim.getId();
	    if (current == null) {
	        return null;
	    }

	    if (Id == current.Id) 
	    {
	    	if (current.left == null && current.right == null) return null;
	    	
	    	else if (current.right == null) return current.left;

	    	else if (current.left == null) return current.right;
	    	
	    	else
	    	{
	    		Node smallestNode = findSmallestNode(current.right);
	    		current.Id = smallestNode.Id;
	    		current.right = deleteRecursive(current.right, smallestNode.sim);
	    		return current;
	    	}
	    	
	        // Node to delete found
	        // ... code to delete the node will go here
	    } 
	    if (Id < current.Id) {
	        current.left = deleteRecursive(current.left, sim);
	        return current;
	    }
	    current.right = deleteRecursive(current.right, sim);
	    return current;
	}
	
	public void remove(Sim sim) {
	    root = deleteRecursive(root, sim);
	    if (root == null) System.out.println("null");
	}

	private boolean containsNodeRecursive(Node current, int Id) {
	    if (current == null) {
	        return false;
	    } 
	    if (Id == current.Id) {
	        return true;
	    } 
	    return Id < current.Id
	      ? containsNodeRecursive(current.left, Id)
	      : containsNodeRecursive(current.right, Id);
	}
	public boolean containsNode(int value) {
	    return containsNodeRecursive(root, value);
	}

	public Sim getRecursive(Node current,int Id) {
		if (current == null) {
	        return null;
	    } 
	    if (Id == current.Id) {
	        return current.sim;
	    } 
	    return Id < current.Id
	      ? getRecursive(current.left, Id)
	      : getRecursive(current.right, Id);
	}
	
	public Sim get(int Id) {
		return getRecursive(root,Id);
	}
}

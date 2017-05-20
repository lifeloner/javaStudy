package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuyang on 2017/5/20.
 */
public class AvlTree<T extends Comparable<T>> {

    private AvlTreeNode root;

    public AvlTree() {
    }

    private static class AvlTreeNode<T extends Comparable<T>> {
        private T key;
        private int height;
        private AvlTreeNode<T> left;
        private AvlTreeNode<T> right;

        public AvlTreeNode(T key) {
            this.key = key;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        public AvlTreeNode(T key, AvlTreeNode<T> left, AvlTreeNode <T>right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 1;
        }
    }

    public AvlTreeNode leftLeftRotation(AvlTreeNode <T>node) {
        AvlTreeNode <T>left = node.left;
        node.left = left.right;
        left.right = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        left.height = Math.max(node.height, height(left.left)) + 1;
        return left;
    }

    public AvlTreeNode rightRightRotation(AvlTreeNode<T> node) {
        AvlTreeNode <T>right = node.right;
        node.right = right.left;
        right.left = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        right.height = Math.max(node.height, height(right.right)) + 1;
        return right;
    }

    public AvlTreeNode leftRightRotation(AvlTreeNode <T>node){
        node.left=rightRightRotation(node.left);
        return leftLeftRotation(node);
    }

    public AvlTreeNode rightLeftRotation(AvlTreeNode <T>node){
        node.right=leftLeftRotation(node.right);
        return rightRightRotation(node);
    }

    public void insert(T key){
        root=insert(key,root);
    }

    public AvlTreeNode insert(T key, AvlTreeNode <T>node){
        if(node==null){
            node=new AvlTreeNode(key);
            return node;
        }
        if(node.key.compareTo(key)==0){
            throw new RuntimeException("节点值重复了");
        }else if(node.key.compareTo(key)>0){
            node.left=insert(key,node.left);
            if(height(node.left)-height(node.right)>1){
                if(node.left.key.compareTo(key)>0){
                    node= leftLeftRotation(node);
                }else {
                    node= leftRightRotation(node);
                }
            }
        }else {
            node.right=insert(key,node.right);
            if(height(node.right)-height(node.left)>1){
                if(node.right.key.compareTo(key)<0){
                    node=rightRightRotation(node);
                }else {
                    node=rightLeftRotation(node);
                }
            }
        }
        node.height=Math.max(height(node.left),height(node.right))+1;
        return node;
    }

    public int height(AvlTreeNode node) {
        return node == null ? 0 : node.height;
    }

    public int getTreeHeight() {
        return height(root);
    }

    public void remove(T key){
        root=remove(key,root);
    }

    public AvlTreeNode remove(T key,AvlTreeNode <T>node){
        if(node==null||key==null){
            return null;
        }
        if(node.key.compareTo(key)>0){
            node.left=remove(key,node.left);
            if(height(node.right)-height(node.left)>1){
                if(height(node.right.left)>height(node.right.right)){
                    node=rightLeftRotation(node);
                }else {
                    node=rightRightRotation(node);
                }
            }
        }else if(node.key.compareTo(key)<0){
            node.right=remove(key,node.right);
            if(height(node.left)-height(node.right)>1){
                if(height(node.left.left)>=height(node.left.right)){
                    node=leftLeftRotation(node);
                }else {
                    node=leftRightRotation(node);
                }
            }
        }else{
            if(node.left==null){
                AvlTreeNode tmp=node;
                node=node.right;
                tmp.right=null;
            }
            else if(node.right==null){
                AvlTreeNode tmp=node;
                node=node.left;
                tmp.left=null;
            }
            else {
                if(height(node.left)>height(node.right)){
                    AvlTreeNode<T> tmp=node.left;
                    while(tmp.right!=null){
                        tmp=tmp.right;
                    }
                    node.key=tmp.key;
                    node.left=remove(tmp.key,node.left);
                }else{
                    AvlTreeNode <T>tmp=node.right;
                    while(tmp.left!=null){
                        tmp=tmp.left;
                    }
                    node.key=tmp.key;
                    node.right=remove(tmp.key,node.right);
                }
            }
        }
        if(node!=null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
        return node;
    }

    public void preOrder(){
        System.out.println("preOrder begin");
        preOrder(root);
        System.out.println("\npreOrder finish");
    }
    public void preOrder(AvlTreeNode node){
        if(node==null){
            return;
        }
        System.out.print(node.key+" ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder(){
        System.out.println("inOrder begin");
        inOrder(root);
        System.out.println("\ninOrder finish");
    }

    public void inOrder(AvlTreeNode node){
        if(node==null){
            return;
        }
        inOrder(node.left);
        System.out.print(node.key+" ");
        inOrder(node.right);
    }

    public void postOrder(){
        System.out.println("postOrder begin");
        postOrder(root);
        System.out.println("\npostOrder finish");
    }

    public void postOrder(AvlTreeNode node){
        if(node==null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.key+" ");
    }

    public AvlTreeNode serach(T key){
        AvlTreeNode node=root;
        while(node!=null){
            if(node.key.compareTo(key)==0){
                return node;
            }else if(node.key.compareTo(key)>0){
                node=node.left;
            }else {
                node=node.right;
            }
        }
        return null;
    }

    public void delete(){
        delete(root);
        root=null;
    }

    public void delete(AvlTreeNode node){
        if(node==null){
            return;
        }
        delete(node.left);
        delete(node.right);
        node.left=null;
        node.right=null;
    }

    public static void main(String[] args) {
        AvlTree tree=new AvlTree();
        int []array={3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};
        for(int i=0;i<array.length;i++){
            System.out.println(i);
            tree.insert(array[i]);
        }
        tree.preOrder();
        tree.inOrder();
        tree.postOrder();
        System.out.println("height=" +tree.getTreeHeight());
        tree.remove(7);
        tree.remove(10);
        tree.preOrder();
        tree.inOrder();
        tree.postOrder();
        System.out.println("height=" +tree.getTreeHeight());
        tree.delete();
        System.out.println("height=" +tree.getTreeHeight());
    }
}

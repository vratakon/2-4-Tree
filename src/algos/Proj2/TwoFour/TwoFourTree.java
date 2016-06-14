package algos.Proj2.TwoFour;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;


public class TwoFourTree<T> implements Set<T>, Comparator<T>
{
protected TwoFourNode<T> rootNode=new TwoFourNode<T>();
protected Comparator<? super T> comparator;
public int splitCount=0, fusionCount=0;

public TwoFourTree(){
comparator=this;
}

public TwoFourTree(Comparator<? super T> comparator){
this.comparator=comparator;
}

public int insert(T k){
add(k);
return (splitCount);
}

public int delete(T k){
remove(k);
return (fusionCount);
}
int transferCount=0;

public int search(T k){ 
contains(k);
return (transferCount);	    
}

public boolean add(T e) 
{
if (contains(e)==true)
return false;
TwoFourNode<T> currentNode=rootNode;
T newElement=e;
while(true) 
{
if(currentNode.isNodeFull())  
{
splitNode(currentNode);
currentNode= getChildSibling(currentNode.getParent(), e);
continue;
}
if(currentNode.isLeafNode())
break;
currentNode=getChildSibling(currentNode, e);
}
currentNode.addElement(newElement);
return true;
}

protected TwoFourNode<T> getChildSibling(TwoFourNode<T> parent, T e) 
{
int nElements=parent.noOfElements();
for(int index=0; index<nElements; index++) 
{
if(comparator.compare(e, parent.getElementAt(index))<0) 
{
return parent.getChildAt(index);
}
}
return parent.getChildAt(nElements);
}

public int size() 
{
return rootNode.size();
}

protected TwoFourNode<T> getNodeWithSmallestElement (TwoFourNode<T> parent) 
{
TwoFourNode<T> currentNode=parent;
while (true)
{
if(currentNode.isLeafNode())
break;
currentNode=currentNode.firstChild();
}
return currentNode;
}

protected TwoFourNode<T> getNodeWithLargestElement(TwoFourNode<T> parent) 
{
TwoFourNode<T> currentNode=parent;
while (true) 
{
if (currentNode.isLeafNode())
break;
currentNode=currentNode.lastChild();
}
return currentNode;
}

protected T findLargestElement() 
{
return getNodeWithLargestElement(rootNode).getLargestElement();
}

protected T findSmallestElement()
{
return getNodeWithSmallestElement(rootNode).getSmallestElement();
}

protected T findGT(T e) 
{
if (e==null)
throw new RuntimeException("Null value passed");
T max=findLargestElement();
if (comparator.compare(max, e) <= 0)
return null;
TwoFourNode<T> currentNode=rootNode;
while (true) 
{
int index=currentNode.findElementGT(e,max);
if (index > -1) 
{
max=currentNode.getElementAt(index);
}
if (currentNode.isLeafNode()) 
{
return max;
}
currentNode=getChildSibling(currentNode,e);
}}

public T findGE(T e) 
{
if (e==null)
return findSmallestElement();
T max=findLargestElement();
if (comparator.compare(max, e) <= -1)
return null;
TwoFourNode<T> currentNode=rootNode;
while (true) 
{
int index=currentNode.findElementGE(e,max);
if (index > -1)
max=currentNode.getElementAt(index);
if (comparator.compare(max,e)==0)
return max;
if (currentNode.isLeafNode())
return max;
currentNode=getChildSibling(currentNode,e);
}}

protected TwoFourNode<T> getPrevChildSibling(TwoFourNode<T> parent, T e) 
{
int nElements=parent.noOfElements();
for(int index=0; index<nElements; index++) {
if( comparator.compare(e, parent.getElementAt(index))<= 0)
return parent.getChildAt(index);
}
return parent.getChildAt(nElements);
}

public T findLT(T e) 
{
if (e==null)
return findLargestElement();
TwoFourNode<T> currentNode=rootNode;
T min= findSmallestElement();
if (comparator.compare(e ,min) <= -1)
return null;
while (true) 
{
int index=currentNode.findElementLT(e,min);
if (index > -1)
min=currentNode.getElementAt(index);
if (currentNode.isLeafNode())
return min;
currentNode=getPrevChildSibling(currentNode,e);
}}

protected boolean remove(TwoFourNode<T> node, T e) 
{
if (node.isLeafNode())
{
int index=node.findElement(e);
if (index > -1) 
{
node.deleteElementAt(index);
return true;
} 
else {
return false;
}}
else{
int index=node.findElement(e);
if (index>-1) 
{
TwoFourNode<T> predChild=node.getChildAt(index);
if (predChild.noOfElements()>1) 
{
T predecessor=getNodeWithLargestElement(predChild).getLargestElement();
node.deleteElementAt(index);
node.addElement(predecessor);
return remove(predChild,predecessor);
}
else 
{
TwoFourNode<T> nextChild=node.getChildAt(index + 1);
if (nextChild.noOfElements() > 1) 
{
T successor=getNodeWithSmallestElement(nextChild).getSmallestElement();
node.deleteElementAt(index);
node.addElement(successor);
return remove(nextChild,successor);
}
else 
{
fuse(node,e,index);
return remove(predChild,e);
}}}
else 
{
TwoFourNode<T> c=getChildSibling(node, e);
int indexC=getNextSiblingIndex(node, e);
TwoFourNode<T> leftSibling=null; 
TwoFourNode<T> rightSibling=null;
if (indexC-1 >= 0) leftSibling=node.getChildAt(indexC-1);
if (indexC+1 <= 3) rightSibling=node.getChildAt(indexC+1);
if (c.noOfElements()==1) 
{
if (leftSibling != null && leftSibling.noOfElements()>1) 
{
T k1=node.deleteElementAt(indexC-1); 
int inew=c.addElement(k1);
TwoFourNode<T> lastchild=leftSibling.deleteChildAt(leftSibling.noOfElements());
T k2=leftSibling.removeLastElement();
node.addElement(k2);
TwoFourNode<T> e0=c.deleteChildAt(0);
TwoFourNode<T> e1=c.deleteChildAt(1);
c.addChildAt(1, e0);
c.addChildAt(2, e1);
c.addChildAt(0, lastchild);
}
else if (rightSibling != null && rightSibling.noOfElements()>1) 
{
T k1=node.deleteElementAt(indexC); 
int inew=c.addElement(k1);
TwoFourNode<T> firstchild=rightSibling.deleteChildAt(0);
int j;
for (j=0;j<4-1;j++) 
{
TwoFourNode<T> tmp=rightSibling.getChildAt(j + 1); 
rightSibling.addChildAt(j, tmp);
}
rightSibling.addChildAt(j, null);
T k2=rightSibling.deleteElementAt(0); 
node.addElement(k2);
c.addChildAt(c.noOfElements(), firstchild);
}else 
{
if (leftSibling != null) 
{
T k1=node.deleteElementAt(indexC-1);
TwoFourNode<T> tmp=node.deleteChildAt(indexC);
if ( tmp != c)
throw new RuntimeException("Issue!");
int j;
for (j=indexC;j<4-1;j++ ) 
{
TwoFourNode<T> tmp2=node.getChildAt(j + 1); 
node.addChildAt(j, tmp2);
}
node.addChildAt(j, null);
int inew=leftSibling.addElement(k1);
inew=leftSibling.addElement(c.getElementAt(0));
leftSibling.addChildAt(2,c.getChildAt(0));
leftSibling.addChildAt(3,c.getChildAt(1));
if (node.noOfElements()<=0) 
{
TwoFourNode<T> parent=node.getParent();
if (node==rootNode) 
{
rootNode=leftSibling;
} else 
{
int l;
for (l=0;l<4-1;l++) 
{
if(node==parent.getChildAt(l))
break;
}
if (l==4) 
throw new RuntimeException("Issue!");
parent.addChildAt(l, leftSibling);}}
c=leftSibling;
}
else 
{
T k1=node.deleteElementAt(indexC);
c.addElement(k1);
int j;
for (j=indexC+1;j<4-1;j++) 
{
TwoFourNode<T> tmp2=node.getChildAt(j + 1); 
node.addChildAt(j, tmp2);
}
node.addChildAt(j, null);
c.addChildAt(2,rightSibling.getChildAt(0));
c.addChildAt(3,rightSibling.getChildAt(1));
T rightSibElem=rightSibling.deleteElementAt(0);
c.addElement(rightSibElem);
if (node.noOfElements() <= 0) 
{
TwoFourNode<T> parent=node.getParent();
if (node==rootNode) 
{
rootNode=c;
c.parent=null;
} 
else 
{
int l;
for (l=0;l<4-1;l++) 
{
if(node==parent.getChildAt(l))
break;
}
if (l==4) 
throw new RuntimeException("Issue!");
parent.addChildAt(l, c);
}}}}}
return remove(c,e);
}}}

protected int getNextSiblingIndex(TwoFourNode<T> parent, T e) 
{
int nElements=parent.noOfElements();
for(int index=0; index<nElements; index++) 
{
if( comparator.compare(e, parent.getElementAt(index))<0){
return index; 
}}
return nElements;
}

protected void fuse(TwoFourNode<T> node, T k,int index) 
{		
TwoFourNode<T> predChild=node.getChildAt(index);
TwoFourNode<T> nextChild=node.getChildAt(index + 1) ;
T temp=node.deleteElementAt(index);
if (comparator.compare(k, temp) != 0)
throw new RuntimeException("Issue fusing nodes");
int i;
for(i=index+1; i<4-1; i++) 
{
TwoFourNode<T> tmp=node.deleteChildAt(i+1);
node.addChildAt(i, tmp);
}
node.deleteChildAt(i);
TwoFourNode<T> c0=nextChild.firstChild();
TwoFourNode<T> c1= nextChild.getChildAt(1);
predChild.addChildAt(2, c0);
predChild.addChildAt(3, c1);
int addIndex;
addIndex=predChild.addElement(k);
addIndex=predChild.addElement(nextChild.getElementAt(0));
if (predChild.noOfElements() != 3) 
{
throw new RuntimeException("fuse Issue!");
}
fusionCount++;
}

protected void splitNode(TwoFourNode<T> node)  
{
T right=node.removeLastElement();
T mid=node.removeLastElement();
TwoFourNode<T> c2=node.deleteChildAt(2);
TwoFourNode<T> c3=node.deleteChildAt(3);
TwoFourNode<T> parent;
if(rootNode==node)   
{
rootNode=new TwoFourNode<T>();
parent=rootNode;
rootNode.addChildAt(0, node);
}
else 
{
parent=node.getParent();
}
int iAdded=parent.addElement(mid);
int elems=parent.noOfElements();
for(int i=elems-1; i>iAdded; i--) 
{
TwoFourNode<T> tmp=parent.deleteChildAt(i);
parent.addChildAt(i+1, tmp);
}
TwoFourNode<T> rnew=new TwoFourNode<T>();
parent.addChildAt(iAdded+1, rnew);
rnew.addElement(right);
rnew.addChildAt(0, c2);
rnew.addChildAt(1, c3);
splitCount++;
}

public boolean removeHack(T x) 
{
if (size()==0)
return false;
TwoFourTree<T> nt=new TwoFourTree<T>();
T obj=findSmallestElement();
Boolean flag=false;
while (obj!=null) 
{
if (comparator.compare(x, obj)==0) 
{
flag=true;
obj=findGT(obj);
continue;
}
nt.add(obj);
obj=findGT(obj);}
rootNode=nt.getRootNode();
return flag;
}

public void clear() 
{
rootNode=new TwoFourNode<T>();
}

public Iterator<T> iterator(T e) 
{
return new TreeIterator(e);
}

public Iterator<T> iterator()
{
return new TreeIterator(findSmallestElement());
}

class TreeIterator implements Iterator<T> 
{
T next;
Boolean last=false;

public TreeIterator(T e) 
{
if (comparator.compare(e, findLargestElement()) > 0 )
throw new RuntimeException("Last Element reached");
if (comparator.compare(e, findLargestElement())==0 )
last=true;
next=findGE(e);
}

public boolean hasNext() 
{
if (next==null)
return false;
else
return true;
}

public T next() 
{
T toReturn=next;
if ( next==null)
throw new NoSuchElementException("Has no next element");
if (last==true)
next=null;
else
next=findGT(next);
return toReturn;
}

public void remove() 
{
removeHack(next);}}

public TwoFourNode<T> getRootNode() 
{
return rootNode;
}
	
public String inOrderElements(TwoFourNode<T> node)
{
String str="";
if(node.isLeafNode())
str += node.toString();		
else
for(int i=0; i<=node.noOfElements();i++)
{
TwoFourNode<T> child=node.getChildAt(i);
str += inOrderElements(child);
if(i != node.noOfElements())
str += node.getElementAt(i);
}
return str;
}

@Override
public int compare(T o1, T o2) 
{
if(o1!=null && o2!=null)
return ((Comparable<T>)o1).compareTo(o2); 
else return 0;
}

@Override
public boolean addAll(Collection<? extends T>c) 
{
return false;
}
static int comparecount=0;
	
@Override
public boolean contains(Object o)
{
T e=(T) o;
TwoFourNode<T> current=rootNode;
comparecount++;
while(true)
{
if (current.findElement(e)>-1)
{
return true;
}
if (current.isLeafNode()) 
{
return false;
}
current=getChildSibling(current,e);
}}

@Override
public boolean containsAll(Collection<?> c) 
{
return false;
}

@Override
public boolean isEmpty() 
{
return false;
}

@Override
public boolean remove(Object e) 
{
return remove(rootNode,(T) e);
}

@Override
public boolean removeAll(Collection<?> c) 
{
return false;
}

@Override
public boolean retainAll(Collection<?> c) 
{
return false;
}

@Override
public Object[] toArray() 
{
return null;
}

@Override
public <T> T[] toArray(T[] a) {
return null;
}}

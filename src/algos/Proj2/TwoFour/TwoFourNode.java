package algos.Proj2.TwoFour;
import java.util.Comparator;

public class TwoFourNode<T> implements Comparator<T>
{
protected static final int CHILD_MAX=4;
protected static final int NODES_MAX=3;
protected TwoFourNode<T> parent;
protected TwoFourNode<T> children[]=new TwoFourNode[CHILD_MAX];
protected T[] elements=(T[]) new Object[NODES_MAX];
protected int nElements;
protected Comparator<T> comparator;

public TwoFourNode() 
{
nElements=0;
comparator=this;
}

public TwoFourNode(Comparator<T> comparator) 
{
nElements=0;
this.comparator=comparator;
}

public int noOfElements()
{
return nElements;
}
	
public T[] getElements()
{
return elements;
}

public int size() 
{
if (this.isLeafNode()==true)
return nElements;
int sum=nElements;
for (int i=0;i< nElements+1;i++) 
sum += getChildAt(i).size();
return sum;
}

public boolean isNodeFull() 
{
if (nElements >= 3) 
return true;
else 
return false;
}
	
public boolean isLeafNode()
{
if(firstChild()==null)
return true;
else
return false;
}

public void addChildAt(int i, TwoFourNode<T> node){
if(node != null)
{
node.parent=this;
children[i]=node;
}}

public TwoFourNode<T> deleteChildAt(int i){
TwoFourNode<T> removedChild=getChildAt(i);
children[i]=null;
return removedChild;
}

public TwoFourNode<T> getParent()
{
return parent;
}

public TwoFourNode<T> firstChild() 
{
return children[0];
}
	
public TwoFourNode<T> lastChild() 
{
return children[nElements];
}

public TwoFourNode<T> getChildAt(int i) 
{
return children[i];
}

public TwoFourNode<T>[] getChildren() 
{
return children;
}


public T getElementAt(int i)  
{
return elements[i];
}
	
public T getSmallestElement()  
{
return elements[0];
}

public T getLargestElement()  
{
return elements[nElements-1];
}

public int findElement(T e)
{
int index=-1;
for(int i=0;i<3;i++)  
{
if(elements[i]==null)
break;
else if( comparator.compare(elements[i],e)==0)
index=i;
}
return index;
}

public int findElementGT(T e, T max) {
int index=-1;
for(int i=0;i<3;i++)  
{
if(elements[i]==null)
break;
else if((comparator.compare(elements[i],e)>0) && (comparator.compare(elements[i],max)<0)) 
{
index=i;
break;
}}
return index;
}
	
public int findElementLT(T e, T min) 
{
int index=-1;
for(int i=2;i>=0;i--)  
{
if(elements[i]==null)
continue;
else if((comparator.compare(elements[i],e)<0) && (comparator.compare(elements[i],min)>0)) 
{
index=i;
break;
}}
return index;
}

public int findElementGE(T e,T max) 
{
int index=-1;
for(int i=0;i<3;i++)  
{
if(elements[i]==null)
break;
else if((comparator.compare(elements[i],e)>=0) && (comparator.compare(elements[i],max)<0)) 
{
index=i;
break;
}}
return index;
}

public T removeLastElement() 
{
nElements--;
T element= elements[nElements];
elements[nElements]=null;
return element;
}

public int addElement(T e) 
{
int index=0;
nElements++;
for(int i=elements.length-1;i>=0;i--)  
{
if(elements[i]==null)
continue;
else
{
T element=elements[i];
if(comparator.compare(e,element)<0)
elements[i+1]=elements[i];
else 
{
elements[i+1]=e;
index=i+1;
break;
}}}
elements[index]=e;
return index;
}

public T deleteElementAt(int index) 
{
T removedElement=getElementAt(index);
for (int i=index;i<nElements-1;i++) 
{
elements[i]=elements[i+1];
}
nElements--;
elements[nElements]=null;
return removedElement;
}

public String toString() 
{
String str="|";
for (int i=0;i< noOfElements();i++) {
str+= elements[i] + "|";
}
return str;
}
	
@Override	
public int compare(T arg0, T arg1) 
{
if(arg0!=null && arg1!=null)
return ((Comparable<T>)arg0).compareTo(arg1);
else 
return 0;
}
}

/**
 * 
 */
package se_lab1;

import java.util.ArrayList;

/** 继承原生ArrayList类添加自定义元素查找方法
 * @author Yumi
 * 
 * @param <E> 内部添加的方法只对TreeNode对象集有效 
 */
class TreeNodeList<E> extends ArrayList<E> implements Cloneable{

	private static final long serialVersionUID = 682330081079347841L;
	int longestWordLength = 0;
	/** 检查节点是否已经存在
	 * @param word 单词
	 * @return 存在节点情况(是则返回节点，否则null)
	 */
	public TreeNode nodeCheck(String word) {
		TreeNode existedNode = null,getNode;
		for(int i = 0;i<this.size();i++) {
			getNode = (TreeNode) this.get(i);
			if(word.equals(getNode.getWord())) {
				existedNode = getNode;
				break;
			}
		}
		return existedNode;
	}
	
	/* 重写添加节点方法，在添加节点时计算最长单词长度
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		// TODO Auto-generated method stub
		if(e != null) {
			TreeNode addNode = (TreeNode) e;
			String word = addNode.word;
			this.longestWordLength = word.length() > this.longestWordLength ? word.length():this.longestWordLength;
		}
		return super.add(e);
	}
	
	/** 返回当前List中最长词长
	 * @return int 最长词长
	 */
	public int getLongestWordLength() {
		return this.longestWordLength;
	}
	
	/** 添加到AList的队列操作 PUSH
	 * @param pushNode
	 * @return 操作返回布尔值
	 */
	public boolean push(E pushNode) {
		boolean flag;
		flag = this.add(pushNode);
		return flag;
	}
	
	/** 添加到AList的队列操作 POP
	 * @return 当前List的首元素出队
	 */
	public TreeNode pop() {
		TreeNode popNode;
		if(this.size() != 0) {
			popNode = (TreeNode) this.get(0);
			this.remove(0);
		} else {
			popNode = null;
		}
		return popNode;
	}
	
	public ArrayList<Integer> multiIndexOf(E e) {
		ArrayList<Integer> multiIndex = new ArrayList<Integer>();
		TreeNode queryNode = (TreeNode) e;
		for(int i = 0;i<this.size();i++) {
			if(this.get(i).equals(queryNode)) {
				multiIndex.add(new Integer(i));
			}
		}
		return multiIndex;
	}
}

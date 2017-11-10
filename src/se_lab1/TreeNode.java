package se_lab1;

import java.util.ArrayList;

/**
 * @author Yumi
 *
 */
class TreeNode {
	String word;
	int level;
	TreeNodeList<TreeNode> parentList;
	TreeNodeList<TreeNode> childList;
	ArrayList<Integer> childPathWeightList;
	
	public TreeNode(String word, TreeNode parent) {
		this.word = word;
		this.parentList = new TreeNodeList<TreeNode>();
		if(parent != null) {
			this.parentList.add(parent);
		}
		this.childList = new TreeNodeList<TreeNode>();
		this.childPathWeightList = new ArrayList<Integer>();
	}
	
	public String getWord() {
		return this.word;
	}
	
	public void addParent(TreeNode anotherParent) {
		if(this.parentList.nodeCheck(anotherParent.getWord()) == null) {
			this.parentList.add(anotherParent);
		}
	}
	
	/** 对当前节点进行添加子节点操作，附带对子节点查重操作
	 * @param anotherChild 添加的子节点
	 */
	public void addChild(TreeNode anotherChild) {
		TreeNode checkNode = this.childList.nodeCheck(anotherChild.getWord());
		int nodeIndex,childPathWeight;
		if(checkNode != null) {
			// State - 3 已经桥接该点
			// 获得该点索引，对权值List进行更新
			nodeIndex = this.childList.indexOf(checkNode);
			childPathWeight = this.childPathWeightList.get(nodeIndex).intValue()+1;
			this.childPathWeightList.set(nodeIndex, new Integer(childPathWeight));
		} else {
			// State - 2 未桥接该点
			// 直接添加到子节点List，同时添加权值
			this.childList.add(anotherChild);
			this.childPathWeightList.add(new Integer(1));
		}
	}
	
	/** 调用节点方法输入子节点进行查询邻接边权值
	 * @param childNode 查询邻接子节点
	 * @return 返回0则为不邻接，返回正整数权值则邻接
	 */
	public int getWeightOfNode(TreeNode childNode) {
		int weight,childIndex;
		childIndex = this.childList.indexOf(childNode);
		if(childIndex == -1) {
			// 没有找到该节点,即未邻接当前node
			weight = 0;
		} else {
			// 找到节点，返回weight值
			weight = this.childPathWeightList.get(childIndex).intValue();
		}
		return weight;
	}
	
	public void setNodeLevel(int level) {
		this.level = level;
	}
	
	public int getNodeLevel() {
		return this.level;
	}
}
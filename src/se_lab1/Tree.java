package se_lab1;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Yumi
 *
 */
class Tree {
	TreeNode head;
	TreeNodeList<TreeNode> TreeNodes;

	public Tree(String[] words) {
		TreeNode node_pr,node_after;
		this.head = new TreeNode(words[0],null);
		this.TreeNodes = new TreeNodeList<TreeNode>(); 
		node_pr = this.head;
		this.TreeNodes.add(node_pr);
		for(int i =1;i<words.length;i++) {
			//生成树图的新节点出现情况：
			//State-1 after节点未出现过
			//	直接桥接两个点，添加新点进List
			//State-2 after节点已经出现过 但是没有已知边桥接（交由节点方法进行处理）
			// 	直接桥接，不用添加进List
			//State-3 after节点已经出现过 并且出现过已知桥接边（交由节点方法进行处理）
			
			node_after = this.TreeNodes.nodeCheck(words[i]);
			if(node_after != null) {
				// State-2/3 节点表中已经有该节点
				node_pr.addChild(node_after); // 2/3情况操作交由节点方法进行处理
			} else {
				//State-1 after节点未出现过
				node_after = new TreeNode(words[i],node_pr);
				node_pr.addChild(node_after);
				this.TreeNodes.add(node_after);
			}
			node_after.addParent(node_pr);
			node_pr = node_after;
		}
		calculateNodeLevel();
	}
	
	/** 利用队列结构计算出TreeNodes中各TreeNode的level值
	 *  
	 *  @注  在TreeNode中直接调用TreeNode.getNodeLevel()即可获得level值
	 */
	public void calculateNodeLevel() {
		TreeNodeList<TreeNode> Queue = new TreeNodeList<TreeNode>();
		TreeNodeList<TreeNode> CopyList = new TreeNodeList<TreeNode>();
		CopyList.addAll(TreeNodes);
		TreeNode presentNode,childNode;
		int presentLevel;
		head.setNodeLevel(1);
		Queue.push(this.head);
 		while(CopyList.size() != 0) {
 			presentNode = Queue.pop();
 			presentLevel = presentNode.getNodeLevel() + 1;
 			for(int i = 0;i<presentNode.childList.size();i++) {
 				childNode = presentNode.childList.get(i);
 				if(CopyList.indexOf(childNode) != -1) {
 					childNode.setNodeLevel(presentLevel);
 					if(Queue.indexOf(childNode) == -1) {
 						Queue.push(childNode);
 					}
 				}
 			}
 			CopyList.remove(presentNode);
 		}
	}

}


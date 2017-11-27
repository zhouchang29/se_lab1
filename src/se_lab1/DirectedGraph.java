package se_lab1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DirectedGraph {
	/** 生成dot文件并生成png格式的最短路有向图(目前只能显示一条路)
	 * @param t 树
	 * @param fileUrl txt文件路径
	 * @param fontname 字体名
	 * @param fontsize 字体大小
	 * @return 成功标识(true)
	 */
	public static boolean createShortestDirectedGraph(String fileUrl, 
					String fontname, int fontsize, String shortest, PathGraphAssist pga) {
		String[] shroads = shortest.split("\n");
		for (int i = 0; i < shroads.length; i++) {
			shroads[i] = shroads[i].replaceAll("Path [0-9]+ :", "");
			shroads[i] = shroads[i].replace(".", "");
		}
		String[] colors = {"#1abc9c", "#3498db", "#f1c40f", "#8e44ad", "#c0392b"};
		File dotFile = new File(fileUrl.replace("txt", "dot"));
		File sdotFile = new File(fileUrl.replace(".txt", "s.dot"));
		try {
			sdotFile.createNewFile();
			Scanner in = new Scanner(dotFile);
			BufferedWriter outBuffer = new BufferedWriter(new FileWriter(sdotFile));
			while(in.hasNextLine()) {
				String str = in.nextLine();
				for (int i = 0; i < pga.AllNodes.size(); i++) {
					for(int j = 0;j < pga.AllNodes.size(); j++) {
						TreeNode node1 = pga.AllNodes.get(i),
								node2 = pga.AllNodes.get(j);
						str = str.replace(String.format("%s -> %s [color = \"#3498db\"]", node1.getWord(), node2.getWord()), 
								String.format("%s -> %s [color = \"%s\"]", node1.getWord(), node2.getWord(), "#778899"));
					}
				}
				for (int i = 0; i < shroads.length; i++) {
					str = replaceResult(str, shroads[i], colors[i], pga);
				}
				outBuffer.write(str);
			}
			outBuffer.flush();
			outBuffer.close();
			in.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		Runnable createGraphRunnable = new CreateGraphRunnable(Lab1.fileUrl.replace(".txt", "s.dot"), Lab1.fileUrl.replace(".txt", "s.png"));
		Runnable showWaitingRunnable = new ShowWaitingRunnable(Lab1.fileUrl.replace(".txt", "s.png"));

		Thread createGraphThread = new Thread(createGraphRunnable);
		Thread showWaitingThread = new Thread(showWaitingRunnable);

		// 初始化图片生成状态
		Lab1.imgState = 0;
		
		// 开始进程
		createGraphThread.start();
		showWaitingThread.start();
		
		return true;
	}

	/** 生成随机游走dot文件并生成png格式的有向图
	 * @param t 树
	 * @param fileUrl txt文件路径
	 * @param fontname 字体名
	 * @param fontsize 字体大小
	 * @return 成功标识(true)
	 */
	
	public static boolean createRandomDirectedGraph(String fileUrl, String fontname, int fontsize, String random) {
		String[] randomWords = random.split(" ");
		String[] colors = {"#1abc9c", "#3498db", "#f1c40f", "#8e44ad", "#c0392b"};
		File dotFile = new File(fileUrl.replace("txt", "dot"));
		File sdotFile = null;
		
		try {
			for(int i = 0; i < randomWords.length; i++) {
				sdotFile = new File(fileUrl.replace(".txt", String.format("%d.dot", i)));
				sdotFile.createNewFile();
				Scanner in = new Scanner(dotFile);
				BufferedWriter outBuffer = new BufferedWriter(new FileWriter(sdotFile));
				while(in.hasNextLine()) {
					String str = in.nextLine();
					str = replaceRandomResult(str, randomWords, colors[2], i);
					outBuffer.write(str);
				}
				outBuffer.flush();
				outBuffer.close();
				in.close();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		Runnable createRandomGraphRunnable = new CreateRandomGraphRunnable(Lab1.fileUrl.replace("txt", "dot"), Lab1.fileUrl.replace("txt", "png"), randomWords.length);
		Runnable showRandomWaitingRunnable = new ShowRandomWaitingRunnable(Lab1.fileUrl.replace("txt", "png"), randomWords.length);

		Thread createRandomGraphThread = new Thread(createRandomGraphRunnable);
		Thread showWaitingThread = new Thread(showRandomWaitingRunnable);

		// 初始化图片生成状态
		Lab1.imgState = 0;
		
		// 开始进程
		createRandomGraphThread.start();
		showWaitingThread.start();
		
		return true;
	}
	
	public static String replaceRandomResult(String in, String[] random, String color, int num) {
		for (int i = 0; i < num - 1; i++) {
			in = in.replace(String.format("%s -> %s", random[i], random[i+1]), String.format("%s -> %s [color = \"%s\"]", random[i], random[i+1], color));
		}
		return in+"\n";
	}
	
	public static String replaceResult(String in, String shortroad, String color, PathGraphAssist pga) {
		String[] srNodes;
		srNodes = shortroad.split("->");
		for (int i = 0; i < srNodes.length - 1; i++) {
			TreeNode node1 = pga.AllNodes.nodeCheck(srNodes[i]),
					node2 = pga.AllNodes.nodeCheck(srNodes[i+1]);
			int state = pga.QueryNodeToNode(node1, node2);
			if(state == Integer.MAX_VALUE) {
				color = "#B71C1C";
			}
			in = in.replace(String.format("%s -> %s", srNodes[i], srNodes[i+1]), String.format("%s -> %s [color = \"%s\"]", srNodes[i], srNodes[i+1], color));
		}
		return in+"\n";
	}
	
	/** 生成dot文件并生成png格式的有向图
	 * @param t 树
	 * @param fileUrl txt文件路径
	 * @param fontname 字体名
	 * @param fontsize 字体大小
	 * @return 成功标识(true)
	 */
	public static boolean createDirectedGraph(Tree t, String fileUrl, String fontname, int fontsize) {
		File dotFile = new File(fileUrl.replace("txt", "dot"));
		try {
			dotFile.createNewFile();
			BufferedWriter outBuffer = new BufferedWriter(new FileWriter(dotFile));
			outBuffer.write(String.format("digraph %s {\n\tfontname = \"%s\";\n\tfontsize = %d;\n\n", "test", fontname, fontsize)); // 这里的名字可以替换的其实
			outBuffer.write(String.format("\tnode [ fontname = \"%s\", fontsize = %d ]\n", fontname, fontsize));
			outBuffer.write(String.format("\tedge [ fontname = \"%s\", fontsize = %d ]\n\n", fontname, fontsize));
			for(int i = 0; i < Lab1.words.length; i++) {
				outBuffer.write(String.format("\t%s;\n", Lab1.words[i]));
			}
			for(int i=0;i<t.TreeNodes.size();i++) {
				for(int j=0;j<t.TreeNodes.size();j++) {
					TreeNode node1 = t.TreeNodes.get(i),
					node2 = t.TreeNodes.get(j);
					if(node1.childList.indexOf(node2) != -1) {
						outBuffer.write(String.format("\t%s -> %s [label=\"%d\"];\n", node1.getWord(), node2.getWord(), node1.getWeightOfNode(node2)));
					}
				}
			}
			outBuffer.write("}");
			outBuffer.flush();
			outBuffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Runnable createGraphRunnable = new CreateGraphRunnable(Lab1.fileUrl.replace("txt", "dot"), Lab1.fileUrl.replace("txt", "png"));
		Runnable showWaitingRunnable = new ShowWaitingRunnable(Lab1.fileUrl.replace("txt", "png"));

		Thread createGraphThread = new Thread(createGraphRunnable);
		Thread showWaitingThread = new Thread(showWaitingRunnable);

		// 初始化图片生成状态
		Lab1.imgState = 0;
		
		// 开始进程
		createGraphThread.start();
		showWaitingThread.start();
		
		return true;
	}
}

/** 调用Graphviz创建随机游走有向图图片的进程
 * @author Czhou
 *
 */
class CreateRandomGraphRunnable implements Runnable {
	String txtPath;
	String graphPath;
	int picNum;
	public CreateRandomGraphRunnable(String txtPath, String graphPath, int picNum) {
		setPath(txtPath, graphPath, picNum);
	}
	
	public void setPath(String txtPath, String graphPath, int picNum) {
		this.txtPath = txtPath;
		this.graphPath = graphPath;
		this.picNum = picNum;
	}
	
	@Override
	public void run() {
		// 运行dot(已配置好环境变量的情况下)
		Runtime run = Runtime.getRuntime();
		try {
			Lab1.imgState = 0;
			Process process = null;
			for(int i = 0; i < picNum; i++) {
				process = run.exec(String.format("dot -Tpng %s -o %s", txtPath.replace(".dot", String.format("%d.dot", i)), graphPath.replace(".png", String.format("%d.png", i))));
			}
			process.waitFor();
			// 生成完图片标记imgState通知Thread2结束
			Lab1.imgState = 1;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}

/** 调用Graphviz创建有向图图片的进程
 * @author Yumi
 *
 */
class CreateGraphRunnable implements Runnable {
		
	
	String txtPath;
	String graphPath;
	
	public CreateGraphRunnable(String txtPath, String graphPath) {
		setPath(txtPath, graphPath);
	}
	
	public void setPath(String txtPath, String graphPath) {
		this.txtPath = txtPath;
		this.graphPath = graphPath;
	}
	
	@Override
	public void run() {
		// 运行dot(已配置好环境变量的情况下)
		Runtime run = Runtime.getRuntime();
		try {
			Lab1.imgState = 0;
			String ins = String.format("dot -Tpng %s -o %s", txtPath, graphPath);
			ins = ins.replace("\\", "/");
			Process process = run.exec(ins);
			process.waitFor();
			// 生成完图片标记imgState通知Thread2结束
			Lab1.imgState = 1;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}

/** 等待Graphviz创建随机图片结束的进程
 * @author Czhou
 *	
 */
class ShowRandomWaitingRunnable implements Runnable {
		
	String graphPath;
	int picNum;
	
	public ShowRandomWaitingRunnable(String graphPath, int picNum) {
		setPath(graphPath, picNum);
	}
	
	public void setPath(String graphPath, int picNum) {
		this.graphPath = graphPath;
		this.picNum = picNum;
	}
	
	@Override
	public void run() {
		while(Lab1.imgState == 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		SetPicRunnable setPicRunnable = new SetPicRunnable(graphPath, picNum);
		Thread setPicThread = new Thread(setPicRunnable);
		setPicThread.start();
	}
}

/** 随机游走设置图片
 * @author Czhou
 *	
 */
class SetPicRunnable implements Runnable {
		
	String graphPath;
	int picNum;
	
	public SetPicRunnable(String graphPath, int picNum) {
		setPath(graphPath, picNum);
	}
	
	public void setPath(String graphPath, int picNum) {
		this.graphPath = graphPath;
		this.picNum = picNum;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < picNum; i++) {
			try {
				picDisplayPanel.setPic(graphPath.replace(".png", String.format("%d.png", i)));
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		
	}
}


/** 等待Graphviz创建图片结束的进程
 * @author Yumi
 *	
 */
class ShowWaitingRunnable implements Runnable {
		
	String graphPath;
	
	public ShowWaitingRunnable(String graphPath) {
		setPath(graphPath);
	}
	
	public void setPath(String graphPath) {
		this.graphPath = graphPath;
	}
	
	@Override
	public void run() {
		// 预留出来的线程（做其他，比如显示等待图片或者进度条）
		
		// while等待图片加载
		while(Lab1.imgState == 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 图片加载完毕后进行操作
		
		// 用于测试的输出
		// System.out.println("Create Img Success!");
		picDisplayPanel.setPic(graphPath);
	}
}
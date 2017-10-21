package se_lab1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

class MyFrame extends JFrame {

  /**
  * 
  */
  private static final long serialVersionUID = -6904245993409935448L;
  private static final int WIDTH = 520;
  private static final int height = 550;
  PicDisplayPanel picPanel;

  public MyFrame() {

    try {
      String feel = UIManager.getSystemLookAndFeelClassName();
      UIManager.setLookAndFeel(feel);
    } catch (Exception e) {
      e.printStackTrace();
    }
    setBackground(Color.WHITE);
    setTitle("Flow chart");
    setSize(WIDTH, height);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    Container c = getContentPane();

    // 菜单栏 定义
    JMenuBar mb = new JMenuBar();
    JMenu mFile = new JMenu("文件(F)");
    // File菜单
    mFile.setMnemonic('F');
    JMenuItem miOpen = new JMenuItem("打开(O)");
    miOpen.setMnemonic('O');
    JMenuItem miReset = new JMenuItem("回复默认图(R)");
    miReset.setMnemonic('O');
    mFile.add(miOpen);
    mFile.add(miReset);
    miReset.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        try {
          Lab1.readInFile();
        } catch (NullPointerException e) {
          // e.printStackTrace();
        }

      }

    });
    miOpen.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Choose file");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.showOpenDialog(Lab1.f);
        File filename = fc.getSelectedFile();
        if (filename != null) {
          Lab1.fileUrl = fc.getSelectedFile().getAbsolutePath();
          Lab1.readInFile();
        }
      }
    });
    // 将主菜单添加到菜单栏
    mb.add(mFile);
    // 将菜单栏添加到主程序
    setJMenuBar(mb);

    JPanel mainPanel = new JPanel(new GridLayout());
    PicDisplayPanel picPanel = new PicDisplayPanel();
    JScrollPane sp = new JScrollPane(picPanel);
    sp.validate();
    mainPanel.add(sp);
    // c.add(sp, twoSeprateLayout);
    FunctionPanel funcPanel = new FunctionPanel();
    mainPanel.add(funcPanel);
    // c.add(funcPanel, twoSeprateLayout);
    c.add(mainPanel);
    setVisible(true);
  }
}

class FunctionPanel extends JPanel {
  private static final long serialVersionUID = -1104559035947942491L;
  private JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP);
  private String[] tabNames = { "查找桥接词", "生成新文本", "查找最短路", "随机游走" };

  public FunctionPanel() {
    setBackground(Color.WHITE);
    JPanel tab1 = new QueryBridgePanel();
    tp.addTab(tabNames[0], null, tab1);
    JPanel tab2 = new NewTextPanel();
    tp.addTab(tabNames[1], null, tab2);
    JPanel tab3 = new ShortestPanel();
    tp.addTab(tabNames[2], null, tab3);
    JPanel tab4 = new RandomPanel();
    tp.addTab(tabNames[3], null, tab4);
    add(tp);
  }
}

class ShortestPanel extends JPanel {
  private static final long serialVersionUID = 7264749733574435443L;
  JTextField tfWord1 = new JTextField(112);
  JLabel lbWord1 = new JLabel("单词1: ");
  JTextField tfWord2 = new JTextField(12);
  JLabel lbWord2 = new JLabel("单词2: ");
  // JLabel lbRst = new JLabel();
  JTextArea txRst = new JTextArea(3, 30);
  JButton btnQB = new JButton("开始计算");
  PathGraphAssist pga;

  public ShortestPanel() {
    GridBagConstraints gbc = new GridBagConstraints();
    btnQB.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          pga = new PathGraphAssist(Lab1.t.treeNodes);
          String shortest = Lab1.t.calcShortestPath(tfWord1.getText(), tfWord2.getText(), pga);
          txRst.setText(shortest);
          DirectedGraph.createShortestDirectedGraph(Lab1.t, Lab1.fileUrl, "Verdana", 12, shortest, pga);
        } catch (CloneNotSupportedException e1) {
          // TODO 自动生成的 catch 块
          e1.printStackTrace();
        } catch (NullPointerException e2) {

        }
      }

    });
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    setLayout(new GridBagLayout());
    add(lbWord1, gbc);
    gbc.gridy = 1;
    add(tfWord1, gbc);
    gbc.gridy = 2;
    add(lbWord2, gbc);
    gbc.gridy = 3;
    add(tfWord2, gbc);
    gbc.gridy = 4;
    add(btnQB, gbc);
    gbc.gridy = 5;
    add(txRst, gbc);
  }
}

class QueryBridgePanel extends JPanel {
  private static final long serialVersionUID = 7264749733574435443L;
  JTextField tfWord1 = new JTextField(112);
  JLabel lbWord1 = new JLabel("单词1: ");
  JTextField tfWord2 = new JTextField(112);
  JLabel lbWord2 = new JLabel("单词2: ");
  JLabel lbRst = new JLabel();
  JButton btnQB = new JButton("开始查询");

  public QueryBridgePanel() {
    GridBagConstraints gbc = new GridBagConstraints();
    btnQB.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          lbRst.setText(Lab1.t.queryBridgeWords(tfWord1.getText(), tfWord2.getText()));
        } catch (Exception err) {

        }

      }

    });
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    setLayout(new GridBagLayout());
    add(lbWord1, gbc);
    gbc.gridy = 1;
    add(tfWord1, gbc);
    gbc.gridy = 2;
    add(lbWord2, gbc);
    gbc.gridy = 3;
    add(tfWord2, gbc);
    gbc.gridy = 4;
    add(btnQB, gbc);
    gbc.gridy = 5;
    add(lbRst, gbc);
  }
}

class RandomPanel extends JPanel {
  private static final long serialVersionUID = -2862015187279261925L;
  JLabel lbRst = new JLabel();
  JButton btnG = new JButton("开始生成");

  public RandomPanel() {
    btnG.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        String random = Lab1.t.randomWalk();
        lbRst.setText(random);
        DirectedGraph.createRandomDirectedGraph(Lab1.t, Lab1.fileUrl, "Verdana", 12, random);
      }

    });
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    setLayout(new GridBagLayout());
    gbc.gridy = 1;
    add(btnG, gbc);
    gbc.gridy = 2;
    add(lbRst, gbc);
  }
}

class NewTextPanel extends JPanel {
  private static final long serialVersionUID = -2862015187279261925L;
  JTextArea taText = new JTextArea(3, 40);
  JLabel lbText = new JLabel("新文本: ");
  JLabel lbRst = new JLabel();
  JButton btnG = new JButton("开始生成");

  public NewTextPanel() {
    taText.setBorder(new LineBorder(new Color(127, 157, 185), 1, false));
    taText.setLineWrap(true);
    btnG.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        try {
          lbRst.setText(Lab1.t.generateNewText(taText.getText()));
        } catch (Exception err) {

        }
      }

    });
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    setLayout(new GridBagLayout());
    add(lbText, gbc);
    gbc.gridy = 1;
    add(taText, gbc);
    gbc.gridy = 4;
    add(btnG, gbc);
    gbc.gridy = 5;
    add(lbRst, gbc);
  }
}

class PicDisplayPanel extends JPanel {
  private static final long serialVersionUID = -466385864846654643L;
  public static JLabel picLabel;
  public static int WIDTH;
  public static int height;
  public static ImageIcon pic;
  boolean isAltDown = false;
  int percent = 100;

  public PicDisplayPanel() {
    picLabel = new JLabel();
    setBackground(Color.WHITE);
    add(picLabel);
  }

  public static void setPic(String path) {
    try {
      pic = new ImageIcon(ImageIO.read(new File(path)));
      WIDTH = pic.getIconWidth();
      height = pic.getIconHeight();
      picLabel.setIcon(pic);
      picLabel.repaint();
    } catch (IOException e) {
      // TODO 自动生成的 catch 块
      e.printStackTrace();
    }
  }

  public static void changeSize(int percent) {
    pic.setImage(pic.getImage().getScaledInstance(percent * WIDTH, percent * height, Image.SCALE_DEFAULT));
    picLabel.setIcon(pic);
  }
}
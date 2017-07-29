package windowBuilder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * swing学习
 * @author huan
 *
 */
public class FaceTest extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FaceTest frame = new FaceTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FaceTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 509);
		//内容块 在design中Layout里选相应的布局方式,这里选绝对布局的方式,默认是flawLayout
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//design-container 放组件的容器,可以根据情况选择布局方式,然后放组件,画界面之前应该用容器规划好,之后画出的界面就不会太乱
		JPanel panel = new JPanel();
		panel.setBounds(10, 70, 678, 44);
		contentPane.add(panel);
		panel.setLayout(null);
		//design中components选中想要放置的组件,在上面定好的内容块中点击放置即可,可拖拽调节大小
		JLabel lblTestlb = new JLabel("test1Lb");
		lblTestlb.setBounds(50, 13, 42, 15);
		panel.add(lblTestlb);
		//按钮 并增加点击事件
		JButton btnNewButton = new JButton("testBt1");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//把face界面跟dialog界面关联起来
				TestDia testDia=new TestDia();
				testDia.setVisible(true);
			}
		});
		btnNewButton.setBounds(316, 5, 93, 23);
		panel.add(btnNewButton);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 105, 21);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("New menu");
		menuBar.add(mnNewMenu_1);
		
		JMenu mnNewMenu = new JMenu("New menu");
		mnNewMenu_1.add(mnNewMenu);
		//选项卡
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(79, 215, 365, 174);
		contentPane.add(tabbedPane);
	}
}

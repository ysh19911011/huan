package windowBuilder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
/**
 * face子页面,创建时点Dialog创建,会自动集成JDialog
 * @author huan
 *
 */
public class TestDia extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTab;
	private JTextField txtTab_1;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			TestDia dialog = new TestDia();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public TestDia() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			//tab下每一个panel都是一个选项卡,可以分别对panel进行布局
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(10, 10, 414, 81);
			contentPanel.add(tabbedPane);
			{
				JPanel panel = new JPanel();
				panel.setToolTipText("");
				//第一个参数 设置选项卡名 第三个对应的panel
				tabbedPane.addTab("New tab1", null, panel, null);
				
				txtTab_1 = new JTextField();
				txtTab_1.setText("tab1");
				panel.add(txtTab_1);
				txtTab_1.setColumns(10);
			}
			{
				JPanel panel = new JPanel();
				tabbedPane.addTab("New tab2", null, panel, null);
				
				txtTab = new JTextField();
				txtTab.setText("tab2");
				panel.add(txtTab);
				txtTab.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBounds(10, 110, 414, 91);
			contentPanel.add(panel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					//关闭当前界面
					@Override
					public void mouseClicked(MouseEvent e) {
						TestDia.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

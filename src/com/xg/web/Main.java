package com.xg.web;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import com.xg.core.IRenameSrv;
import com.xg.core.impl.RenameImpl;
import com.xg.msg.IMessageSrv;
import com.xg.msg.impl.MessageLog;

public class Main implements IMessageSrv {

	protected Shell shell;
	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());
	private Text inPutTxt;

	private String selectedDir;
	private Button selectPath;
	private Button startBtn;

	private IRenameSrv renameSrv;

	private Label successLabel;
	private Label failedLabel;
	private Label otherLabel;
	private Label label;
	private Label label_1;
	private Label label_2;
	private Text outPutTxt;
	private Button outPutBtn;
	private Label msgLabel;
	private Combo combo;
	private Label lblNewLabel_1;

	public Main() {
		renameSrv = new RenameImpl();
		renameSrv.registerMsg(this);
		renameSrv.registerMsg(new MessageLog());
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main window = new Main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		shell.setSize(736, 473);
		shell.setText("二维码查找替换");

		inPutTxt = new Text(shell, SWT.BORDER);
		inPutTxt.setBounds(24, 30, 442, 25);
		formToolkit.adapt(inPutTxt, true, true);

		selectPath = new Button(shell, SWT.NONE);
		selectPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog directoryDialog = new DirectoryDialog(shell);

				directoryDialog.setFilterPath(selectedDir);
				directoryDialog
						.setMessage("Please select a directory and click OK");

				String dir = directoryDialog.open();
				if (dir != null) {
					inPutTxt.setText(dir);
					selectedDir = dir;
				}
			}
		});
		selectPath.setBounds(492, 30, 80, 25);
		formToolkit.adapt(selectPath, true, true);
		selectPath.setText("\u8F93\u5165\u76EE\u5F55");

		outPutBtn = new Button(shell, SWT.NONE);
		outPutBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog directoryDialog = new DirectoryDialog(shell);

				directoryDialog.setFilterPath(selectedDir);
				directoryDialog.setMessage("Please select a directory and click OK");

				String dir = directoryDialog.open();
				if (dir != null) {
					outPutTxt.setText(dir);
				}
			}
		});
		
		startBtn = new Button(shell, SWT.NONE);
		startBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reset();
				if("".equals(inPutTxt.getText().trim())){
					msgLabel.setText("输入目录不能为空");
					return;
				}
				if("".equals(outPutTxt.getText().trim())){
					msgLabel.setText("输出目录不能为空");
					return;
				}
				startBtn.setEnabled(false);
				msgLabel.setText(renameSrv.rename(inPutTxt.getText(),outPutTxt.getText(),combo.getSelectionIndex()));
				startBtn.setEnabled(true);
			}
		});
		
		startBtn.setBounds(578, 72, 90, 25);
		formToolkit.adapt(startBtn, true, true);
		// 开始
		startBtn.setText("\u5F00\u59CB");

		successLabel = new Label(shell, SWT.NONE);
		successLabel.setBounds(87, 186, 61, 15);
		formToolkit.adapt(successLabel, true, true);
		successLabel.setText("0");

		failedLabel = new Label(shell, SWT.NONE);
		failedLabel.setText("0");
		failedLabel.setBounds(87, 215, 61, 15);
		formToolkit.adapt(failedLabel, true, true);

		otherLabel = new Label(shell, SWT.NONE);
		otherLabel.setText("0");
		otherLabel.setBounds(87, 247, 61, 15);
		formToolkit.adapt(otherLabel, true, true);

		label = new Label(shell, SWT.NONE);
		label.setBounds(24, 186, 57, 15);
		formToolkit.adapt(label, true, true);
		label.setText("\u6210\u529F\uFF1A");

		label_1 = new Label(shell, SWT.NONE);
		label_1.setText("\u5931\u8D25\uFF1A");
		label_1.setBounds(24, 215, 57, 15);
		formToolkit.adapt(label_1, true, true);

		label_2 = new Label(shell, SWT.NONE);
		label_2.setText("\u5176\u4ED6\u6587\u4EF6\uFF1A");
		label_2.setBounds(24, 247, 57, 15);
		formToolkit.adapt(label_2, true, true);

		outPutTxt = new Text(shell, SWT.BORDER);
		outPutTxt.setBounds(24, 72, 442, 25);
		formToolkit.adapt(outPutTxt, true, true);

		outPutBtn.setText("\u8F93\u51FA\u76EE\u5F55");
		outPutBtn.setBounds(492, 72, 80, 25);
		formToolkit.adapt(outPutBtn, true, true);
		
		msgLabel = new Label(shell, SWT.NONE);
		msgLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		msgLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		msgLabel.setBounds(24, 103, 442, 15);
		formToolkit.adapt(msgLabel, true, true);
		
		combo = new Combo(shell, SWT.NONE);
		combo.setItems(new String[] {"\u8986\u76D6","\u91CD\u547D\u540D" });
		combo.setToolTipText("\u8986\u76D6");
		combo.setBounds(87, 124, 88, 23);
		formToolkit.adapt(combo);
		formToolkit.paintBordersFor(combo);
		combo.select(0);
		
		lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblNewLabel_1.setBounds(24, 127, 61, 15);
		formToolkit.adapt(lblNewLabel_1, true, true);
		lblNewLabel_1.setText("\u6587\u4EF6\u51B2\u7A81");

	}

	@Override
	public void perSussess(String filePath) {
		int count = Integer.parseInt(successLabel.getText());
		successLabel.setText(++count + "");
	}

	@Override
	public void perFailed(String filePath) {
		int count = Integer.parseInt(failedLabel.getText());
		failedLabel.setText(++count + "");
	}

	@Override
	public void perOther(String filePath) {
		int count = Integer.parseInt(otherLabel.getText());
		otherLabel.setText(++count + "");
	}
	
	public void reset(){
		successLabel.setText("0");
		failedLabel.setText("0");
		otherLabel.setText("0");
	}
}

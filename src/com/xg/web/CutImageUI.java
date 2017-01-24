package com.xg.web;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
//import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class CutImageUI {

	private Image image = null;
	protected Shell shell;
	private java.lang.String imagepath = "";
	private int startX = -1;
	private int startY = -1;
	private int endX = 0;
	private int endY = 0;
	private int offsetX = 0;
	private int offsetY = 0;
	private int x = 0;
	private int y = 0;
	private boolean moveBool = false;
	private boolean custBool = false;
	Double a = null;
	Double b = null;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CutImageUI window = new CutImageUI();
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
		shell.setModified(true);
		shell.setSize(814, 636);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);

		Composite composite = new Composite(shell, SWT.NONE);

		final Group group = new Group(composite, SWT.SHADOW_OUT);
		group.setText("\u622A\u56FE\u533A");

		Group group_1 = new Group(composite, SWT.NONE);
		group_1.setText("\u56FE\u7247\u9884\u89C8\u533A");

		Group group_2 = new Group(composite, SWT.NONE);
		group_2.setText("\u64CD\u4F5C\u533A");
		group_2.setLayout(null);

		Button button = new Button(group_2, SWT.NONE);

		button.setBounds(25, 55, 60, 22);
		button.setText("\u9009\u62E9\u56FE\u7247");

		Button button_1 = new Button(group_2, SWT.NONE);
		button_1.setBounds(102, 55, 60, 22);
		button_1.setText("\u4FDD\u5B58\u56FE\u7247");

		Composite composite_1 = new Composite(composite, SWT.CENTER);
		//composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		ToolBar toolBar = new ToolBar(composite_1, SWT.CENTER);

		ToolItem toolItem = new ToolItem(toolBar, SWT.RIGHT);
		toolItem.setText("\u653E\u5927");

		ToolItem toolItem_1 = new ToolItem(toolBar, SWT.RIGHT);
		toolItem_1.setText("\u7F29\u5C0F");

		ToolItem toolItem_2 = new ToolItem(toolBar, SWT.RIGHT);
		toolItem_2.setText("\u6062\u590D\u539F\u59CB\u5927\u5C0F");
		GroupLayout gl_composite = new GroupLayout(composite);
		gl_composite.setHorizontalGroup(gl_composite.createParallelGroup(
				GroupLayout.LEADING).add(
				gl_composite
						.createSequentialGroup()
						.addContainerGap()
						.add(gl_composite
								.createParallelGroup(GroupLayout.LEADING)
								.add(composite_1, GroupLayout.DEFAULT_SIZE,
										583, Short.MAX_VALUE)
								.add(group, GroupLayout.DEFAULT_SIZE, 583,
										Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.RELATED)
						.add(gl_composite
								.createParallelGroup(GroupLayout.LEADING)
								.add(group_1, GroupLayout.PREFERRED_SIZE, 195,
										GroupLayout.PREFERRED_SIZE)
								.add(group_2, GroupLayout.PREFERRED_SIZE, 195,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		gl_composite
				.setVerticalGroup(gl_composite
						.createParallelGroup(GroupLayout.TRAILING)
						.add(gl_composite
								.createSequentialGroup()
								.addContainerGap()
								.add(gl_composite
										.createParallelGroup(
												GroupLayout.TRAILING)
										.add(gl_composite
												.createSequentialGroup()
												.add(group_1,
														GroupLayout.DEFAULT_SIZE,
														437, Short.MAX_VALUE)
												.addPreferredGap(
														LayoutStyle.RELATED)
												.add(group_2,
														GroupLayout.PREFERRED_SIZE,
														137,
														GroupLayout.PREFERRED_SIZE))
										.add(gl_composite
												.createSequentialGroup()
												.add(group,
														GroupLayout.DEFAULT_SIZE,
														554, Short.MAX_VALUE)
												.addPreferredGap(
														LayoutStyle.RELATED)
												.add(composite_1)))
								.addContainerGap()));

		ToolBar toolBar_1 = new ToolBar(composite_1, SWT.FLAT | SWT.RIGHT);

		final ToolItem toolItem_3 = new ToolItem(toolBar_1, SWT.NONE);
		toolItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!custBool) {
					custBool = true;
					toolItem_3.setText("暂停截图");
				} else {
					custBool = false;
					toolItem_3.setText("截图");
				}
			}
		});
		toolItem_3.setText("\u622A\u56FE");

		final ToolItem toolItem_4 = new ToolItem(toolBar_1, SWT.NONE);
		toolItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!moveBool) {
					moveBool = true;
					toolItem_4.setText("取消拖动");
				} else {
					moveBool = false;
					toolItem_4.setText("拖动");
				}
			}
		});
		toolItem_4.setText("\u62D6\u52A8");
		GroupLayout gl_composite_1 = new GroupLayout(composite_1);
		gl_composite_1.setHorizontalGroup(gl_composite_1.createParallelGroup(
				GroupLayout.LEADING).add(
				gl_composite_1
						.createSequentialGroup()
						.add(132)
						.add(toolBar, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(toolBar_1, GroupLayout.PREFERRED_SIZE, 159,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(133, Short.MAX_VALUE)));
		gl_composite_1.setVerticalGroup(gl_composite_1.createParallelGroup(
				GroupLayout.LEADING).add(
				gl_composite_1
						.createSequentialGroup()
						.addContainerGap()
						.add(gl_composite_1
								.createParallelGroup(GroupLayout.TRAILING)
								.add(toolBar_1, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.add(toolBar, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		composite_1.setLayout(gl_composite_1);
		gl_composite.setAutocreateGaps(true);
		Canvas canvas_1 = new Canvas(group_1, SWT.BORDER);
		canvas_1.setBounds(10, 10, 175, 151);
		group.setLayout(null);

		final Canvas canvas = new Canvas(group, SWT.NONE);
		canvas.setBounds(3, 12, 577, 517);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// 获得终点位置坐标
				endX = e.x;
				endY = e.y;
				// 拖动的偏移量
				offsetX = endX - startX;
				offsetY = endY - startY;
				System.out.println("开始坐标为：(" + x + "," + y + ")");

				// 拖动后的图片的位置
				x = canvas.getBounds().x + offsetX;
				y = canvas.getBounds().y + offsetY;

				System.out.println("坐标为：(" + x + "," + y + ")");
				if (moveBool) {// 为True表示可以拖动图片
				// 再次重新设置图片的位置
					canvas.setBounds(x, y, canvas.getSize().x,
							canvas.getSize().y);
					canvas.setRedraw(true);
				}
				if (custBool) {// 为True表示可以对图片进行截图

				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// 获得初始位置的坐标
				if (startX == -1 && startY == -1) {
					startX = e.x;
					startY = e.y;
				}
			}
		});
		canvas.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite.setLayout(gl_composite);
		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				if (image != null) {
					a = 0.5 * (canvas.getBounds().width - image.getBounds().width);
					b = 0.5 * (canvas.getBounds().height - image.getBounds().height);
					Rectangle rectangle = new Rectangle(a.intValue(), b
							.intValue(), image.getBounds().width, image
							.getBounds().height);
					canvas.setBounds(rectangle);
					e.gc.drawImage(image, a.intValue(), b.intValue());
				}
			}
		});
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				String[] extentions = new String[] { ".jpg", ".bmp", ".*" };
				fileDialog.setFilterNames(extentions);
				fileDialog.open();
				imagepath = fileDialog.getFilterPath() + "\\"
						+ fileDialog.getFileName();
				image = new Image(Display.getDefault(), imagepath);
				if (image != null) {
					canvas.redraw();
				}
			}
		});
	}

	private void custImage() {

	}
}
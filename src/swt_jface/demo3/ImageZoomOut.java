package swt_jface.demo3;

import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class ImageZoomOut {

	protected Shell shell;
	private Canvas canvas;
	private Image image;
	private double rate = 1;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ImageZoomOut window = new ImageZoomOut();
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
		shell.setSize(678, 576);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		{
			Composite composite = new Composite(shell, SWT.NONE);
			Composite composite_1 = new Composite(composite, SWT.BORDER);
			composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
			{
				canvas = new Canvas(composite_1, SWT.NONE);
				canvas.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent e) {
						if (image != null) {
							int width = (int) (image.getBounds().width * rate);
							int heigth = (int) (image.getBounds().height * rate);
							e.gc.drawImage(image, 0, 0,
									image.getBounds().width,
									image.getBounds().height, 0, 0, width,
									heigth);
						}
					}
				});
			}
			Group group = new Group(composite, SWT.NONE);
			{
				Button button = new Button(group, SWT.NONE);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
						String[] extentions = new String[] { ".jpg", ".bmp",
								".*" };
						fileDialog.setFilterNames(extentions);
						fileDialog.open();
						if (fileDialog.getFilterPath() != null
								&& !fileDialog.getFilterPath().equals("")) {
							String imagepath = fileDialog.getFilterPath()
									+ "\\" + fileDialog.getFileName();
							image = new Image(Display.getDefault(), imagepath);
							if (image != null) {
								canvas.redraw();
							}
						}
					}
				});
				button.setBounds(137, 40, 72, 22);
				button.setText("选择图片");
			}
			{
				Button button = new Button(group, SWT.NONE);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						zoomImageOperation(1);
					}
				});
				button.setBounds(234, 40, 72, 22);
				button.setText("放大");
			}
			{
				Button button = new Button(group, SWT.NONE);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						zoomImageOperation(-1);
					}
				});
				button.setBounds(337, 40, 72, 22);
				button.setText("缩小");
			}
			{
				Button button = new Button(group, SWT.NONE);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						zoomImageOperation(0);
					}
				});
				button.setBounds(440, 40, 72, 22);
				button.setText("恢复");
			}
			GroupLayout gl_composite = new GroupLayout(composite);
			gl_composite
					.setHorizontalGroup(gl_composite
							.createParallelGroup(GroupLayout.LEADING)
							.add(composite_1, GroupLayout.DEFAULT_SIZE, 670,
									Short.MAX_VALUE)
							.add(group, GroupLayout.DEFAULT_SIZE, 670,
									Short.MAX_VALUE));
			gl_composite.setVerticalGroup(gl_composite.createParallelGroup(
					GroupLayout.LEADING).add(
					gl_composite
							.createSequentialGroup()
							.add(composite_1, GroupLayout.DEFAULT_SIZE, 433,
									Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(group, GroupLayout.PREFERRED_SIZE, 103,
									GroupLayout.PREFERRED_SIZE)));
			composite.setLayout(gl_composite);
		}
	}

	private void zoomImageOperation(int i) {
		rate = ZoomInOut.onZoom(i);
		canvas.redraw();
	}
}
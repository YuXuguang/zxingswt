package com.xg.web;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Section;

public class ImageMain {

	protected Shell shell;
	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ImageMain window = new ImageMain();
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
		shell.setSize(706, 505);
		shell.setText("SWT Application");

		Canvas canvas = new Canvas(shell, SWT.NONE);
		canvas.setBounds(116, 87, 276, 185);
		formToolkit.adapt(canvas);
		formToolkit.paintBordersFor(canvas);

		ImageLoader loader = new ImageLoader();
		ImageData[] imageData = loader.load("D:\\data\\sygjdl-9556401172.jpg");
		ImageData data = rotate(imageData[0]);
		data = imageData[0].scaledTo(800, 500);
		Image image = new Image(null, data);
		canvas.setBackgroundImage(image);

	}

	private static ImageData rotate(ImageData srcData) {
		int bytesPerPixel = srcData.bytesPerLine / srcData.width;
		int destBytesPerLine = srcData.height * bytesPerPixel;
		byte[] newData = new byte[srcData.data.length];
		int width = 0, height = 0;
		for (int srcY = 0; srcY < srcData.height; srcY++) {
			for (int srcX = 0; srcX < srcData.width; srcX++) {
				int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;
				destX = srcY;
				destY = srcData.width - srcX - 1;
				width = srcData.height;
				height = srcData.width;
				destIndex = (destY * destBytesPerLine)
						+ (destX * bytesPerPixel);
				srcIndex = (srcY * srcData.bytesPerLine)
						+ (srcX * bytesPerPixel);
				System.arraycopy(srcData.data, srcIndex, newData, destIndex,
						bytesPerPixel);
			}
		}

		return new ImageData(width, height, srcData.depth, srcData.palette,
				destBytesPerLine, newData);
	}
}

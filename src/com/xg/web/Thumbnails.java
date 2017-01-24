package com.xg.web;

import java.io.File;  
import org.eclipse.jface.resource.ImageDescriptor;  
import org.eclipse.swt.SWT;  
import org.eclipse.swt.custom.CLabel;  
import org.eclipse.swt.events.MouseListener;  
import org.eclipse.swt.events.PaintEvent;  
import org.eclipse.swt.events.PaintListener;  
import org.eclipse.swt.graphics.Image;  
import org.eclipse.swt.graphics.Point;  
import org.eclipse.swt.graphics.Rectangle;  
import org.eclipse.swt.layout.FillLayout;  
import org.eclipse.swt.layout.GridData;  
import org.eclipse.swt.layout.GridLayout;  
import org.eclipse.swt.widgets.Composite;  
import org.eclipse.swt.widgets.Display;  
import org.eclipse.swt.widgets.Label;  
import org.eclipse.swt.widgets.Shell; 

public class Thumbnails extends Composite{
	private CLabel cLabelImage;  
    private Image image;  
    private Label label;  
    private String labelText;  
  
    private final int IMAGE_SIZE = 164;//缩放后的图片最长的边长为定值。  
  
    /** 
     * 对外使用的构造函数。 
     * @param parent 
     * @param style 
     * @param image 
     * @param labelText 
     */  
    public Thumbnails(Composite parent, int style, Image image, String labelText) {  
        super(parent, style);  
        this.image = image;  
        this.labelText = labelText;  
        initGUI();  
    }  
  
    private void initGUI() {  
        GridData data = new GridData ();  
        data.widthHint = 170;  
        data.heightHint = 180;  
        this.setLayoutData(data);  
        try {  
            GridLayout thisLayout = new GridLayout();  
            this.setLayout(thisLayout);  
  
            {  
                GridData cLabelImageGridData = new GridData();  
                cLabelImageGridData.horizontalAlignment = GridData.FILL;  
                cLabelImageGridData.verticalAlignment = GridData.FILL;  
                cLabelImageGridData.grabExcessVerticalSpace = true;  
                cLabelImageGridData.grabExcessHorizontalSpace = true;  
                cLabelImage = new CLabel(this, SWT.NONE);  
                cLabelImage.setLayoutData(cLabelImageGridData);  
  
//              cLabelImage.setImage(this.image);  
                cLabelImage.addPaintListener(new PaintListener() {  
                    public void paintControl(PaintEvent evt) {  
                        cLabelImagePaintControl(evt);  
                    }  
                });  
            }  
            {  
                label = new Label(this, SWT.NONE);  
                GridData labelGridData = new GridData();  
                labelGridData.horizontalAlignment = GridData.CENTER;  
                label.setLayoutData(labelGridData);  
                label.setText(this.labelText);  
            }  
            this.layout();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 缩放图片 
     * @param evt 
     */  
    private void cLabelImagePaintControl(PaintEvent evt) {  
        Rectangle bounds = image.getBounds();  
        int imageWidth = bounds.width;  
        int imageHeight = bounds.height;  
  
        int thumbWidth = IMAGE_SIZE;// TODO: 缩放后的图片最大宽度  
        int thumbHeight = IMAGE_SIZE;// TODO: 缩放后的图片最大高度  
  
        double ratio = (double)imageWidth/(double)imageHeight;  
  
        //若宽大于高  
        if(ratio>(double)1){  
            thumbHeight = (int)(thumbWidth/ratio);  
        }else {  
            thumbWidth = (int)(thumbHeight*ratio);  
        }  
  
        // 如果图片小于所略图大小, 不作处理  
        if (imageWidth < IMAGE_SIZE && imageHeight < IMAGE_SIZE) {  
            thumbWidth = imageWidth;  
            thumbHeight = imageHeight;  
        }  
  
        evt.gc.drawImage(image, 0, 0, bounds.width, bounds.height, 0, 0,  
                thumbWidth, thumbHeight);  
        //   evt.gc.drawImage(image, srcX, srcY, srcWidth, srcHeight, destX, destY, destWidth, destHeight)  
    }  
  
  
    @Override  
    public void addMouseListener(MouseListener listener) {  
        super.addMouseListener(listener);  
        //这样在点击图标或标题的时候，整个控件才能被选中。  
        cLabelImage.addMouseListener(listener);  
        label.addMouseListener(listener);  
    }  
  
    @Override  
    public void dispose() {  
        if(null !=image && !image.isDisposed()){  
            image.dispose();  
        }  
        if(null !=cLabelImage && !cLabelImage.isDisposed()){  
            cLabelImage.dispose();  
        }  
        if(null !=label && !label.isDisposed()){  
            label.dispose();  
        }  
  
        super.dispose();  
    }  
    /**********************************以下代码用于测试SWT控件,实际使用中不会调用*********************************/  
    /** 
     * Auto-generated main method to display this 
     * org.eclipse.swt.widgets.Composite inside a new Shell. 
     */  
    public static void main(String[] args) {  
        showGUI();  
    }  
  
    /** 
     * Auto-generated method to display this org.eclipse.swt.widgets.Composite 
     * inside a new Shell. 
     */  
    public static void showGUI() {  
        Display display = Display.getDefault();  
        Shell shell = new Shell(display);  
  
        File imageFile = new File("D:\\data\\sygjdl-9556401172.jpg");  
        Image localImage = null;  
        if (imageFile.exists()) {  
            ImageDescriptor imageDescriptor = ImageDescriptor.createFromFile(  
                    null, imageFile.getAbsolutePath());  
            localImage = imageDescriptor.createImage();  
        }  
  
        Thumbnails inst = new Thumbnails(shell, SWT.NULL, localImage, "hello");  
  
        Point size = inst.getSize();  
        shell.setLayout(new FillLayout());  
        shell.layout();  
        if (size.x == 0 && size.y == 0) {  
            inst.pack();  
            shell.pack();  
        } else {  
            Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);  
            shell.setSize(shellBounds.width, shellBounds.height);  
        }  
        shell.open();  
        while (!shell.isDisposed()) {  
            if (!display.readAndDispatch())  
                display.sleep();  
        }  
    }  
}

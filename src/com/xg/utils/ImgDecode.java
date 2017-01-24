package com.xg.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.BufferedImageLuminanceSource;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class ImgDecode {
	@SuppressWarnings("unchecked")
	public static String decode(String imgPath) throws IOException, NotFoundException {
		String resultStr = "";
		File file = new File(imgPath);
		BufferedImage image;
		image = ImageIO.read(file);
		if (image == null) {
			System.out.println("Could not decode image");
		}
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		@SuppressWarnings("rawtypes")
		Hashtable hints = new Hashtable();
		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		// �������ñ��뷽ʽΪ��utf-8��
		result = new MultiFormatReader().decode(bitmap, hints);
		resultStr = result.getText();
		System.out.println("���������ݣ�" + resultStr);
		return resultStr;
	}

}

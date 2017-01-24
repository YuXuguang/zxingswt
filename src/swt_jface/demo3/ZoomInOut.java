package swt_jface.demo3;

public class ZoomInOut {
	private static double rate = 1;

	public static double onZoom(int zoom) {
		double drate = 0;
		if (zoom == 0) {
			drate = showOriginal();
		} else if (zoom > 0) {
			drate = zoomOut();
		} else if (zoom < 0) {
			drate = zoomIn();
		}
		return drate;
	}

	private static double showOriginal() {
		rate = 1;
		return rate;
	}

	private static double zoomIn() {
		// ��������ͼƬ�����¼�������ͼƬ
		rate = rate - 0.1;
		if (rate < 0.1) {
			rate = 0.1;
		}
		return rate;
	}

	private static double zoomOut() {
		// ��������ͼƬ�����¼�������ͼƬ
		rate = rate + 0.1;
		if (rate > 5) {
			rate = 5;
		}
		return rate;
	}
}

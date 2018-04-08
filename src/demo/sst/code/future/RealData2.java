package demo.sst.code.future;

import java.util.concurrent.Callable;

public class RealData2 implements Callable<String> {
	private String data;

	public RealData2(String data) {
		this.data = data;
	}

	@Override
	public String call() throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			stringBuffer.append(data);
			Thread.sleep(100);
		}

		return stringBuffer.toString();
	}
}
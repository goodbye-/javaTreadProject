package demo.sst.code.future;

public class Client {
	public Data request(final String queryStr) {
		final FutureData futureData = new FutureData();
		new Thread() {
			public void run() {// RealData 的构造很慢, 所以在单独的线程中运行
				RealData realData = new RealData(queryStr);
				futureData.setRealData(realData);
			}
		}.start();
		return futureData;
	}
}
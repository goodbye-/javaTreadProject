package demo.sst.code.future;

public class FutureData implements Data {

	protected RealData realData = null;
	protected boolean isReady = false;

	public synchronized void setRealData(RealData realData) {
		if (isReady) {
			return;
		}
		this.realData = realData;
		isReady = true;
		notifyAll();// RealData已经被注入，通知getResult
	}

	@Override
	public synchronized String getResult() {
		while (!isReady) {
			try {
				wait();//一直等待，直到realData被注入
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return realData.result;
	}
}
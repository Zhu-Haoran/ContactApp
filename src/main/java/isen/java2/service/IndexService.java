package isen.java2.service;




public class IndexService {

	private int Index;

	private IndexService() {
		Index=0;
	}

	public static int getIndex() {
		return IndexServiceHolder.INSTANCE.Index;
	}

	public static void setIndex(int i) {
		IndexServiceHolder.INSTANCE.Index=i;
	}

	private static class IndexServiceHolder {
		private static final IndexService INSTANCE = new IndexService();
	}


}

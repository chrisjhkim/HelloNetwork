package com.bit2015.network1001.multithread;

public class AlphabetRunnableImpl implements Runnable {

	@Override
	public void run() {
		for( char c='A'; c<='Z'; c++){
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}

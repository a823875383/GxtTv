package com.jsqix.gxt.tv.update;

import android.os.RemoteException;

public interface IPackageInstallObserver {
	public class Stub implements IPackageInstallObserver {
		public void packageInstalled(String packageName, int returnCode)
				throws RemoteException {
			// TODO Auto-generated method stub
		}
	}
}

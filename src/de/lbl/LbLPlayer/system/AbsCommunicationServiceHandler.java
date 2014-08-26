package de.lbl.LbLPlayer.system;

import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.*;

public abstract class AbsCommunicationServiceHandler
{
	private static final String TAG = AbsCommunicationServiceHandler.class.getSimpleName();

	protected Context con;
	private Messenger mService = null;
	protected boolean mIsBound;
	private final Messenger mMessenger;
	private ServiceConnection mConnection;

	public AbsCommunicationServiceHandler(Context con)
	{
		this.con = con;

		mConnection = new OnConnection();
		mMessenger = new Messenger(new OnIncomingHandler());
		//CheckIfServiceIsRunning();
	}

	private void doBindService(Class clazz)
	{
		Log.wtf(TAG,"bind on service");
		con.bindService(new Intent(con, clazz), mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}


	private void doUnbindService()
	{
		if (mIsBound)
		{
// If we have received the service, and hence registered with it, then now is the time to unregister.
			if (mService != null)
			{
				try
				{
					Message msg = Message.obtain(null, AbsCommunicationService.UNREGISTER_CLIENT);
					msg.replyTo = mMessenger;
					mService.send(msg);
				}
				catch (RemoteException e)
				{
// There is nothing special we need to do if the service has crashed.
				}
			}
// Detach our existing connection.
			con.unbindService(mConnection);
			mIsBound = false;
		}
	}

	class OnIncomingHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			AbsCommunicationServiceHandler.this.handleMessage(msg);
		}
	}

	class OnConnection implements ServiceConnection
	{
		@Override
		public void onServiceConnected(ComponentName className, IBinder service)
		{
			Log.wtf(TAG,"service connected");
			mService = new Messenger(service);
			try
			{
				Message msg = Message.obtain(null, AbsCommunicationService.REGISTER_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);
			}
			catch (RemoteException e)
			{
// In this case the service has crashed before we could even do anything with it
			}
		}
		@Override
		public void onServiceDisconnected(ComponentName className)
		{
// This is called when the connection with the service has been unexpectedly disconnected - process crashed.
			mService = null;
		}

	}

	public abstract void handleMessage(Message msg);


	public void sendMessageToService(int what, Bundle data)
	{
		if (mIsBound)
		{
			if (mService != null)
			{
				try
				{
					//Message msg = Message.obtain(null, MyService.MSG_SET_INT_VALUE, intvaluetosend, 0);
					Message msg = Message.obtain(null, what);
					msg.setData(data);
					msg.replyTo = mMessenger;
					mService.send(msg);
				}
				catch (RemoteException e)
				{
				}
			}
		}
	}

	public void startService(Class clazz)
	{
		Log.wtf(TAG,"start service");
		con.startService(new Intent(con, clazz));
		doBindService(clazz);
	}

	public void stopService(Class clazz)
	{
		doUnbindService();
		con.stopService(new Intent(con, clazz));
	}
	
	protected void onDestroy()
	{
		try
		{
			doUnbindService();
		}
		catch (Throwable t)
		{
			Log.e(TAG, "Failed to unbind from the service", t);
		}
	}
}


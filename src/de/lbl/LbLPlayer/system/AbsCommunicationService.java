package de.lbl.LbLPlayer.system;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import de.lbl.LbLPlayer.*;
import java.util.*;
import java.lang.ref.*;

public abstract class AbsCommunicationService extends Service
{
	public static final String TAG = AbsCommunicationService.class.getSimpleName();
	
	//public static final int DATA_SET = -3;
	public static final int REGISTER_CLIENT = -1;
	public static final int UNREGISTER_CLIENT = -2;

    private static boolean isRunning = false;

    private List<Messenger> mClients; // Keeps track of all current registered clients.
	private Messenger mMessenger; // Target we publish for clients to send messages to IncomingHandler.

	private NotificationManager nm; 
	
    @Override
    public IBinder onBind(Intent intent)
	{
		Log.wtf(TAG, "onbind zurück");
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate()
	{
        super.onCreate();
        mClients = new ArrayList<Messenger>();
		Log.i(TAG, "Service Started.");
		mMessenger = new Messenger(new IncomingHandler(this));
		
        initNotification();
        isRunning = true;
    }
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
	{
        Log.wtf(TAG, "Received start id " + startId + ": " + intent);
        return START_STICKY; // run until explicitly stopped.
    }
	
	public void sendMessageToUI(int what, Bundle data)
	{
        for (int i=mClients.size() - 1; i >= 0; i--)
		{
            try
			{
                Message msg = Message.obtain(null, what);
                msg.setData(data);
                mClients.get(i).send(msg);
            }
			catch (RemoteException e)
			{
                // The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
                mClients.remove(i);
            }
        }
    }

    public static boolean isRunning()
    {
        return isRunning;
    }
	
	static class IncomingHandler extends Handler
	{
		private final WeakReference<AbsCommunicationService> mService; // Handler of incoming messages from clients.
	
		public IncomingHandler(AbsCommunicationService service){
			mService = new WeakReference<AbsCommunicationService>(service);
		}
        @Override
        public void handleMessage(Message msg)
		{
			AbsCommunicationService service = mService.get();
			if(service != null)
			switch (msg.what)
			{
				case REGISTER_CLIENT:
					service.mClients.add(msg.replyTo);
					break;
				case UNREGISTER_CLIENT:
					service.mClients.remove(msg.replyTo);
					break;
				default:
					service.handleMessage(msg);
            }
			
        }
    }
	
	public abstract void handleMessage(Message msg);
	

    @Override
    public void onDestroy()
	{
        super.onDestroy();
		     Log.i(TAG, "Service Stopped.");
        isRunning = false;
    }
	
	
	
	public void initNotification(){
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		// In this sample, we'll use the same text for the ticker and the expanded notification
        //CharSequence text = getText(R.string.app_name);
		//   Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.note, "Hey der Player läuft :)", System.currentTimeMillis());
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		//  Set the info for the views that show in the notification panel.
		//notification.
        notification.setLatestEventInfo(this, getText(R.string.app_name), "Hier ist ein Text", contentIntent);
		//    Send the notification.
		//   We use a layout id because it is a unique number.  We use it later to cancel.
        nm.notify(R.string.app_name, notification);
	}

	public NotificationManager getNotificationManager(){
		return nm;
	}

	public void cancelNotification(){
		if(nm != null)
			nm.cancel(R.string.app_name); // Cancel the persistent notification.

	}
	
}

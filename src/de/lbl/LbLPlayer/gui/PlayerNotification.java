package de.lbl.LbLPlayer.gui;

import android.app.*;
import android.content.*;
import android.support.v4.app.*;
import android.widget.*;

public class PlayerNotification extends NotificationCompat
{
	/**
	 * PRIVATE ATTRIBUTES
	 */
	// log
	private static final String         TAG             = PlayerNotification.class.getSimpleName();
	// notification
	private NotificationManager         _notificationManager;
	private NotificationCompat.Builder  _builder        = null;
	private Notification                _notification;
	// data
	public static final int             NOTIFICATION_ID = 1;
	private Class                       _notifActivity;
	private Context                     _context;
	private String                      _notifTitle;
	private String                      _notifText;
	private int                         _notifLayout;

	public PlayerNotification(String _notifActivity, Context _context, String _notifTitle, String _notifText, int _notifLayout)
	{

		super();
		try
		{
			this._notifActivity = Class.forName(_notifActivity);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		this._context = _context;
		this._notifTitle = _notifTitle;
		this._notifText = _notifText;
		this._notifLayout = _notifLayout;
		// manager
		_notificationManager = (NotificationManager)_context.getSystemService(Context.NOTIFICATION_SERVICE);
		// notif builder
		_builder = new NotificationCompat.Builder(_context);
		buildSimpleNotification();
	}

	private void buildSimpleNotification()
	{

		// notif intent
		final Intent notificationIntent = new Intent(_context, _notifActivity);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		// remote view
		RemoteViews contentView = new RemoteViews(_context.getPackageName(), _notifLayout);
		// pending intent
		final PendingIntent contentIntent = PendingIntent.getActivity(_context, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		_builder.setContentIntent(contentIntent).setContent(contentView).setOngoing(true).setWhen(System.currentTimeMillis()).setAutoCancel(false).setContentTitle(_notifTitle)
			.setContentText(_notifText);
		// notification build
		_notification = _builder.getNotification();
		_notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_FOREGROUND_SERVICE | Notification.FLAG_NO_CLEAR;
		_notificationManager.notify(NOTIFICATION_ID, _notification);
	}
}

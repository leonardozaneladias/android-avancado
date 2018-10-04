package br.com.caelum.leozd.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import br.com.caelum.leozd.R;
import br.com.caelum.leozd.activity.CarrinhoActivity;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String message = data.get("message");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                123,
                new Intent(getApplicationContext(), CarrinhoActivity.class),
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.casadocodigo)
                .setContentTitle("Nova Notificação")
                .setContentText(message)
                .setColor(Color.WHITE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        Context context = getApplicationContext();
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(123, notification);

    }
}

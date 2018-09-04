package studioproject.first.my.locationmessageapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.gsm.SmsManager;
import android.util.Log;

public class MessageReceiver extends BroadcastReceiver
{
    String address;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle b=intent.getExtras();
        Object obj[]=(Object[])b.get("pdus");
        for(int i=0;i<obj.length;i++){
            SmsMessage message=SmsMessage.createFromPdu((byte[])obj[i]);
            address=message.getOriginatingAddress();
            String msg=message.getMessageBody();
        }
        SmsManager manager=SmsManager.getDefault();
        manager.sendTextMessage(address,null,GPSTracker.getAddress(),null,null);

    }
}

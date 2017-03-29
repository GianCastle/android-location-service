package com.example.castle.opensource;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.rabbitmq.client.*;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;


public class PhoneStateReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(final Context context, Intent intent) {

        try {
            System.out.println("Receiver start");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Toast.makeText(context,"Incoming phone call",Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"The phone number is" + incomingNumber,Toast.LENGTH_SHORT).show();
                        try {
                            SmartLocation.with(context).location()
                                    .oneFix()
                                    .start( new OnLocationUpdatedListener() {
                                        Locate loc = new Locate();

                                        @Override
                                        public void onLocationUpdated(Location location) {
                                            System.out.println(Double.toString(location.getLatitude()) + Double.toString(location.getLongitude()));
                                            loc.sendToMessageBroker(location.getLatitude(), location.getLongitude());
                                        }

                                    });

                        } catch(Exception e) {
                            e.printStackTrace();
                        }

            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                Toast.makeText(context,"Call Received State",Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

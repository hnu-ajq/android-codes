package com.example.GhostBuster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class check extends AppCompatActivity {
    private Button check_button;
    private Button back_button;
    private EditText check_editText;
    private EditText wifiinfo_editText;
    private Activity activity;
    public static String GetLocalIPAddress()
    {
        String ip = "";
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                     enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address)
                    {
// Inetaddressutils.isIPv4Address(inetAddress.getHostAddress())) { API 19 以前可用
                        ip = inetAddress.getHostAddress().toString();
                        break;
                    }
                }
                if (!TextUtils.isEmpty(ip)) break;
            }
        }
        catch (Exception e)
        {
            ip = "";
        }
        return ip;
    }
    //获取当前设备所连接wifi信息
    public static String  GetMyWifiInfo(Context context)
    {
        String str = "";
        WifiManager mWifi = (WifiManager) context.getSystemService(WIFI_SERVICE);
        if (mWifi.isWifiEnabled()) {
            // List<ScanResult> scanResults = mWifi.getScanResults();  //getScanResults() 扫描到的当前设备的WiFi列表
            WifiInfo wifiInfo = mWifi.getConnectionInfo();
            String netName = wifiInfo.getSSID(); //获取被连接网络的名称
            String netMac =  wifiInfo.getBSSID(); //获取被连接网络的mac地址
            String localMac = wifiInfo.getMacAddress();// 获得本机的MAC地址
            int loalIP = wifiInfo.getIpAddress();
            int level = wifiInfo.getRssi();
            wifiInfo.getLinkSpeed();
            str = wifiInfo.toString();
        }
        return  str;
    }
    // 根据ip 网段去 发送arp 请求
    private void SendDataToLoacl()
    {
        //局域网内存在的ip集合
        final List<String> ipList = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();

        //获取本机所在的局域网地址
        String hostIP = GetLocalIPAddress();
        int lastIndexOf = hostIP.lastIndexOf(".");
        final String substring = hostIP.substring(0, lastIndexOf + 1);
        //创建线程池
        //        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramPacket dp = new DatagramPacket(new byte[0], 0, 0);
                DatagramSocket socket;
                try {
                    socket = new DatagramSocket();
                    int position = 2;
                    while (position < 255)
                    {
                        Log.e("Scanner ", "run: udp-" + substring + position);
                        dp.setAddress(InetAddress.getByName(substring + String.valueOf(position)));
                        socket.send(dp);
                        position++;
                        if (position == 125)
                        {//分两段掉包，一次性发的话，达到236左右，会耗时3秒左右再往下发
                            socket.close();
                            socket = new DatagramSocket();
                        }
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //读取arp表中的信息并与数据库进行比对
    private void ReadArp()
    {
        int macfoundcount=0;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("/proc/net/arp"));
            String line = "";
            String ip = "";
            String flag = "";
            String mac = "";
            wifiinfo_editText.setText("");
            if (br.readLine() == null)
            {
                Log.e("scanner", "readArp: null");
            }
            while ((line = br.readLine()) != null)
            {
                line = line.trim();
                if (line.length() < 63) continue;
                if (line.toUpperCase(Locale.US).contains("IP")) continue;
                ip = line.substring(0, 17).trim();
                flag = line.substring(29, 32).trim();
                mac = line.substring(41, 63).trim();
                FindMac fdmac1=new FindMac();
                if(SQLService.MatchMac(mac.substring(0,8)))
                {
                    macfoundcount++;
                }
                if (mac.contains("00:00:00:00:00:00")) continue;
                Log.e("scanner", "readArp: mac= " + mac + " ; ip= " + ip + " ;flag= " + flag);
                wifiinfo_editText.append("\nip:" + ip + "\tmac:" + mac);
            }
            br.close();
        } catch (Exception ignored) {
        }
        if(macfoundcount>0)
        {
            wifiinfo_editText.append(String.valueOf("\n已检测到 "+macfoundcount+" 个无线摄像头！"));
        }
        else if(macfoundcount==0)
        {
            wifiinfo_editText.append(String.valueOf("\n未检测到无线摄像头。"));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        check_button=(Button) findViewById(R.id.check_button);
        back_button=(Button) findViewById(R.id.check_backbutton);
        check_editText=(EditText)findViewById(R.id.check_result);
        wifiinfo_editText=(EditText)findViewById(R.id.check_wifi);
        check_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                check_editText.setText(GetLocalIPAddress());
                //wifiinfo_editText.setText(getMyWifiInfo(getApplicationContext()));
                SendDataToLoacl();
                ReadArp();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(check.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
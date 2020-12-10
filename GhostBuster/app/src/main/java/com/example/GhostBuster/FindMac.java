package com.example.GhostBuster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FindMac
{
    public static Boolean FindReturnMac(String mac)
    {
        mac=mac.substring(0,8);
        boolean mark=false;
        File file = new File("app/src/main/res/raw/allmac.txt"); //在文件中查找
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file)); //构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null) //使用readLine方法，一次读一行
            {
                //System.out.println("\""+s+"\""+",");
                if(s.equals(mac))
                {
                    mark=true;
                    break;
                }
            }
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
//        if(mac.equals("64:90:c1"))
//        {
//            mark=true;
//        }
        AllMacAdress alm=new AllMacAdress();
        int l_alm=alm.m_macadress.length;
        for(int i=0;i<l_alm;i++)
        {
            if(alm.m_macadress[i].equals(mac))
            {
                mark=true;
                break;
            }
        }
        return mark;
    }
//    public static void main(String[] args)
//    {
//        System.out.println(FindReturnMac("ahahahaaahahahaasd"));
//    }
}
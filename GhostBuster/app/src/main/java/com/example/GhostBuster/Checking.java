package com.example.GhostBuster;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
public class Checking {
    public String RandCode()
    {
        int m_rand=(int)((Math.random()*9+1)*100000);//生成6位的随机验证码
        return String.valueOf(m_rand);
    }
    public static String m_Fcode="";
    public void Sendcode(String m_tnum)
    {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GHypS5UhkDCDxMWAyS1", "RG7lki6yd0vXbUNvrVjsZqrjEogb8y");
        IAcsClient client = new DefaultAcsClient(profile);
        String m_temp=new Checking().RandCode();
        m_Fcode=m_temp;
        String m_s="{\"code\":\""+m_temp+"\"}";
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", m_tnum);
        request.putQueryParameter("SignName", "中小学试卷生成系统");
        request.putQueryParameter("TemplateCode", "SMS_204111214");
        request.putQueryParameter("TemplateParam", m_s);//获取正确格式
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}

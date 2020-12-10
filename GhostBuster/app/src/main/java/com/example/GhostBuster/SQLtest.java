package com.example.GhostBuster;

public class SQLtest {
    public static void main(String[] args)
    {
        if (SQLService.SignUp("11111111122","dwadw"))
            System.out.println('1');
        else {
            System.out.println('0');
        };
//        System.out.println("返回结果："+UserService.SignIn("13218666803"));
    }
}

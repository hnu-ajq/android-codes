1.三个界面xml和java对应如下:
MainActivity:初始界面,按下注册跳转到RigsterAcitivty;按下登录跳转到CheckActivity
RigsterAcitivty:按下跳转到CheckActivity
注意:皆为无条件跳转
2.添加进整个项目的操作
先把三个界面的xml和java添加到项目中,其中变量名要自己改改
注意:drawable中的文件
    1)hehe.png不用改,变量名一样
    2)btn_1,需新建drawable resorce file,root element选shape
    3)btn_2,同样新建,然后使用默认的selector,建好之后复制粘贴代码即可
3.在你的电脑上如果出现button不能变色的情况
打开res目录下的AndroidManifest
ctrl+点击进入android:theme="@style/Theme.MyApplication">
你是夜间模式就选夜间,白天模式就选白天
在themes.xml中把<style name="Theme.MyApplication" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
改为<style name="Theme.MyApplication" parent="Theme.MaterialComponents.DayNight.DarkActionBar.Bridge">加一个.Bridge


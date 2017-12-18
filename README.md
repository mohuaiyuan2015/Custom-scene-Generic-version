# Custom-scene-Generic-version
订制场景：通用版本

客户端  //mohuaiyuan 20171215

-------------------------------------
mohuaiyuan 20171215 

1.自定义对话 ，界面已经完成；

2.自定义对话，动作部分 ，包括动作以及动作下发时间点，已经完成；

备注:
基本思路，通过蓝牙发送数据给机器人，手机作为客户端，机器人作为服务器端；
数据的格式，数据有N种；
数据基本格式，
type:function（功能）、dance（舞蹈）、basic action（基本动作）、custom dialog（自定义对话）	
data:
	1)function:function name	, eg:close chat (关闭聊天)
	2)dance:dance action id 	, eg：123 （舞蹈：小苹果）
	3)basic action:basic action id 	, eg:41 (基本动作：弯腰)
	4)custom dialog:question and answer and/or action and/or expression ,
	eg:
	question:你是谁？
	answer:我是个机器人，可以叫我小图
	action:41
	expression:欢乐
	注：custom dialog(自定义对话)仅仅需要发送后面3部分的数据给机器人。
---------------------------------------------------------------------
mohuaiyuan 20171216 

1.舞蹈： 界面完成 ，功能完成

2.基本动作： 界面完成 ，功能完成

---------------------------------------------------------------------





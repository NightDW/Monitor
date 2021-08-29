一个简易的可以通过手机浏览器来操作电脑的应用。

首先，在电脑上运行本程序，并确保手机和电脑在同一个局域网下。
然后，用手机浏览器访问http://{ws.ip}:{server.port}，进入控制页面。

控制页面的主要提供了两个功能：
1. 页面每隔一段时间就会收到服务器发送过来的屏幕截图并显示出来；
2. 可以通过点击页面的图片来让服务器去点击相应的位置，从而通过手机来操作电脑。

程序所需的所有配置都已经写在application.properties文件中了：
ws.ip：websocket服务器的ip地址，也就是电脑的ip地址；
ws.port：websocket服务器的端口；
ws.path：将http协议升级为websocket协议时的url地址；
screen.refresh-millis：截屏发送的频率，即每隔多少毫秒向客户端发送一次截图；
screen.image-format：向客户端发送截图时，将截图转成指定类型的格式再发送；
screen.fast-refresh-millis：快速模式下，截屏每隔多少毫秒就会发送一次；
screen.fast-refresh-times：快速模式下，截屏发送次数达到该值后，将回到正常模式。

另外，程序需要以管理员身份运行，否则某些窗口可能无法点击，导致用户体验不好。
你可以先将项目打成Jar包，然后在命令提示符(管理员)中执行java -jar命令来让程序以管理员身份运行。

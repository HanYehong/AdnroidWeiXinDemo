# AdnroidWeiXinDemo
Android模拟微信APP，包括多媒体、数据共享、SQLite存储、动画、Socket网络通信

直接导入项目，但版本可能会不同，我的gradle是4.6版本的
如果因为版本号错误运行部起来，可以直接修改项目中的buld.gradle文件中的版本号就行了

除了网络部分，其他的都可以直接在手机/模拟器上进行测试
若想运行网络部分（Socket聊天）则要先运行服务器部分，在包app\src\main\java\com\example\administrator\weixin\socket下的SocketServer
直接右键选择run“SocketServer.main”就行了
服务器运行过后，可以在项目中打开聊天界面，进行文字发送

注意：
1. 由于时间问题，代码可能有冗余部分，而且有些地方不规范，对于两个客户端进行通信的部分，为了方便测试我是直接将对方的userId写死了，比如A要给B发消息，则A要给服务器发送的东西有【要发送的内容】【接收人的ID】，在这里我把“接收人的ID”写死了，A发消息就只能发给B，B也是一样的只能发给A。这里只需要将代码中[发送消息]部分的[userID]改为[动态生成]的即可。
2. 该项目包含登录部分，数据都存储在Android内嵌的SQLite数据库中，该数据库包含两张表：
               （1）user表：user_id，password，nickname  [用户ID，密码，昵称]
               （2）friends表：user_id，friend_id，beizhu  [用户ID，另一个用户ID，备注]
               在user表中包含两条数据（两个用户）：用户名 gly520 密码 123456，用户名 happy123 密码 123456，用这两个账号进行登录即可
3. 部分手机可能部分代码不兼容，比如调用本地相册进行裁剪后显示头像，我的Nubia Z17的Android 7.1就可以，但是小米5的Android 8.0不行，好像是裁剪后无法正确显示头像

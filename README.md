#NewSmartLab
1.环境：
	java-v：jdk1.7
	web服务器：tomacat8.0
	数据库访问层：jpa
	视图处理层：spring
	sms服务：淘宝大鱼
	email服务：sendcloud
	json处理：Google开发的Gson
	项目管理：git中国    url：https://git.oschina.net/nirvana123/NewSmartLab
	项目jar包管理：maven
	服务器：腾讯云 ubuntu14
	数据库：mysql5.0以上
2.项目结构
	src
		main 项目主要代码
			java 源代码
				app.config项目运行配置
				app.controller视图层MVC架构中的C层 用于路径定向页面
				app.exception 项目自定义异常
				app.interceptor接口访问拦截器
				app.util 工具类文件夹
				app.validate 接口访问校验类文件夹
				app.vo 视图展示类
				bll.bo 项目服务层接口
				bll.service 服务层实现
				dal.api 数据库访问层实现
				dal.po 项目的持久化对象
			resource 资源文件
				接口文档
				datasource.properties数据库配置文件
				spring-jpa.xml spring和jpa整合配置文件
				spring-mvc.xml springmvc框架配置文件
		test 测试类文件夹
	pom.xml  maven jar管理文件
	.gitignore git版本控制文件
	readme.md markdown文件 介绍文档
	
3.项目安装(假设环境安装完毕)
	1.git clone https://git.oschina.net/nirvana123/NewSmartLab.git
	2.打包成war
	3.cd /opt/apache-tomcat版本号略/webapps/
	3.copy to 服务器下 /opt/apache-tomcat版本号略/webapps/
	4.cd /opt/apache-tomcat版本号略/bin/
	5. ./startdown.sh

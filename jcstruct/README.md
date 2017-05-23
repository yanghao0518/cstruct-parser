# 介绍
```java
描述：基于配置解决java与C结构体解析与反解析的灵活方案。
```
# 功能说明
```java
1、基于spring框架开发
2、对于结构体定义采用可视化XML配置，便于维护与调试，防止协议多次修改带来的麻烦。
3、所有关健解析操作都基于接口开发，有默认实现，也可以用于自定义实现，能满足所有特殊业务解析需求。
4、配置简单，使用方便、易上手。
```
# 使用实例
```java
1、全局配置
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
  xmlns:cjava="http://www.haoyu.com/schema/cjava"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.haoyu.com/schema/cjava
        http://www.haoyu.com/schema/cjava/cjava-1.0.xsd
		"
	default-lazy-init="false">

 <!-- 对于ByteArray类型的特殊处理，该类需要继承com.haoyu.module.jcstruct.opt.AbstractJFieldOpt-->	
	<bean id="byteArrayJFieldOpt" class="com.haoyu.module.jcstruct.opt.ByteArrayJFieldOpt"></bean>
  
	<!-- 加入模板容器处理，默认实现类com.haoyu.module.jcstruct.template.DefaultTemplateContainer -->
	<cjava:templateContainer/>
  
  <!-- 解析管理类，默认com.haoyu.module.jcstruct.opt.DefaultOptManager，继承com.haoyu.module.jcstruct.opt.AbstractOptManager-->
	<cjava:optManager class="com.haoyu.module.jcstruct.opt.OptManagerForDebug">
   <!-- 对于ByteArray类型的解析的特殊处理->	
  <cjava:optManagerOne key="bytearray" value-ref="byteArrayJFieldOpt"/>
  </cjava:optManager>
  
  <!-- 调度中心配置，该类主要用于处理不同协议的业务处理，并返回处理结果。默认实现类:com.haoyu.module.jcstruct.dispatch.DefaultDispatchCenterServiceImpl，自定义实现需要继承AbstractDispatchCenterService-->	
	<cjava:dispatchCenterService/>
  
  <!-- 启动加载完成后的自定义刷新管理类，用于加载完成后从spring集合里取出指定类型或者指定KEY的对象，具体刷新类需要实现RefreshService接口，默认实现类:com.haoyu.module.jcstruct.refresh.RefreshServiceManager-->
	<cjava:refreshManager/>
	
  <!-- 测试类的包路径加入到spring-->
	<context:component-scan base-package="com.haoyu.module.jcstruct.test" />
	
  <!-- 解析的辅助类加入到spring-->
	<context:component-scan base-package="com.haoyu.module.jcstruct.resolve" />

  <!-- 启动检查字段-->
	<cjava:check/> 

  <!-- 接收C结构体定义XML-->
  <import resource="spring-cjava-fc.xml"/>
    
  <!-- 返回C结构体的协议定义XML->
  <import resource="spring-cjava-tc.xml"/>
	
</beans>

说明：
spring头文件需要加入
xmlns:cjava="http://www.haoyu.com/schema/cjava"
xsi:schemaLocation中需要加入
http://www.haoyu.com/schema/cjava
http://www.haoyu.com/schema/cjava/cjava-1.0.xsd
对自定义cjava标签支持。
```
```java
2、结构体协议配置(举例)
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:cjava="http://www.haoyu.com/schema/cjava"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.haoyu.com/schema/cjava
        http://www.haoyu.com/schema/cjava/cjava-1.0.xsd
		"
	default-lazy-init="false">
	
    <!-- 协议头-->
	<cjava:fch key="Package_Type"><!-- key用来定位协议ID的标识 -->
		<cjava:field name="Constant_Up" type="ushort" desc="2字节，前导字符，固定为0XAAAA，表示为上行数据包-" />
		<cjava:field name="Gateway_Id" type="unsigendint" desc="4字节，网关id号" />
		<cjava:field name="Package_Type" type="char" desc="具体根据每个协议ID决定" />
	</cjava:fch>
	
	
	<cjava:fct name="ACK" id="1" desc="通讯确认协议号0x01">
		<cjava:field name="Version" type="char" desc="1字节,网关软件版本  " />
		<cjava:field name="Package_length" type="ushort" desc="2字节数据包长度，单位字节，从Package_length（包含）至BCC（包含） " />
		<cjava:field name="Package_Number" type="ushort" desc="字节，包序号，copy接收到的上行数据包包序号 " />
		<cjava:field name="command_properties" type="char" desc="
		 =0x01,表示传感节点测试通信 
		=0x02，数据接收正确
		=0x03，数据接收错误，要求重发
        =0x04，数据接收错误，不要求重发
        =0X05，终止传输（多包传输时使用）。
        =0X06，接收正确，命令无法执行。"/>
		<cjava:field name="BCC" type="char" desc="1个字节，前面所有数据含前导，异或运算 " />
		<cjava:field name="Constant_Up_Stop" type="ushort" desc="2字节，固定为0XAA55，表示为上行数据包结束 " />
	</cjava:fct>
	
	<cjava:fct name="HeartBeat" id="5" desc="心跳 协议号0x05">
	    <cjava:field name="Package_Number" type="ushort" desc="字节，包序号，copy接收到的上行数据包包序号 " />
	    <cjava:field name="BCC" type="char" desc="1个字节，前面所有数据含前导，异或运算 " />
		<cjava:field name="Constant_Up_Stop" type="ushort" desc="2字节，固定为0XAA55，表示为上行数据包结束 " />
	</cjava:fct>
	
	<cjava:fct name="Character" id="2" desc="特征值  0x02">
	
	  <cjava:field name="Sensor_Id" type="unsigendint" desc="4字节，传感器ID  " />
		<cjava:field name="Version" type="char" desc="1字节,网关软件版本  " />
		<cjava:field name="Package_length" type="ushort" desc="2字节数据包长度，单位字节，从Package_length（包含）至BCC（包含） " />
		<cjava:field name="Package_Number" type="ushort" desc="字节，包序号，copy接收到的上行数据包包序号 " />
		<cjava:field name="command_properties" type="char" desc="
		 1字节，=0x01,表示传感器霍尔触发上传数据=0x02，表示传感器定时上传数据"/>
		 
		 
		 <cjava:field name="Recver_Riss" type="char" desc="接收到这包数据的场强 " />
		 <cjava:field name="Battery" type="char" desc="电池电量,百分数  " />
		 <cjava:field name="Temperature" type="short" realValueRule="{*}{0.01}" desc="2字节，温度值,真实值等于该值*0.01" />
		 <cjava:field name="Character_Attribute" type="char" desc="1字节，数据类型，即后面一组数据的类型。1加速度 2速度  " />
		 <cjava:field name="Data_coefficient" type="ushort" desc="系数, 计算方法见5.11中Data_ coefficient字段  " />
		 
 		 <cjava:field name="Data_x_Rms" type="ushort" desc="2字节，x方向RMS值 " />
 		 <cjava:field name="Data_x_PP" type="ushort" desc="2字节，x方向峰峰值" />
 		 <cjava:field name="Data_x_P" type="ushort" desc="2字节，x方向峰值 " />
 		 
 		 <cjava:field name="Data_y_Rms" type="ushort" desc="2字节，y方向RMS值 " />
 		 <cjava:field name="Data_y_PP" type="ushort" desc="2字节，y方向峰峰值" />
 		 <cjava:field name="Data_y_P" type="ushort" desc="2字节，y方向峰值 " />
 
 		 <cjava:field name="Data_z_Rms" type="ushort" desc="2字节，z方向RMS值 " />
 		 <cjava:field name="Data_z_PP" type="ushort" desc="2字节，z方向峰峰值" />
 		 <cjava:field name="Data_z_P" type="ushort" desc="2字节，z方向峰值 " />
		 
		<cjava:field name="BCC" type="char" desc="1个字节，前面所有数据含前导，异或运算 " />
		<cjava:field name="Constant_Up_Stop" type="ushort" desc="2字节，固定为0XAA55，表示为上行数据包结束 " />
		
	</cjava:fct>
	
	<cjava:fct name="Sensor" id="3" desc="传感器组网信息上传（0X03">
	  <cjava:field name="Sensor_Id" type="unsigendint" desc="4字节，传感器ID  " />
		<cjava:field name="Version" type="char" desc="1字节,网关软件版本  " />
		<cjava:field name="Package_length" type="ushort" desc="2字节数据包长度，单位字节，从Package_length（包含）至BCC（包含） " />
		<cjava:field name="Package_Number" type="ushort" desc="2字节，包序号，copy接收到的上行数据包包序号 " />
		<cjava:field name="command_properties" type="char" desc="
		1字节，=0x01,表示传感器霍尔触发上传数据=0x02，表示传感器定时上传数据"/>
		<cjava:field name="Recver_Riss" type="char" desc="接收到这包数据的场强 " />
		<cjava:field name="BCC" type="char" desc="1个字节，前面所有数据含前导，异或运算 " />
		<cjava:field name="Constant_Up_Stop" type="ushort" desc="2字节，固定为0XAA55，表示为上行数据包结束 " />
	</cjava:fct>
	
	<cjava:fct name="Wave" id="4" desc="波形数据 ">
	  <cjava:field name="Sensor_Id" type="unsigendint" desc="4字节，传感器ID  " />
		<cjava:field name="Version" type="char" desc="1字节,网关软件版本  " />
		<cjava:field name="Package_length" type="ushort" desc="2字节数据包长度，单位字节，从Package_length（包含）至BCC（包含） " />
		<cjava:field name="Package_Number" type="ushort" desc="2字节，包序号，copy接收到的上行数据包包序号 " />
		<cjava:field name="command_properties" type="char" desc="
		1字节，命令属性为0X04，表示传感节点申请波形上传
		1字节，命令属性为0X05，表示中间包
		1字节，命令属性为0X06，表示结束包
		"/>
		
		
		<cjava:branchs key="command_properties"><!-- 特殊情况，对于二级KEY定位的标识 -->
		
			<cjava:branch keyValue="4" desc="表示传感节点申请波形上传"> <!-- 二级KEY的分支协议-->
				<cjava:field name="Wave_attribute" type="char" desc="当前数据属性" />
				<cjava:field name="Wave_long" type="char" desc="波形长度=0  512点  =1  1024点 =2 	2048点	=3	4096点 =4	8192点" />
				<cjava:field name="Temperature" type="short" realValueRule="{*}{0.01}" desc="真实值等于该值*0.01" />
				<cjava:field name="Sample_Frequency" type="ushort"  desc="采样频率 " />
				<cjava:field name="Wave_coefficient" type="ushort"  desc="后续发送值*系数/100得到真实值 " />
			</cjava:branch>
			
			<cjava:branch keyValue="5" desc="波形结束包数据 "> <!-- 二级KEY的分支协议-->
				<cjava:field name="Wave_data" type="bytearray" realLengthRule="{Package_length}{-}{6}" desc="1024个数据，每包1024个数据，最后一包按实际剩余数据量 " />
			</cjava:branch>
			
			<cjava:branch keyValue="6" desc="波形结束包数据 "> <!-- 二级KEY的分支协议-->
			</cjava:branch>
			
		</cjava:branchs><!-- 二级KEY的分支结束-->

		<cjava:field name="BCC" type="char" desc="1个字节，前面所有数据含前导，异或运算 " />
		<cjava:field name="Constant_Up_Stop" type="ushort" desc="2字节，固定为0XAA55，表示为上行数据包结束 " />
	</cjava:fct>
	
</beans>
注：返回给C结构的协议类同参考包下面的spring-cjava-tc.xml
```
```java
3、自定义业务实现（举例，对特征值解析以后的处理）
package com.sg2k.dtu.handle;

import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.annotation.HandleMessageProtocol;
import com.haoyu.module.jcstruct.handle.HandleMessageService;
import com.haoyu.module.jcstruct.model.DefaultResponseResult;
import com.sg2k.consts.DefaultConsts;
import com.sg2k.dtu.cache.DataCache;
import com.sg2k.dtu.common.GatewayServerCodeType;
import com.sg2k.service.ReceiveDataService;

/**
 * 处理特征值显示
 * 
 * @author haoyu
 *
 */
//spring注解
@Service("handleCharacterMessage")
//待处理的协议注解，解析出数据后的主键KEY(协议号标识)与根据此注解id匹配，如果匹配成功，在此做协议的处理并返回处理结果
@HandleMessageProtocol(id = GatewayServerCodeType.Gateway_Server_Character)
//处理类必须要实现HandleMessageService接口
public class HandleCharacterMessage  implements HandleMessageService
{
	Logger LOG = Logger.getLogger(this.getClass());

	@Override
	public void handle(JSONObject message, DefaultResponseResult responseResult)
	{

		LOG.info("接收特征值信息:" + message);
		// 更新特征值公共缓存
		DataCache.getInstance().setPubCharacter(message);
		// 往趋势图加入数据
		try {
			String Sensor_Id = message.getLong("Sensor_Id").toString();
			// 获取温度
			float Temperature = message.getFloatValue("Temperature");
			// 获取电量
			int Battery = message.getIntValue("Battery");
			// 接收端数据= 数据/Data_coefficient
			float Data_coefficient = message.getIntValue("Data_coefficient");

			float Data_x_Rms = message.getIntValue("Data_x_Rms") / Data_coefficient;
			float Data_x_PP = message.getIntValue("Data_x_PP") / Data_coefficient;
			float Data_x_P = message.getIntValue("Data_x_P") / Data_coefficient;
			float Data_y_Rms = message.getIntValue("Data_y_Rms") / Data_coefficient;
			float Data_y_PP = message.getIntValue("Data_y_PP") / Data_coefficient;
			float Data_y_P = message.getIntValue("Data_y_P") / Data_coefficient;
			float Data_z_Rms = message.getIntValue("Data_z_Rms") / Data_coefficient;
			float Data_z_PP = message.getIntValue("Data_z_PP") / Data_coefficient;
			float Data_z_P = message.getIntValue("Data_z_P") / Data_coefficient;

			JSONObject receiveData = new JSONObject();
			receiveData.put("temperature", Temperature);
			receiveData.put("battery", Battery);
			// receiveData.put("datatime", new Date());
			receiveData.put("datatime", System.currentTimeMillis());
			LOG.info("特征值上传时间:mills:" + receiveData.get("datatime") + "->times:" + new Date());
			// 用于判断是否为速度、加速度
			int type = message.getIntValue("Character_Attribute");
			if (type == DefaultConsts.DATA_TYPE_ADD_SPEED) {
				receiveData.put("dataType", DefaultConsts.DATA_TYPE_ADD_SPEED);
				receiveData.put("xValue", Data_x_P);
				receiveData.put("yValue", Data_y_P);
				receiveData.put("zValue", Data_z_P);
			} else {
				// 速度
				receiveData.put("dataType", DefaultConsts.DATA_TYPE);
				receiveData.put("xValue", Data_x_Rms);
				receiveData.put("yValue", Data_y_Rms);
				receiveData.put("zValue", Data_z_Rms);
			}
			// 调用接口
			//receiveDataService.receiveFromSimulative(ReceiveDataService.HIS_DATA, Sensor_Id, receiveData);
			buildDefaultSuccess(message, responseResult);
		} catch (Exception e) {
			LOG.error("处理接收后的特征值信息接口异常:" + e.getMessage(), e);
			buildDefaultFail(message, responseResult);
		}
	}
  
  public void buildDefault(JSONObject message, DefaultResponseResult responseResult, RtnCommandProperties commandProperties)
	{
		// 发送确认包
		JSONObject config = new JSONObject();
		if (!message.containsKey("Package_Number") || null == message.get("Package_Number")) {
			config.put("Package_Number", DEFAULT_PACKAGE_NUM);
		} else {
			config.put("Package_Number", message.get("Package_Number"));
		}
		config.put("Gateway_Id", message.get("Gateway_Id"));
		config.put("command_properties", commandProperties.ID);
		responseResult.buildReturnData(config).buildId(RtnCodeType.Server_Gateway_ACK.ID);
	}

	public void buildDefaultSuccess(JSONObject message, DefaultResponseResult responseResult)
	{
		// 发送确认包
		buildDefault(message, responseResult, RtnCommandProperties.SUCCESS);
	}

	public void buildDefaultFail(JSONObject message, DefaultResponseResult responseResult)
	{
		buildDefault(message, responseResult, RtnCommandProperties.ERROR_NOT_RETRY);
	}

	public void buildDefaultRetry(JSONObject message, DefaultResponseResult responseResult)
	{
		buildDefault(message, responseResult, RtnCommandProperties.ERROR_AND_RETRY);
	}

}

```

```java
4、测试代码
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-cjava.xml")
public class AppTest
{

	@Autowired
	private TemplateContainer templateContainer;

	@Autowired
	private OptManager optManager;

	@Autowired
	private DispatchCenterService dispatchCenterService;

	@Autowired
	private DefaultResolve defaultResolve;

  //测试解析特征值，协议号0x02
	private void testCharacterResolve() throws DtuMessageException, IOException
	{
		byte[] data = new byte[] {-86,-86,-3,79,-6,-119,2,18,-85,-51,-17,1,0,31,101,-61,3,83,0,10,-26,2,0,10,1,87,6,69,3,-80,0,-18,3,98,1,-59,1,-114,7,66,4,104,31,-86,85};
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);
		System.out.println(json.getId() + "->" + json.getResult());
	}
	
   //测试解析波形，协议号0x04
	private void testWaveResolve() throws DtuMessageException, IOException
	{
		//申请上传波形
		byte[] data = new byte[]{-86,-86,-3,79,-6,-119,4,18,-85,-51,-17,1,0,14,102,72,4,2,1,10,-26,0,0,0,100,-16,-86,85};
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);
		System.out.println(json.getId() + "->" + json.getResult());
		//中间波形
	  data = new byte[] {-86,-86,-3,79,-6,-119,4,18,-85,-51,-17,1,4,6,102,73,5,-17,-61,-18,-5,-10,-71,4,-94,16,30,22,14,22,110,19,119,15,67,6,111,-1,85,-4,72,-4,89,5,64,13,-15,17,-89,13,-29,6,66,-2,-55,-10,59,-17,-103,-30,108,-38,-22,-33,29,-19,-62,-3,42,8,94,6,-13,5,-79,4,-78,2,-50,-6,-127,-14,-21,-16,114,-6,46,7,56,16,62,18,93,13,-3,8,-15,1,66,-6,-76,0,46,-3,-57,-5,-17,2,40,13,-116,21,-63,23,-82,18,19,12,49,6,57,-4,30,-6,13,-10,35,-8,-37,3,97,11,118,14,-83,9,54,2,122,-6,-83,-13,64,-20,-8,-27,-7,-28,14,-22,-64,-7,83,5,114,13,75,13,-11,12,-39,10,1,3,121,-7,74,-11,-37,-11,-20,-1,-35,10,-96,16,123,14,-8,8,-117,2,3,-7,-24,-13,-54,-25,-37,-34,73,-32,72,-19,69,-3,88,8,-16,8,-75,5,110,4,56,2,-83,-5,1,-15,89,-19,56,-11,117,3,-96,13,-25,18,114,14,-39,10,-91,3,-121,-4,-78,-6,-22,-8,-118,-10,101,-3,39,9,-96,19,-75,24,35,21,-46,17,15,11,-84,2,47,0,-125,-3,91,-3,-90,6,58,14,76,17,68,12,-102,4,-78,-4,-53,-12,-87,-19,-30,-29,-50,-33,50,-28,91,-14,-34,0,88,9,-51,8,63,7,49,5,103,2,20,-8,-4,-13,40,-14,21,-5,-57,7,-9,15,-5,17,6,12,15,6,-125,-2,-88,-8,91,-3,-97,-7,12,-9,-79,-2,-40,10,-84,18,-29,19,-19,13,-79,7,-12,2,-94,-8,-28,-11,70,-15,11,-11,31,0,-20,9,-49,13,-32,8,-61,2,-15,-5,98,-12,78,-18,80,-22,-105,-23,-123,-16,-128,-2,-113,10,21,17,11,17,-112,15,-65,12,78,4,-104,-7,-12,-10,53,-10,110,0,43,10,109,15,-39,13,-26,7,55,0,-108,-8,125,-14,70,-27,-47,-36,-95,-33,52,-20,-29,-3,3,8,-106,8,19,4,-2,3,-82,2,35,-6,103,-15,22,-19,34,-11,-71,3,-63,13,-12,18,102,14,-54,10,-107,3,-94,-3,0,-1,-93,-3,-121,-5,1,0,-59,12,78,21,-91,25,24,21,-77,16,55,10,121,0,-97,-2,-69,-5,-127,-4,49,4,-21,12,-2,15,-10,11,75,3,114,-5,-18,-13,-13,-19,80,-29,31,-34,-58,-28,32,-14,-37,0,123,9,-22,8,51,6,-56,4,-65,1,104,-8,-113,-14,-44,-15,-57,-5,-86,8,2,16,47,17,38,11,-15,6,44,-2,125,-8,31,-4,55,-9,10,-11,-62,-3,100,9,-85,18,85,19,-100,13,94,7,-8,2,-63,-7,84,-14,-65,-19,127,-15,-62,-2,102,8,98,13,66,8,-13,3,-122,-4,87,-11,-92,-17,-46,-20,105,-21,69,-14,67,0,21,11,-112,18,97,18,-89,16,-90,13,9,5,-124,-6,-50,-10,-23,-10,-27,0,121,10,-35,16,116,14,-70,8,8,1,102,-7,50,-14,-41,-25,116,-34,59,-32,50,-19,59,-3,62,8,-81,8,-82,5,1,3,53,1,-111,-7,-16,-16,-12,-20,106,-12,106,2,-115,12,-27,17,-81,14,41,9,-9,3,28,-4,-77,-5,118,-8,-9,-10,-38,-3,54,9,-84,19,-22,24,-128,22,-39,18,109,13,37,3,-93,-3,2,-6,12,-6,-65,3,-64,12,97,15,-40,11,-118,4,12,-4,-8,-11,55,-18,-42,-30,13,-37,-115,-32,86,-17,98,-2,59,8,-46,7,10,5,90,4,57,2,8,-7,-20,-14,49,-16,7,-7,-13,7,20,15,-7,18,33,13,-107,8,79,0,-26,-6,-110,0,69,-4,50,-6,124,1,39,12,-46,21,111,23,81,17,106,11,-116,5,-93,-5,-104,-6,25,-10,-109,-7,56,3,-66,12,41,15,108,9,-6,3,1,-5,53,-13,-76,-19,89,-25,-81,-26,20,-20,120,-6,-71,6,-104,14,55,13,-10,12,-121,9,109,3,30,-8,-14,-12,111,-12,83,-2,74,9,97,15,-7,15,24,8,-43,2,118,-6,69,-12,19,-23,24,-33,-99,-31,53,-19,-20,-3,-26,9,103,10,3,6,29,3,-5,1,-40,-6,57,-15,1,-20,8,-13,-122,1,-64,12,-121,17,-67,13,-24,9,-110,2,-74,-4,27,-6,-27,-8,-128,-10,43,-4,86,8,-104,19,45,24,65,23,45,19,26,14,12,4,-102,-3,98,-6,4,-6,108,3,102,12,75,15,-17,12,46,4,-85,-3,-120,-11,-81,-17,99,-31,-16,-38,-85,-33,60,-18,43,-3,117,8,-121,6,-28,5,49,4,58,2,95,-6,124,-14,36,-17,-115,-7,55,6,-112,15,-57,18,76,14,54,9,83,1,-53,-5,89,1,71,-3,120,-5,109,1,-57,13,97,21,-17,23,-46,18,56,12,95,6,126,-4,29,-5,5,-8,9,-6,-111,4,-59,12,-112,15,125,9,-50,2,-56,-6,-21,-13,58,-20,-33,-27,-117,-29,103,-23,-42,-8,82,4,-77,12,-50,12,-52,11,-88,9,4,2,-23,-8,-41,-12,1,-12,4,-2,49,9,124,16,8,15,21,8,-26,2,-87,-6,76,-37,-86,85};
		
		 json = defaultResolve.resolve(data, null);
		 System.out.println(json.getId() + "->" + json.getResult());
		//最后一组上传波形
		data = new byte[]{-86,-86,-3,79,-6,-119,4,18,-85,-51,-17,1,0,6,102,83,6,106,-86,85};
		json = defaultResolve.resolve(data, null);
		System.out.println(json.getId() + "->" + json.getResult());

	}
	
   //测试解析确认信息协议，协议号0x01
	private void testACKResolve() throws DtuMessageException, IOException
	{
		byte[] data = new byte[] {-86,-86,-3,79,-6,-119,2,18,-85,-51,-17,1,0,31,101,-61,3,83,0,10,-26,2,0,10,1,87,6,69,3,-80,0,-18,3,98,1,-59,1,-114,7,66,4,104,31,-86,85};
		
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);

		System.out.println(json.getId() + "->" + json.getResult());

	}
	
	//测试解析心跳信息协议，协议号0x05
	private void testHeartBeatResolve() throws DtuMessageException, IOException
	{
		byte[] data = new byte[] {-86,-86,-3,79,-6,-119,2,18,-85,-51,-17,1,0,31,101,-61,3,83,0,10,-26,2,0,10,1,87,6,69,3,-80,0,-18,3,98,1,-59,1,-114,7,66,4,104,31,-86,85};
		
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);

		System.out.println(json.getId() + "->" + json.getResult());

	}
	
  //测试解析组网上传协议，协议号0x03
	private void testSensorResolve() throws DtuMessageException, IOException
	{
		byte[] data = new byte[] {-86,-86,-3,79,-6,-119,2,18,-85,-51,-17,1,0,31,101,-61,3,83,0,10,-26,2,0,10,1,87,6,69,3,-80,0,-18,3,98,1,-59,1,-114,7,66,4,104,31,-86,85};
		
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);

		System.out.println(json.getId() + "->" + json.getResult());

	}
	
  //测试反解析
	private void testUnResolve() throws DtuMessageException, IOException{
		//反解析
		JSONObject ack = new JSONObject();
		ack.put("Package_Type", 1);
		ack.put("Gateway_Id", 4249877129L);
		ack.put("Package_Number", 0);
		ack.put("command_properties", 2);
		ack.put("BCC", 1);
		
		byte[] data = defaultResolve.unresolve("1",ack);//参数：协议号、协议属性对象。
		
		//[85, 85, -3, 79, -6, -119, 1, 0, 6, 0, 0, 2, 1, 85, -86]
		//打印16进制
		System.out.println(HexUtils.bytesToHexString(data));
		
	}
  
	 //测试反解析组网下传
	private void testUnResolveSensorConfig() throws DtuMessageException, IOException{
		
		String s = "{\"Gateway_Id\":\"4249877129\",\"Upload_Sensor_Type\":\"9\",\"Sensor_Id\":\"313249263\","
				+ "\"HP_Filter\":\"2\",\"X_Angle\":\"0\",\"Sample_Character_Time\":\"1\",\"Upload_Wave_Type\":\"113\","
				+ "\"Upload_Character_Time\":\"1\",\"Upload_Wave_Time\":\"2\",\"Wave_long\":\"1\",\"Sample_Frequency\":\"800\","
				+ "\"Temperature_alarm_diff\":\"35\",\"Temperature_alarm_HH\":\"80\",\"Temperature_alarm_Change\":\"5\","
				+ "\"Acc_X_alarm_HH\":\"1\",\"Acc_X_alarm_H\":\"0.8\",\"Acc_Y_alarm_HH\":\"1\",\"Acc_Y_alarm_H\":\"0.8\","
				+ "\"Acc_Z_alarm_HH\":\"1\",\"Acc_Z_alarm_H\":\"0.8\",\"Speed_X_alarm_HH\":\"1\",\"Speed_X_alarm_H\":\"0.8\","
				+ "\"Speed_Y_alarm_HH\":\"1\",\"Speed_Y_alarm_H\":\"0.8\",\"Speed_Z_alarm_HH\":\"1\",\"Speed_Z_alarm_H\":\"0.8\"}";
		
		JSONObject sensorConfig = JSONObject.parseObject(s);
		
		sensorConfig.put("Package_Type", 6);
		
		sensorConfig.put("Package_Number", 0);
		
		sensorConfig.put("BCC", 1);
		
		byte[] data = defaultResolve.unresolve("6",sensorConfig);
		
		System.out.println(HexUtils.bytesToHexString(data));
		
	}
	

	//@Test
	public void test()
	{
		try {
			testCharacterResolve();
			
			testWaveResolve();
			
			testUnResolve();
			
			testUnResolveSensorConfig();
			
			JSONObject content = new JSONObject();
			dispatchCenterService.handle("5",content);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void testother()
	{
    //获取读取BYTEARRAY类的操作类
		JFieldOpt jReadArray = optManager.getJFieldOpt(BaseDataType.BYTEARRAY);
		try {
			// jRead.read(null, null);
      //调用read方法
			jReadArray.read(null, null, 512);

		} catch (IOException e) {
			e.printStackTrace();
		}
   //获取读取SHORTARRAY类的操作类
		JFieldOpt jReadShortArray = optManager.getJFieldOpt(BaseDataType.SHORTARRAY);
		try {
			// jRead.read(null, null);
			jReadShortArray.read(null, null, 520);

		} catch (IOException e) {
			e.printStackTrace();
		}
    
   //根据返回C协议号ID获取模板类
		Template tm = templateContainer.getFCTemplate("1");
		System.out.println(tm.toString());
    
    //测试处理业务类
		JSONObject content = new JSONObject();
		dispatchCenterService.handle("5",content);
	}

}
```

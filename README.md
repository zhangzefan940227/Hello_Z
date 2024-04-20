# Hello_Z

## 无线adb方式

1. 打开手机设置，进入开发者选项，打开USB调试，并选择“ADB调试”；
2. 用USB连接手机
3. 打开命令输入 `adb tcpip 5555`

结果

```bash
adb tcpip 5555;restarting in TCP mode port: 5555
```

4. 查看手机ip

```bash
adb shell ifconfig wlan0
```
5. 命令输入 `adb connect <ip>` 将刚刚的ip地址填入

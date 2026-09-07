# 2026-09-07 Docker部署学习

## 今天要做什么

把已有的用户中心JAR放进Java17镜像，再启动一个本地容器。镜像构建成功不等于接口正常；还需要验证应用、MySQL及Redis连接。本次是学习部署，不是生产上线。

## 可重复执行的步骤

在仓库根目录打开PowerShell，执行：

```powershell
cd .\springboot-user-center\springboot-user-center
.\mvnw.cmd package '-DskipTests'
docker build -t user-center:study .
.\scripts\Start-StudyContainer.ps1
```

启动脚本在没有DB_PASSWORD环境变量时隐藏提示输入MySQL应用账号java_app的密码，不把密码写进脚本或镜像。此本地练习通过容器环境变量传入密码，Docker管理员仍能检查到该值，不等同于生产密钥管理。不要把包含环境变量的完整docker inspect输出或IDEA运行配置上传到GitHub。

前提：Docker Desktop、电脑上的MySQL和redis-learning正在运行；MySQL已建库和配置账号；8081未被占用。若PowerShell策略阻止脚本，先查看提示，不修改整台电脑的执行策略。容器重启可用docker start user-center-study，脚本不会覆盖已有同名容器。

访问 http://localhost:8081/swagger-ui/index.html 。不要用8080验证这个新容器；8080可能是IDEA启动的另一个应用。

## 必须理解的四件事

- Dockerfile是构建说明，镜像是模板，容器是实例。一份镜像可以创建多个容器。
- JAR是Java发布包，运行仍需要Java环境。基础镜像提供Java17，COPY把JAR放进镜像，ENTRYPOINT指定启动命令。
- 127.0.0.1:8081:8080表示只允许本机通过8081访问容器的8080。EXPOSE本身不会发布电脑端口。
- 容器里的localhost指容器自己。这里通过host.docker.internal连接Windows上的MySQL与Redis，并用Spring环境变量覆盖原有地址，不修改原本在IDEA运行的配置。

## 检查和停止

```powershell
docker logs --tail 60 user-center-study
Invoke-RestMethod http://localhost:8081/users
docker stop user-center-study
```

停止应用容器不等于停止电脑上的MySQL或redis-learning。不要删除数据库或Redis数据卷。

## 网络故障说明

首次构建在访问auth.docker.io获取公开镜像令牌时连接超时。若Dockerfile的FROM行被标记，仍应阅读后面的真实错误，不直接认为语法有误。重试docker pull eclipse-temurin:17-jre-jammy后下载完成，本轮未修改代理或替换为第三方镜像源。无法仅凭一次重试成功确定原先超时的具体原因。

## 本次实际结果

2026-09-07，助手按用户授权完成本地镜像构建和启动验证：

- 镜像user-center:study构建成功，容器user-center-study已运行，仅发布127.0.0.1:8081到容器8080。
- 日志确认Java17运行JAR、Tomcat在8080启动、Redisson已连接现有Redis。
- GET http://localhost:8081/users 返回HTTP200、业务code200和6名启用用户，验证了容器应用到现有MySQL的查询链路。
- 未新增/禁用用户、未重配MySQL账号、未修改现有Redis数据，也没有进行完整接口回归或性能测试。

## 学习进度边界

用户已完成Maven打包并保存Dockerfile与.dockerignore；镜像构建和容器访问由助手验证。用户额度不足，要求算法及八股一次整理收尾，不再连续提问。下文是讲解材料，不等于用户独立复写、复述或纠错已通过。

## 有效括号复习

沿用已有src/hot100/ValidParenthesesReview.java，不覆盖用户原实现。输入约定为非null、只包含六种括号字符；空串返回true。任意字符及null校验不在这道练习的输入约定中。

核心：最近遇到的左括号必须先匹配，所以使用后进先出的栈。push放入栈顶；pop取出并移除栈顶；isEmpty判断栈是否为空。

1. 左括号入栈。
2. 右括号出现时，若栈为空，直接false。
3. 弹出栈顶左括号，类型不匹配就false。
4. 扫描结束，只有栈为空才true。

例如([])：读入(后栈顶是(；读入[后栈顶变为[；读入]弹出[并匹配；读入)弹出(并匹配，最终为空，返回true。
([)]虽然数量相等，但读到)时栈顶是[，顺序错误，返回false。
输入(扫描结束后栈不为空，返回false；输入)一开始没有左括号，返回false。

按现有实现，时间复杂度O(n)，空间复杂度O(n)：每个字符至多入栈出栈一次，栈最坏保存n个字符，toCharArray还会创建长度n的字符数组。循环中出现push/pop并不代表O(n²)。

新增ValidParenthesesReviewTest.java包含10个断言用例，覆盖空串、单组、多组、嵌套、错配、交叉、缺左和缺右。助手运行测试与用户独立写出算法应分别记录。

## 旧八股 RDB与AOF有什么区别

面试简答：RDB按某个时刻保存数据快照；AOF记录用于恢复数据的写操作。RDB通常适合备份和较快加载，但故障时可能丢失最近一次快照后的修改；AOF按配置进行刷盘，通常能保留更新的数据，也有存储和维护开销。两者都不是自动保证零丢失。

纠错要点：

- AOF不是记录所有Redis命令，GET等读取操作不用于AOF恢复日志。
- RDB某一时刻的全量快照，不等于故障前的数据一定最新。
- appendfsync everysec通常可能丢失约一秒的写入，不是所有故障条件下的绝对上限；always更频繁同步，也不能承诺任意硬件故障下零丢失。
- 例题答案：RDB保存100后改为200，未生成新快照且AOF关闭，异常重启按旧快照恢复100；不是因为Redis只能保存100。

## 新八股 Docker容器与虚拟机有什么区别

面试简答：虚拟机通过虚拟化运行完整的客户操作系统及其内核；容器主要在操作系统层面隔离进程，多个容器共享承载它们的内核，因此通常更轻量、启动更快。容器并不是完全独立的操作系统，隔离边界也与虚拟机不同。

本机注意：Windows上运行Linux容器时，Docker Desktop通常通过Linux虚拟机环境提供Linux内核，并非Linux容器直接共用Windows内核。

关联概念：Dockerfile是构建说明，镜像是模板，容器是实例。同一镜像能创建多个容器，但主从关系不会自动建立。

## 今日直接记住的答案

- 127.0.0.1:9090:8080：浏览器访问localhost:9090，容器内部监听8080。
- 容器里的localhost是容器自己；host.docker.internal用于访问宿主机服务。
- 本例删除应用容器不会删除电脑上的MySQL和现有Redis数据；一般情况下，删除容器会丢失其可写层数据，重要数据应使用合适的持久化存储。
- `([])`的前两个字符是`(`和`[`，此时栈顶是`[`，后进先出。

## 收尾状态

今天的部署主线、算法示例和八股材料已整理。用户独立复写有效括号、解释端口映射、完整复述RDB/AOF及容器/虚拟机区别仍待后续短复查，不补记为已掌握。当天没有新增投递，也未自动提交或推送Git。

参考：[Docker构建上下文](https://docs.docker.com/build/concepts/context/)、[Docker Desktop容器访问宿主机](https://docs.docker.com/desktop/features/networking/networking-how-tos/)。

package com.luban;

import javafx.scene.effect.SepiaTone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@SpringBootApplication
@EnableEurekaServer//一个bean  Mark
//eureka 服务注册与发现通过网络发送心跳请求保持运行，那么和web开发类似，也需要请求响应，底层也会有mvc架构实现
//用的是jersey框架 基于filter
//EnableAutoConfiguration   EurekaServerAutoConfiguration  加载配置 jerseyFilterRegistration(相当于mvc初始化)
//那么然后再实现controller 在eureka叫做resource
// ApplicationResource 注册控制类 post请求addInstance
//对云服务的处理DataCenterInfo {
//    enum Name {Netflix, Amazon, MyOwn}
/**
 * Guice作为它的依赖注入DI基础组件，因此源码处你进场能看见@Singleton、@Inject
 * 依赖注入（DI），相信你首先想到的必然是Spring，Spring是Java技术全家桶，
 * 是Java EE开发的一站式解决方案和实际开发标准。、
 * 谷歌开源的轻量级依赖注入框架：Guice。
 * Guice在Java领域并不流行（相较于Spring），但因为它轻量级，
 * 另有些流行框架/库使用了它作为基础DI库，如：Druid、Elastic Search、Play2
 * 以及我们熟悉的携程开源的Apollo和Netflix的Eureka。
 * 一个纯粹的DI框架，主要用于减轻你对工厂的需求以及Java代码中对new的使用。
 * 默认是多例的（每次get/注入的都是不同的实例）
 * @Singleton  该注解只能标注在实现类上，不能标注在接口/抽象类上
 * 习成本颇高，学习曲线相对     陡峭
 * InstanceInfo以及LeaseInfo
 * InstanceInfo
 * Guice下注入该实例时由EurekaConfigBasedInstanceInfoProvider负责创建；
 * 但是在Spring Cloud下该实例由自己提供的InstanceInfoFactory完成创建的。
 * Spring Cloud下完全没有使用Guice来管理依赖，而是自己实现的管理，
 * 毕竟它也支持@Inject等标准注解嘛，接手过来比较容易
 * PeerAwareInstanceRegistry 责任链模式(每一个事情拆分由专门的来做)
 * syncUp 集群同步
 * register 服务注册 springcloud 由InstanceRegistry（发布一个监听
 * handleCancelation
 * publishEvent(new EurekaInstanceCanceledEvent(this, appName, id, isReplication));）实现
 * extends  PeerAwareInstanceRegistryImpl （集群同步
 * int leaseDuration = Lease.DEFAULT_DURATION_IN_SECS; 90 心跳续约 90秒 注册完之后才会集群同步，所以先执行
 * super.regist后再执行
 * replicateToPeers(Action.Register, info.getAppName(), info.getId(), info, null, isReplication);）
 * extends AbstractInstanceRegistry(服务注册) implements InstanceRegistry
 * AbstractInstanceRegistry 真正做服务注册  ReentrantReadWriteLock eadWriteLock.readLock()
 * ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> registry
 * （微服务名字  {微服务几集群 {集群中的每个实例 微服务ip  s实例对象 ）
 * 个微服务注册中心（可以是Eureka或者Consul或者你自己写的一个微服务注册中心）
 * 肯定会在内存中有一个服务注册表的概念。
 * 能不能尽量在写数据期间还保证可以继续读数据呢？大量加读锁的时候，
 * 会阻塞人家写数据加写锁过长时间，这种情况能否避免呢？
 * 可以的，采用多级缓存的机制(response缓存 用了google guava cache
 * https://www.cnblogs.com/exmyth/p/13489627.html 缓存机制)
 * 断点调试的时候走到 Map<String, Lease<InstanceInfo>> gMap = registry.get(registrant.getAppName());
 * 发现是有值得，是因为时间过长，eureka抛弃了本次请求，又开了一个线程注册，并发注册的原因
 * 客户端在尝试跟服务端注册的时候，会判断你当前这个注册很久（有个超时判断）没有成功的话，会中断，开启一个新的去重试
 * 对于有冲突的是根据存在的时间戳和当前注册的时间戳对比，哪个大用哪个。不管是否有冲突，都会new 一个新的
 *  Lease(租赁)<InstanceInfo> lease put到map中
 *  关于lease evictionTimestamp 服务剔除时间  registrationTimestamp 服务注册时间
 *  serviceUpTimestamp 回复正常工作时间
 *  lastUpdateTimestamp 最后操作时间
 *  Lease（租赁） renew(续约) cancle（）
 *
 *
 *  instanceresource 操作单个实例
 *   public Response renewLease( 心跳续约方法
 *   方法调用链和注册类似，也是InstanceRegistry renew  发布监听事件 PeerAwareInstanceRegistryImpl
 *   renew （调用父类AbstractInstanceRegistry 完成
 *   真实续约）再
 *   PeerAwareInstanceRegistryImpl  replicateToPeers集群同步
 *   客户端续约时候如果获取实例返回为空，那么客户端会将心跳续约改成服务注册 duration 设置的过期时间
 *   心跳续约最终调用lease的renew方法（lastUpdateTimestamp = System.currentTimeMillis() + duration;）
 *   举例说明：假设续约间隔是30s 最后操作时间是21:44:30 续约前过期时间是21:45:00
 *   执行一次心跳续约后时间是21:45:00 续约后过期时间是21:45:30
 *   心跳续只是更改lastUpdateTimestamp
 *：
 *   判断是否过期  additionalLeaseMs 集群同步时间差（源码注释上这段是有bug的 是因为在续约的时候计算
 *   lastUpdateTimestamp 这个值本应该是system时间，不应该加上duration
 *
 *   检查给定的{@link com.netflix.appinfo.InstanceInfo}的租约是否已到期。
 *   * *请注意，由于renew（）做错了事，并将lastUpdateTimestamp设置为+ duration多于*持续时间，
 *   因此有效期实际上是2 *持续时间。
 *   这是一个小错误，仅会影响* *由于可能会对现有使用产生广泛影响，因此*无法解决。
 *   ）
 *   public boolean isExpired(long additionalLeaseMs) {
 *         return (evictionTimestamp > 0 || System.currentTimeMillis() > (lastUpdateTimestamp + duration + additionalLeaseMs));
 *     }
 *
 *    PeerAwareInstanceRegistryImpl  replicateToPeers做集群同步
 *     public enum Action {
 *         Heartbeat, Register, Cancel, StatusUpdate, DeleteStatusOverride;
 *         这些操作需要同步集群
 * private void replicateToPeers(Action action, String appName, String id,
  *boolean isReplication){   isReplication 是否来自于集群同步
 * eureka集群同步实现是发一个同样的http请求给其他的server,比如eureka server1 server2 server3
 * ,user客户端(选取server1)发送量一个
 * register.do 给server1，注册成功后， 那么server1会向server2,server3 都发一个同样的请求
 * 怎么解决server1发给server2，server2发给server3的问题?
 * （if (peerEurekaNodes == Collections.EMPTY_LIST || isReplication) {
 *                 return;
 *             }）isisReplication防止死循环套娃调用
 * 判断完之后还会在判断是否是自己，会剔除自己同步，然后就是一路执行到jersey springcloud下的风险装的
 * resetTemplate
 */
public class AppEureka3000 {

    public static void main(String[] args) {
        Set set= new HashSet();
        SpringApplication.run(AppEureka3000.class);
    }
}

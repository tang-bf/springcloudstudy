package config;

import com.luban.rule.LuBanRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class PowerRuleConfig {
    @Bean
    public IRule iRule(){

        return  new LuBanRule();
    }
}
/**
 * IRule是什么? 它是Ribbon对于负载均衡策略实现的接口
 * spring autowired 一个集合
 * @LoadBalanced
 *        @Autowired(required = false)
 * 	private List<RestTemplate> restTemplates = Collections.emptyList();
 * 	RestTemplate 采用同步方式执行 HTTP 请求的类，底层使用 JDK 原生 HttpURLConnection API ，
 * 	或者 HttpComponents等其他 HTTP 客户端请求类库。
 * 	还有一处强调的就是 RestTemplate 提供模板化的方法让开发者能更简单地发送 HTTP 请求。
 * 	5.0 以上，官方标注了更推荐使用非阻塞的响应式 HTTP 请求处理类 org.springframework.web.reactive.client.WebClient
 * 	来替代 RestTemplate，尤其是对应异步请求处理的场景上 。
 * 	并且这个集合上有一个带有@Qualifier的注解才会全部加入到容器中
 * 	resttemplate.getinterceptors ,add的时候可以设置index，那么这样自定义的拦截器执行的时候就可以顺序可控
 * 	ribbonloadbalanceclient
 * 	ILoadBalancer loadBalancer = getLoadBalancer(serviceId);通过微服务名获取一个负载均衡器（没有就创建 第一次调用的时候才hi创建）
 * 	以微服务名字对应一个负载均衡器  不通的loadbalance又对应不同的springconTXT容器（spring父子容器的知识）
 * 	会创建springcontext annnottionconfigwebapplicationcontext 加入两个ribbon的自动配置类
 * 	ribbonautoconfiguration ribboneurekaautoconfiguration
 * 	然后设置// Uses Environment from parent as well as beans
 * 			context.setParent(this.parent);
 * 		再context.refresh();在这里面ribbon去eureka client拿注册信息
 * 	DynamicServerListLoadBalancer   updateListOfServers(); 1.定时配置刷新2.去eureka client拿注册信息3.ping eureka client 是否存活
 * 		Server server = getServer(loadBalancer);
 * 	流程： 拦截resttemplate  通过微服务名字拿到负载均衡器（n拿不到就创建） 负载均衡器去拿eureka注册信息（缓存： 30s一次读取eureka注册信息；
 * 	10s判断下你要调用的微服务是否存活ping ） 根据负载均衡算法调用
 *
 * 	baseloadbalance  pingtask 其实没有真正去ping，提供了接口可以让我们实现 IPing
 * 	dummyping  a完全不去平直接返回true
 * 	NIWSDiscoveryPing 根据注册中心的状态 up down 和eureka使用默认的
 * 	NoOpPing 不去ping
 * 	PingUrl 真正去ping
 * 	 // NOTE: IFF we were doing a real ping
 *                     // assuming we had a large set of servers (say 15)
 *                     // the logic below will run them serially
 *                     // hence taking 15 times the amount of time it takes
 *                     // to ping each server
 */

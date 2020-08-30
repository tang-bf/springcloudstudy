package config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class OrderRuleConfig {
    /**  没一台服务器都有这两个属性
     * A 5 B 1  C 1
     * 平滑加权轮训
     * currentweight
     * 初始值 0 0 0
     * weight                                                          7
     * currentweight +=weigth    max (currentWeight)   ip   max-sum(weight)=current
     * 5 1  1                           5               A   -2,1,1
     *3,2,2                             3               A   -4,2,2
     * 1,3,3                            3               B   1,-4,3
     * 6,-3,4                           6               A   -1,-3,4
     * 4,-2,5                           5               C   4,-2,-2
     * 9,-1,-1                          9               A   2,-1,-1
     * 7,0,0                            7               A   0,0,0
     * 5,1,1 ............
     *
     * @return
     */
    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}


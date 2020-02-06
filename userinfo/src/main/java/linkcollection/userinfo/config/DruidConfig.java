package linkcollection.userinfo.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

    /**
     * 打开Druid监控
     * @return ......
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet(){

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        // 白名单
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        // 黑名单
//        servletRegistrationBean.addInitParameter("deny","192.168.1.73");
        // 登录查看信息的账号和密码
        servletRegistrationBean.addInitParameter("loginUsername","root");
        servletRegistrationBean.addInitParameter("loginPassword","hlmdawS");
        return servletRegistrationBean;
    }

    /**
     * Druid的Web应用查看配置
     * @return ......
     */
    @Bean
    public FilterRegistrationBean druidWebViewServlet(){

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 白名单
        filterRegistrationBean.addInitParameter("allow","127.0.0.1");
        // 黑名单
//        filterRegistrationBean.addInitParameter("deny","192.168.1.73");
        // 登录查看信息的账号和密码
        filterRegistrationBean.addInitParameter("loginUsername","root");
        filterRegistrationBean.addInitParameter("loginPassword","hlmdawS");
        return filterRegistrationBean;
    }
}

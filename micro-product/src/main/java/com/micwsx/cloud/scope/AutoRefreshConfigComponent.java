package com.micwsx.cloud.scope;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author Michael
 * @create 8/20/2020 4:08 PM
 * bean实例化的时候，获取配置中心数据，监听zookeeper结点更新事件,这种自动更新属性有问题。
 * 1.bean销毁后将eurekaClient的bean也销毁了。服务无法再次注册到eureka上。
 * 2.zookeerer结点加载时一直会收到添加节点事件。
 */
//@Component
public class AutoRefreshConfigComponent implements ApplicationContextAware {

    private static final String ZOOKEEPER_HOST = "192.168.1.111:2181";
    private static final String PATH = "/mic";
    private ConfigurableApplicationContext context;

    private CuratorFramework client;
    private static final String zkPropertyName = "zookeeperSource";

    @Value("${zookeeper.config.enable:false}")
    private boolean enable;

    @Autowired
    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }

    @PostConstruct
    public void init() {
        if (enable) {
            client = CuratorFrameworkFactory.builder()
                    .connectString(ZOOKEEPER_HOST)
                    .sessionTimeoutMs(5000)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();
            client.start();
            try {
                Stat stat = client.checkExists().forPath(PATH);
                if (stat == null) {
                    // 添加配置节点 mic/name,mic/city
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                            .forPath(PATH, "zookeeper config".getBytes());
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                            .forPath(PATH + "/xx.name", "Michael".getBytes());
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                            .forPath(PATH + "/xx.city", "Shanghai".getBytes());
                    TimeUnit.SECONDS.sleep(1);
                }

                // 读取配置节点
                addPropertyEnvironment();

                // 添加节点监控
                monitorNodes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加配置属性对象或更新配置属性值
     */
    private void addPropertyEnvironment() {
        OriginTrackedMapPropertySource originTrackedMapPropertySource = null;
        // 尝试从environment中获取配置属性对象
        MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            if (zkPropertyName.equals(propertySource.getName())) {
                originTrackedMapPropertySource = (OriginTrackedMapPropertySource) propertySources.get(zkPropertyName);
            }
        }
        // 如果没有加载，则向environment对象中添加配置属性对象
        if (originTrackedMapPropertySource == null) {
            originTrackedMapPropertySource = new OriginTrackedMapPropertySource(zkPropertyName, new ConcurrentHashMap<>());
            propertySources.addLast(originTrackedMapPropertySource);
        }
        // 获取最新或覆盖旧配置值。
        ConcurrentHashMap zkmap = (ConcurrentHashMap) originTrackedMapPropertySource.getSource();
        try {
            List<String> nodes = client.getChildren().forPath(PATH);
            for (String node : nodes) {
                String nodeValue = new String(client.getData().forPath(PATH + "/" + node));
                zkmap.put(node, nodeValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void monitorNodes() {
        try {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(client, PATH, false);
            pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            pathChildrenCache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> {

                switch (pathChildrenCacheEvent.getType()) {
                    case CHILD_ADDED:
                        System.out.println("添加了节点");
                        addEnv(pathChildrenCacheEvent.getData(),client);
                        break;
                    case CHILD_UPDATED:
                        System.out.println("更新了节点");
                       addEnv(pathChildrenCacheEvent.getData(),client);
                        break;
                    case CHILD_REMOVED:
                        System.out.println("删除了节点");
                        delEnv(pathChildrenCacheEvent.getData());
                        break;
                    default:
                        break;
                }
                // 更新spring容器中的所有scope名为refresh的bean
                refreshBeans();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新scope为refresh所有bean
     */
    private void refreshBeans() {
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = context.getBeanFactory().getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getScope().equals(RefreshScope.SCOPE_NAME)) {
                // 销毁bean,这里会调用自定义scope的remove方法
                context.getBeanFactory().destroyScopedBean(beanDefinitionName);
                // 再实例化bean
                context.getBean(beanDefinitionName);
            }
        }
    }

    private void delEnv(ChildData childData) {
        ChildData next = childData;
        String childpath = next.getPath();
        MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            if (zkPropertyName.equals(propertySource.getName())) {
                OriginTrackedMapPropertySource ps = (OriginTrackedMapPropertySource) propertySource;
                ConcurrentHashMap chm = (ConcurrentHashMap) ps.getSource();
                chm.remove(childpath.substring(PATH.length() + 1));
            }
        }
    }

    private void addEnv(ChildData childData, CuratorFramework client) {
        ChildData next = childData;
        String childpath = next.getPath();
        String data = null;
        try {
            data = new String(client.getData().forPath(childpath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            if (zkPropertyName.equals(propertySource.getName())) {
                OriginTrackedMapPropertySource ps = (OriginTrackedMapPropertySource) propertySource;
                ConcurrentHashMap chm = (ConcurrentHashMap) ps.getSource();
                chm.put(childpath.substring(PATH.length() + 1), data);
            }
        }
    }
}

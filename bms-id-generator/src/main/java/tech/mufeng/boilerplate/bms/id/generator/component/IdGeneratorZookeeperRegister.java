package tech.mufeng.boilerplate.bms.id.generator.component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tech.mufeng.boilerplate.bms.common.utils.IpUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
@Slf4j
public class IdGeneratorZookeeperRegister {
    private static final int MAX_MACHINE_ID = 31;

    @Getter
    private int workerId = 0;
    private String currentNodePath;
    private PathChildrenCache pathChildrenCache;

    @Value("${spring.application.name}")
    private String prefixPath;

    @Resource
    private CuratorFramework zkClient;

    @PostConstruct
    public void init() throws Exception {
        String rootPath = ZKPaths.makePath(ZKPaths.PATH_SEPARATOR, prefixPath);
        String nodePath = ZKPaths.makePath(rootPath, IpUtil.getIp() + "-");
        log.info("nodePath: {}", nodePath);
        currentNodePath = createNode(nodePath, workerId);
        // 设置节点监听器
        pathChildrenCache = new PathChildrenCache(zkClient, rootPath, true);
        pathChildrenCache.start();
        pathChildrenCache.getListenable().addListener((client, event) -> {
            switch ( event.getType() ) {
                case CHILD_ADDED: {
                    log.info("[Watcher]-新增节点: {}", ZKPaths.getNodeFromPath(event.getData().getPath()));
                    checkOtherNodeData(event.getData());
                    break;
                }

                case CHILD_UPDATED: {
                    log.info("[Watcher]-更新节点: {}", ZKPaths.getNodeFromPath(event.getData().getPath()));
                    checkOtherNodeData(event.getData());
                    break;
                }

                case CHILD_REMOVED: {
                    log.info("[Watcher]-删除节点: {}", ZKPaths.getNodeFromPath(event.getData().getPath()));
                    checkOtherNodeData(event.getData());
                    break;
                }
                // ZK挂掉
                case CONNECTION_SUSPENDED: break;

                // 重新启动ZK
                case CONNECTION_RECONNECTED: break;

                // ZK挂掉一段时间后
                case CONNECTION_LOST: break;

                // 初始化
                case INITIALIZED: break;

                default: break;
            }
        });
        log.info("工作节点注册成功: {}, 解析workerId为: {}", currentNodePath, this.workerId);
    }

    @PreDestroy
    public void destroy() {
        CloseableUtils.closeQuietly(pathChildrenCache);
        log.info("close pathChildrenCache success");
        CloseableUtils.closeQuietly(zkClient);
        log.info("close zkClient success");
    }

    private String createNode(String nodePath, int workerId) throws Exception {
        return zkClient.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(nodePath, String.valueOf(workerId).getBytes());
    }

    private void setWorkerIdByNodePath(String nodePath, int workerId) throws Exception{
        zkClient.setData().forPath(nodePath, String.valueOf(workerId).getBytes());
        log.info("设置节点={};workerId={}", ZKPaths.getNodeFromPath(nodePath), workerId);
    }

    private void checkOtherNodeData(ChildData childData) throws Exception{
        if (childData == null) return;
        String path = childData.getPath();
        if (currentNodePath.equals(path)) return;
        String otherWorkerId = new String(childData.getData());
        if (StringUtils.isEmpty(otherWorkerId)) return;
        String myWorkId = String.valueOf(workerId);
        if (otherWorkerId.equals(myWorkId)) {
            log.info("workerId冲突, 准备重新设置...");
            workerId++;
            if (workerId > MAX_MACHINE_ID) {
                throw new Exception(String.format("超过最大WorkerID值: %s", MAX_MACHINE_ID));
            }
            setWorkerIdByNodePath(currentNodePath, workerId);
        }
    }
}

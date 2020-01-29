package tech.mufeng.boilerplate.bms.id.generator.component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.mufeng.boilerplate.bms.common.utils.IpUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Component
@Slf4j
public class IdGeneratorZookeeperRegister {
    private static final int MAX_MACHINE_ID = 31;

    private static final String separator = File.separator;
    private String rootPath;
    @Getter
    private Long workerId = 0L;

    @Value("${spring.application.name}")
    private String prefixPath;

    @Resource
    private CuratorFramework zkClient;

    @PostConstruct
    public void init() {
        rootPath = getRootPath();
        String fullNodePath = getFullNodePath();
        log.info("fullNodePath: {}", fullNodePath);
        // 注册当前节点
        String currentNode = createNode(fullNodePath);
        // 所有子节点
        List<String> childNodes = getChildNodes();
        log.info("childNodes: {}", childNodes);
        // 子节点的workerId
        List<Integer> childWorkerIds = getSortChildrenWorkerId(childNodes);
        log.info("childWorkerIds: {}", childWorkerIds);
        int checkId = 0;
        while (true) {
            if (checkId > MAX_MACHINE_ID) {
                throw new RuntimeException(String.format("机器数超过限制: %d", (MAX_MACHINE_ID + 1)));
            }
            if (childWorkerIds.contains(checkId)) {
                checkId++;
            }else {
                break;
            }
        }
        // 设置当前的workerId为checkId
        setWorkerIdByNodePath(currentNode, checkId);
        workerId = (long) checkId;
        log.info("工作节点注册成功: {}, 解析workerId为: {}", currentNode, workerId);
    }

    private String getFullNodePath() {
        return getRootPath() + getNodePath();
    }

    private String getRootPath() {
        return separator + prefixPath;
    }

    private String getNodePath() {
        String ip = IpUtil.getIp();
        return separator + ip + "-";
    }

    private String createNode(String zNodePath) {
        try {
            return zkClient.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(zNodePath, "-1".getBytes());
        }catch (Exception e) {
            log.error("创建节点错误", e);
            throw new RuntimeException(e.getCause());
        }
    }

//    private long getWorkerIdAsLong() {
//        if (currentNode == null) return 0;
//        long n = nodeToIdAsLong(currentNode);
//        long modId = n % (MAX_MACHINE_ID + 1);
//        if (n > MAX_MACHINE_ID)
//        return modId;
//    }
//
//    private static long nodeToIdAsLong(String node) {
//        Matcher matcher = PATTERN_NODE_NAME.matcher(node);
//        if (matcher.matches()) {
//            return Long.parseLong(matcher.group(1));
//        }
//        return 0;
//    }

    private List<String> getChildNodes() {
        String rootPath = getRootPath();
        try {
            return zkClient.getChildren().forPath(rootPath);
        }catch (Exception e) {
            throw new RuntimeException("获取节点失败", e.getCause());
        }
    }

    private List<Integer> getSortChildrenWorkerId(List<String> childNodes) {
        if (childNodes.isEmpty()) return Collections.emptyList();
        Set<Integer> workerIdSet = new HashSet<>();
        childNodes.forEach(childNode -> {
            int workerId = getWorkerIdByNodePath(rootPath + separator + childNode);
            workerIdSet.add(workerId);
        });
        List<Integer> workerIds = new ArrayList<>(workerIdSet);
        Collections.sort(workerIds);
        return workerIds;
    }

    private int getWorkerIdByNodePath(String nodePath) {
        try {
            byte[] workerIdRaw = zkClient.getData().forPath(nodePath);
            String workerIdStr = new String(workerIdRaw);
            return Integer.parseInt(workerIdStr);
        }catch (Exception e) {
            throw new RuntimeException("获取节点数据失败", e.getCause());
        }
    }

    private void setWorkerIdByNodePath(String nodePath, int workerId) {
        try {
            Object value = zkClient.setData().forPath(nodePath, String.valueOf(workerId).getBytes());
            log.info("update workerId by nodePath: {}; value: {}", nodePath, value);
        }catch (Exception e) {
            throw new RuntimeException("设置节点数据失败", e.getCause());
        }
    }
}

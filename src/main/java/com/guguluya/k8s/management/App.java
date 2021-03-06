package com.guguluya.k8s.management;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.guguluya.k8s.management.models.DemoDeployment;
import com.guguluya.k8s.management.models.DemoNamespace;
import com.guguluya.k8s.management.models.DemoNode;
import com.guguluya.k8s.management.models.DemoPod;

import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1PodList;

public class App {

    public static void main(String[] args) throws Exception {

        // 初始化连接
        DemoUtil.initConfiguration();

//        excute();
        DemoNode.updateNodeLabel("10.144.213.190", "testKey", "testValue");
    }

    public static void excute() throws Exception {

        // Deployment
        System.out.println("\n****** Deployment");
        // 获取 Deployment 一览
        V1DeploymentList deploymentList = DemoDeployment.listDeployments();
        DemoDeployment.printDeployment(deploymentList);
//        // 通过 yaml 文件创建 Deployment
//        DemoDeployment.createDeploymentByYaml();
//        // 通过 modal 创建 Deployment
//        DemoDeployment.createDeploymentByModal();

        // Namespace
        System.out.println("\n****** Namespace");
        // 获取 NameSpace 一览
        List<String> namespaces = DemoNamespace.listNameSpace();
        DemoNamespace.printNameSpaces(namespaces);

        // Node
        System.out.println("\n****** Node");
        // 获取 Node 一览(根据集群的 Pod 信息)
        List<String> listNodes = DemoNode.listNodes();
        DemoNode.printNodes(listNodes);
        // 获取所有的 Node 和 Pod 的 mapping
        Map<String, Set<String>> allNodeAndPodMapping = DemoNode.getAllNodeAndPodMapping();
        DemoNode.printAllNodeAndPodMapping(allNodeAndPodMapping);
        // 获取指定 Node 的 Info
        String nodeInfo = DemoNode.getNodeInfo("http://localhost:8001", "127.0.0.1");
        DemoNode.printNodeInfo(nodeInfo);
        // 为指定的 Node 更新/添加 label
        DemoNode.updateNodeLabel("nodeName", "testKey", "testValue");

        // Pod
        System.out.println("\n****** Pod");
        // 获取所有 NameSpace 的 Pod 一览
        V1PodList podList = DemoPod.listPod();
        DemoPod.printPods(podList);
        // 获取指定 Node 上的 Pod 一览
        List<String> podInNodeList = DemoPod.listPodsInNode("http://localhost:8001", "127.0.0.1");
        System.out.println("指定 Node 对应的 Pod 一览:");
        podInNodeList.forEach(item -> System.out.println("\t" + item));
        // 获取指定 Pod 的日志
        DemoPod.readPodLog();
    }
}

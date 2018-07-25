package activiti1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class test1 {

	//1.��ʼ�����ݿ�
	@Test
	public void testactiviti1() {
		ProcessEngine buildProcessEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
												.buildProcessEngine();
		System.out.println(buildProcessEngine);
		
	}
	
	//2.����.bpmn����ͼ
	
	//3.��������(����bpmn�ļ�)
	@Test
	public void deploy() {
		//3.1��ȡ��������
		ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
		//3.2��ȡ�ֿ�ʵ��
		Deployment deploy = defaultProcessEngine.getRepositoryService()
							.createDeployment()
							.addClasspathResource("diagram/MyProcess.bpmn")
							.addClasspathResource("diagram/MyProcess.png")
							.deploy();
		System.out.println(deploy.getId() + "     "+deploy.getName());
		
	}
	//��ȡ�����������
			ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
	
	//4.��������
	@Test
	public void startProcess() {
		
		//��������  key����Ĭ�ϰ������°汾��������ʵ��
		ProcessInstance pi = defaultProcessEngine.getRuntimeService().startProcessInstanceByKey("myProcess");
		System.out.println("pid"+pi.getId() + ",activitiId:"+pi.getActivityId());
	}
	
	@Test
	public void queryMyTask() {
		 List<Task> list = defaultProcessEngine.getTaskService().createTaskQuery().list();
		 System.out.println(list.size());
		 if(list!=null && list.size()>0) {
			 for(Task t : list) {
				 System.out.println("getAssignee:"+t.getAssignee());
				 System.out.println("getId:"+ t.getId());
				 System.out.println("getName"+t.getName());
			 }
		 }
		 
		
	}
	
	
	@Test
	public void completeMyPersonalTask() {
		String taskId = "1906";
		defaultProcessEngine.getTaskService().complete(taskId);
		System.out.println("�������id:"+taskId);
	}
	
	@Test
	public void ViewImage() throws IOException {
		String dep_id = "401";
		List<String> deploymentResourceNames = defaultProcessEngine.getRepositoryService()
		.getDeploymentResourceNames(dep_id);
		String imagesname = null;
		for(String name : deploymentResourceNames) {
			System.out.println("reName:"+name);
			if(name.indexOf(".png")>0) {
				imagesname = name;
			}
		}
		
		System.out.println(imagesname);
		if(imagesname != null ) {
			File file = new File("C:\\Users\\tangz\\Desktop\\store\\"+imagesname);
			InputStream resourceAsStream = defaultProcessEngine.getRepositoryService().getResourceAsStream(dep_id, imagesname);
			FileUtils.copyInputStreamToFile(resourceAsStream, file);
		}
	}
	
}

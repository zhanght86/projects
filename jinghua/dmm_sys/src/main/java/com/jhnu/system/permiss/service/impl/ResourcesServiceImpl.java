package com.jhnu.system.permiss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.framework.entity.NodeTree;
import com.jhnu.framework.entity.NodeZtree;
import com.jhnu.framework.entity.ResultBean;
import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.handle.HasOneException;
import com.jhnu.framework.exception.param.EmptyParamException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.code.entity.Code;
import com.jhnu.system.common.code.service.CodeService;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.dao.ResourcesDao;
import com.jhnu.system.permiss.entity.Resources;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.service.PermssionService;
import com.jhnu.system.permiss.service.ResourcesService;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.common.TreeUtil;

@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService {

	@Autowired
    private ResourcesDao resourcesDao;
	@Autowired
	private PermssionService permssionService;
	@Autowired
	private CodeService coedService;
	@Autowired
	private UserService userService;
	
	private static final Logger logger = Logger.getLogger(ResourcesServiceImpl.class);

	@Override
	@Transactional
	public Resources createResource(Resources resource) throws AddException,ParamException{
		Resources rs = null;
		if(resource != null){
			if(StringUtils.isEmpty(resource.getName_())){
				logger.error("====资源名称不能为空====");
				throw new EmptyParamException("资源名称不能为空");
			}
			/*else  if(!resource.getResource_type_code().equalsIgnoreCase("03")){
				Resources re = new Resources();
				re.setName_(resource.getName_());
				re.setSysGroup_(resource.getSysGroup_());
				List<Resources> rl = resourcesDao.getResourcesByThis(resource);
				if(rl!=null && rl.size()>0){
					logger.error("====资源名称不能重复====");
					throw new HasOneException("资源名称不能重复");
				}
			}*/
			if(StringUtils.isEmpty(resource.getDescription())){
				throw new EmptyParamException("资源描述不能为空");
			}
			if(StringUtils.isEmpty(resource.getSysGroup_())){
				throw new EmptyParamException("系统类型不能为空");
			}
			if(resource.getPid()==null){
				throw new EmptyParamException("父节点不能为空");
			}else{
				if(resource.getPid()==-1){
					resource.setLevel_(1);
				}else{
					resource.setLevel_(findById(resource.getPid()).getLevel_()+1);
				}
				//resource.setPath_("1");
				//TODO 资源全息码没有做
			}
		//	if(StringUtils.isEmpty(resource.getLevel_())){
		//		throw new EmptyParamException("层次不能为空");
		//	}
		//	if(StringUtils.isEmpty(resource.getPath_())){
		//		throw new EmptyParamException("全息码不能为空");
		//	}
			if(StringUtils.isEmpty(resource.getIstrue())){
				throw new EmptyParamException("是否可用不能为空");
			}
			if(StringUtils.isEmpty(resource.getOrder_())){
				throw new EmptyParamException("排序不能为空");
			}
			if(StringUtils.isEmpty(resource.getKeyWord())){
				throw new EmptyParamException("关键字不能为空");
			}
			if(StringUtils.isEmpty(resource.getResource_type_code())){
				throw new EmptyParamException("资源类型不能为空");
			}else{
				if("02".equals(resource.getResource_type_code())){
					if(StringUtils.isEmpty(resource.getUrl_())){
						logger.error("====URL不能为空====");
						throw new EmptyParamException("URL不能为空");
					}else{
						Resources re = new Resources();
						re.setUrl_(resource.getUrl_());
						re.setSysGroup_(resource.getSysGroup_());
						List<Resources> rl = resourcesDao.getResourcesByThis(re);
						if(rl!=null && rl.size()>0){
							logger.error("====资源URL不能重复====");
							throw new HasOneException("资源URL不能重复");
						}
					}
				}
			}
			if(StringUtils.isEmpty(resource.getShiro_tag())){
				throw new EmptyParamException("shiro资源标识符不能为空");
			}else{
				Resources re = new Resources();
				re.setShiro_tag(resource.getShiro_tag());
				List<Resources> rl = resourcesDao.getResourcesByThis(re);
				if(rl!=null && rl.size()>0){
					throw new HasOneException("shiro资源标识符不能重复");
				}
				/*if(com.jhnu.util.common.StringUtils.hasOtherText(resource.getShiro_tag())){
					throw new ParamException("shiro资源标识符不能包含特殊字符");
				}*/
			}
			logger.info("====创建资源开始保存====");
			rs = resourcesDao.createResource(resource);
			logger.info("====创建资源结束保存====");
		}
		return rs;
	}

	@Override
	public List<Resources> getResourcesByThis(Resources resources) {
		return resourcesDao.getResourcesByThis(resources);
	}

	@Override
	public List<Resources> getAllResources() {
		return resourcesDao.getAllResources();
	}
	
	@Override
	@Transactional
	public void deleteResources(Long resourcesId) throws ParamException{
		Resources resource = new Resources();
		resource.setPid(resourcesId);
		List<Resources> resourcesList = resourcesDao.getResourcesByThis(resource);
		if(resourcesList != null && resourcesList.size()>0){
			throw new ParamException("该节点包含子节点，须先删除子节点");
		}else{
			logger.info("====开始删除资源====");
			resourcesDao.deleteResources(resourcesId);
			logger.info("====删除资源结束====");
		}
	}

	@Override
	@Transactional
	public void updateResources(Resources resource) throws AddException,ParamException{
		if(resource != null){
			if(StringUtils.isEmpty(resource.getId())){
				throw new EmptyParamException("要修改的资源ID不能为空");
			}
			if(StringUtils.isEmpty(resource.getName_())){
				throw new EmptyParamException("修改的资源名称不能为空");
			}
		/*	else if(!resource.getResource_type_code().equalsIgnoreCase("03")){
				Resources re = new Resources();
				re.setName_(resource.getName_());
				re.setSysGroup_(resource.getSysGroup_());
				List<Resources> rl = resourcesDao.getResourcesByThis(re);
				if(rl!=null && rl.size()>0){
					for(Resources r : rl){
						if(!resource.getId().equals(r.getId())){
							throw new ParamException("修改的资源名称不能重复");
						}
					}
				}
			}*/
			if(StringUtils.isEmpty(resource.getDescription())){
				throw new EmptyParamException("修改的资源描述不能为空");
			}
			if(StringUtils.isEmpty(resource.getIstrue())){
				throw new EmptyParamException("修改的是否可用不能为空");
			}
			if(StringUtils.isEmpty(resource.getOrder_())){
				throw new EmptyParamException("修改的排序不能为空");
			}
			if(StringUtils.isEmpty(resource.getKeyWord())){
			    throw new EmptyParamException("修改的关键字不能为空");
			}
			if(StringUtils.isEmpty(resource.getResource_type_code())){
				throw new EmptyParamException("修改的资源类型不能为空");
			}else{
				if("02".equals(resource.getResource_type_code())){
					if(StringUtils.isEmpty(resource.getUrl_())){
						throw new EmptyParamException("修改的URL不能为空");
					}else{
						Resources re = new Resources();
						re.setUrl_(resource.getUrl_());
						re.setSysGroup_(resource.getSysGroup_());
						List<Resources> rl = resourcesDao.getResourcesByThis(re);
						if(rl!=null && rl.size()>0){
							for(Resources r : rl){
								if(!resource.getId().equals(r.getId())){
									throw new ParamException("修改的URL不能重复");
								}
							}
						}
					}
				}
			}
			if(StringUtils.isEmpty(resource.getShiro_tag())){
				throw new EmptyParamException("修改的shiro资源标识符不能为空");
			}else{
				Resources re = new Resources();
				re.setShiro_tag(resource.getShiro_tag());
				List<Resources> rl = resourcesDao.getResourcesByThis(re);
				if(rl!=null && rl.size()>0){
					for(Resources r : rl){
						if(!resource.getId().equals(r.getId())){
							throw new ParamException("修改的shiro资源标识符不能重复");
						}
					}
				}
			}
			logger.info("====开始修改资源====");
			resourcesDao.updateResources(resource);
			logger.info("====修改资源结束====");
			
			//修改shiro通配符
			permssionService.updateRolePermssion(resource);
			permssionService.updateUserPermssion(resource);
		}
		
	}

	@Override
	public Page getResourcesPageByThis(int currentPage, int numPerPage,
			Resources resources) {
		return resourcesDao.getResourcesPageByThis(currentPage, numPerPage, resources);
	}

	@Override
	public ResultBean createResourceAjax(Resources resource) {
		ResultBean rb=new ResultBean();
		rb.setTrue(true);
		try {
			rb.setObject(createResource(resource));
		} catch (AddException e) {
			rb.setName(e.getMessage());
			rb.setTrue(false);
		} catch (ParamException e) {
			rb.setName(e.getMessage());
			rb.setTrue(false);
		}
		return rb;
	}

	@Override
	public Resources findById(Long id) {
		return resourcesDao.findById(id);
	}

	@Override
	public ResultBean updateResourcesAjax(Resources resources) {
		ResultBean rb = new ResultBean();
		rb.setTrue(true);
		try {
			updateResources(resources);
		} catch (AddException e) {
			rb.setName(e.getMessage());
			rb.setTrue(false);
		} catch (ParamException e) {
			rb.setName(e.getMessage());
			rb.setTrue(false);
		}
		return rb;
	}

	@Override
	public ResultBean deleteResourcesAjax(Long resourcesId) {
		ResultBean rb = new ResultBean();
		rb.setTrue(true);
		try {
			deleteResources(resourcesId);
		} catch (ParamException e) {
			rb.setTrue(false);
			rb.setName(e.getMessage());
		}
		return rb;
	}

	@Override
	public String getAllResNodeJson() {
		List<Resources> resourcesList = getAllResources();
		List<NodeZtree> nodes=new ArrayList<NodeZtree>();
		for(Resources res:resourcesList){
			NodeZtree node=new NodeZtree();
			node.setId(res.getId().toString());
			node.setpId(res.getPid().toString());
			node.setName(res.getName_());
			nodes.add(node);
		}
		return JSON.toJSONString(nodes);
	}

	@Override
	public Resources getMainPageByRole(Role role) {
		Resources res=null;
		if(role!=null){
			role.setIsmain(1);
			res= resourcesDao.getMainPageByRole(role);
		}
		return res;
	}

	@Override
	public Resources getMainPageByUser(User user) {
		return resourcesDao.getMainPageByUser(user);
	}

	@Override
	public Object getResourceAngularTree(Resources resources) {
		List<NodeAngularTree> treeList=resourcesDao.getResourceAngularTree(resources);
		Map<String,NodeAngularTree> deptMap=new HashMap<String,NodeAngularTree>();
		NodeAngularTree root=null;
		for(NodeAngularTree tree:treeList){
			deptMap.put(tree.getId(), tree);
		}
		for(NodeAngularTree tree:treeList){
			if("-1".equals(tree.getPid())){
				root=tree;
			}else{
				if(deptMap.get(tree.getPid())!=null)
				deptMap.get(tree.getPid()).getChildren().add(tree);
			}
			
		}
		if(root!=null){
			return root;
		}
		return null;
	}
	@Override
	public Object getPermssionsById(String flag, String id) {

		List<NodeAngularTree> treeList0= resourcesDao.getPermssionsById(flag,id);
		Map<String,Object> map0=new HashMap<String,Object>();
		for(NodeAngularTree tree:treeList0){
			map0.put(tree.getId(), null);
		}
		List<NodeAngularTree> treeList=resourcesDao.getResourceAngularTree(null);
		Map<String,NodeAngularTree> deptMap=new HashMap<String,NodeAngularTree>();
		NodeAngularTree root=null;
		for(NodeAngularTree tree:treeList){
			deptMap.put(tree.getId(), tree);
		}
		for(NodeAngularTree tree:treeList){
			if(map0.containsKey(tree.getId())){
				tree.setChecked(true);
			}
			if("-1".equals(tree.getPid())){
				root=tree;
			}else{
				if(deptMap.get(tree.getPid())!=null)
				deptMap.get(tree.getPid()).getChildren().add(tree);
			}
			
		}
		if(root!=null){
			return root;
		}
		return null;
	}
	@Override
	public Map<String, Object> getPermssionsByFlagAndId(String flag, String id) {
		List<NodeTree> treeList0= resourcesDao.getPermssionsJLById(flag,id);
		List<NodeTree> treeList=resourcesDao.getResourceTree();
		Map<String,String> deptMap=new HashMap<String,String>();
		for(NodeTree tree:treeList0){
			deptMap.put(tree.getId(), "");
		}
		for(NodeTree tree:treeList){
			if(deptMap.get(tree.getId())!=null){
				tree.setChecked(true);
			}
		}
		return TreeUtil.getTreeMap(treeList);
	}
	@Override
	public List<Resources> getMenusByUserName(String username,String sys){
		String roolRole=userService.getUserRootRole(username);
		if("admin".equalsIgnoreCase(roolRole)){
			return resourcesDao.getAllResources(sys);
		}
		return resourcesDao.getMenusByUserName(username,sys);
	}
	
	@Override
	public List<String> getAllPermssionByUserName(String username) {
		return resourcesDao.getAllPermssionByUserName(username);
	}

	@Override
	public boolean hasPermssion(String username, String shiroTag) {
    	if("admin".equalsIgnoreCase(userService.getUserRootRole(username))){
    		List<Resources> res=resourcesDao.getAllResources();
    		String star=shiroTag.substring(0,shiroTag.lastIndexOf(":"));
    		for (int i = 0; i < res.size(); i++) {
    			if(res.get(i).getShiro_tag().equals(star)&&res.get(i).getIstrue()==1){
    				return true;
    			}
			}
    		return false;
    	}else{
    		if(resourcesDao.hasPermssion(username, shiroTag).size()>0){
    			return true;
    		}
    	}
    	return false;
    }
	@Override
	public List<Code> getResType() {
		Code code=new Code();
		code.setIstrue(1);
		code.setCode_type("RESOURCE_TYPE");
		List<Code> codes=coedService.getCode(code);
		return codes;
	}

	@Override
	public List<Resources> getMenusByUserNameShiroTag(String username,String shiroTag) {
		if(StringUtils.hasText(shiroTag)){
			shiroTag=shiroTag.substring(0,shiroTag.lastIndexOf(":"));
			
			String roolRole=userService.getUserRootRole(username);
			if("admin".equalsIgnoreCase(roolRole)){
				Resources r=new Resources();
				r.setShiro_tag(shiroTag);
				return resourcesDao.getResourcesByThis(r);
			}
			
			return resourcesDao.getMenusByUserNameShiroTag(username, shiroTag);
		}
		return null;
	}
	@Override
	public boolean hasMenuByUserName(String username,String basePath, String sys){
		String roolRole=userService.getUserRootRole(username);
		if("admin".equalsIgnoreCase(roolRole)){
    		List<Resources> res=resourcesDao.getAllResources(basePath);
    		for (int i = 0; i < res.size(); i++) {
    			if(sys.toLowerCase().indexOf(res.get(i).getUrl_().toLowerCase())>=0){
    				return true;
    			}
			}
    		return false;
    	}
		return resourcesDao.hasMenuByUserName(username, basePath, sys);
	}
}